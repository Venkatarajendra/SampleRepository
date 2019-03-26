package com.crm.qa.reports;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentReportManager {
	private static ExtentReports eReport;

	public synchronized static ExtentReports getReporter(String filePath) {
		if (eReport == null) {
			eReport = new ExtentReports(filePath, false);

			eReport.addSystemInfo("Host Name", "Rajendra").addSystemInfo("Environment", "QA");
		}

		return eReport;
	}
}