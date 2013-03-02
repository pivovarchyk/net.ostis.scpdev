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

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import net.ostis.sc.memory.SCMemory;
import net.ostis.sc.memory.SCSession;

/**
 * Implementation of SC-memory through Remote Graph Protocol (RGP).
 *
 * @author Dmitry Lazurkin
 */
public class RGPMemory implements SCMemory {

	private String rgphost;
	private int rgpport;

	private List<RGPSession> sessions = new LinkedList<RGPSession>();

	public RGPMemory(String host, int port) {
		rgphost = host;
		rgpport = port;
	}

	@Override
	public void connect() throws Exception {
	}

	@Override
	public void disconnect() throws Exception {
		for (RGPSession session : sessions)
			session.close();
	}

	@Override
	public SCSession login() throws Exception {
		Socket controlSocket = new Socket(rgphost, rgpport);
		Socket eventSocket = new Socket(rgphost, rgpport + 1);
		RGPSession session = new RGPSession(this, controlSocket, eventSocket);
		sessions.add(session);
		return session;
	}

}
