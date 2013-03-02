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

import net.ostis.sc.memory.SCAddr;
import net.ostis.sc.memory.SCSegment;

/**
 * Implementation of IScAddr for RGP.
 *
 * @author Dmitry Lazurkin
 */
public class RGPAddr extends RGPObject implements SCAddr {

	private RGPSession session;

	private SCSegment segment = null;
	private boolean dead = false;
	private int inputCount = 0;
	private int outputCount = 0;

	public RGPAddr(Integer id) {
		super(id);
	}

	private void fillNew() {
		if (isNew())
			session.fillNewAddr(this);
	}

	@Override
	public boolean isDead() {
		fillNew();
		return dead;
	}

	@Override
	public SCSegment getSegment() {
		fillNew();
		return segment;
	}

	public boolean isNew() {
		return segment == null;
	}

	public void setSegment(SCSegment segment) {
		this.segment = segment;
	}

	public int getInputCount() {
		return inputCount;
	}

	public void setInputCount(int inputCount) {
		this.inputCount = inputCount;
	}

	public int getOutputCount() {
		return outputCount;
	}

	public void setOutputCount(int outputCount) {
		this.outputCount = outputCount;
	}

	public void setSession(RGPSession session) {
		this.session = session;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

}
