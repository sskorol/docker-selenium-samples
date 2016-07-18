package com.blogspot.notes.automation.qa.core;

import org.testng.*;

import static com.blogspot.notes.automation.qa.core.BaseTest.getDriver;
import static com.blogspot.notes.automation.qa.core.BaseTest.getVideoRecordingPath;
import static com.blogspot.notes.automation.qa.utils.AttachmentUtils.attachVideo;
import static com.blogspot.notes.automation.qa.utils.HARUtils.saveHar;

/**
 * Author: Serhii Korol.
 */
public class BaseTestListener implements IInvokedMethodListener {

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod() && getDriver() != null) {
			getDriver().quit();
			saveHar("HAR Viewer");
			attachVideo("Video", getVideoRecordingPath());
		}
	}
}
