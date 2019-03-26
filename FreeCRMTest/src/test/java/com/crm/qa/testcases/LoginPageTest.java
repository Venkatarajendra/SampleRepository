package com.crm.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.*;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPageTest extends TestBase {
	
	LoginPage loginPage;
	HomePage homePage;
	String sheetName = "LoginSheet";

	LoginPageTest() {
		super();
	}
	
	@BeforeClass
	public void setUp() {
		//initialization();
		loginPage = new LoginPage();
		homePage = new HomePage();
	}
	
	@Test(priority = 1)
	public void validateLoginPageTitle() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, "#1 Free CRM software in the cloud for sales and service");
	}
	
	@Test(priority = 2)
	public void logoValidation() {
		boolean logoSts = loginPage.isLogoDisplayed();
		Assert.assertTrue(logoSts, "Logo is not displayed on login page");
	}
	
	@Test(priority = 3, dataProvider="getTestData")
	public void LoginTest(String username, String password) throws InterruptedException {
		eTest = eReport.startTest("Free CRM - Login Test");
		String homePageTitle = loginPage.login(username, password);
		System.out.println("HomePage Title is: "+homePageTitle);
		Assert.assertEquals(homePageTitle, "CRMPRO", "Home Page title didn't match");
		eTest.log(LogStatus.PASS, "Login is successful");
		Assert.assertTrue(homePage.validateLogout(), "Logout failed");
		eTest.log(LogStatus.PASS, "Logout is successful");
	}
	
	@DataProvider
	public Object[][] getTestData() {
		Object[][] testData = TestUtil.getTestData(sheetName);
		return testData;
	}
	
	
	@AfterClass
	public void tearDown() {
		driver.close();
	}
}
