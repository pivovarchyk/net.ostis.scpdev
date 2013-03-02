/*
 * This source file is part of OSTIS (Open Semantic Technology for Intelligent
 * Systems) For the latest info, see http://www.ostis.net
 *
 * Copyright (c) 2010 OSTIS
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
 * @author Roman Serdyukov
 * @author Dmitry Lazurkin
 */
public class SCType {
	public final static int _EMPTY = 0;

	public final static int CONST = 1;
	public final static int VAR = 2;
	public final static int METAVAR = 4;
	public final static int CONSTANCY_MASK = CONST | VAR | METAVAR;

	public final static int POS = 8;
	public final static int NEG = 16;
	public final static int FUZ = 32;
	public final static int FUZZYNESS_MASK = POS | NEG | FUZ;

	public final static int UNDF = 64;
	public final static int ARC = 128;
	public final static int NODE = 256;
	public final static int ELMNCLASS_MASK = ARC | NODE | UNDF;


	public final static SCType EMPTY = new SCType();
	public final static SCType NODE_CONST = new SCType(CONST | NODE);
	public final static SCType NODE_VAR = new SCType(VAR | NODE);
	public final static SCType NODE_METAVAR = new SCType(METAVAR | NODE);

	public final static SCType ARC_CONST_POS = new SCType(POS | CONST | ARC);
	public final static SCType ARC_VAR_POS = new SCType(POS | VAR | ARC);
	public final static SCType ARC_METAVAR_POS = new SCType(POS | METAVAR | ARC);

	public final static SCType ARC_CONST_NEG = new SCType(NEG | CONST | ARC);
	public final static SCType ARC_VAR_NEG = new SCType(NEG | VAR | ARC);
	public final static SCType ARC_METAVAR_NEG = new SCType(NEG | METAVAR | ARC);

	public final static SCType ARC_CONST_FUZ = new SCType(FUZ | CONST | ARC);
	public final static SCType ARC_VAR_FUZ = new SCType(FUZ | VAR | ARC);
	public final static SCType ARC_METAVAR_FUZ = new SCType(FUZ | METAVAR | ARC);

	public final static SCType UNDF_CONST = new SCType(CONST | UNDF);
	public final static SCType UNDF_VAR = new SCType(VAR | UNDF);

	public int type = _EMPTY;

	public SCType() {
	}

	public SCType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		String result = "";// type + "=";

		if ((type & ELMNCLASS_MASK) == ARC)
			result += "ARC";
		else if ((type & ELMNCLASS_MASK) == NODE)
			result += "NODE";
		else if ((type & ELMNCLASS_MASK) == UNDF)
			result += "UNDF";

		if ((type & CONSTANCY_MASK) == CONST)
			result += "-CONST";
		else if ((type & CONSTANCY_MASK) == VAR)
			result += "-VAR";
		else if ((type & CONSTANCY_MASK) == METAVAR)
			result += "-METAVAR";

		if ((type & FUZZYNESS_MASK) == POS)
			result += "-POS";
		else if ((type & FUZZYNESS_MASK) == NEG)
			result += "-NEG";
		else if ((type & FUZZYNESS_MASK) == FUZ)
			result += "-FUZ";

		return result;
	}

	public boolean check(int type) {
		return ((CONSTANCY_MASK & type) > 0) ? ((this.type & CONSTANCY_MASK) == type)
				: ((ELMNCLASS_MASK & type) > 0) ? ((this.type & ELMNCLASS_MASK) == type)
						: ((FUZZYNESS_MASK & type) > 0) ? ((this.type & FUZZYNESS_MASK) == type) : false;
	}

}
