package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.util.ElementUtil;

public class ResultsPage {
	private WebDriver driver;
	private ElementUtil eleutil;
	private By searchHeader = By.cssSelector("div#content h1");
	private By productResults = By.cssSelector("div.caption a");

	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	public int getProductListCount() {
		int productCount = eleutil.waitForElementsVisible(productResults, 10).size();
		System.out.println("Total search product " + productCount);
		return productCount;
	}

	public ProductInfoPage selectProduct(String mainProductName) {
		System.out.println("Main product name is : " + mainProductName);
		List<WebElement> searchList = eleutil.waitForElementsPresence(productResults, 10);
		for (WebElement e : searchList) {
			String text = e.getText();
			if (text.equals(mainProductName)) {
				e.click();
				break;
			}
		}
		return new ProductInfoPage(driver);
	}
}
