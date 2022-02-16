package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.util.Constants;

import io.netty.util.Constant;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC -100 : Design login page for Open Cart Application")
@Story("US - 101 :Login page Features")
public class LoginPageTest extends BaseTest {

	@Description("Login page title Test")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String title = loginPage.getLoginPageTitle();
		System.out.println("Login page title is updated (to validate in git): " + title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
	}

	@Description("Login page URL Test")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageURLTest() {
		String pageURL = loginPage.getLoginPageUrl();
		System.out.println("Login Page URL Is :  : " + pageURL);
		Assert.assertTrue(pageURL.contains(Constants.LOGIN_PAGE_URL_FRACTION));
	}

	@Description("This is testing forgot password link on login Page")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void ForgotPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}

	@Description("Positive test case for login with correct credentials")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description="login test with correct username and password")
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
}
