package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.util.Constants;

public class ProductInfoTest extends BaseTest {

	@BeforeClass
	public void productInfoSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void productHeaderTest() {
		resultsPage = accPage.doSearch("Macbook");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		Assert.assertEquals(productInfoPage.getProductHeaderName(), "MacBook Pro");
	}

	@DataProvider
	public Object[][] productData() {
		return new Object[][] { { "Macbook", "MacBook Pro", Constants.MACBOOKPRO_IMAGES_COUNT },
				{ "Macbook", "MacBook Air", Constants.MACBOOKPRO_IMAGES_COUNT },
				{ "iMac", "iMac", Constants.IMAC_IMAGES_COUNT } };
	}

	@Test(dataProvider = "productData")
	public void productImagesCountTest(String productName, String mainProductName, int imagesCount) {
		resultsPage = accPage.doSearch(productName);
		productInfoPage = resultsPage.selectProduct(mainProductName);
		int totalImages = productInfoPage.getProductImageCount();
		System.out.println("Total images for : " + mainProductName + ":" + totalImages);
		Assert.assertEquals(totalImages, imagesCount);
	}

	// From HashMap: Not Maintaining the Order
	// Main product name is : MacBook Pro
	// Brand:Apple
	// Availability:Out Of Stock
	// totalimages:4
	// name:MacBook Pro
	// Product Code:Product 18
	// Reward Points:800
	// exTaxPrice:Ex Tax: $2,000.00

	// From LinkedHashMap: Maintain Order

	// Main product name is : MacBook Pro
	// name:MacBook Pro
	// totalimages:4
	// price:$2,000.00
	// exTaxPrice:Ex Tax: $2,000.00
	// Brand:Apple
	// Product Code:Product 18
	// Reward Points:800
	// Availability:Out Of Stock

	// From TreeSet: Sorting Order

	// Main product name is : MacBook Pro
	// Availability:Out Of Stock
	// Brand:Apple
	// Product Code:Product 18
	// Reward Points:800
	// exTaxPrice:Ex Tax: $2,000.00
	// name:MacBook Pro
	// price:$2,000.00
	// totalimages:4

	@Test
	public void productDataTest() {
		resultsPage = accPage.doSearch("Macbook");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
		actProductInfoMap.forEach((k, v) -> System.out.println(k + ":" + v));
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProductInfoMap.get("name"), "MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProductInfoMap.get("price"), "$2,000.00");
		softAssert.assertAll();
	}
}
