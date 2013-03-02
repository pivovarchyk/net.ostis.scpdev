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
 * Utility for working with relations in sc-memory.
 *
 * @author Dmitry Lazurkin
 */
public class RelUtils {

	public static SCAddr getBinOrd(SCSession session, SCAddr rel, SCAddr val, SCAddr valAttr, SCAddr resAttr) {
		SCIterator iter = session.createIterator(new SCConstraintBase(STDConstraints.CONSTR_ORD_BIN_CONN1, rel, SCType.EMPTY,
				SCType.EMPTY /* tuple */, SCType.EMPTY, val, SCType.EMPTY, SCType.EMPTY /* rv */, SCType.EMPTY, valAttr, SCType.EMPTY, resAttr));
		try {
			SCAddr value = null;
			if (!iter.isOver())
				value = iter.getValue(6);

			return value;
		} finally {
			if (iter != null)
				iter.erase();
		}
	}

	public static SCAddr getBinOrd1(SCSession session, SCAddr rel, SCAddr val2) {
		return getBinOrd(session, rel, val2, SCKeynodes.n2_, SCKeynodes.n1_);
	}

	public static SCAddr getBinOrd2(SCSession session, SCAddr rel, SCAddr val1) {
		return getBinOrd(session, rel, val1, SCKeynodes.n1_, SCKeynodes.n2_);
	}

}
