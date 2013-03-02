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
package net.ostis.sc.memory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Dmitry Lazurkin
 */
public class SCGenerator {

	private class Attribute {
		public SCAddr addr = null;
		public SCType type = null;
		public SCSegment seg = null;

		public Attribute(SCAddr elementAttr, SCType typeAttr, SCSegment segment) {
			this.addr = elementAttr;
			this.type = typeAttr;
			this.seg = segment;
		}
	}

	private class GeneratorState {
		public List<SCAddr> elements = new LinkedList<SCAddr>();
		public List<Attribute> attrs = new LinkedList<Attribute>();
		public List<SCAddr> prev_els = new LinkedList<SCAddr>();
		public SCAddr set = null;
		public SCSegment sent_seg = null;
		public SCType sent_arc_type = null;
		public SCAddr sys_sign = null;
		public List<SCAddr> system_set = new LinkedList<SCAddr>();
		public SCSegment system_segment = null;
		public SCType system_arc_type = null;
		public List<SCAddr> tmp = new LinkedList<SCAddr>();
		public boolean system_context = false;

		public GeneratorState() {
		}

		public GeneratorState(boolean is_system) {
			this.system_context = is_system;
		}
	}

	private SCSession session = null;
	private SCSegment active_segment = null;

	private SCType element_type = SCType.NODE_CONST;
	private SCType arc_type = SCType.ARC_CONST_POS;
	private SCType set_arc_type = SCType.ARC_CONST_POS;
	private SCType system_arc_type = SCType.ARC_CONST_POS;
	private LinkedList<GeneratorState> state = new LinkedList<GeneratorState>();

	public SCGenerator(SCSession session, SCSegment activeSegment) {
		this.session = session;
		this.active_segment = activeSegment;
		state.add(new GeneratorState());
	}

	public void erase() {
		leaveAll();
		finish();
		state = null;
	}

	public void setContent(SCAddr el, SCContent cont) {
		session.setContent(el, cont);
	}

	public void setIdtf(SCAddr el, String idtf) {
		session.setIdtf(el, idtf);
	}

	public void changeType(SCAddr el, SCType type) {
		session.changeType(el, type);
	}

	private SCAddr generateInternal(String idtf, SCType type, SCSegment seg) {
		if (type == null)
			type = element_type;

		if (seg == null)
			seg = active_segment;

		SCAddr el = session.createElement(seg, type);

		if (StringUtils.isEmpty(idtf))
			return el;

		setIdtf(el, idtf);

		return el;
	}

	private SCAddr generateFindInternal(String idtf, SCType type, SCSegment seg) {
		if (seg == null)
			seg = active_segment;

		SCAddr el = null;
		if (StringUtils.isNotEmpty(idtf))
			el = session.findByIdtf(idtf, seg);

		if (el == null)
			return generateInternal(idtf, type, seg);

		if (type != null && type != session.getType(el))
			return null;

		return el;
	}

	private void pushState(boolean is_system) {
		state.add(new GeneratorState(is_system));
	}

	private SCAddr internalGenArc(SCAddr from, SCAddr to, SCType arc_type, SCSegment seg) {
		GeneratorState st = state.peekLast();
		Iterator<Attribute> it = st.attrs.iterator();
		SCAddr arc = session.gen3_f_a_f(from, seg, arc_type, to);
		if (arc == null)
			return null;

		st.tmp.add(arc);

		while (it.hasNext()) {
			Attribute attr = it.next();
			SCAddr arc4 = session.gen3_f_a_f(attr.addr, attr.seg, attr.type, arc);
			if (arc4 == null) {
				session.eraseElement(arc);
				return null;
			}
			st.tmp.add(arc4);
		}

		return arc;
	}

	public SCAddr element(SCAddr el) {
		if (el == null)
			return element("");
		return __element(el);
	}

	public SCAddr element(String idtf) {
		return element(idtf, null, null);
	}

	private SCAddr __element(SCAddr el) {
		GeneratorState st = state.peekLast();
		st.tmp.add(el);

		if (!st.prev_els.isEmpty()) {
			Iterator<SCAddr> it = st.prev_els.iterator();
			while (it.hasNext())
				internalGenArc(it.next(), el, st.sent_arc_type, st.sent_seg);
		}

		if (st.set != null) {
			SCAddr rv = internalGenArc(st.set, el, set_arc_type, active_segment);
			if (rv == null)
				return null;
		}

		if (st.sys_sign != null) {
			Iterator<SCAddr> it = st.tmp.iterator();
			while (it.hasNext())
				st.system_set.add(it.next());
		}

		st.tmp.clear();
		st.attrs.clear();
		st.elements.add(el);

		return el;
	}

