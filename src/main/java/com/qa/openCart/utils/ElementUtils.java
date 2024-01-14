package com.qa.openCart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.openCart.exceptions.FrameworkException;
import com.qa.openCart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtils {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtils(WebDriver driver) {
		this.driver = driver; // this.global variable = local variable
		jsUtil = new JavaScriptUtil(driver);
	}

	private void isHighlight(WebElement element) {
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doElementGetText(String locatorType, String locatorValue) {
		return getElement(locatorType, locatorValue).getText();
	}

	@Step("Entering value: {1} to element: {0}")
	public void doSendKeys(String locatorType, String locatorValue, String value) {
		getElement(locatorType, locatorValue).sendKeys(value);
	}

	@Step("Entering value: {1} to element: {0}")
	public void doSendKeys(By locator, String value) {
		getElement(locator).sendKeys(value);
	}

	@Step("Clicking on element: {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doClick(String locatorType, String locatorValue) {
		getElement(locatorType, locatorValue).click();
	}

	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		isHighlight(element);
		return element;
	}

	public WebElement getElement(String locatorType, String locatorValue) {
		WebElement element = driver.findElement(getBy(locatorType, locatorValue));
		isHighlight(element);
		return element;
	}

	public By getBy(String locatorType, String locatorValue) {

		By by = null;

		switch (locatorType.toLowerCase()) {
		case "id":
			by = By.id(locatorValue);
			break;
		case "name":
			by = By.name(locatorValue);
			break;
		case "class":
			by = By.className(locatorValue);
			break;
		case "xpath":
			by = By.xpath(locatorValue);
			break;
		case "css":
			by = By.cssSelector(locatorValue);
			break;
		case "linktext":
			by = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			by = By.partialLinkText(locatorValue);
			break;
		case "tag":
			by = By.tagName(locatorValue);
			break;
		default:
			System.out.println("Wrong locator type is passed: " + locatorType);
			throw new FrameworkException("WRONG LOCATOR TYPE");
		}
		return by;
	}

	// WAF: capture the texts of the links and return List<String>
	public List<String> getElementsTextList(By locator) {
		List<WebElement> elementsList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();

		for (WebElement e : elementsList) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	// WAF: capture specific attribute from the list
	public List<String> getElementsAttributeList(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrList = new ArrayList<String>();

		for (WebElement e : eleList) {
			String attrValue = e.getAttribute(attrName);
			eleAttrList.add(attrValue);
		}
		return eleAttrList;
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void search(By searchField, By suggestions, String searchKey, String suggName) throws InterruptedException {
		// driver.findElement(searchField).sendKeys(searchKey);
		doSendKeys(searchField, searchKey);

		Thread.sleep(3000);

		// List<WebElement> sugList = driver.findElements(suggestions);
		List<WebElement> sugList = getElements(suggestions);

		for (WebElement e : sugList) {
			if (e.getText().contains(suggName)) {
				e.click();
				break;
			}
		}
	}

	public void clickOnElement(String text, By locator) {
		List<WebElement> eleList = getElements(locator);

		for (WebElement l : eleList) {
			String linkText = l.getText();
			System.out.println(linkText);
			if (linkText.equals(text)) {
				l.click();
				break;
			}
		}
	}

	public boolean checkSingleElementPresent(By locator) {
		return driver.findElements(locator).size() == 1 ? true : false;
	}

	public boolean checkElementPresent(By locator) {
		return driver.findElements(locator).size() >= 1 ? true : false;
	}

	/************** Select drop down utils ***************/

	private Select createSelect(By locator) {
		Select select = new Select(getElement(locator));
		return select;
	}

	public void doSelectDropDownByIndex(By locator, int index) {
//		Select select = new Select(getElement(locator));
//		select.selectByIndex(index);
		createSelect(locator).selectByIndex(index);
	}

	public void doSelectDropDownByVisibleText(By locator, String visibleText) {
//		Select select = new Select(getElement(locator));
//		select.selectByVisibleText(visibleText);
		createSelect(locator).selectByVisibleText(visibleText);
	}

	public void doSelectDropDownByValue(By locator, String value) {
//		Select select = new Select(getElement(locator));
//		select.selectByValue(value);
		createSelect(locator).selectByValue(value);
	}

	public int getDropDownOptionsCount(By locator) {
//		Select select = new Select(getElement(locator));
//		return select.getOptions().size();
		return createSelect(locator).getOptions().size();
	}

	public List<String> getDropDownOptions(By locator) {
		// Select select = new Select(getElement(locator));

		List<WebElement> optionsList = createSelect(locator).getOptions();
		List<String> optionsTextList = new ArrayList<String>();

		for (WebElement ele : optionsList) {
			String text = ele.getText();
			optionsTextList.add(text);
		}

		return optionsTextList;
	}

	public void selectDropDownOption(By locator, String dropDownValue) {

		// Select select = new Select(getElement(locator));

		List<WebElement> optionsLists = createSelect(locator).getOptions();

		System.out.println(optionsLists.size());

		for (WebElement ele : optionsLists) {
			if (ele.getText().equalsIgnoreCase(dropDownValue)) {
				ele.click();
				break;
			}
		}
	}

	public void selectDropDownOptionsWithoutSelect(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equalsIgnoreCase(value)) {
				e.click();
				break;
			}
		}
	}

	public boolean isDropDownMultiple(By locator) {
		// Select select = new Select(getElement(locator));
		// return select.isMultiple() ? true : false;
		return createSelect(locator).isMultiple() ? true : false;

	}

	public void selectDropDownMutipleValues(By locator, String... values) {
		/*
		 * This method is used to select the value from drop down. It can select: 1.
		 * Single selection 2. Multiple selections 3. All selection: Please "All" as a
		 * value to select all values
		 * 
		 * @param locator
		 * 
		 * @param values
		 */
		// Select select = new Select(getElement(locator));
		if (isDropDownMultiple(locator)) {
			if (values[0].equalsIgnoreCase("all")) {
				List<WebElement> allOptions = createSelect(locator).getOptions();
				for (WebElement e : allOptions) {
					e.click();
				}
			} else {
				for (String value : values) {
					createSelect(locator).selectByVisibleText(value);
				}
			}
		}
	}

	public void selectDropDownMutipleValues(By locator, By optionsLocator, String... values) {
		/*
		 * This method is used to select the value from drop down. It can select: 1.
		 * Single selection 2. Multiple selections 3. All selection: Please "All" as a
		 * value to select all values
		 * 
		 * @param locator
		 * 
		 * @param values
		 */

		Select select = new Select(getElement(locator));
		if (isDropDownMultiple(locator)) {
			if (values[0].equalsIgnoreCase("all")) {
				List<WebElement> allOptions = getElements(optionsLocator);
				for (WebElement e : allOptions) {
					e.click();
				}
			} else {
				for (String value : values) {
					select.selectByVisibleText(value);
				}
			}
		}
	}

	public void jqueryDropDownSelection(By locator, By choiceLocator, String... values) {

		getElement(locator).click();

		List<WebElement> choiceList = getElements(choiceLocator);

		if (values[0].equalsIgnoreCase("all")) {
			for (WebElement e : choiceList) {
				e.click();
			}
		} else {
			for (String value : values) {
				for (WebElement ee : choiceList) {
					if (ee.getText().equalsIgnoreCase(value)) {
						ee.click();
					}
				}
			}
		}

	}

//	public void jqueryDropDownSelection(By locator, By optionsLocator, String... values) {
//		getElement(locator).click();
//		List<WebElement> optionsList = getElements(optionsLocator);
//
//		if (values[0].equalsIgnoreCase("all")) {
//			for (WebElement e : optionsList) {
//				try {
//					e.click();
//				} catch (ElementNotInteractableException ex) {
//					System.out.println("Drop down options are over.....");
//					break;
//				}
//			}
//		} else {
//			boolean found = false;
//			for (String value : values) {
//				for (WebElement e : optionsList) {
//					if (e.getText().equalsIgnoreCase(value)) {
//						e.click();
//						found = true;
//						break;
//					}
//				}
//			}
//			if (found == false) {
//				System.out.println("Value not found in the drop down");
//			}
//		}
//	}

	//////////////////////////////// Actions Utils////////////////////////////////

	public void twoMenuHandle(By parentMenuLocator, By childMenuLocator) throws InterruptedException {
		Actions act = new Actions(driver);

		act.moveToElement(getElement(parentMenuLocator)).build().perform();
		Thread.sleep(1000);
		doClick(childMenuLocator);

	}

	public void fourMenuHandle(By parentMenuLocator, By firstChildMenuLocator, By secondChildMenuLocator,
			By thirdChildMenuLocator) throws InterruptedException {

		Actions act = new Actions(driver);

		doClick(parentMenuLocator);
		Thread.sleep(1000);

		act.moveToElement(getElement(firstChildMenuLocator)).build().perform();

		act.moveToElement(getElement(secondChildMenuLocator)).build().perform();

		// act.click(getElement(thirdChildMenuLocator)).build().perform();
		doClick(thirdChildMenuLocator);

	}

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsClick(By locater) {
		Actions act = new Actions(driver);
		act.click(getElement(locater)).build().perform();
	}

	public void doActionsSendKeysWithPause(By locator, String value) {

		Actions act = new Actions(driver);
		char val[] = value.toCharArray();

		for (char c : val) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(500).perform();
		}
	}

	public void scrollToElementClick(By locator) {
		Actions act = new Actions(driver);
		act.scrollToElement(getElement(locator)).click(getElement(locator)).perform();
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does notnecessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForPresenceOfElement(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		isHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @param intervalTimout
	 * @return
	 */
	public WebElement waitForPresenceOfElement(By locator, int timeout, int intervalTimout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(intervalTimout));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		isHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step("Waiting for element: {0} with timeout {1}")
	public WebElement waitForVisibilityOfElement(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @param intervalTimeout
	 * @return
	 */
	@Step("Waiting for element: {0} with timeout {1} and interval of: {2}")
	public WebElement waitForVisibilityOfElement(By locator, int timeout, int intervalTimeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout),
				Duration.ofSeconds(intervalTimeout));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step("Waiting for element: {0} with timeout: {1}")
	public List<WebElement> waitForVisibilityOfElements(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * @param locator
	 * @param timeout
	 */
	public void waitForPresenceOfElements(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public void doClickWithVisibilityWait(By locator, int timeout) {
		waitForVisibilityOfElement(locator, timeout).click();
	}

	public void doEnterDataWithVisibilityWait(By locator, String value, int timeout) {
		waitForVisibilityOfElement(locator, timeout).sendKeys(value);
	}

	public String waitForTitleContains(String titleFraction, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println(titleFraction + ": Title value is not present.....");
			e.printStackTrace();
		}

		return null;

	}

	@Step("Waiting for the page title: {0} and timeout: {1}")
	public String waitForTitleIs(String title, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				System.out.println("Title is correct");
				return driver.getTitle();
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Incorrect Title");
			e.printStackTrace();
		}
		return null;
	}

	@Step("Waiting for url: {0} and timeout is: {1}")
	public String waitForURLContains(String urlFraction, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				System.out.println("Correct url");
				return driver.getCurrentUrl();
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Incorrect url");
			e.printStackTrace();
		}
		return null;
	}

	public String waitForURLToBe(String url, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.urlToBe(url))) {
				System.out.println("Correct url");
				return driver.getCurrentUrl();
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Incorrect url");
			e.printStackTrace();
		}
		return null;
	}

	public Alert waitForJSAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptJSAlert(int timeout) {
		waitForJSAlert(timeout).accept();
	}

	public void dismissJSAlert(int itmeout) {
		waitForJSAlert(itmeout).dismiss();
	}

	public String getJSAlertText(int timeout) {
		return waitForJSAlert(timeout).getText();
	}

	public void enterValueOnJSAlert(int timeout, String value) {
		waitForJSAlert(timeout).sendKeys(value);
	}

	public void waitForFrameByLocator(By frameLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFrameByIndex(int frameIndex, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameByIdOrName(String frameNameOrId, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
	}

	public void waitForFrameByElement(WebElement frameElement, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	public boolean checkNewWindowExist(int timeout, int expectedNumberOfWindows) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows))) {
				return true;
			}
		} catch (TimeoutException e) {
			System.out.println("Number of windows are not same.");
		}
		return false;
	}

	public void clickElementWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		} catch (TimeoutException e) {
			System.out.println("Element is not clickable");
		}
	}

	public WebElement waitForElementWithFluentWait(By locator, int timeout, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.withMessage("Time out is done -- element is not found.....")
				.ignoring(ElementNotInteractableException.class).ignoring(NoSuchElementException.class);

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isHighlight(element);
		return element;
	}

	public void waitForFrameWithFluentWait(String frameIdOrName, int timeout, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.withMessage("Time out is done -- frame is not found.....").ignoring(NoSuchFrameException.class);

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdOrName));
	}

	public void waitForJSAlertWithFluentWait(int timeout, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.withMessage("Time out is done -- alert is not appeared.....").ignoring(NoAlertPresentException.class);

		wait.until(ExpectedConditions.alertIsPresent());
	}

	public WebElement retryingElement(By locator, int timeout) {

		WebElement element = null;
		int attempts = 0;

		while (attempts < timeout) {
			try {
				element = getElement(locator);
				System.out.println("Element is found: " + locator + " in attempt " + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("Element is not found.." + locator + " in attempt " + attempts);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}

		if (element == null) {
			System.out.println("Element is not found..tried for " + timeout + " times. With interval of " + 500
					+ " milli seconds..");

			throw new FrameworkException("No such element");
		}
		isHighlight(element);
		return element;

	}

	public WebElement retryingElement(By locator, int timeout, int intervalTime) {

		WebElement element = null;
		int attempts = 0;

		while (attempts < timeout) {
			try {
				element = getElement(locator);
				System.out.println("Element is found: " + locator + " in attempt " + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("Element is not found.." + locator + " in attempt " + attempts);
				try {
					Thread.sleep(intervalTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}

		if (element == null) {
			System.out.println("Element is not found..tried for " + timeout + " times. With interval of " + 500
					+ " milli seconds..");

			throw new FrameworkException("No such element");
		}
		isHighlight(element);
		return element;

	}

	public boolean isPageLoaded(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==='complete'"))
				.toString();
		return Boolean.parseBoolean(flag);
	}

}