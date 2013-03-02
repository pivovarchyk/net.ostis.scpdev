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
package net.ostis.sc.memory.impl.remote.rgp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import net.ostis.sc.memory.SCConstraintInfo;
import net.ostis.sc.memory.SCContent;
import net.ostis.sc.memory.SCContentInMemory;
import net.ostis.sc.memory.SCWait;
import net.ostis.sc.memory.SCType;

/**
 * High-level RGP protocol stream.
 *
 * @author Dmitry Lazurkin
 */
public class RGPProtocolStream {

	private DataInputStream input = null;
	private DataOutputStream output = null;

	private byte[] buf16 = new byte[2];
	private byte[] buf32 = new byte[4];

	private RGPObjectsRegistry objectsRegistry;

	public RGPProtocolStream(InputStream input, OutputStream output, RGPObjectsRegistry objectsRegistry) {
		this.input = new DataInputStream(input);
		this.output = new DataOutputStream(output);
		this.objectsRegistry = objectsRegistry;
	}

	public RGPCommandId readCommandId() throws IOException {
		return RGPCommandId.values()[read8()];
	}

	public void writeCommandId(RGPCommandId id) throws IOException {
		write8((byte) id.ordinal());
	}

	public int readArgsCount() throws IOException {
		return read8();
	}

	public void writeArgsCount(int i) throws IOException {
		write8((byte) i);
	}

	private void assertArgType(RGPArgumentType expected) throws IOException, RGPProtocolException {
		RGPArgumentType type = readArgType();
		if (type != expected)
			throw new RGPProtocolException("Expects argument type " + expected + ", but retrieved from server "
					+ type.toString());
	}

	public void writeRetval(int v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_RETVAL);
		write16((short) v);
	}

	public int readRetval() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_RETVAL);
		return read16();
	}

	public void write(String v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_STRING);
		write32(v.length());
		output.write(v.getBytes());
	}

	public void writeImpl(String v) throws IOException, RGPProtocolException {
		write32(v.length());
		output.write(v.getBytes());
	}

	public void writeImpl(byte[] v) throws IOException, RGPProtocolException {
		write32(v.length);
		output.write(v);
	}

	public String readString() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_STRING);
		return readStringImpl();
	}

	public String readStringImpl() throws IOException, RGPProtocolException {
		int length = read32();
		StringBuffer buffer = new StringBuffer(length);
		while (length-- != 0)
			buffer.append((char) read8());
		return buffer.toString();
	}

	public RGPSegment readSegment() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_SEGMENT);
		return readSegmentImpl();
	}

	public RGPSegment readSegmentImpl() throws IOException, RGPProtocolException {
		int id = read32();
		return objectsRegistry.getSegment(id);
	}

	public void write(boolean v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_BOOLEAN);
		write8(v ? (byte) 1 : (byte) 0);
	}

	public boolean readBoolean() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_BOOLEAN);
		return read8() != 0;
	}

	public int readInt32() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_INT32);
		return read32();
	}

	public void write(RGPSegment v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_SEGMENT);
		write32(v.getId());
	}

	public RGPAddr readAddr() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_ADDR);
		return readAddrImpl();
	}

	public RGPAddr readAddrImpl() throws IOException, RGPProtocolException {
		int id = read32();
		return objectsRegistry.getAddr(id);
	}

	public RGPWait readWait() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_WAIT);
		return readWaitImpl();
	}

	public RGPWait readWaitImpl() throws IOException, RGPProtocolException {
		int id = read32();
		return objectsRegistry.getWait(id);
	}

	public RGPActivity readActivity() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_ACTIVITY);
		return readActivityImpl();
	}

	public RGPActivity readActivityImpl() throws IOException, RGPProtocolException {
		int id = read32();
		return objectsRegistry.getActivity(id);
	}

	public void write(RGPAddr v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_ADDR);
		if (v != null) {
			write32(v.getId());
		} else {
			write32(0);
		}
	}

	public SCType readType() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_TYPE);
		return new SCType(read32());
	}

	public void write(SCType v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_TYPE);
		write32(v.type);
	}

	public byte[] readData() throws IOException {
		int size = read32();
		byte[] data = new byte[size];
		input.read(data);
		return data;
	}

	public SCContent readContent() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_CONTENT);
		RGPContentType type = RGPContentType.values()[read16()];
		SCContent content = new SCContentInMemory();
		switch (type) {
			case EMPTY:
				break;
			case STRING:
				content.setString(readStringImpl());
				break;
			case INT:
				content.setInteger(read32());
				break;
			case REAL:
				throw new UnsupportedOperationException("Not support real content yet");
			case DATA:
				content.setData(readData());
				break;
			default:
				break;
		}
		return content;
	}

	public void write(int v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_INT32);
		write32(v);
	}

	public void write(SCContent.Type v) throws IOException, RGPProtocolException {
		write16((short) RGPContentType.values()[v.ordinal()].ordinal());
	}

	public void write(SCContent v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_CONTENT);
		write(v.getType());
		switch (v.getType()) {
			case EMPTY:
				break;
			case STRING:
				writeImpl(v.getString());
			case INTEGER:
				write32(v.getInteger());
				break;
			case REAL:
				throw new UnsupportedOperationException("Not support real content yet");
			case DATA:
				writeImpl(v.getData());
				break;
			default:
				throw new RuntimeException("Unreacheble flow in switch");
		}
	}

	public SCWait.Type readWaitType() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_WAIT_TYPE);
		return SCWait.Type.values()[read16()];
	}

	public void write(SCWait.Type v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_WAIT_TYPE);
		write16((short) v.ordinal());
	}

	public void write(RGPWait v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_WAIT);
		write32(v.getId());
	}

	public void write(SCConstraintInfo v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_CONSTRAINT_INFO);
		write16((short) RGPConstraintId.valueOf(v.getName()).ordinal());
	}

	public RGPIterator readIterator() throws IOException, RGPProtocolException {
		assertArgType(RGPArgumentType.SC_ITERATOR);
		return readIteratorImpl();
	}

	public RGPIterator readIteratorImpl() throws IOException, RGPProtocolException {
		int id = read32();
		return objectsRegistry.getIterator(id);
	}

	public void write(RGPIterator v) throws IOException, RGPProtocolException {
		writeArgumentType(RGPArgumentType.SC_ITERATOR);
		write32(v.getId());
	}

	public RGPArgumentType readArgType() throws IOException {
		return RGPArgumentType.values()[read16()];
	}

	public void writeArgumentType(RGPArgumentType type) throws IOException {
		write16((short) type.ordinal());
	}

	public SCContent.Type readContentType() throws IOException {
		return SCContent.Type.values()[read16()];
	}

	private byte read8() throws IOException {
		return input.readByte();
	}

	private void write8(byte d) throws IOException {
		output.writeByte(d);
	}

	private short read16() throws IOException {
		input.read(buf16);
		return ByteBuffer.wrap(buf16).order(ByteOrder.BIG_ENDIAN).getShort();
	}

	private void write16(short d) throws IOException {
		ByteBuffer.wrap(buf16).order(ByteOrder.BIG_ENDIAN).putShort(d);
		output.write(buf16);
	}

	private int read32() throws IOException {
		input.read(buf32);
		return ByteBuffer.wrap(buf32).order(ByteOrder.BIG_ENDIAN).getInt();
	}

	private void write32(int d) throws IOException {
		ByteBuffer.wrap(buf32).order(ByteOrder.BIG_ENDIAN).putInt(d);
		output.write(buf32);
	}

}
