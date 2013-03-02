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
package net.ostis.sc.memory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * Utility for pretty printing sc-elements and other structures.
 *
 * @author Dmitry Lazurkin
 */
public class SCPrintUtils {

	static {
        Properties props = new Properties();
        try {
			props.load(SCSession.class.getResourceAsStream("/log4j.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        PropertyConfigurator.configure(props);
	}

	private static void printShortElementInfo(SCSession session, SCAddr el, PrintWriter out) {
		if (el != null) {
			SCType type = session.getType(el);
			String idtf = session.getIdtf(el);
			out.print(type);
			out.print(":");
			out.print(idtf);
		} else {
			out.print("NIL");
		}
	}

	private static void printElementInfo(SCSession session, SCAddr el, PrintWriter out) {
		printShortElementInfo(session, el, out);

		if (el != null) {
			SCType type = session.getType(el);
			if ((type.type & SCType.ARC) == SCType.ARC) {
				out.print(" = < ");

				SCAddr begin = session.getBegin(el);
				printShortElementInfo(session, begin, out);

				out.print(", ");

				SCAddr end = session.getBegin(el);
				printShortElementInfo(session, end, out);

				out.print(" >");
			}
		}
	}

	public static void printElement(SCSession session, SCAddr el, OutputStream out) {
		PrintWriter writer = new PrintWriter(out);
		printElementInfo(session, el, writer);

		if (el != null) {
			writer.println("\nOutput arcs:");
			SCIterator it = session.createIterator(new SCConstraintBase(STDConstraints.CONSTR_3_f_a_a, el, SCType.EMPTY,
					SCType.EMPTY));
			try {
				for (; !it.isOver(); it.next()) {
					writer.print("\t");
					printShortElementInfo(session, it.getValue(1), writer);
					writer.print(" >- ");
					printShortElementInfo(session, it.getValue(2), writer);
					writer.println();
				}
			} finally {
				it.erase();
			}

			writer.println("Input arcs:");
			it = session.createIterator(new SCConstraintBase(STDConstraints.CONSTR_3_a_a_f, SCType.EMPTY, SCType.EMPTY,
					el));
			try {
				for (; !it.isOver(); it.next()) {
					writer.print("\t");
					printShortElementInfo(session, it.getValue(1), writer);
					writer.print(" -< ");
					printShortElementInfo(session, it.getValue(0), writer);
					writer.println();
				}
			} finally {
				it.erase();
			}

			writer.println();
		}

		writer.flush();
	}
}
