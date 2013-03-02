/*
 * This source file is part of OSTIS (Open Semantic Technology for Intelligent
 * Systems) For the latest info, see http://www.ostis.net
 *
 * Copyright (c) 2011 OSTIS
 *
 * OSTIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * OSTIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with OSTIS. If not, see <http://www.gnu.org/licenses/>.
 */
package net.ostis.sc.memory.impl.remote.rgp;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.ostis.sc.memory.SCActivity;
import net.ostis.sc.memory.SCAddr;
import net.ostis.sc.memory.SCConstraint;
import net.ostis.sc.memory.SCConstraintBase;
import net.ostis.sc.memory.SCConstraintInfo;
import net.ostis.sc.memory.SCContent;
import net.ostis.sc.memory.SCIterator;
import net.ostis.sc.memory.SCMemory;
import net.ostis.sc.memory.SCSegment;
import net.ostis.sc.memory.SCSession;
import net.ostis.sc.memory.SCType;
import net.ostis.sc.memory.SCWait;
import net.ostis.sc.memory.STDConstraints;

import org.apache.commons.lang3.Pair;

/**
 * @author Dmitry Lazurkin
 */
public class RGPSession implements SCSession, Runnable {

	private RGPMemory memory;

	private final Socket controlSocket;

	private final Socket eventSocket;

	private Thread eventThread;

	private RGPProtocolStream controlStream;

	private RGPProtocolStream eventStream;

	private RGPObjectsRegistry objectsRegistry;

	private Map<String, RGPSegment> openedSegments = new TreeMap<String, RGPSegment>();

	private Map<SCWait, RGPWait> wait2proxyWait = new HashMap<SCWait, RGPWait>();
	private Map<SCActivity, RGPActivity> activity2proxyActivity = new HashMap<SCActivity, RGPActivity>();

	private ThreadLocal<Integer> curArgsCount = new ThreadLocal<Integer>();
	private ThreadLocal<Integer> curRetval = new ThreadLocal<Integer>();

	boolean closed = false;

	public RGPSession(RGPMemory memory, Socket controlSocket, Socket eventSocket) throws IOException,
			RGPProtocolException {
		this.memory = memory;
		this.controlSocket = controlSocket;
		this.eventSocket = eventSocket;
		this.objectsRegistry = new RGPObjectsRegistry(this);
		this.controlStream = new RGPProtocolStream(controlSocket.getInputStream(), controlSocket.getOutputStream(),
				objectsRegistry);
		this.eventStream = new RGPProtocolStream(eventSocket.getInputStream(), eventSocket.getOutputStream(),
				objectsRegistry);

		eventThread = new Thread(this);
		eventThread.setName("RGP Events Listener");
		eventThread.setDaemon(true);
		eventThread.start();

		login();
	}

