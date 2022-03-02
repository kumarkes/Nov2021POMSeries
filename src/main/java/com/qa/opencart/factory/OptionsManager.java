package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless")))
			co.addArguments("--headless");
		if (Boolean.parseBoolean(prop.getProperty("incognito")))
			co.addArguments("--incognito");
		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setBrowserVersion(prop.getProperty("browserverion"));
			co.setPlatformName("linux");
		}
		return co;
	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless")))
			fo.addArguments("--headless");
		if (Boolean.parseBoolean(prop.getProperty("incognito")))
			fo.addArguments("--incognito");
		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			fo.setBrowserVersion(prop.getProperty("browserverion"));
			fo.setPlatformName("linux");
		}
		return fo;
	}

	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless")))
			eo.addArguments("--headless");
		if (Boolean.parseBoolean(prop.getProperty("incognito")))
			eo.addArguments("--incognito");
		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			eo.setBrowserVersion(prop.getProperty("browserverion"));
			eo.setPlatformName("linux");
		}
		return eo;
	}

}
