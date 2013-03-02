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

import java.util.Collection;
import java.util.Map;

/**
 * Utility for working with sets in sc-memory.
 *
 * @author Dmitry Lazurkin
 */
public class SetUtils {

	public static void fill(SCSession session, SCAddr set, Collection<SCAddr> c) {
		SCIterator iter = session.createIterator(new SCConstraintBase(STDConstraints.CONSTR_3_f_a_a, set, SCType.EMPTY,
				SCType.EMPTY));
		try {
			for (; !iter.isOver(); iter.next())
				c.add(iter.getValue(2));
		} finally {
			iter.erase();
		}
	}

	public static void memberOf(SCSession session, SCAddr el, Collection<SCAddr> sets) {
		SCIterator iter = session.createIterator(new SCConstraintBase(STDConstraints.CONSTR_3_a_a_f, SCType.EMPTY, SCType.EMPTY,
				el));
		try {
			for (; !iter.isOver(); iter.next())
				sets.add(iter.getValue(0));
		} finally {
			iter.erase();
		}
	}

	public static void fill(SCSession session, SCAddr set, Map<SCAddr, String> map) {
		SCIterator iter = session.createIterator(new SCConstraintBase(STDConstraints.CONSTR_3_f_a_a, set, SCType.EMPTY,
				SCType.EMPTY));
		try {
			for (; !iter.isOver(); iter.next()) {
				SCAddr el = iter.getValue(2);
				if (el != null)
					map.put(el, session.getIdtf(el));
			}
		} finally {
			iter.erase();
		}
	}

}
