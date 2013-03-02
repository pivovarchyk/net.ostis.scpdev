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


import org.apache.commons.lang3.Pair;

/**
 * @author Roman Serdyukov
 * @author Dmitry Lazurkin
 */
public interface SCSession {

	public SCMemory getMemory();

	public void close();

	public SCSegment createSegment(String uri);

	public void unlink(String uri);

	public void closeSegment(SCSegment segment);

	public SCSegment openSegment(String uri);

	public SCAddr findByIdtf(String idtf, SCSegment segment);

	public void setIdtf(SCAddr addr, String idtf);

	public String getIdtf(SCAddr element);

	public boolean isSegmentOpened(String uri);

	public SCAddr createElement(SCSegment segment, SCType type);

	public SCAddr gen3_f_a_f(SCAddr e1, SCSegment seg2, SCType t2, SCAddr e3);

	public Pair<SCAddr, SCAddr> gen5_f_a_f_a_f(SCAddr e1, SCSegment seg2, SCType t2, SCAddr e3, SCSegment seg4,
			SCType t4, SCAddr e5);

	public void eraseElement(SCAddr element);

	public void mergeElement(SCAddr from, SCAddr to);

	public SCAddr getBegin(SCAddr arc);

	public void setBegin(SCAddr arc, SCAddr element);

	public SCAddr getEnd(SCAddr arc);

	public void setEnd(SCAddr arc, SCAddr element);

	public SCType getType(SCAddr element);

	public void changeType(SCAddr element, SCType type);

	public SCContent getContent(SCAddr element);

	public void setContent(SCAddr element, SCContent content);

	public SCIterator createIterator(SCConstraint constraint);

	public void eraseIterator(SCIterator iterator);

	public boolean searchOneshort(SCConstraintBase constraint, Object... arguments);

	public void attachWait(SCWait wait, SCWait.Type type, Object... params);

	public void detachWait(SCWait wait);

	public void activate(SCAddr element, SCAddr... arguments);

	public void reimplement(SCAddr element, SCActivity activity);
}
