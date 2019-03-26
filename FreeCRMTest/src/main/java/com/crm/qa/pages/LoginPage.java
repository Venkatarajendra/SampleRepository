package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.crm.qa.base.TestBase;
import com.crm.qa.util.TestUtil;

public class LoginPage extends TestBase{
	
	//Page Factory - OR
	
	@FindBy(name="username")
	WebElement username;
	
	@FindBy(name="password")
	WebElement password;
	
	@FindBy(xpath = "//input[@value='Login']")
	WebElement loginBtn;
	
	@FindBy(xpath = "//img[@class='img-responsive']")
	WebElement crmLogo;
	
	@FindBy (xpath = "//a/font[text()='Sign Up']")
	WebElement signupLink;
	
	@FindBy(xpath = "//a[@title='Home'][text()='Home']")
	WebElement homePageTab;
	
	// Initializing the page objects
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	// Actions or Page Methods:
	
	public String getLoginPageTitle() {
		return driver.getTitle();
	}
	
	public boolean validateSignupLink() {
		return signupLink.isDisplayed();
	}
	
	public boolean isLogoDisplayed() 
	{
		return crmLogo.isDisplayed();
	}
	
	public String login(String uname, String pwd) throws InterruptedException {
		username.sendKeys(uname);
		password.sendKeys(pwd);
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
		loginBtn.click();
		//wait.until(ExpectedConditions.visibilityOf(homePageTab));
		TestUtil.switchToFrame();
		return driver.getTitle();
	}

}
