package com.qa.openCart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.openCart.constants.AppConstants;
import com.qa.openCart.utils.ElementUtils;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtils eleUtil;

	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

	//private Map<String, String> productMap = new HashMap<String, String>();
	//private Map<String, String> productMap = new LinkedHashMap<String, String>();
	private Map<String, String> productMap = new LinkedHashMap<String, String>();

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(this.driver);
	}

	public String getProductHeaderName() {
		String productHeaderVal = eleUtil.doElementGetText(productHeader);
		System.out.println("Product header is: " + productHeaderVal);
		return productHeaderVal;
	}

	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForVisibilityOfElements(productImages, AppConstants.MEDIUM_DEFAULT_WAIT).size();
		System.out.println("Product " + getProductHeaderName() + " Images Count " + imagesCount);
		return imagesCount;
	}

//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock

	public void getProductMetaData() {
		List<WebElement> metaDataList = eleUtil.waitForVisibilityOfElements(productMetaData,
				AppConstants.MEDIUM_DEFAULT_WAIT);
		for (WebElement e : metaDataList) {
			String metaData = e.getText();
			String metaKey = metaData.split(":")[0].trim();
			String metaVal = metaData.split(":")[1].trim();
			productMap.put(metaKey, metaVal);
		}
	}

	public void getProductPriceData() {
		List<WebElement> metaPriceList = eleUtil.waitForVisibilityOfElements(productPriceData,
				AppConstants.MEDIUM_DEFAULT_WAIT);
		String productPrice = metaPriceList.get(0).getText();
		String productExTaxPrice = metaPriceList.get(1).getText().split(":")[1].trim();

		productMap.put("Price", productPrice);
		productMap.put("ExTaxPrice", productExTaxPrice);
	}

	public Map<String, String> getProductDetails() {
		productMap.put("Product Name", getProductHeaderName());
		getProductMetaData();
		getProductPriceData();

		System.out.println(productMap);
		return productMap;
	}

}