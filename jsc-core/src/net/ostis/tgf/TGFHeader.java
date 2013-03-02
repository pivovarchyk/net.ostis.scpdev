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
import java.nio.ByteOrder;

public class TGFHeader implements TGFSerializable {
    protected static final String signature = "TGF";
    protected byte minorVersion = 0;
    protected byte majorVersion = 3;

    protected ByteOrder endian = ByteOrder.nativeOrder();
    protected boolean isCompression = false;
    protected byte crcMode = 0;
    protected String streamName = "";

    public void read(TGFReader reader) throws IOException {
        byte[] buffer;

        buffer = new byte[3];
        reader.read(buffer);
        if (signature.compareTo(new String(buffer)) != 0)
            return;

        buffer = new byte[10];
        reader.read(buffer);

        minorVersion = buffer[0];
        majorVersion = buffer[1];

        endian = (buffer[2] == 0) ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;

        isCompression = (buffer[3] == 0) ? false : true;
        crcMode = buffer[6];

        buffer = new byte[buffer[9]];
        reader.read(buffer);
        streamName = new String(buffer);
    }

    public void write(TGFWriter stream) {
    }

    @Override
    public String toString() {
        return "TGF v" + majorVersion + "." + minorVersion + "' " + streamName + "', " + endian + ", zip="
                + String.valueOf(isCompression) + ", CRC=" + crcMode + ".";
    }

    public boolean isCompression() {
        return isCompression;
    }

    public void setCompression(boolean enable) {
        this.isCompression = enable;
    }

    public ByteOrder getEndian() {
        return endian;
    }

    public void setEndian(ByteOrder endian) {
        this.endian = endian;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public byte getCrcMode() {
        return crcMode;
    }

    public void setCrcMode(byte crcMode) {
        this.crcMode = crcMode;
    }

}
