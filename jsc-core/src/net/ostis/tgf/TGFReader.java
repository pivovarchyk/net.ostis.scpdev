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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class TGFReader {
    private long pos = 0;
    private DataInputStream stream;
    private byte[] buf16;
    private byte[] buf32;

    protected TGFHeader header = null;
    protected int countCommand = -1;
    protected TGFCommand command = null;

    public TGFReader(InputStream stream) throws IOException {
        this.stream = new DataInputStream(stream);

        header = new TGFHeader();
        header.read(this);
        buf16 = new byte[2];
        buf32 = new byte[4];
    }

    public boolean processCommand(TGFCommandListener listener) throws IOException {
        TGFCommand cmd = new TGFCommand();
        countCommand++;
        cmd.read(this);
        listener.processCommand(cmd);
        return (cmd.getType() == TGFCommand.ENDOFSTREAM) ? false : true;
    }

    public void processAllCommand(TGFCommandListener listener) throws IOException {
        while (processCommand(listener)) {
            // do nothing...
        }
    }

    short readInt16() throws IOException {
        pos += stream.read(buf16);
        return ByteBuffer.wrap(buf16).order(header.getEndian()).getShort();
    }

    int readInt32() throws IOException {
        pos += stream.read(buf32);
        return ByteBuffer.wrap(buf32).order(header.getEndian()).getShort();
    }

    double readDouble() throws IOException {
        pos += 8;
        return stream.readDouble();
    }

    byte readByte() throws IOException {
        pos += 1;
        return stream.readByte();
    }

    void read(byte[] b) throws IOException {
        pos += stream.read(b);
    }

    public void align() throws IOException {
        long align = pos % 4;
        if (align != 0)
            align = 4 - align;
        pos += stream.skip(align);
    }

    public void skip(int n) throws IOException {
        pos += stream.skip(n);
    }

    public void close() throws IOException {
        stream.close();
    }

    public TGFHeader getHeader() {
        return header;
    }

}
