package com.blogspot.notes.automation.qa.testcases;

import com.blogspot.notes.automation.qa.core.BaseTest;
import com.blogspot.notes.automation.qa.pages.SearchPage;
import org.testng.annotations.Test;

import static com.blogspot.notes.automation.qa.utils.PageObjectSupplier.$;
import static com.blogspot.notes.automation.qa.utils.PageObjectSupplier.loadSiteUrl;
import static org.testng.Assert.assertEquals;

/**
 * Author: Serhii Korol.
 */
public class GoogleTests extends BaseTest {

	@Test
	public void searchForQualityInGoogle() {
		loadSiteUrl("https://google.com.ua")
				.searchFor("quality");

		assertEquals($(SearchPage.class).getSearchInputValue(), "quality");
	}

	@Test
	public void searchForAutomationInGoogle() {
		loadSiteUrl("https://google.com.ua")
				.searchFor("automation");

		assertEquals($(SearchPage.class).getSearchInputValue(), "automation");
	}
}
