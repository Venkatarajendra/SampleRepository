package com.crm.qa.testcases;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;

public class WebDriverManagerDemo extends TestBase{
	
	@Test
	public void openanyBrowser() {
		eTest = eReport.startTest("Opening Browser using webdrivermanager");
		//initialization();
		System.out.println(prop.getProperty("browser")+" browser opened");
		//driver.findElement(By.id("rajendra")).click();
		eTest.log(LogStatus.PASS, "Browser opened successfully with WebDriverManager");
		driver.close();
	}
}