	@Override
	public void run() {
		try {
			while (!eventThread.isInterrupted()) {
				RGPCommandId replyId = eventStream.readCommandId();

				if (replyId == RGPCommandId.REQ_ACTIVATE_WAIT) {
					int argsCount = eventStream.readArgsCount();

					RGPWait wait = eventStream.readWait();
					SCWait.Type waitType = eventStream.readWaitType();

					--argsCount;
					--argsCount;

					Object[] params = new Object[argsCount];
					for (int i = 0; i < argsCount; ++i) {
						RGPArgumentType type = eventStream.readArgType();
						switch (type) {
							case SC_ADDR:
								params[i] = eventStream.readAddrImpl();
								break;
							default:
								break;
						}
					}

					boolean result = wait.activate(waitType, params);
					eventStream.writeCommandId(RGPCommandId.REP_RETURN);
					eventStream.writeArgsCount(2);
					eventStream.writeRetval(0);
					eventStream.write(result);
				} else if (replyId == RGPCommandId.REQ_ACTIVATE) {
					@SuppressWarnings("unused")
					int argsCount = eventStream.readArgsCount();

					RGPActivity activity = eventStream.readActivity();

					--argsCount;

					RGPAddr _this = eventStream.readAddr();
					RGPAddr prm1 = eventStream.readAddr();
					RGPAddr prm2 = eventStream.readAddr();
					RGPAddr prm3 = eventStream.readAddr();
					activity.activate(this, _this, prm1, prm2, prm3);
				}

				if (closed)
					break;

				eventStream.writeCommandId(RGPCommandId.REP_RETURN);
				eventStream.writeArgsCount(1);
				eventStream.writeRetval(0);
			}
		} catch (SocketException e) {
			return;
		} catch (EOFException e) {
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int getCurArgsCount() {
		return curArgsCount.get();
	}

	int getCurRetval() {
		return curRetval.get();
	}

	RGPObjectsRegistry getObjectsRegistry() {
		return objectsRegistry;
	}

	RGPProtocolStream getStream() {
		if (Thread.currentThread().equals(eventThread)) {
			return eventStream;
		} else {
			return controlStream;
		}
	}

	void fillNewSegment(RGPSegment segment) {
		RGPProtocolStream stream = getStream();
		synchronized (stream) {
			synchronized (segment) {
				try {
					writeCommandStart(RGPCommandId.REQ_GET_SEG_INFO, 1);
					stream.write(segment);

					readReturn();
					assertRetvalOk();

					segment.setSign(stream.readAddr());
					segment.setURI(stream.readString());
					segment.setDead(stream.readBoolean());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	void fillNewAddr(RGPAddr addr) {
		RGPProtocolStream stream = getStream();
		synchronized (stream) {
			synchronized (addr) {
				try {
					writeCommandStart(RGPCommandId.REQ_GET_EL_INFO, 1);
					stream.write(addr);

					readReturn();
					assertRetvalOk();

					addr.setSegment(stream.readSegment());
					addr.setDead(stream.readBoolean());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	void readReturn() throws IOException, RGPProtocolException {
		synchronized (getStream()) {
			RGPProtocolStream stream = getStream();

			RGPCommandId replyId = stream.readCommandId();

			if (replyId != RGPCommandId.REP_RETURN)
				throw new RuntimeException("Expectes " + RGPCommandId.REP_RETURN + ", but recieved " + replyId);

			curArgsCount.set(stream.readArgsCount());

			if (curArgsCount.get() < 1)
				throw new RuntimeException(RGPCommandId.REP_RETURN + " with arguments count 0");

			curArgsCount.set(curArgsCount.get() - 1);

			curRetval.set(stream.readRetval());
		}
	}

	void assertRetvalOk() {
		if (curRetval.get() != 0)
			throw new RuntimeException("Retval is " + curRetval.get());
	}

	void writeCommandStart(RGPCommandId id, int argsCount) throws IOException {
		synchronized (getStream()) {
			RGPProtocolStream stream = getStream();
			stream.writeCommandId(id);
			stream.writeArgsCount(argsCount);
		}
	}

	private void login() throws IOException, RGPProtocolException {
		writeCommandStart(RGPCommandId.REQ_LOGIN, 1);
		getStream().write(true);
		readReturn();
		assertRetvalOk();
	}

	@Override
	public SCMemory getMemory() {
		return memory;
	}

	@Override
	public void close() {
		synchronized (getStream()) {
			try {
				writeCommandStart(RGPCommandId.REQ_CLOSE, 0);

				readReturn();
				assertRetvalOk();

				eventThread.interrupt();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					controlSocket.close();
					eventSocket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				closed = true;
			}
		}
	}

	@Override
	public SCSegment createSegment(String uri) {
		synchronized (getStream()) {
			RGPSegment segment = openedSegments.get(uri);
			if (segment == null) {
				try {
					RGPProtocolStream stream = getStream();
					writeCommandStart(RGPCommandId.REQ_CREATE_SEGMENT, 1);
					stream.write(uri.toString());

					readReturn();
					assertRetvalOk();

					segment = stream.readSegment();
					RGPAddr sign = stream.readAddr();
					if (segment.isNew()) {
						segment.setSign(sign);
						segment.setURI(uri);
						openedSegments.put(uri, segment);
					}

					return segment;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return segment;
		}
	}

	@Override
	public void closeSegment(SCSegment segment) {
		synchronized (getStream()) {
		}
	}

	@Override
	public SCSegment openSegment(String uri) {
		synchronized (getStream()) {
			RGPSegment segment = openedSegments.get(uri);
			if (segment == null) {
				try {
					RGPProtocolStream stream = getStream();
					writeCommandStart(RGPCommandId.REQ_OPEN_SEGMENT, 1);
					stream.write(uri.toString());

					readReturn();
					assertRetvalOk();

					segment = stream.readSegment();
					RGPAddr sign = stream.readAddr();
					if (segment.isNew()) {
						segment.setSign(sign);
						segment.setURI(uri);
						openedSegments.put(uri, segment);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return segment;
		}
	}

	@Override
	public SCAddr findByIdtf(String idtf, SCSegment segment) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_FIND_BY_IDTF, 2);
				stream.write(idtf);
				stream.write((RGPSegment) segment);

				readReturn();

				RGPAddr addr = null;
				if (curArgsCount.get() > 0) {
					addr = stream.readAddr();
					if (addr.isNew())
						addr.setSegment(segment);
				}

				return addr;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public String getIdtf(SCAddr element) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_GET_IDTF, 1);
				stream.write((RGPAddr) element);

				readReturn();
				assertRetvalOk();

				return stream.readString();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public void setIdtf(SCAddr addr, String idtf) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_SET_IDTF, 2);
				stream.write((RGPAddr) addr);
				stream.write(idtf);

				readReturn();
				assertRetvalOk();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isSegmentOpened(String uri) {
		return openedSegments.containsKey(uri);
	}

	@Override
	public synchronized void unlink(String uri) {
		// TODO Auto-generated method stub

	}

	@Override
	public SCAddr createElement(SCSegment segment, SCType type) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_CREATE_EL, 2);
				stream.write((RGPSegment) segment);
				stream.write(type);

				readReturn();

				RGPAddr addr = null;
				if (curArgsCount.get() > 0) {
					addr = stream.readAddr();
					if (addr.isNew())
						addr.setSegment(segment);
				}

				return addr;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public SCAddr gen3_f_a_f(SCAddr e1, SCSegment seg2, SCType t2, SCAddr e3) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_GEN3_F_A_F, 4);
				stream.write((RGPAddr) e1);
				stream.write((RGPSegment) seg2);
				stream.write(t2);
				stream.write((RGPAddr) e3);

				readReturn();

				RGPAddr addr = null;
				if (curArgsCount.get() > 0) {
					addr = stream.readAddr();
					if (addr.isNew())
						addr.setSegment(seg2);
				}

				return addr;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public Pair<SCAddr, SCAddr> gen5_f_a_f_a_f(SCAddr e1, SCSegment seg2, SCType t2, SCAddr e3, SCSegment seg4,
			SCType t4, SCAddr e5) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_GEN3_F_A_F, 4);
				stream.write((RGPAddr) e1);
				stream.write((RGPSegment) seg2);
				stream.write(t2);
				stream.write((RGPAddr) e3);
				stream.write((RGPSegment) seg4);
				stream.write(t4);
				stream.write((RGPAddr) e5);

				readReturn();

				RGPAddr addr2 = null;
				RGPAddr addr4 = null;
				if (curArgsCount.get() > 0) {
					addr2 = stream.readAddr();
					if (addr2.isNew())
						addr2.setSegment(seg2);

					addr4 = stream.readAddr();
					if (addr4.isNew())
						addr4.setSegment(seg4);
				}

				return new Pair<SCAddr, SCAddr>(addr2, addr4);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public void eraseElement(SCAddr element) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_ERASE_EL, 1);
				stream.write((RGPAddr) element);

				readReturn();
				assertRetvalOk(); // TODO: throw exception
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mergeElement(SCAddr from, SCAddr to) {
		synchronized (getStream()) {
		}
	}

	@Override
	public SCAddr getBegin(SCAddr arc) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_GET_EL_BEGIN, 1);
				stream.write((RGPAddr) arc);

				readReturn();
				assertRetvalOk();

				RGPAddr addr = null;
				if (curArgsCount.get() > 0)
					addr = stream.readAddr();

				return addr;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public void setBegin(SCAddr arc, SCAddr from) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_SET_EL_BEGIN, 2);
				stream.write((RGPAddr) arc);
				stream.write((RGPAddr) from);

				readReturn();
				assertRetvalOk();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public SCAddr getEnd(SCAddr arc) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_GET_EL_END, 1);
				stream.write((RGPAddr) arc);

				readReturn();
				assertRetvalOk();

				RGPAddr addr = null;
				if (curArgsCount.get() > 0)
					addr = stream.readAddr();

				return addr;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public void setEnd(SCAddr arc, SCAddr to) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_SET_EL_END, 2);
				stream.write((RGPAddr) arc);
				stream.write((RGPAddr) to);

				readReturn();
				assertRetvalOk();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public SCType getType(SCAddr el) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_GET_EL_TYPE, 1);
				stream.write((RGPAddr) el);

				readReturn();
				assertRetvalOk();

				return stream.readType();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public void changeType(SCAddr element, SCType type) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_CHANGE_EL_TYPE, 2);
				stream.write((RGPAddr) element);
				stream.write(type);

				readReturn();
				assertRetvalOk();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public SCContent getContent(SCAddr element) {
		synchronized (getStream()) {
			SCContent content = null;

			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_GET_EL_CONTENT, 1);
				stream.write((RGPAddr) element);

				readReturn();
				assertRetvalOk();
				content = stream.readContent();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return content;
		}
	}

	@Override
	public void setContent(SCAddr element, SCContent content) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_SET_EL_CONTENT, 2);
				stream.write((RGPAddr) element);
				stream.write(content);

