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

public class TGFArgument implements TGFSerializable, Cloneable {
    public final short TGF_INT32 = 0;
    public final short TGF_INT64 = 1;
    public final short TGF_FLOAT = 2;
    public final short TGF_DATA = 3;
    public final short TGF_SCTYPE = 4;
    public final short TGF_INT16 = 5;
    public final short TGF_STRING = 6;
    public final short TGF_LAZY_DATA = 7;
    public final short TGF_ARG_LAST = 8;
    public final short TGF_USERID = 254;
    public final short TGF_NONE = 255;

    private short type;
    private Object value;

    public void read(TGFReader reader) throws IOException {
        reader.align();
        type = (short) reader.readInt32();
        switch (type) {
        case TGF_INT16:
            value = new Short(reader.readInt16());
            break;
        case TGF_INT32:
            value = new Integer(reader.readInt32());
            break;
        case TGF_FLOAT:
            value = new Double(reader.readDouble());
            break;
        case TGF_DATA:
            int cb = reader.readInt32();
            byte[] b = new byte[cb];
            reader.read(b);
            value = b;
            break;
        case TGF_SCTYPE:
            value = new TGFType(reader.readByte());
            break;
        case TGF_STRING:
            int cs = reader.readInt32();
            byte[] s = new byte[cs];
            reader.read(s);
            value = new String(s);
            break;
        case TGF_LAZY_DATA:
            break;
        default:
        }
    }

    public void write(TGFWriter writer) throws IOException {
    }

    public short getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
