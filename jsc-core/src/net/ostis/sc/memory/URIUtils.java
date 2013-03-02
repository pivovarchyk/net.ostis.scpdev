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
public class URIUtils {

	public static String[] splitByIdtf(String uri) {
		String[] c = new String[2];
		for(int i = uri.length() - 1; i > -1; i--) {
			if(uri.charAt(i) == '/') {
				c[0] = uri.substring(0, i);
				c[1] = uri.substring(i + 1);
				break;
			}
		}

		return c;
	}

	public static SCAddr findElementByURI(SCSession session, String uri) {
		String[] comp = splitByIdtf(uri);
		SCSegment segment = session.openSegment(comp[0]);
		if (segment != null)
			return session.findByIdtf(comp[1], segment);
		return null;
	}

	public static String getFullURI(SCSession session, SCAddr element) {
		return element.getSegment().getURI() + "/" + session.getIdtf(element);
	}

}