				readReturn();
				assertRetvalOk();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public SCIterator createIterator(SCConstraint constraint) {
		synchronized (getStream()) {
			RGPIterator iterator = null;

			try {
				SCConstraintInfo info = constraint.getInfo();
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_CREATE_ITERATOR, 1 + info.getParamsCount());
				stream.write(constraint.getInfo());

				int paramsCount = info.getParamsCount();
				for (int i = 0; i < paramsCount; ++i) {
					Object param = constraint.get(i);
					SCConstraintInfo.ParamType t = info.getParamType(i);

					if (t == SCConstraintInfo.ParamType.SC_ADDR) {
						stream.write((RGPAddr) param);
					} else if (t == SCConstraintInfo.ParamType.SC_TYPE) {
						stream.write((SCType) param);
					} else if (t == SCConstraintInfo.ParamType.SC_SEGMENT) {
						stream.write((RGPSegment) param);
					} else if (t == SCConstraintInfo.ParamType.SC_BOOLEAN) {
						stream.write((Boolean) param);
					}
				}

				readReturn();
				assertRetvalOk();

				iterator = stream.readIterator();
				curArgsCount.set(curArgsCount.get() - 1);

				if (info.getName().equals(STDConstraints.CONSTR_ON_SEGMENT.getName()))
					iterator.setNeedValues(new Integer[] {
						0
					});
				iterator.setConstraint(constraint);
				iterator.setSession(this);
				iterator.fetchState();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return iterator;
		}
	}

