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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.util.Properties;

import net.ostis.sc.memory.SCAddr;
import net.ostis.sc.memory.SCContent;
import net.ostis.sc.memory.SCContentInMemory;
import net.ostis.sc.memory.SCKeynodes;
import net.ostis.sc.memory.SCMemory;
import net.ostis.sc.memory.SCPKeynodes;
import net.ostis.sc.memory.SCSegment;
import net.ostis.sc.memory.SCSession;
import net.ostis.sc.memory.SCType;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link RGPSession}.
 *
 * @author Dmitry Lazurkin
 */
public class RGPSessionTest {

	private SCMemory memory;
	private SCSession session;

	@Before
	public void setUp() throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream("log4j.properties"));
        PropertyConfigurator.configure(props);

		memory = new RGPMemory("localhost", 47805);
		memory.connect();
		session = memory.login();
	}

	@Test
	public void testOpenSegment() {
		String uri = "/proc/keynode";
		SCSegment segment = session.openSegment(uri);
		assertNotNull(segment);
		assertNotNull(segment.getSign());
		assertEquals(uri, segment.getURI());
		assertTrue(session.isSegmentOpened(uri));
	}

	@Test
	public void testCreateSegment() {
		String uri = "/test_segment";
		SCSegment segment = session.createSegment(uri);
		assertNotNull(segment);
		assertTrue(session.isSegmentOpened(uri));
	}

	@Test
	public void findByIdtf() {
		SCSegment segment = session.openSegment("/proc/keynode");
		assertNotNull(segment);
		SCAddr addr = session.findByIdtf("1_", segment);
		assertNotNull(addr);
		assertEquals(segment, addr.getSegment());
	}

	@Test
	public void initKeynodes() {
		SCKeynodes.init(session);
		assertNotNull(SCKeynodes.dirent);
		SCPKeynodes.init(session);
	}

	@Test
	public void createElement() {
		String uri = "/test_segment";
		SCSegment segment = session.createSegment(uri);
		assertNotNull(segment);

		SCAddr el = session.createElement(segment, SCType.NODE_CONST);
		assertNotNull(el);
		assertEquals(segment, el.getSegment());
	}

	@Test
	public void eraseElement() {
		String uri = "/test_segment";
		SCSegment segment = session.createSegment(uri);
		assertNotNull(segment);

		SCAddr el = session.createElement(segment, SCType.NODE_CONST);
		assertNotNull(el);
		assertEquals(segment, el.getSegment());

		session.eraseElement(el);
		//assertTrue(el.isDead());
	}

	@Test
	public void content() {
		String uri = "/tmp/test_segment";
		SCSegment segment = session.createSegment(uri);
		assertNotNull(segment);

		SCAddr el = session.createElement(segment, SCType.NODE_CONST);
		assertNotNull(el);
		assertEquals(segment, el.getSegment());

		SCContent contentOut = new SCContentInMemory(1);
		session.setContent(el, contentOut);
		SCContent contentIn = session.getContent(el);
		assertEquals(contentOut, contentIn);
	}

	@After
	public void tearDown() throws Exception {
		memory.disconnect();
	}

}
