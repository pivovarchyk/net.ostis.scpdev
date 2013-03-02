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

/**
 * RGP Command Ids for requests and responses.
 *
 * @author Dmitry Lazurkin
 */
public enum RGPCommandId {
	// Requests
	REQ_LOGIN,
	REQ_CLOSE,

	REQ_CREATE_SEGMENT,
	REQ_OPEN_SEGMENT,

	REQ_GET_IDTF,
	REQ_SET_IDTF,
	REQ_ERASE_IDTF,
	REQ_FIND_BY_IDTF,

	REQ_CREATE_EL,
	REQ_GEN3_F_A_F,
	REQ_GEN5_F_A_F_A_F,
	REQ_ERASE_EL,
	REQ_MERGE_EL,

	REQ_SET_EL_BEGIN,
	REQ_SET_EL_END,
	REQ_GET_EL_BEGIN,
	REQ_GET_EL_END,
	REQ_GET_EL_TYPE,
	REQ_CHANGE_EL_TYPE,
	REQ_GET_EL_CONTENT,
	REQ_SET_EL_CONTENT,

	REQ_CREATE_ITERATOR,
	REQ_NEXT_ITERATOR,
	REQ_ERASE_ITERATOR,

	REQ_ATTACH_WAIT,
	REQ_DETACH_WAIT,
	REQ_REIMPLEMENT,

	REQ_ACTIVATE,

	REQ_GET_SEG_INFO,
	REQ_GET_EL_INFO,

	REQ_ACTIVATE_WAIT,

	// Replies
	REP_RETURN
}
