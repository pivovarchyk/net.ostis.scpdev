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

import junit.framework.Assert;
import net.ostis.sc.memory.SCMemory;
import net.ostis.sc.memory.SCSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link RGPMemory}.
 *
 * @author Dmitry Lazurkin
 */
public class RGPMemoryTest {

	private SCMemory memory;

	@Before
	public void setUp() throws Exception {
		memory = new RGPMemory("localhost", 10001);
		memory.connect();
	}

	@Test
	public void login() throws Exception {
		SCSession session = memory.login();
		Assert.assertNotNull(session);
	}

	@After
	public void tearDown() throws Exception {
		memory.disconnect();
	}

}