	public SCAddr element(String idtf, SCType type, SCSegment segment) {
		SCAddr el = generateFindInternal(idtf, type, segment);
		if (el == null)
			return null;
		return __element(el);
	}

	public SCAddr attr(SCAddr el) {
		return attr(el, arc_type);
	}

	public SCAddr attr(SCAddr el, SCType arcType) {
		GeneratorState st = state.peekLast();
		Attribute str = new Attribute(el, arcType, active_segment);
		st.attrs.add(str);
		if (st.sys_sign != null)
			st.system_set.add(el);
		return el;
	}

	public SCAddr attr(String idtf, SCType type, SCSegment segment) {
		SCAddr el = generateFindInternal(idtf, type, segment);
		if (el == null)
			return null;
		return attr(el);
	}

	public void arc() {
		GeneratorState st = state.peekLast();
		st.prev_els = st.elements;
		st.elements.clear();
		st.sent_arc_type = arc_type;
		st.sent_seg = active_segment;
	}

	public void finish() {
		GeneratorState st = state.peekLast();
		st.prev_els.clear();
		st.elements.clear();
	}

	public SCAddr enterSet(SCAddr a_set) {
		element(a_set);
		pushState(false);
		GeneratorState old_st = state.get(state.size() - 2);
		GeneratorState st = state.peekLast();
		st.sys_sign = old_st.sys_sign;
		st.system_set = old_st.system_set;
		old_st.system_set.clear();
		st.set = a_set;
		return a_set;
	}

	public SCAddr enterSet(String idtf, SCType type, SCSegment segment) {
		SCAddr el = generateFindInternal(idtf, type, segment);
		if (el == null)
			return null;
		return enterSet(el);
	}

	public SCAddr leaveSet() {
		GeneratorState old_st = state.get(state.size() - 2);
		GeneratorState st = state.peekLast();
		if (st.sys_sign != null)
			old_st.system_set = st.system_set;
		state.removeLast();
		return st.set;
	}

	public SCAddr enterSystem(SCAddr sys_sign) {
		element(sys_sign);
		pushState(true);
		GeneratorState st = state.peekLast();
		st.sys_sign = sys_sign;
		st.system_segment = active_segment;
		st.system_arc_type = system_arc_type;
		return sys_sign;
	}

	public SCAddr enterSystem(String idtf, SCType type, SCSegment segment) {
		SCAddr el = generateFindInternal(idtf, type, segment);
		if (el == null)
			return null;
		return enterSystem(el);
	}

	public SCAddr leaveSystem() {
		GeneratorState st = state.peekLast();
		GeneratorState old_st = state.get(state.size() - 2);

		Iterator<SCAddr> it = st.system_set.iterator();
		SCType at = st.system_arc_type;
		SCSegment seg = st.system_segment;

		if (old_st.sys_sign != null) {
			while (it.hasNext()) {
				SCAddr itVal = it.next();
				SCAddr arc = session.gen3_f_a_f(st.sys_sign, seg, at, itVal);
				old_st.system_set.add(arc);
				old_st.system_set.add(itVal);
			}
		} else {
			while (it.hasNext()) {
				SCAddr itVal = it.next();
				session.gen3_f_a_f(st.sys_sign, seg, at, itVal);
			}
		}

		state.removeLast();
		return st.sys_sign;
	}

	public SCAddr genArc(SCAddr from, SCAddr to, String idtf, SCType type, SCSegment segment) {
		if (type == null)
			type = arc_type;

		if (segment == null)
			segment = active_segment;

		SCAddr el = session.findByIdtf(idtf, segment);
		if (el != null) {
			if (type != session.getType(el))
				return null;
		} else {
			el = session.createElement(segment, type);
		}

		session.setBegin(el, from);
		session.setEnd(el, to);
		return element(el);
	}

	public void leaveAll() {
		Iterator<GeneratorState> it = state.descendingIterator();
		while (it.hasNext()) {
			if (it.next().system_context) {
				leaveSystem();
			} else {
				leaveSet();
			}
		}
	}

	public SCSegment setActiveSegment(SCSegment seg) {
		SCSegment prev = active_segment;
		active_segment = seg;
		return prev;
	}

	public SCType getElementType() {
		return element_type;
	}

	public void setElementType(SCType elementType) {
		this.element_type = elementType;
	}

	public SCSession getSession() {
		return session;
	}

}
