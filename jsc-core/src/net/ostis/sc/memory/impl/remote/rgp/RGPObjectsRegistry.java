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

import java.util.Map;
import java.util.TreeMap;

/**
 * Mapping for RGP-objects:
 * <li>SC-addresses
 * <li>SC-segments
 * <li>SC-iterators
 * <li>SC-activities
 * <li>SC-waits
 *
 * @author Dmitry Lazurkin
 */
public class RGPObjectsRegistry {

	private final RGPSession session;

	private Map<Integer, RGPAddr> addresses = new TreeMap<Integer, RGPAddr>();
	private Map<Integer, RGPSegment> segments = new TreeMap<Integer, RGPSegment>();
	private Map<Integer, RGPIterator> iterators = new TreeMap<Integer, RGPIterator>();
	private Map<Integer, RGPActivity> activities = new TreeMap<Integer, RGPActivity>();
	private Map<Integer, RGPWait> waits = new TreeMap<Integer, RGPWait>();


	public RGPObjectsRegistry(RGPSession session) {
		this.session = session;
	}

	//
	// SC-addresses
	//

	/**
	 * Return SC-address by RGP-id.
	 */
	public synchronized RGPAddr getAddr(int id) {
		RGPAddr o = (RGPAddr) addresses.get(id);
		if (o == null && id != 0) {
			o = new RGPAddr(id);
			o.setSession(session);
			addresses.put(id, o);
		}
		return o;
	}

	/**
	 * Unregister sc-address.
	 */
	public synchronized void unregister(RGPAddr o) {
		addresses.remove(o.getId());
	}

	//
	// SC-segments
	//

	/**
	 * SC-segment by RGP-id.
	 */
	public synchronized RGPSegment getSegment(int id) {
		RGPSegment o = (RGPSegment) segments.get(id);
		if (o == null && id != 0) {
			o = new RGPSegment(id);
			o.setSession(session);
			segments.put(id, o);
		}
		return o;
	}

	/**
	 * Unregister sc-segment.
	 */
	public synchronized void unregister(RGPSegment o) {
		segments.remove(o.getId());
	}

	//
	// SC-iterators
	//

	/**
	 * SC-iterator by RGP-id.
	 */
	public synchronized RGPIterator getIterator(int id) {
		RGPIterator o = (RGPIterator) iterators.get(id);
		if (o == null && id != 0) {
			o = new RGPIterator(id);
			iterators.put(id, o);
		}
		return o;
	}

	/**
	 * Unregister sc-iterator.
	 */
	public synchronized void unregister(RGPIterator o) {
		iterators.remove(o.getId());
	}

	//
	// SC-activities
	//

	/**
	 * SC-activity by RGP-id.
	 */
	public synchronized RGPActivity getActivity(int id) {
		RGPActivity o = (RGPActivity) activities.get(id);
		if (o == null && id != 0) {
			o = new RGPActivity(id);
			activities.put(id, o);
		}
		return o;
	}

	/**
	 * Unregister sc-activity.
	 */
	public synchronized void unregister(RGPActivity o) {
		activities.remove(o.getId());
	}

	//
	// SC-waits
	//

	/**
	 * SC-wait by RGP-id.
	 */
	public synchronized RGPWait getWait(int id) {
		RGPWait o = (RGPWait) waits.get(id);
		if (o == null && id != 0) {
			o = new RGPWait(id);
			waits.put(id, o);
		}
		return o;
	}

	/**
	 * Unregister sc-wait.
	 */
	public synchronized void unregister(RGPWait o) {
		waits.remove(o.getId());
	}

}
