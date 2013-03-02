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

/**
 * Utility for working with tuplies in sc-memory.
 *
 * @author Dmitry Lazurkin
 */
public class TupUtils {

	public static SCAddr get(SCSession session, SCAddr tuple, SCAddr attr) {
		SCIterator iter = session.createIterator(new SCConstraintBase(STDConstraints.CONSTR_5_f_a_a_a_f, tuple,
				SCType.ARC_CONST_POS, SCType.EMPTY, SCType.ARC_CONST_POS, attr));
		try {
			SCAddr value = null;
			if (!iter.isOver())
				value = iter.getValue(2);

			return value;
		} finally {
			if (iter != null)
				iter.erase();
		}
	}

	public static SCAddr[] unpack(SCSession session, SCAddr tuple, SCAddr... attrs) {
		SCIterator iter = session.createIterator(new SCConstraintBase(STDConstraints.CONSTR_5_f_a_a_a_a, tuple,
				SCType.ARC_CONST_POS, SCType.EMPTY, SCType.ARC_CONST_POS, SCType.EMPTY));

		try {
			SCAddr[] result = new SCAddr[attrs.length];

			for (int count = 0; !iter.isOver(); iter.next()) {
				SCAddr comp = iter.getValue(2);
				SCAddr attr = iter.getValue(4);

				for (int i = 0; i < attrs.length; ++i) {
					if (attrs[i].equals(attr)) {
						result[i] = comp;
						++count;
						break;
					}
				}

				if (count == result.length)
					break;
			}

			return result;
		} finally {
			iter.erase();
		}
	}

}
