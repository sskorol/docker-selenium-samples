package com.blogspot.notes.automation.qa.utils;

import com.blogspot.notes.automation.qa.pages.SearchPage;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import javaslang.control.Try;
import ru.yandex.qatools.allure.annotations.Step;

import static com.blogspot.notes.automation.qa.core.BaseTest.getDriver;

/**
 * Author: Serhii Korol.
 */
public final class PageObjectSupplier {

	public static <T> T $(Class<T> pageObject) {
		return ConstructorAccess.get(pageObject).newInstance();
	}

	@Step("Open browser and type the following address `{0}`.")
	public static SearchPage loadSiteUrl(final String url) {
		Try.run(() -> getDriver().navigate().to(url))
				.getOrElseThrow(ex -> new IllegalArgumentException("Unable to navigate to specified URL", ex));
		return $(SearchPage.class);
	}

	private PageObjectSupplier() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}
}
