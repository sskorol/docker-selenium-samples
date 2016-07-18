package com.blogspot.notes.automation.qa.utils;

import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.blogspot.notes.automation.qa.utils.PropertyUtils.Constants.HOST_OS_OUTPUT_DIR;
import static com.blogspot.notes.automation.qa.utils.TemplateUtils.execute;
import static com.google.common.io.Files.toByteArray;
import static org.awaitility.Awaitility.await;

/**
 * Author: Serhii Korol.
 */
public final class AttachmentUtils {

	public static final String HAR_TEMPLATE = "templates/har.mustache";
	public static final String WORK_DIR = System.getProperty("user.home") + HOST_OS_OUTPUT_DIR;

	private static final AtomicInteger COUNTER = new AtomicInteger();

	private AttachmentUtils() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}

	@Attachment(value = "{0}", type = "video/mp4")
	public static byte[] attachVideo(String name, String path) {
		try {
			File mp4 = new File(WORK_DIR + "/" + path + ".mp4");
			File mp4Tmp = new File(WORK_DIR + "/tmp/" + path + ".mp4");

			await().atMost(PropertyUtils.Constants.VIDEO_WAIT_TIMEOUT, TimeUnit.SECONDS)
					.pollDelay(1, TimeUnit.SECONDS)
					.ignoreExceptions()
					.until(() -> mp4.exists() && mp4.length() == mp4Tmp.length());

			return toByteArray(mp4);
		} catch (Exception ignored) {
			return new byte[0];
		}
	}

	@Attachment(value = "{0}", type = "text/html")
	public static byte[] attachHtml(final String name, final String templateName, final Map<String, Object> args) {
		final String outName = "target" + File.separator + "attachment" + COUNTER.incrementAndGet();
		try {
			execute(templateName, outName, args);
			return toByteArray(new File(outName));
		} catch (Exception ignored) {
			return new byte[0];
		}
	}
}
