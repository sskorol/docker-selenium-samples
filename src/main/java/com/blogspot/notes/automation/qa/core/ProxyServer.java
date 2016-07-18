package com.blogspot.notes.automation.qa.core;

import com.blogspot.notes.automation.qa.proxy.BrowsermobProxyRestClient;
import lombok.Getter;
import org.openqa.selenium.Proxy;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.blogspot.notes.automation.qa.utils.PropertyUtils.Constants.HAR_FILES_ADDRESS;

/**
 * Author: Serhii Korol.
 */
@Getter
public class ProxyServer {

	private BrowsermobProxyRestClient browserMobProxy;
	private int port;
	private String harName;
	private String ip;
	private String initialPageId;

	public ProxyServer(final String ip, final int port, final String initialPageId) {
		this.ip = ip;
		this.initialPageId = initialPageId;
		this.harName = this.initialPageId + "_" + LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("MM.dd.yyyy_HH-mm-ss")) + ".har";
		this.browserMobProxy = new BrowsermobProxyRestClient(this.ip, port);
		this.port = browserMobProxy.getPort();
		this.browserMobProxy.setPort(this.port);
		this.browserMobProxy.newHar(this.initialPageId, true, true, true);
	}

	public void disposeProxy() {
		if (browserMobProxy != null) {
			browserMobProxy.stop();
			browserMobProxy.dispose();
		}
	}

	public Proxy getSeleniumProxy() {
		final String actualProxy = ip + ":" + port;
		return new Proxy().setHttpProxy(actualProxy).setFtpProxy(actualProxy).setSslProxy(actualProxy);
	}

	public String getHarDetailsLink() {
		return HAR_FILES_ADDRESS + File.separator + harName;
	}
}
