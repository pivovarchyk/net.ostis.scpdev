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

import net.ostis.sc.memory.SCActivity;
import net.ostis.sc.memory.SCAddr;
import net.ostis.sc.memory.SCSession;

/**
 * @author Dmitry Lazurkin
 */
public class RGPActivity extends RGPObject implements SCActivity {

	private SCActivity activity = null;

	public RGPActivity(Integer id) {
		super(id);
	}

	@Override
	public boolean activate(SCSession session, SCAddr this_, SCAddr... params) {
		return activity.activate(session, this_, params);
	}

	public SCActivity getActivity() {
		return activity;
	}

	public void setActivity(SCActivity activity) {
		this.activity = activity;
	}

}
