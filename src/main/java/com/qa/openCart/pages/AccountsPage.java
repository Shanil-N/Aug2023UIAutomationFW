package com.qa.openCart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.openCart.constants.AppConstants;
import com.qa.openCart.utils.ElementUtils;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtils eleUtil;

	private By logoutLink = By.linkText("Logout");
	private By searchField = By.name("search");
	private By accHeaders = By.cssSelector("div#content>h2");
	private By searchIcon = By.cssSelector("div#search button");

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(this.driver);
	}

	public boolean isLogoutLinkExist() {
		return eleUtil.waitForVisibilityOfElement(logoutLink, 5).isDisplayed();
	}

	public void logout() {
		if (isLogoutLinkExist()) {
			eleUtil.doClick(logoutLink);
		}
	}

	public boolean isSearchFieldExist() {
		return eleUtil.waitForVisibilityOfElement(searchField, 0).isDisplayed();
	}

	public List<String> getAccountHeaders() {
		List<WebElement> headersList = eleUtil.waitForVisibilityOfElements(accHeaders,
				AppConstants.MEDIUM_DEFAULT_WAIT);
		List<String> headersValueList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String text = e.getText();
			headersValueList.add(text);
		}
		return headersValueList;
	}

	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Acc page title: " + title);
		return title;
	}

	public String getAccPageUrl() {
		String url = eleUtil.waitForURLContains(AppConstants.ACC_PAGE_URL_FRACTION, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login page url: " + url);
		return url;
	}

	public SearchResultsPage doSearch(String searchKey) {
		eleUtil.waitForVisibilityOfElement(searchField, AppConstants.MEDIUM_DEFAULT_WAIT).clear();
		eleUtil.doSendKeys(searchField, searchKey);
		// eleUtil.waitForVisibilityOfElement(searchField,
		// AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}

}