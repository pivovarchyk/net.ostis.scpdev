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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Dmitry Lazurkin
 */
public class SCKeynodesBase {

	private static final Log log = LogFactory.getLog(SCKeynodesBase.class);

	/**
	 * Indicate URI of segment field.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface SegmentURI {
		String value();
	}

	/**
	 * Set default segment for keynodes.
	 * @see Keynode
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface DefaultSegment {
		String value();

		String fieldName() default "defaultSegment";
	}

	/**
	 * Indicate keynode name as field name. Use default segment.
	 * @see DefaultSegment
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Keynode {
		String value() default "";
	}

	/**
	 * Indicate keynode URI.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface KeynodeURI {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface KeynodesNumberPatternURI {
		String patternURI();

		String patternName();

		int startIndex();

		int endIndex();
	}

	protected static void init(SCSession session, Class<? extends SCKeynodesBase> klass) {
		if (log.isDebugEnabled())
			log.debug("Start retrieving keynodes for " + klass);

		try {
			//
			// Search default segment for keynodes
			//
			SCSegment defaultSegment = null;
			{
				DefaultSegment annotation = klass.getAnnotation(DefaultSegment.class);
				if (annotation != null) {
					defaultSegment = session.openSegment(annotation.value());
					Validate.notNull(defaultSegment, "Default segment \"{0}\" not found", annotation.value());
					klass.getField(annotation.fieldName()).set(null, defaultSegment);
				}
			}

			Field[] fields = klass.getFields();
			for (Field field : fields) {
				int modifiers = field.getModifiers();

				if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
					Class<?> type = field.getType();
					if (type.equals(SCSegment.class)) {
						//
						// We have segment field. Load segment by uri.
						//
						SegmentURI annotation = field.getAnnotation(SegmentURI.class);

						if (annotation != null) {
							String uri = annotation.value();
							SCSegment segment = session.openSegment(uri);
							field.set(null, segment);
						} else {
							// May be it already has value?
							if (log.isWarnEnabled()) {
								if (field.get(null) == null)
									log.warn(field + " doesn't have value");
							}
						}
					} else {
						if (!(checkKeynode(session, defaultSegment, field) || checkKeynodeURI(session, field) || checkKeynodesNumberPatternURI(
								session, klass, field))) {

							if (log.isWarnEnabled()) {
								if (field.get(null) == null)
									log.warn(field + " doesn't have annotations and value");
							}

						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle
			e.printStackTrace();
		}
	}

	private static boolean checkKeynode(SCSession session, SCSegment defaultSegment, Field field)
			throws IllegalArgumentException, IllegalAccessException {
		Keynode annotation = field.getAnnotation(Keynode.class);

		if (annotation != null) {
			String keynodeName = annotation.value();

			if (StringUtils.isEmpty(keynodeName))
				keynodeName = field.getName();

			SCAddr keynode = session.findByIdtf(keynodeName, defaultSegment);
			Validate.notNull(keynode, "Not found keynode with URI \"" + defaultSegment.getURI() + "/" + keynodeName
					+ "\"");

			field.set(null, keynode);

			if (log.isDebugEnabled())
				log.debug(defaultSegment.getURI() + "/" + keynodeName + " --> " + field.getName());

			return true;
		} else {
			return false;
		}
	}

	private static boolean checkKeynodeURI(SCSession session, Field field) throws IllegalArgumentException,
			IllegalAccessException {
		KeynodeURI keynodeURI = field.getAnnotation(KeynodeURI.class);

		if (keynodeURI != null) {
			String[] comp = URIUtils.splitByIdtf(keynodeURI.value());

			SCSegment segment = session.openSegment(comp[0]);

			SCAddr keynode = session.findByIdtf(comp[1], segment);
			Validate.notNull(keynode);

			field.set(null, keynode);

			if (log.isDebugEnabled())
				log.debug(keynodeURI.value() + " --> " + field.getName());

			return true;
		} else {
			return false;
		}
	}

	private static boolean checkKeynodesNumberPatternURI(SCSession session, Class<?> klass, Field field)
			throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		KeynodesNumberPatternURI patternURI = field.getAnnotation(KeynodesNumberPatternURI.class);

		if (patternURI != null) {
			String[] comp = URIUtils.splitByIdtf(patternURI.patternURI());

			SCSegment segment = session.openSegment(comp[0]);

			List<SCAddr> keynodes = new LinkedList<SCAddr>();
			for (int i = patternURI.startIndex(); i <= patternURI.endIndex(); ++i) {
				String keynodeName = MessageFormat.format(comp[1], i);

				SCAddr keynode = session.findByIdtf(keynodeName, segment);
				Validate.notNull(keynode, keynodeName);

				keynodes.add(keynode);

				String fieldName = MessageFormat.format(patternURI.patternName(), i);
				Field keynodeField = klass.getField(fieldName);
				keynodeField.set(null, keynode);

				if (log.isDebugEnabled())
					log.debug(comp[0] + "/" + keynodeName + " --> " + keynodeField.getName());
			}

			field.set(null, (SCAddr[]) keynodes.toArray(new SCAddr[keynodes.size()]));

			if (log.isDebugEnabled())
				log.debug(patternURI.patternURI() + " --> " + field.getName());

			return true;
		} else {
			return false;
		}
	}
}
