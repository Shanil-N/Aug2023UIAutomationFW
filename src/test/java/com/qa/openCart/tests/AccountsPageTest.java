package com.qa.openCart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.openCart.base.BaseTest;
import com.qa.openCart.constants.AppConstants;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void accPageTitleTest() {
		Assert.assertEquals(accPage.getAccPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}

	@Test
	public void accPageUrlTest() {
		Assert.assertTrue(accPage.getAccPageUrl().contains(AppConstants.ACC_PAGE_URL_FRACTION));
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	@Test
	public void isSearchFieldExist() {
		Assert.assertTrue(accPage.isSearchFieldExist());
	}

	@Test
	public void accPageHeadersCountTest() {
		List<String> actualAccPageHeadersList = accPage.getAccountHeaders();
		// assert??
		System.out.println(actualAccPageHeadersList);

		Assert.assertEquals(actualAccPageHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}

	@Test
	public void accPageHeadersTest() {
		List<String> actualAccPageHeadersList = accPage.getAccountHeaders();
		System.out.println(actualAccPageHeadersList);

		Assert.assertEquals(actualAccPageHeadersList, AppConstants.ACCOUNTS_PAGE_HEADERS_LIST);
	}

	@Test
	public void searchTest() {
		searchResultsPage = accPage.doSearch("MacBook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		String actProductHeader = productInfoPage.getProductHeaderName();
		Assert.assertEquals(actProductHeader, "MacBook Pro");
	}
}