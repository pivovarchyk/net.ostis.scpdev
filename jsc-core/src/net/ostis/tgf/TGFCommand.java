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

import java.io.IOException;

public class TGFCommand implements TGFSerializable, Cloneable {
    public static final short NOP = 0;
    public static final short GENEL = 1;
    public static final short DECLARE_SEGMENT = 2;
    public static final short SWITCH_TO_SEGMENT = 3;
    public static final short SETBEG = 4;
    public static final short SETEND = 5;
    public static final short FINDBYIDTF = 6;
    public static final short ENDOFSTREAM = 7;

	private int index;
    private short type;
    private TGFArgument[] args;

    public final static String[] sidCommand = {
            "NOP", "GENEL", "DECLARE_SEGMENT", "SWITCH_TO_SEGMENT", "SETBEG", "SETEND", "FINDBYIDTF", "ENDOFSTREAM"
    };

    public TGFCommand() {
        // do nothing
    }

    public void read(TGFReader reader) throws IOException {
        reader.align();
        index = reader.countCommand;
        type = reader.readInt16();
        short argCnt = reader.readInt16();
        args = new TGFArgument[argCnt];
        for (int i = 0; i < argCnt; i++) {
            args[i] = new TGFArgument();
            args[i].read(reader);
        }
        if (reader.getHeader().getCrcMode() != 0) {
            reader.readByte();
        }
    }

    public void write(TGFWriter writer) {
    }

    public short getType() {
        return type;
    }

    public TGFArgument[] getArguments() {
        return args;
    }

    public TGFArgument getArgument(int index) {
        return args[index];
    }

    public int getIndex() {
        return index;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        String res = sidCommand[type] + "( ";
        for (int i = 0; i < args.length; i++) {
            res += args[i].toString() + ((i + 1 < args.length) ? ", " : "");
        }
        res += " )";
        return res;
    }

}
