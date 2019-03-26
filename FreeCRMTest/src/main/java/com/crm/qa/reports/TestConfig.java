package com.crm.qa.reports;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.crm.qa.util.CaptureScreenshot;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public abstract class TestConfig extends ManageReport {
	protected static ExtentReports eReport;
	public static ExtentTest eTest;
	protected WebDriver driver;
	public static String dateStr;

	// final String filePath = System.getProperty("user.dir") + "\\reports\\TestReport.html";

	@AfterMethod
	protected void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = CaptureScreenshot.get(result.getName(), driver, dateStr);
			System.out.println("screenshotPath.................." + screenshotPath);
			// String image = eTest.addScreenCapture(screenshotPath);
			// eTest.log(LogStatus.FAIL, image);
			eTest.log(LogStatus.INFO, "Snapshot below: " + eTest.addScreenCapture(screenshotPath));
			eTest.log(LogStatus.FAIL, result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			eTest = eReport.startTest(result.getName());
			eTest.log(LogStatus.SKIP, "Test skipped " + result.getThrowable());

		} else {
			eTest.log(LogStatus.PASS, "Test passed");
		}

		eReport.endTest(eTest);
		eReport.flush();

	}

	@BeforeSuite
	public void beforeSuite() throws IOException {
		System.out.println("In Before Suite");
		cleanReports();
		createReportDirectories();
		SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		dateStr = df.format(new java.util.Date());
		final String filePath = System.getProperty("user.dir") + "\\reports\\TestReport.html";
		eReport = ExtentReportManager.getReporter(filePath);
		System.out.println("Exiting Before Suite");
	}

	@AfterSuite
	protected void afterSuite() {
		eReport.close();
		// driver.quit();

	}
}
