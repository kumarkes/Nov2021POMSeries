package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal();

	/**
	 * This method is used to initialize the driver using browser name
	 * 
	 * @param browserName
	 * @return this returns the WebDriver
	 */

	public WebDriver init_driver(Properties prop) {
		String browserName = prop.getProperty("browser").trim();
		String browserVersion = prop.getProperty("browserversion").trim();
		highlight = prop.getProperty("highlight").trim();

		System.out.println("Browser Name Is : " + browserName + "BrowserVersion is : " + browserVersion);
		optionsManager = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase("chrome")) {
			// WebDriverManager.chromedriver().setup();
			// driver = new ChromeDriver(optionsManager.getChromeOptions());

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// Remote Execution
				init_remoteDriver("chrome");
			} else {
				// Local Execution
				WebDriverManager.chromedriver().setup();
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}

		} else if (browserName.equalsIgnoreCase("firefox")) {
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// Remote Execution
				init_remoteDriver("firefox");
			} else {
				WebDriverManager.firefoxdriver().setup();
				// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}

		} else if (browserName.equalsIgnoreCase("edge")) {
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// Remote Execution
				init_remoteDriver("edge");
			} else {
				WebDriverManager.edgedriver().setup();
				// driver = new EdgeDriver(optionsManager.getEdgeOptions());
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));

			}
		} else {
			System.out.println("Please pass the right browser : " + browserName);
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());
		return getDriver();
	}

	/**
	 * Run test cases on remote machine
	 * 
	 * @param browser
	 */
	private void init_remoteDriver(String browser) {
		System.out.println("Running test cases on remote grid server : " + browser);
		if (browser.equals("chrome")) {
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browser.equals("firefox")) {
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This will return the thread local copy of the driver
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method initialize the properties
	 * 
	 * @return this return properties reference
	 */
	public Properties init_prop() {

		prop = new Properties();
		FileInputStream ip = null;

		// mvn clean install -Denv="qa"
		String envName = System.getProperty("env");// qa/stage/dev
		System.out.println(envName + "------------");
		System.out.println("Running test on environments...." + envName);
		try {
			switch (envName.toLowerCase()) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "stage":
				ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;

			default:
				System.out.println("Please pass the right env");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
		
	}

	// Thread Local --JDK 1.8 --> Create a local copy of driver
	// Will help to Set Driver by create local driver
	// get driver -- driver
	// Solve : Null Pointer Problem
	// You can take your driver to anywhere in the framework
	// Better Thread Management
	// To avoid the dead local condition - TL Driver copy
	// large test case count -- 200,300 TCS (Extent Report starts to give wrong
	// report) --> Proper Test Results

	/**
	 * take screenshot
	 */
	public String getScreenshot() {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
