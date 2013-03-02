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
package net.ostis.sc.memory;

/**
 * @author Dmitry Lazurkin
 */
public interface SCWait {

	public enum Type {
	    DIE,
		ATTACH_INPUT,
		ATTACH_OUTPUT,
		CHANGE_TYPE,
		DETACH_INPUT,
		DETACH_OUTPUT,
		ARC_BEG,
		ARC_END,
		ARCS_ENDS,
		CONT,
		IDTF,
		HACK_SET_MEMBER,
		HACK_IN_SET,
		MERGE,
		PRE_FIRST,
		ATTACH_INPUT_PRE,
		ATTACH_OUTPUT_PRE,
		CHANGE_TYPE_PRE,
		DETACH_INPUT_PRE,
		DETACH_OUTPUT_PRE,
		HACK_SET_MEMBER_PRE,
		HACK_IN_SET_PRE,
		COUNT,
		SEGMENT_ON_ADD,
		SEGMENT_ON_REMOVE,
	}

	boolean activate(Type type, Object ... args);

}
