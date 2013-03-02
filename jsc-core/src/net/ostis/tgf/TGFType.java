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
package net.ostis.tgf;

/**
 * @author Dmitry Lazurkin
 */
public class TGFType {
    public final static int EMPTY = 0x00;
    public final static int CONST = 0x01;
    public final static int VAR = 0x02;
    public final static int METAVAR = 0x03;
    public final static int CONSTANCY_MASK = CONST | VAR | METAVAR;
    public final static int POS = 0x04;
    public final static int NEG = 0x08;
    public final static int FUZ = 0x0C;
    public final static int FUZZY_MASK = POS | NEG | FUZ;
    public final static int ARC = 0x10;
    public final static int NODE = 0x20;
    public final static int UNDF = 0x30;
    public final static int STRUCT_MASK = ARC | NODE | UNDF;

    public final static TGFType NODE_CONST = new TGFType(CONST | NODE);
    public final static TGFType NODE_VAR = new TGFType(VAR | NODE);
    public final static TGFType NODE_METAVAR = new TGFType(METAVAR | NODE);
    public final static TGFType ARC_CONST_POS = new TGFType(POS | CONST | ARC);
    public final static TGFType UNDF_CONST = new TGFType(CONST | UNDF);
    public final static TGFType UNDF_VAR = new TGFType(VAR | UNDF);

    public int type = EMPTY;

    public TGFType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String result = "";// type + "=";

        if ((type & STRUCT_MASK) == ARC)
            result += "ARC";
        else if ((type & STRUCT_MASK) == NODE)
            result += "NODE";
        else if ((type & STRUCT_MASK) == UNDF)
            result += "UNDF";

        if ((type & CONSTANCY_MASK) == CONST)
            result += "/CONST";
        else if ((type & CONSTANCY_MASK) == VAR)
            result += "/VAR";
        else if ((type & CONSTANCY_MASK) == METAVAR)
            result += "/METAVAR";

        if ((type & FUZZY_MASK) == POS)
            result += "/POS";
        else if ((type & FUZZY_MASK) == NEG)
            result += "/NEG";
        else if ((type & FUZZY_MASK) == FUZ)
            result += "/FUZ";

        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TGFType))
            return false;

        TGFType other = (TGFType) obj;

        return this.type == other.type;
    }

    public boolean check(int type) {
        return ((CONSTANCY_MASK & type) > 0) ? ((this.type & CONSTANCY_MASK) == type)
                : ((STRUCT_MASK & type) > 0) ? ((this.type & STRUCT_MASK) == type)
                        : ((FUZZY_MASK & type) > 0) ? ((this.type & FUZZY_MASK) == type) : false;
        // return (this.type & type) == type;
    }
}
