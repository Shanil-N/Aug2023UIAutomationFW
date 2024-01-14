package com.qa.openCart.tests;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.openCart.base.BaseTest;
import com.qa.openCart.constants.AppConstants;
import com.qa.openCart.utils.ExcelUtils;

public class RegistrePageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}

	// method to generate random data
	public String getRandomEmailID() {
		return "testautomation" + System.currentTimeMillis() + "@opencart.com";
		// return "testautomation"+UUID.randomUUID()+"@opencart.com";

		// We can also pass firstName or any data to getRandomEmailID method and use it
		// in return statement
		// return firstName+UUID.randomUUID()+"@opencart.com";
		// return firstName+System.currentTimeMillis+"@opencart.com";
	}

//	@DataProvider
//	public Object[][] getUserRegData() {
//		return new Object[][] { { "Rahul11", "Automation11", "1100330012", "Automation@123", "Yes" },
//				{ "Thomas11", "Automation11", "3300110012", "Automation@123", "Yes" },
//				{ "Jyothi11", "Automation11", "1133110012", "Automation@123", "Yes" } };
//	}
	// Above one is valid and commented to illustrate the data fetching from excel.
	// Since the data used in above one is not clubed with test methods or page
	// methods, it is valid
	
	@DataProvider
	public Object[][] getUserRegTestExcelData() {
		Object regData[][] = ExcelUtils.getTestData(AppConstants.REGISTER_DATA_SHEET_NAME);
		return regData;
	}

	//@Test(dataProvider = "getUserRegData")
	@Test(dataProvider = "getUserRegTestExcelData")
	public void userRegTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		
		boolean isRegDone = registerPage.userRegistration(firstName, lastName, getRandomEmailID(), telephone, password,
				subscribe);

		Assert.assertTrue(isRegDone);
		
	}

}