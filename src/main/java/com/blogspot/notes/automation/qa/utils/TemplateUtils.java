package com.blogspot.notes.automation.qa.utils;

import com.github.mustachejava.DefaultMustacheFactory;
import javaslang.control.Try;

import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Author: Serhii Korol.
 */
public final class TemplateUtils {

	private static final Logger LOGGER = Logger.getLogger(TemplateUtils.class.getName());

	private TemplateUtils() {
		throw new UnsupportedOperationException("IllegalAccess to private constructor");
	}

	public static void execute(final String templateName, final String fileName, final Object scope) {
		Try.run(() -> new DefaultMustacheFactory()
				.compile(templateName)
				.execute(new FileWriter(fileName), scope)
				.flush()).onFailure(ex -> LOGGER.severe("Unable to create template: " + ex));
	}

	public static Map<String, Object> getScope(final String link, final String height) {
		return Collections.unmodifiableMap(new HashMap<String, Object>() {
			{
				put("link", link);
				Optional.ofNullable(height).ifPresent(h -> put("height", h));
			}
		});
	}
}
