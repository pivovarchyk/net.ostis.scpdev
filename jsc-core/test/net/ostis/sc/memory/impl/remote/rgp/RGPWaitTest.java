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

import static org.junit.Assert.assertNotNull;
import net.ostis.sc.memory.SCAddr;
import net.ostis.sc.memory.SCMemory;
import net.ostis.sc.memory.SCSegment;
import net.ostis.sc.memory.SCSession;
import net.ostis.sc.memory.SCType;
import net.ostis.sc.memory.SCWait;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link RGPSession}, which tests SC-waits functionality.
 *
 * @author Dmitry Lazurkin
 */
public class RGPWaitTest {

	private SCMemory memory;
	private SCSession session;

	private SCAddr addr;

	@Before
	public void setUp() throws Exception {
		memory = new RGPMemory("localhost", 10001);
		memory.connect();
		session = memory.login();

		String uri = "/test_segment";
		SCSegment segment = session.createSegment(uri);
		assertNotNull(segment);
		addr = session.createElement(segment, SCType.NODE_CONST);
		assertNotNull(addr);
	}

	private class TestWait implements SCWait {

		@Override
		public boolean activate(Type type, Object... args) {
			return true;
		}

	}

	@Test
	public void attachDetachWait() {
		SCWait wait = new TestWait();
		session.attachWait(wait, SCWait.Type.DIE, addr);
		session.detachWait(wait);
	}

	@Test
	public void activateWait() {
		SCWait wait = new TestWait();
		session.attachWait(wait, SCWait.Type.DIE, addr);
		session.eraseElement(addr);
	}

	@After
	public void tearDown() throws Exception {
		memory.disconnect();
	}

}
