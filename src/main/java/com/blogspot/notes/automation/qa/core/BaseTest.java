package com.blogspot.notes.automation.qa.core;

import com.blogspot.notes.automation.qa.entities.WebDriverContainer;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.Optional;

import static com.blogspot.notes.automation.qa.utils.HARUtils.releaseHARFiles;

/**
 * Author: Serhii Korol.
 */
public class BaseTest {

	private static final ThreadLocal<WebDriverContainer> WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<>();

	@BeforeMethod
	public void setUp(final ITestContext context, final Method method) {
		WEB_DRIVER_THREAD_LOCAL.set(new WebDriverContainer(context, method));
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		WEB_DRIVER_THREAD_LOCAL.remove();
	}

	@AfterSuite
	public void cleanUpHARFiles() {
		releaseHARFiles();
	}

	public static WebDriver getDriver() {
		return Optional.ofNullable(WEB_DRIVER_THREAD_LOCAL.get())
				.map(WebDriverContainer::getDriver)
				.orElseThrow(() -> new IllegalStateException("Unable to get WebDriver instance"));
	}

	public static String getVideoRecordingPath() {
		return Optional.ofNullable(WEB_DRIVER_THREAD_LOCAL.get())
				.map(WebDriverContainer::getVideoRecordingPath)
				.orElseThrow(() -> new IllegalStateException("Unable to get video recording path"));
	}

	public static ProxyServer getProxyServer() {
		return Optional.ofNullable(WEB_DRIVER_THREAD_LOCAL.get())
				.map(WebDriverContainer::getProxyServer)
				.orElseThrow(() -> new IllegalStateException("Unable to get ProxyServer instance"));
	}
}
