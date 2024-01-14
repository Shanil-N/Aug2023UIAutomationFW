package com.qa.openCart.factory;

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

		if (prop.getProperty("headless").trim().equalsIgnoreCase("true"))
			co.addArguments("--headless");

		if (prop.getProperty("incognito").trim().equalsIgnoreCase("true"))
			co.addArguments("--incognito");

		return co;
	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();

		if (prop.getProperty("headless").trim().equalsIgnoreCase("true")) {
			fo.addArguments("--headless");
		}

		if (prop.getProperty("incognito").trim().equalsIgnoreCase("true")) {
			fo.addArguments("--incognito");
		}
		return fo;
	}

	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();

		if (prop.getProperty("headless").trim().equalsIgnoreCase("true")) {
			eo.addArguments("--headless");
		}

		if (prop.getProperty("incognito").trim().equalsIgnoreCase("true")) {
			eo.addArguments("--incognito");
		}

		return eo;
	}

	// safari doesnot suppoert incognito and headless

}