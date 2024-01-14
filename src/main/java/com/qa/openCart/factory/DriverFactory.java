package com.qa.openCart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.openCart.exceptions.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public static String highlight = null;

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser"); // To get the browser from properties file
		// String browserName = System.getProperty("browser");// To get the browser from
		// system

		highlight = prop.getProperty("highlight");
		
		System.out.println("Browser name is: " + browserName);

		optionsManager = new OptionsManager(prop);

		switch (browserName.toLowerCase().trim()) {

		case "chrome":
			// passing driver without thread local
			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			// passing drive with thread local
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "safari":
			// driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;
		default:
			System.out.println("Please pass the right browser name" + browserName);
			throw new FrameworkException("No browser found");

		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {

		// mvn clean install -Denv="qa"
		// mvn clean install
		FileInputStream ip = null;
		prop = new Properties();

		String envName = System.getProperty("env");
		System.out.println("Environment name is: " + envName);

		try {
			if (envName == null) {
				System.out.println("Entered environment is null.. hence running in QA environment");
				ip = new FileInputStream("./src/test/resource/config/config.qa.properties");
			} else {

				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resource/config/config.qa.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resource/config/config.dev.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resource/config/config.stage.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resource/config/config.uat.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resource/config/config.properties");
					break;

				default:
					System.out.println("Please pass right environment name: " + envName);
					throw new FrameworkException("Wrong Env Name: " + envName);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static String getScreenshot(String methodName) {

		TakesScreenshot ts = (TakesScreenshot) getDriver();
		File srcFile = ts.getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/screenshots/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";

		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

}