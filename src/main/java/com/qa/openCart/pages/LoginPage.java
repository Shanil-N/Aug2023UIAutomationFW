package com.qa.openCart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.openCart.constants.AppConstants;
import com.qa.openCart.utils.ElementUtils;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtils eleUtil;

	// By locators
	private By userName = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotften Password");
	private By logo = By.xpath("//img[@title='naveenopencart']");
	private By searchField = By.name("search");

	private By registerLink = By.linkText("Register");

	private By footerLinks = By.xpath("//footer//li");

	// page constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(this.driver);
	}

	// page actions or page methods or features functions
	@Step("Getting login page title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login page title: " + title);
		return title;
	}

	@Step("Getting login page url")
	public String getLoginPageUrl() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login page url: " + url);
		return url;
	}
	
	@Step("Checking forgot password link exist")
	public boolean isForgotPwdLinkExists() {
		return eleUtil.waitForVisibilityOfElement(forgotPwdLink, AppConstants.SHORT_DEFAULT_WAIT).isDisplayed();
	}
	
	@Step("Checking logo exist")
	public boolean isLogoExist() {
		return eleUtil.waitForVisibilityOfElement(logo, AppConstants.SHORT_DEFAULT_WAIT).isDisplayed();
	}

	@Step("Username is: {0} and password is: {1}")
	public AccountsPage doLogin(String username, String pwd) {
		eleUtil.waitForVisibilityOfElement(userName, AppConstants.SHORT_DEFAULT_WAIT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);

		return new AccountsPage(driver);
	}

	@Step("Checking search field exist")
	public boolean searchAvailability() {
		return eleUtil.checkSingleElementPresent(searchField);
	}
	
	@Step("Navigating to registration page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForVisibilityOfElement(registerLink, AppConstants.MEDIUM_DEFAULT_WAIT).click();
		;
		return new RegisterPage(driver);
	}

	@Step("Checking footers exist")
	public List<String> footersPresent() {
		return eleUtil.getElementsTextList(footerLinks);
	}
}