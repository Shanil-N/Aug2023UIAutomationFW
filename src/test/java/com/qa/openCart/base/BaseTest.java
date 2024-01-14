package com.qa.openCart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.openCart.factory.DriverFactory;
import com.qa.openCart.pages.AccountsPage;
import com.qa.openCart.pages.LoginPage;
import com.qa.openCart.pages.ProductInfoPage;
import com.qa.openCart.pages.RegisterPage;
import com.qa.openCart.pages.SearchResultsPage;

public class BaseTest {

	protected WebDriver driver;
	protected Properties prop;
	DriverFactory df;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;

	protected SoftAssert softAssert;

	@Parameters({ "browser" })
	@BeforeTest
	public void setUp(String browserName) {
		df = new DriverFactory();
		prop = df.initProp();

		if (browserName != null) {
			prop.setProperty("browser", browserName);
		}
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}