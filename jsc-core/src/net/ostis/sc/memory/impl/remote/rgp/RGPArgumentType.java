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
 * RGP Argument types.
 *
 * @author Dmitry Lazurkin
 */
public enum RGPArgumentType {
	UNKNOWN,       /// not argument type, for internal purpose
	SC_TYPE,       /// sc_type_id
	SC_SEGMENT,    /// sc_segment_id
	SC_ADDR,       /// sc_addr_id
	SC_ITERATOR,   /// sc_iterator_id
	SC_CONTENT,    /// sc_content_id and data (see #content_type)
	SC_ACTIVITY,   /// sc_activity_id
	SC_WAIT_TYPE,  /// sc_wait_type_id
	SC_WAIT,       /// sc_wait_id
	SC_RETVAL,     /// sc_retval_id
	SC_CONSTRAINT_INFO, /// sc_constraint_id and constraint arguments (see #constraint_type)
	SC_INT16,      /// int16
	SC_INT32,      /// int32
	SC_BOOLEAN,    /// boolean
	SC_STRING      /// int32 + non null terminated string data with 8-bit chars
}
