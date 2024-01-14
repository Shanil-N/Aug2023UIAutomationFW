package com.qa.openCart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.openCart.base.BaseTest;
import com.qa.openCart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic 100: Design open cart login page")
@Story("US 101: Login page features")
@Feature("F50: Feaute login page")
public class LoginPageTest extends BaseTest {

	@Description("Login page title test")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void loginPageTitle() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Description("Login page url test")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 2)
	public void loginPageUrl() {
		String actUrl = loginPage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
	}

	@Description("Verifying forgot password link test")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3)
	public void forgotPwdLinkExist() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExists());
	}

	@Description("Verifying application logo test")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4)
	public void appLogoExist() {
		Assert.assertTrue(loginPage.isLogoExist());
	}
	
	@Description("Search field availability test")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 5)
	public void searchFieldAvailableTest() {
		Assert.assertTrue(loginPage.searchAvailability());
	}

	@Description("Verifying user is able to login test with correct login")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	@DataProvider
	public Object[][] footerNames() {
		return new Object[][] { { "About Us", "Privacy Policy", "Contact Us", "Brands", "My Account","Affiliate" } };
	}

	@Test(dataProvider = "footerNames")
//	public void footerTest(String link1Name, String link2Name, String link3Name, String link4Name, String link5Name) {
//		List<String> footersAvailable = loginPage.footersPresent();		
//
//		softAssert.assertEquals(footersAvailable.contains(link1Name), true,link1Name+" not not found or mismatch in name");
//		softAssert.assertEquals(footersAvailable.contains(link2Name), true,link2Name+" not not found or mismatch in name");
//		softAssert.assertEquals(footersAvailable.contains(link3Name), true,link3Name+" not not found or mismatch in name");
//		softAssert.assertEquals(footersAvailable.contains(link4Name), true,link4Name+" not not found or mismatch in name");
//		softAssert.assertEquals(footersAvailable.contains(link5Name), true,link5Name+" not not found or mismatch in name");
//		
//		softAssert.assertAll();
//	}
	
	public void footerTest(String [] linkNames) {
		List<String> footersAvailable = loginPage.footersPresent();
		
		for(String linkName: linkNames) {
			softAssert.assertEquals(footersAvailable.contains(linkName), true, linkName+" not found or name mismatch");
		}
		softAssert.assertAll();
		
	}

}