package com.blogspot.notes.automation.qa.entities;

import com.blogspot.notes.automation.qa.utils.PropertyUtils.Constants;
import org.codehaus.jackson.map.ObjectMapper;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import ru.stqa.selenium.factory.WebDriverFactory;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Author: Serhii Korol.
 */
public class WebDriverContainer {

	private WebDriver driver;
	private XMLConfig config;
	private String videoRecordingPath;
	private String currentTestName;

	public WebDriverContainer(final ITestContext context, final Method method) {
		this.config = new XMLConfig(context.getCurrentXmlTest().getAllParameters());
		this.currentTestName = method.getName();
		this.videoRecordingPath = getCurrentTestName() + "-" + getBrowser() + "-" +
				getDefaultPlatform() + "-" + LocalDateTime.now();
		this.driver = WebDriverFactory.getDriver(Constants.HUB_URL, getCapabilities());
	}

	public WebDriver getDriver() {
		return driver;
	}

	public String getVideoRecordingPath() {
		return videoRecordingPath;
	}

	private Capabilities getCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(getBrowser());
		capabilities.setPlatform(getDefaultPlatform());

		String videoInfo = getVideoRecordingInfo();
		if (!videoInfo.isEmpty()) {
			capabilities.setCapability("videoInfo", videoInfo);
		}

		return capabilities;
	}

	private String getVideoRecordingInfo() {
		try {
			VideoInfo videoInfo = new VideoInfo(Constants.VIDEO_OUTPUT_DIR, getVideoRecordingPath(),
					Constants.QUALITY, Constants.FRAME_RATE);
			return new ObjectMapper().writeValueAsString(videoInfo);
		} catch (Exception ignored) {
			return "";
		}
	}

	private String getBrowser() {
		return config.getBrowser();
	}

	private String getCurrentTestName() {
		return currentTestName;
	}

	private Platform getDefaultPlatform() {
		return Platform.LINUX;
	}
}
