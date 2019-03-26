package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.TestBase;

public class HomePage extends TestBase{

	LoginPage loginPage = new LoginPage();
	@FindBy(xpath = "//a[contains(text(),'Logout')]")
	WebElement logoutBtn;
	
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public boolean validateLogout() {
		logoutBtn.click();
		return loginPage.loginBtn.isDisplayed();
	}
}
