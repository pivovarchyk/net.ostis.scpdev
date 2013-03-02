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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.ostis.sc.memory.SCAddr;
import net.ostis.sc.memory.SCConstraint;
import net.ostis.sc.memory.SCConstraintInfo;
import net.ostis.sc.memory.SCConstraintInfo.ParamType;
import net.ostis.sc.memory.SCIterator;

/**
 * @author Dmitry Lazurkin
 */
class RGPIterator extends RGPObject implements SCIterator {

	private RGPSession session;
	private SCConstraint constraint;

	private Integer[] needValues;
	private boolean outputIsNeedValues = false;

	private SCAddr[][] cache;
	private int curIndex;
	private boolean over;
	private boolean erased = false;

	RGPIterator(Integer id) {
		super(id);
	}

	void setSession(RGPSession session) {
		this.session = session;
	}

	void setConstraint(SCConstraint constraint) {
		this.constraint = constraint;
		if (needValues == null) {
			SCConstraintInfo info = constraint.getInfo();

			int paramsCount = info.getParamsCount();
			List<Integer> needValuesTemp = new ArrayList<Integer>(paramsCount);
			for (int i = 0; i < paramsCount; ++i) {
				if (info.getParamType(i) == SCConstraintInfo.ParamType.SC_TYPE)
					needValuesTemp.add(i);
			}
			needValues = (Integer[]) needValuesTemp.toArray(new Integer[needValuesTemp.size()]);
		}
	}

	void setNeedValues(Integer[] needValues) {
		this.needValues = needValues;
		outputIsNeedValues = true;
	}

	void fetchState() throws IOException, RGPProtocolException {
		int curArgsCount = session.getCurArgsCount();

		if (curArgsCount > 1) {
			int blocksCount = (curArgsCount - 1) / needValues.length;
			cache = new SCAddr[blocksCount][];
			for (int blockIndex = 0; blockIndex < blocksCount; ++blockIndex) {
				cache[blockIndex] = new SCAddr[needValues.length];
				for (int valueIndex = 0; valueIndex < needValues.length; ++valueIndex)
					cache[blockIndex][valueIndex] = session.getStream().readAddr();
			}
			curIndex = 0;
		}

		over = session.getStream().readBoolean();
	}

	@Override
	public boolean isOver() {
		if (erased)
			throw new RuntimeException();

		if (cache == null || curIndex == cache.length) {
			return over;
		} else {
			return false;
		}
	}

	@Override
	public void next() {
		if (!isOver()) {
			++curIndex;

			if (curIndex == cache.length && !over) {
				try {
					synchronized (session.getStream()) {
						session.writeCommandStart(RGPCommandId.REQ_NEXT_ITERATOR, 1 + needValues.length);
						session.getStream().write(this);
						for (int i = 0; i < needValues.length; ++i)
							session.getStream().write(needValues[i]);
						session.readReturn();
						session.assertRetvalOk();
						fetchState();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

	private int findNeedIndex(int passedIndex) {
		for (int i = 0; i < needValues.length; ++i) {
			if (needValues[i] == passedIndex)
				return i;
		}
		return -1;
	}

	@Override
	public SCAddr getValue(int index) {
		if (isOver())
			return null;

		if (outputIsNeedValues) {
			if (index < needValues.length) {
				return cache[curIndex][index];
			}
		} else {
			SCConstraintInfo info = constraint.getInfo();
			ParamType paramType = info.getParamType(index);
			if (paramType == ParamType.SC_TYPE) {
				return cache[curIndex][findNeedIndex(index)];
			} else if (paramType == ParamType.SC_ADDR) {
				return (SCAddr) constraint.get(index);
			} else {
				throw new RuntimeException();
			}
		}

		return null;
	}

	@Override
	public void erase() {
		synchronized (session.getStream()) {
			try {
				session.writeCommandStart(RGPCommandId.REQ_ERASE_ITERATOR, 1);
				session.getStream().write(this);
				session.readReturn();
				session.assertRetvalOk();
				session.getObjectsRegistry().unregister(this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			erased = true;
		}
	}
}
