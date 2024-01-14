package com.qa.openCart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	private WebDriver driver;
	private JavascriptExecutor js;

	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
	}

	public String getTitleByJS() {
		return js.executeScript("return document.title").toString();
	}

	public String getURLByJS() {
		return js.executeScript("return document.url").toString();
	}

	public void generateJSAlert(String msg) {
		js.executeScript("alert('" + msg + "')");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.switchTo().alert().accept();

	}

	public void generateJSConfirm(String msg) {
		js.executeScript("confirm('" + msg + "')");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.switchTo().alert().accept();
	}

	public void generatePrompt(String msg, String value) {
		js.executeScript("prompt('" + msg + "')");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(value);
		alert.accept();
	}

	public void goBackWithJS() {
		js.executeScript("history.go(-1)");
	}

	public void goForwardWithJS() {
		js.executeScript("history.go(1)");
	}

	public void pageRefreshWithJS() {
		js.executeScript("history.go(0)");
	}

	public void pageRefreshWithJSReload() {
		js.executeScript("location.reload()");
	}

	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText").toString();
	}

	public void scrollPageDownToMiddle() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight/2)");
	}

	public void scrollPageDownUsingJS() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public void scrollPageDownUsingJS(String height) {
		js.executeScript("window.scrollTo(0," + height + ")");
	}

	public void scrollPageUpUsingJS() {
		js.executeScript("window.scrollTo(document.body.scrollToHeight,0)");
	}

	public void scrollPageUpUsingJS(String height) {
		js.executeScript("window.scrollTo(" + height + ",0)");
	}

	public void scrollIntoViewJS(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true)", element);
	}

	public void zoomChromeEdgeSafari(String zoomPercentage) {
		String zoom = "document.body.style.zoom='" + zoomPercentage + "%'";
		js.executeScript(zoom);
	}

	public void zoomFirefox(String zoomPercentage) {
		String zoom = "document.body.style.MozTransform='scale(" + zoomPercentage + ")'";
		js.executeScript(zoom);
	}

	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	public void flash(WebElement element) {
		String bgColor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 10; i++) {
			changeColor("rgb(0,200,0)", element);
			changeColor(bgColor, element);
		}
	}

	public void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor='" + color + "'", element);

	}

	public void clickElementByJS(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	public void sendKeysUsingWithId(String id, String value) {
		js.executeScript("document.getElementById('" + id + "').value='" + value + "'");
		//js.executeScript("document.getElementById('input-email').value='testing@gmail.com'");
	}
	
	public void pseudoElementMandatoryIconCheck(String locator, String fieldName) {
		String script = 
				"return window.getComputedStyle(document.querySelector(\""+locator+"\"),'::before').getPropertyValue('content');";

		JavascriptExecutor js = (JavascriptExecutor) driver;

		String mand_text = js.executeScript(script).toString();
		System.out.println("Content is: "+mand_text);
		if (mand_text.contains("*")) {
			System.out.println(fieldName+" field is mandatory");
		}
		
	}
	
}