package com.blogspot.notes.automation.qa.entities;

import java.util.Map;
import java.util.Optional;

/**
 * Author: Serhii Korol.
 */
public class XMLConfig {

	private Map<String, String> parameters;

	public XMLConfig(final Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getParameter(final String key) {
		return parameters.getOrDefault(key, "");
	}

	public String getBrowser() {
		return Optional.ofNullable(getParameter("browser"))
				.orElse("firefox");
	}
}
