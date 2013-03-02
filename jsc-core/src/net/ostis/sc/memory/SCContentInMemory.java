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

import java.util.Arrays;

/**
 * Simple representation of sc-content just in memory.
 *
 * @author Roman Serdyukov
 * @author Dmitry Lazurkin
 */
public class SCContentInMemory implements SCContent {
	private Type type;
	private int size;
	private Object value;

	private void clearValue() {
		type = Type.EMPTY;
		size = 0;
		value = null;
	}

	public SCContentInMemory() {
		clearValue();
	}

	public SCContentInMemory(int v) {
		setInteger(v);
	}

	public SCContentInMemory(double v) {
		setReal(v);
	}

	public SCContentInMemory(String v) {
		setString(v);
	}

	public SCContentInMemory(byte[] v) {
		setData(v);
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#getSize()
	 */
	@Override
	public int getSize() {
		return size;
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#getType()
	 */
	@Override
	public Type getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#getData()
	 */
	@Override
	public byte[] getData() {
		assert type == Type.DATA;
		return (byte[]) value;
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#getInteger()
	 */
	@Override
	public int getInteger() {
		assert type == Type.INTEGER;
		return ((Integer) value).intValue();
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#getReal()
	 */
	@Override
	public double getReal() {
		assert type == Type.REAL;
		return ((Double) value).doubleValue();
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#getString()
	 */
	@Override
	public String getString() {
		assert type == Type.STRING;
		return ((String) value);
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return (type == Type.EMPTY);
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#setData(byte[])
	 */
	@Override
	public void setData(byte[] data) {
		clearValue();
		type = Type.DATA;
		value = data;
		size = data.length;
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#setEmpty()
	 */
	@Override
	public void setEmpty() {
		clearValue();
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#setInteger(int)
	 */
	@Override
	public void setInteger(int data) {
		clearValue();
		type = Type.INTEGER;
		value = Integer.valueOf(data);
		size = Integer.SIZE;
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#setReal(double)
	 */
	@Override
	public void setReal(double data) {
		clearValue();
		type = Type.REAL;
		value = Double.valueOf(data);
		size = Double.SIZE;
	}

	/* (non-Javadoc)
	 * @see net.ostis.sc.memory.SCContent#setString(java.lang.String)
	 */
	@Override
	public void setString(String data) {
		clearValue();
		type = Type.STRING;
		value = data;
		size = data.length();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;

		if (!(obj instanceof SCContent))
			return false;

		SCContent castObj = (SCContent) obj;

		if (type != castObj.getType())
			return false;

		switch (type) {
			case EMPTY:
				return true;
			case INTEGER:
				return getInteger() == castObj.getInteger();
			case REAL:
				return getReal() == castObj.getReal();
			case STRING:
				return getString().equals(castObj.getString());
			case DATA:
				return Arrays.equals(getData(), castObj.getData());
			default:
				throw new RuntimeException("Really unexpected");
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(type);

		if (type != Type.EMPTY) {
			buffer.append(", ");
			switch (type) {
				case INTEGER:
					buffer.append(getInteger());
				case REAL:
					buffer.append(getReal());
				case STRING:
					buffer.append("\"");
					buffer.append(getString());
					buffer.append("\"");
				case DATA:
					buffer.append("size = ");
					buffer.append(getData().length);
				default:
					throw new RuntimeException("Really unexpected");
			}
		}

		return buffer.toString();
	}
}
