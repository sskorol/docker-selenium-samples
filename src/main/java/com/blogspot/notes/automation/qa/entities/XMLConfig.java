package com.blogspot.notes.automation.qa.entities;

import javaslang.control.Try;
import lombok.RequiredArgsConstructor;
import org.zeroturnaround.exec.ProcessExecutor;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

/**
 * Author: Serhii Korol.
 */
@RequiredArgsConstructor
public class XMLConfig {

	private final Map<String, String> parameters;
	private static final String LOCALHOST = "127.0.0.1";

	public String getParameter(final String key) {
		return parameters.getOrDefault(key, "");
	}

	public String getBrowser() {
		return Optional.ofNullable(getParameter("browser"))
				.orElse("firefox");
	}

	public String getProxyIp() {
		return Try.of(() -> new ProcessExecutor()
				.command("/bin/sh", Paths.get(ClassLoader.getSystemResource("scripts/proxyIpExtractor.sh").toURI()).toString())
				.readOutput(true)
				.execute()
				.outputUTF8()
				.trim())
				.getOrElseGet(ex -> LOCALHOST);
	}
}
