package com.blogspot.notes.automation.qa.entities;

import com.blogspot.notes.automation.qa.core.ProxyServer;
import com.blogspot.notes.automation.qa.utils.PropertyUtils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import ru.stqa.selenium.factory.WebDriverFactory;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.logging.Logger;

import static com.blogspot.notes.automation.qa.utils.PropertyUtils.Constants.DURATION;
import static com.blogspot.notes.automation.qa.utils.PropertyUtils.Constants.PROXY_PORT;
import static com.blogspot.notes.automation.qa.utils.PropertyUtils.Constants.USE_PROXY;

/**
 * Author: Serhii Korol.
 */
@Getter
public class WebDriverContainer {

	private final WebDriver driver;
	private final XMLConfig config;
	private final String videoRecordingPath;
	private final String currentTestName;
	private ProxyServer proxyServer;

	private static final Logger LOG = Logger.getLogger(WebDriverContainer.class.getName());

	public WebDriverContainer(final ITestContext context, final Method method) {
		this.config = new XMLConfig(context.getCurrentXmlTest().getAllParameters());
		this.currentTestName = method.getName();
		this.videoRecordingPath = getCurrentTestName() + "-" + getBrowser() + "-" +
				getDefaultPlatform() + "-" + LocalDateTime.now();
		if (USE_PROXY) {
			this.proxyServer = new ProxyServer(config.getProxyIp(), PROXY_PORT,
					context.getName() + "-" + method.getName() + "-" + config.getBrowser());
		}
		this.driver = WebDriverFactory.getDriver(Constants.HUB_URL, getCapabilities());
	}

	private Capabilities getCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(getBrowser());
		capabilities.setPlatform(getDefaultPlatform());

		if (USE_PROXY && proxyServer != null) {
			capabilities.setCapability(CapabilityType.PROXY, proxyServer.getSeleniumProxy());
		} else {
			capabilities.setCapability(CapabilityType.PROXY, new Proxy().setProxyType(Proxy.ProxyType.DIRECT));
		}

		Optional.of(getVideoRecordingInfo())
				.filter(videoInfo -> !videoInfo.isEmpty())
				.ifPresent(videoInfo -> capabilities.setCapability("videoInfo", videoInfo));

		return capabilities;
	}

	private String getVideoRecordingInfo() {
		try {
			VideoInfo videoInfo = new VideoInfo(Constants.VIDEO_OUTPUT_DIR, getVideoRecordingPath(),
					Constants.QUALITY, Constants.FRAME_RATE, Duration.of(DURATION, ChronoUnit.MINUTES));
			return new ObjectMapper().findAndRegisterModules().writeValueAsString(videoInfo);
		} catch (Exception ex) {
			LOG.severe("Unable to create VideoInfo json: " + ex);
			return "";
		}
	}

	private String getBrowser() {
		return config.getBrowser();
	}

	private Platform getDefaultPlatform() {
		return Platform.LINUX;
	}
}
