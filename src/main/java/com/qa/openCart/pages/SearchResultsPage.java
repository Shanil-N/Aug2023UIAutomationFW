package com.qa.openCart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.openCart.constants.AppConstants;
import com.qa.openCart.utils.ElementUtils;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(this.driver);
	}
	
	public ProductInfoPage selectProduct(String productName) {
		eleUtil.waitForVisibilityOfElement(By.linkText(productName), AppConstants.MEDIUM_DEFAULT_WAIT).click();;
		return new ProductInfoPage(driver);
	}

}