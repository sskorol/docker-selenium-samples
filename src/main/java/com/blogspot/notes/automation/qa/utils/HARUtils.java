package com.blogspot.notes.automation.qa.utils;

import com.blogspot.notes.automation.qa.core.BaseTestListener;
import com.blogspot.notes.automation.qa.core.ProxyServer;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import static com.blogspot.notes.automation.qa.core.BaseTest.getProxyServer;
import static com.blogspot.notes.automation.qa.utils.AttachmentUtils.HAR_TEMPLATE;
import static com.blogspot.notes.automation.qa.utils.AttachmentUtils.attachHtml;
import static com.blogspot.notes.automation.qa.utils.PropertyUtils.Constants.HAR_VIEW_HEIGHT;
import static com.blogspot.notes.automation.qa.utils.PropertyUtils.Constants.HTTPD_DIR;
import static com.blogspot.notes.automation.qa.utils.PropertyUtils.Constants.USE_PROXY;
import static com.blogspot.notes.automation.qa.utils.TemplateUtils.getScope;

/**
 * Author: Serhii Korol.
 */
public final class HARUtils {

	private static final List<CompletableFuture<String>> HAR_FILES = new ArrayList<>();
	private static final Logger LOG = Logger.getLogger(BaseTestListener.class.getName());

	private HARUtils() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}

	public static void releaseHARFiles() {
		if (!HAR_FILES.isEmpty()) {
			CompletableFuture.allOf(HAR_FILES.stream().toArray(CompletableFuture[]::new)).join();
		}
	}

	public static void saveHar(final String attachmentName) {
		if (!USE_PROXY)
			return;

		final ProxyServer proxy = getProxyServer();
		final String harName = proxy.getHarName();
		final String harAddress = proxy.getHarDetailsLink();

		HAR_FILES.add(CompletableFuture.supplyAsync(() -> proxy.getBrowserMobProxy().getHarAsString())
				.whenComplete((har, err) -> {
					if (saveHar(HTTPD_DIR + File.separator + harName, har))
						attachHtml(attachmentName, HAR_TEMPLATE, getScope(harAddress, String.valueOf(HAR_VIEW_HEIGHT)));
					proxy.disposeProxy();
				})
		);
	}

	private static boolean saveHar(final String path, final String har) {
		boolean isSaved;

		Optional.ofNullable(new File(path).getParent())
				.map(File::new)
				.filter(dir -> !dir.exists())
				.ifPresent(File::mkdirs);

		try (final FileWriter writer = new FileWriter(path)) {
			writer.write("onInputData(" + har + ")");
			isSaved = true;
		} catch (Exception ex) {
			LOG.severe("Unable to save har file: " + ex);
			isSaved = false;
		}

		return isSaved;
	}
}
