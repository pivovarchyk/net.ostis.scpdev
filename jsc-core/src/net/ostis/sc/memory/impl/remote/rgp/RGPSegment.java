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
import net.ostis.sc.memory.SCDirectory;
import net.ostis.sc.memory.SCSegment;

/**
 * Base SC-segment implementation.
 *
 * @author Dmitry Lazurkin
 */
public class RGPSegment extends RGPObject implements SCSegment {

	private RGPSession session;

	private RGPAddr sign = null;
	private String name = null;
	private String uri = null;
	private boolean dead = false;

	public RGPSegment(Integer id) {
		super(id);
	}

	private void fillNew() {
		if (isNew())
			session.fillNewSegment(this);
	}

	@Override
	public SCAddr getSign() {
		fillNew();
		return sign;
	}

	@Override
	public String getURI() {
		fillNew();
		return uri;
	}

	@Override
	public String getName() {
		fillNew();
		return name;
	}

	@Override
	public boolean isDead() {
		fillNew();
		return dead;
	}

	@Override
	public SCDirectory getParent() {
		fillNew();
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNew() {
		return sign == null;
	}

	public void setSign(RGPAddr sign) {
		this.sign = sign;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void setSession(RGPSession session) {
		this.session = session;
	}

}
