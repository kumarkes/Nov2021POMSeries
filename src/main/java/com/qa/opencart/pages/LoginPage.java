package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.util.Constants;
import com.qa.opencart.util.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleutil;

	// 1. Private By Locator
	private By emailId = By.id("input-email");
	private By passWord = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");

	// 2. Page constructor

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	// 3. Public page actions/methods

	@Step("Getting the login page title")
	public String getLoginPageTitle() {
		// return driver.getTitle();
		return eleutil.doGetPageTitle(Constants.LOGIN_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}

	@Step("Getting the login page URL")
	public String getLoginPageUrl() {
		return eleutil.waitForUrlContains(Constants.LOGIN_PAGE_URL_FRACTION, Constants.DEFAULT_TIME_OUT);
	}

	@Step("Check forgot password link is displayed")
	public boolean isForgotPwdLinkExist() {
		return eleutil.doIsDisplayed(forgotPwdLink);
	}

	@Step("Login with username :{0} and password :{1}")
	public AccountsPage doLogin(String userName, String pwd) {
		eleutil.doSendKeys(emailId, userName);
		eleutil.doSendKeys(passWord, pwd);
		eleutil.doClick(loginBtn);
		return new AccountsPage(driver);
	}

	@Step("Navigating to regiter page")
	public RegisterPage goToRegisterPage() {
		eleutil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
