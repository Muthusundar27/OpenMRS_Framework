package listeners;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utils.LoggerUtil;

public class ExtentReportListener {
	private ExtentReports extent;
	private ExtentTest test;
	private WebDriver driver;

	public ExtentReportListener(ExtentReports extent, ExtentTest extentTest, WebDriver driver) {
		this.extent = extent;
		this.test = extentTest;
		this.driver = driver;
	}

	// Get current time as a formatted string
	private String getCurrentTime() {
		return java.time.LocalTime.now().toString().substring(0, 8); // Get HH:mm:ss format timestamp
	}

	// Log step info to both ExtentReports and Log4j
	public void logStepInfo(String stepName) {
		String timestamp = getCurrentTime();
		String logMessage = timestamp + " " + stepName;

		test.log(Status.INFO, logMessage); // Log to ExtentReports
		LoggerUtil.logInfo(logMessage); // Log to console and file using Log4j
	}

	// Log step success to both ExtentReports and Log4j
	public void logStepSuccess(String stepName) {
		String timestamp = getCurrentTime();
		String logMessage = timestamp + " " + stepName;

		test.log(Status.PASS, logMessage); // Log to ExtentReports
		LoggerUtil.logInfo(logMessage); // Log to console and file using Log4j
	}

	// Log step failure to both ExtentReports and Log4j
	public void logStepFailure(String stepName) {
		String timestamp = getCurrentTime();
		String logMessage = timestamp + " " + stepName;

		test.log(Status.FAIL, logMessage); // Log to ExtentReports
		LoggerUtil.logError(logMessage); // Log to console and file using Log4j
	}
}