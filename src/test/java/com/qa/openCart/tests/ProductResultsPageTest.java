package com.qa.openCart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.openCart.base.BaseTest;
import com.qa.openCart.constants.AppConstants;
import com.qa.openCart.utils.ExcelUtils;

public class ProductResultsPageTest extends BaseTest {

	@BeforeClass
	public void productInfoSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] { 
			{ "MacBook", "MacBook Pro", 4 },
			{ "MacBook", "MacBook Air", 4 },
			{ "iMac", "iMac", 3 },
			{ "Samsung", "Samsung SyncMaster 941BW", 1 }
		};
	}

	@DataProvider
	public Object[][] getSearchExcelTestData() {
		return ExcelUtils.getTestData(AppConstants.PRODUCT_DATA_SHEET_NAME);
	}

	@Test(dataProvider = "getSearchExcelTestData")
	public void productImagesTest(String searchKey, String productName, String imageCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(String.valueOf(productInfoPage.getProductImagesCount()), imageCount);
	}

	@DataProvider
	public Object[][] getProductInfoData() {
		return new Object[][] {
			{"MacBook","MacBook Pro", "Apple", "In Stock", "Product 18", "800","$2,000.00","$2,000.00"},
			{"MacBook","MacBook Air", "Apple", "Out Of Stock", "Product 17", "700","$1,202.00","$1,000.00"}
		};	
	}
	
	@DataProvider
	public Object[][] getProductInfoDataExcel(){
		return ExcelUtils.getTestData(AppConstants.PRODUCT_INFO_DATA_SHEET_NAME);
	}
	
	@Test(dataProvider = "getProductInfoData")
	public void productInfoTest(String searchKey, String productName, String brandName, String availabilityStatus,
			String productCode, String rewardPoints, String price, String priceExtTax) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);

		Map<String, String> productDetailsMap = productInfoPage.getProductDetails();

		softAssert.assertEquals(productDetailsMap.get("Brand"), brandName);
		softAssert.assertEquals(productDetailsMap.get("Availability"), availabilityStatus);
		softAssert.assertEquals(productDetailsMap.get("Product Code"), productCode);
		softAssert.assertEquals(productDetailsMap.get("Reward Points"), rewardPoints);

		softAssert.assertEquals(productDetailsMap.get("Price"), price);
		softAssert.assertEquals(productDetailsMap.get("ExTaxPrice"), priceExtTax);

		softAssert.assertAll();
	}
	
//	@Test
//	public void productInfoTest() {
//		searchResultsPage = accPage.doSearch("MacBook");
//		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
//
//		Map<String, String> productDetailsMap = productInfoPage.getProductDetails();
//
//		softAssert.assertEquals(productDetailsMap.get("Brand"), "Apple");
//		softAssert.assertEquals(productDetailsMap.get("Availability"), "In Stock");
//		softAssert.assertEquals(productDetailsMap.get("Product Code"), "Product 18");
//		softAssert.assertEquals(productDetailsMap.get("Reward Points"), "800");
//
//		softAssert.assertEquals(productDetailsMap.get("Price"), "$2,000.00");
//		softAssert.assertEquals(productDetailsMap.get("ExTaxPrice"), "$2,000.00");
//
//		softAssert.assertAll();
//	}
	
}