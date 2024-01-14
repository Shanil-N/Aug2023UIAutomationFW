package com.qa.openCart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.openCart.constants.AppConstants;
import com.qa.openCart.utils.ElementUtils;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtils eleUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");

	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[1]//input[@type='radio']");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[2]//input[@type='radio']");

	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");

	private By successMessg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(this.driver);
	}

	public boolean userRegistration(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {

		eleUtil.waitForVisibilityOfElement(this.firstName, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);

		if (subscribe.equalsIgnoreCase("Yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}

		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);

		String successMesg = eleUtil.waitForVisibilityOfElement(successMessg, AppConstants.MEDIUM_DEFAULT_WAIT)
				.getText();
		System.out.println(successMesg);

		if (successMesg.contains(AppConstants.USER_REGISTRATION_SUCCESS_MSG)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		} else {
			return false;
		}
	}
}