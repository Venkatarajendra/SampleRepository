package com.crm.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.crm.qa.reports.ExtentReportManager;
import com.crm.qa.reports.ManageReport;
import com.crm.qa.util.CaptureScreenshot;
import com.crm.qa.util.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase extends ManageReport {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Properties prop;
	public static String dateStr;
	protected static ExtentReports eReport;
	public static ExtentTest eTest;
	
	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream("E:\\Webdriverwork\\FreeCRMTest\\src\\main\\java\\com\\crm\\qa\\config\\config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @author venkat
	 * @param browser 
	 */
	@BeforeClass
	@Parameters ("browser")
	public static void initialization(String browser) {
		//String browser = prop.getProperty("browser");
		if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			//System.setProperty("webdriver.chrome.driver","E:\\Webdriverwork\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browser.equals("IE")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}
		
		else if(browser.equals("FF")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		
		driver.get(prop.getProperty("url"));
		driver.navigate().refresh();
		wait = new WebDriverWait(driver, 20);
	}
	
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
