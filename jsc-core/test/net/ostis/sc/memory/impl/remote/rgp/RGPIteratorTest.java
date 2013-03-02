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

import java.io.FileInputStream;
import java.util.Properties;

import net.ostis.sc.memory.SCConstraint;
import net.ostis.sc.memory.SCIterator;
import net.ostis.sc.memory.SCMemory;
import net.ostis.sc.memory.SCSegment;
import net.ostis.sc.memory.SCSession;
import net.ostis.sc.memory.SCConstraintBase;
import net.ostis.sc.memory.STDConstraints;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link RGPIterator}.
 *
 * @author Dmitry Lazurkin
 */
public class RGPIteratorTest {

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
	public void iterateOnSegment() {
		SCSegment segment = session.openSegment("/proc/keynode");
		assertNotNull(segment);

		SCConstraint constr = new SCConstraintBase(STDConstraints.CONSTR_ON_SEGMENT, segment, false);
		SCIterator iterator = session.createIterator(constr);
		assertNotNull(iterator);

		for (; !iterator.isOver(); iterator.next())
			System.out.println(session.getIdtf(iterator.getValue(0)));
	}

	@After
	public void tearDown() throws Exception {
		memory.disconnect();
	}

}
