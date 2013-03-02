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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Roman Serdyukov
 * @author Dmitry Lazurkin
 */
public enum STDConstraints implements SCConstraintInfo {
	CONSTR_ALL_INPUT(ParamType.SC_ADDR),
	CONSTR_ALL_OUTPUT(ParamType.SC_ADDR),
	CONSTR_3_f_a_a(ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_TYPE),
	CONSTR_3_f_a_f(ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_ADDR),
	CONSTR_3_a_a_f(ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_ADDR),
	CONSTR_5_f_a_a_a_a(ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_TYPE),
	CONSTR_5_f_a_a_a_f(ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_ADDR),
	CONSTR_5_f_a_f_a_a(ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_TYPE),
	CONSTR_5_f_a_f_a_f(ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_ADDR),
	CONSTR_5_a_a_a_a_f(ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_ADDR),
	CONSTR_5_a_a_f_a_a(ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_ADDR),
	CONSTR_5_a_a_f_a_f(ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_ADDR),
	CONSTR_3l2_f_a_a_a_f(ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_ADDR),
	CONSTR_ON_SEGMENT(ParamType.SC_SEGMENT, ParamType.SC_BOOLEAN),
	CONSTR_ORD_BIN_CONN1(ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_ADDR,
			ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_TYPE, ParamType.SC_ADDR, ParamType.SC_TYPE,
			ParamType.SC_ADDR),
	CONSTR_ORD_BIN_CONN2(ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_TYPE,
			ParamType.SC_TYPE, ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_ADDR, ParamType.SC_TYPE,
			ParamType.SC_ADDR, ParamType.SC_TYPE, ParamType.SC_ADDR);

	private List<ParamType> args;

	private STDConstraints(ParamType... arguments) {
		args = new ArrayList<ParamType>(arguments.length);
		Collections.addAll(args, arguments);
	}

	@Override
	public int getParamsCount() {
		return args.size();
	}

	@Override
	public ParamType getParamType(int index) {
		return args.get(index);
	}

	@Override
	public String getName() {
		return this.name();
	}
}
