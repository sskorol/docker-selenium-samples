package com.blogspot.notes.automation.qa.pages;

import com.blogspot.notes.automation.qa.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Author: Serhii Korol.
 */
public class SearchPage extends BasePage {

	private By inputSearch = By.id("lst-ib");
	private By linkResults = By.cssSelector(".r>a");

	@Step("Search for `{0}` in google.")
	public SearchPage searchFor(final String text) {
		setText(inputSearch, text + Keys.ENTER);
		waitForItemsAppearance(linkResults);
		return this;
	}

	public String getSearchInputValue() {
		return getValue(inputSearch);
	}
}
