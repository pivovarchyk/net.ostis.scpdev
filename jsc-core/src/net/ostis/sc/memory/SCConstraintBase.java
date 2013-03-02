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
import java.util.List;

import org.apache.commons.lang3.Validate;

/**
 * @author Dmitry Lazurkin
 */
public class SCConstraintBase implements SCConstraint {

	private SCConstraintInfo info;
	private List<Object> params;

	public SCConstraintBase(SCConstraintInfo info, Object... params) {
		this.info = info;
		this.params = new ArrayList<Object>(info.getParamsCount());
		set(params);
	}

	@Override
	public Object get(int index) {
		return params.get(index);
	}

	@Override
	public void set(int index, Object param) {
		validateParam(index, param);
		params.set(index, param);
	}

	@Override
	public SCConstraintInfo getInfo() {
		return info;
	}

	private void validateParam(int index, Object param) {
		SCConstraintInfo.ParamType t = info.getParamType(index);

		Class<?> paramClass = null;

		if (t == SCConstraintInfo.ParamType.SC_ADDR) {
			paramClass = SCAddr.class;
		} else if (t == SCConstraintInfo.ParamType.SC_TYPE) {
			paramClass = SCType.class;
		} else if (t == SCConstraintInfo.ParamType.SC_SEGMENT) {
			paramClass = SCSegment.class;
		} else if (t == SCConstraintInfo.ParamType.SC_BOOLEAN) {
			paramClass = Boolean.class;
		}

		Validate.isInstanceOf(paramClass, param, "Parameter %d for constraint %s must be %s, but it is %s",
				index, info.getName(), paramClass.getName(), param.getClass().getName());
	}

	private void set(Object... params) {
		Validate.isTrue(params.length == info.getParamsCount(),
				"Constraint %s expects %d parameters, but passed %d parameters", info.getName(),
				info.getParamsCount(), params.length);

		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			validateParam(i, param);
			this.params.add(param);
		}
	}
}
