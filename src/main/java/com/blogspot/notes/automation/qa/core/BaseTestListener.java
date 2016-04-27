package com.blogspot.notes.automation.qa.core;

import com.blogspot.notes.automation.qa.utils.PropertyUtils.Constants;
import com.google.common.io.Files;
import org.testng.*;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.blogspot.notes.automation.qa.core.BaseTest.getDriver;
import static com.blogspot.notes.automation.qa.core.BaseTest.getVideoRecordingPath;
import static com.jayway.awaitility.Awaitility.await;

/**
 * Author: Serhii Korol.
 */
public class BaseTestListener implements ITestListener, IInvokedMethodListener {

	private static final String WORK_DIR = System.getProperty("user.home") + Constants.HOST_OS_OUTPUT_DIR;

	@Attachment(value = "{0}", type = "video/mp4")
	public byte[] attachVideo(String name, String path) {
		try {
			File mp4 = new File(WORK_DIR + "/" + path + ".mp4");
			File mp4Tmp = new File(WORK_DIR + "/tmp/" + path + ".mp4");

			await().atMost(Constants.VIDEO_WAIT_TIMEOUT, TimeUnit.SECONDS)
					.pollDelay(1, TimeUnit.SECONDS)
					.ignoreExceptions()
					.until(() -> mp4.exists() && mp4.length() == mp4Tmp.length());

			return Files.toByteArray(mp4);
		} catch (Exception ignored) {
			return new byte[0];
		}
	}

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		attachVideo("Video", getVideoRecordingPath());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		attachVideo("Video", getVideoRecordingPath());
	}

	@Override
	public void onTestSkipped(ITestResult result) {

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {

	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod() && getDriver() != null) {
			getDriver().quit();
		}
	}
}
