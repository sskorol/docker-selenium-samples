package com.blogspot.notes.automation.qa.utils;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

import java.util.Arrays;

/**
 * Author: Serhii Korol.
 */
public final class PropertyUtils {

	private static final CompositeConfiguration MIXED_CONFIG;

	static {
		try {
			MIXED_CONFIG = new CompositeConfiguration(Arrays.asList(
					new SystemConfiguration(),
					new PropertiesConfiguration("properties/config.properties")));
		} catch (Exception ex) {
			throw new IllegalArgumentException("Can't load properties", ex);
		}
	}

	private PropertyUtils() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}

	public static final class Constants {
		public static final String HUB_URL = getStringValue("hub.url.arg");
		public static final String VIDEO_OUTPUT_DIR = getStringValue("video.output.dir.arg");
		public static final String HOST_OS_OUTPUT_DIR = getStringValue("host.os.output.dir.arg");
		public static final int QUALITY = getIntValue("quality.arg");
		public static final int DURATION = getIntValue("duration.arg");
		public static final int HAR_VIEW_HEIGHT = getIntValue("har.view.height.arg");
		public static final int FRAME_RATE = getIntValue("frame.rate.arg");
		public static final int VISIBILITY_TIMEOUT = getIntValue("element.visibility.timeout.arg");
		public static final int VIDEO_WAIT_TIMEOUT = getIntValue("video.wait.timeout.arg");
		public static final int PROXY_PORT = getIntValue("proxy.port.arg");
		public static final boolean USE_PROXY = getBoolValue("use.proxy.arg");
		public static final String HTTPD_DIR = getStringValue("httpd.dir.arg");
		public static final String HAR_FILES_ADDRESS = getStringValue("har.files.address.arg");

		private Constants() {
			throw new UnsupportedOperationException("Illegal access to private constructor");
		}
	}

	public static String getStringValue(final String key) {
		return MIXED_CONFIG.getString(key);
	}

	public static int getIntValue(final String key) {
		return MIXED_CONFIG.getInt(key);
	}

	public static boolean getBoolValue(final String key) {
		return MIXED_CONFIG.getBoolean(key);
	}
}