	@Override
	public void eraseIterator(SCIterator iterator) {
		RGPIterator it = (RGPIterator) iterator;
		it.erase();
	}

	@Override
	public boolean searchOneshort(SCConstraintBase constraint, Object... arguments) {
		synchronized (getStream()) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	@Override
	public void attachWait(SCWait wait, SCWait.Type type, Object... params) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_ATTACH_WAIT, 1 + params.length);
				stream.write(type);

				for (Object param : params) {
					if (param instanceof SCAddr) {
						stream.write((RGPAddr) param);
					} else if (param instanceof SCSegment) {
						stream.write((RGPSegment) param);
					} else if (param instanceof SCType) {
						stream.write((SCType) param);
					}
				}

				readReturn();
				assertRetvalOk();
				RGPWait proxyWait = stream.readWait();
				proxyWait.setWait(wait);
				wait2proxyWait.put(wait, proxyWait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void detachWait(SCWait wait) {
		synchronized (getStream()) {
			RGPWait proxyWait = wait2proxyWait.get(wait);
			if (proxyWait == null)
				throw new RuntimeException("Detach for unregistred wait");

			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_DETACH_WAIT, 1);
				stream.write(proxyWait);

				readReturn();
				assertRetvalOk();

				wait2proxyWait.remove(wait);
				objectsRegistry.unregister(proxyWait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void activate(SCAddr element, SCAddr... arguments) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_ACTIVATE, 4);
				stream.write((RGPAddr) element);

				for (SCAddr addr : arguments)
					stream.write((RGPAddr) addr);

				readReturn();
				assertRetvalOk();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void reimplement(SCAddr element, SCActivity activity) {
		synchronized (getStream()) {
			try {
				RGPProtocolStream stream = getStream();
				writeCommandStart(RGPCommandId.REQ_REIMPLEMENT, 1);
				stream.write((RGPAddr) element);

				readReturn();
				assertRetvalOk();
				RGPActivity proxyActivity = stream.readActivity();
				proxyActivity.setActivity(activity);
				activity2proxyActivity.put(activity, proxyActivity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
