package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> stepTest = new ThreadLocal<>();

	public static void initReport() {
		// Get current date and time
		String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String currentTime = new SimpleDateFormat("HH_mm").format(new Date());

		// Create folder path like: reports/14-04-2025/
		String reportFolder = "reports/" + currentDate;
		new File(reportFolder).mkdirs(); // Create folder if not exists

		// Create report file path: WEB_AutomationExecutionResultHH_mm.html
		String reportFilePath = reportFolder + "/WEB_AutomationExecutionResult" + currentTime + ".html";

		// Set up ExtentSparkReporter
		ExtentSparkReporter reporter = new ExtentSparkReporter(reportFilePath);
		reporter.config().setReportName("Test Automation Results");
		reporter.config().setDocumentTitle("ExtentReports");

		extent = new ExtentReports();
		extent.attachReporter(reporter);

		// Add execution details
		extent.setSystemInfo("Tester", "Muthu Sundar");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("Date", currentDate);
		extent.setSystemInfo("Execution Time", currentTime);

	}

	public static void flushReports() {
		extent.flush();
	}

	public static ExtentReports getExtent() {
		return extent;
	}

	public static ExtentTest getTest() {
		return scenarioTest.get();
	}

	public static void createScenario(String scenarioName) {
		ExtentTest test = extent.createTest(scenarioName);
		scenarioTest.set(test);
	}

	public static void createStep(String stepName) {
		ExtentTest step = scenarioTest.get().createNode(stepName);
		stepTest.set(step);
	}

	public static void logPass(String message) {
		if (stepTest.get() != null) {
			stepTest.get().pass(message); // Log message to ExtentReports for current step
		} else if (scenarioTest.get() != null) {
			scenarioTest.get().pass(message); // Log message to ExtentReports for scenario
		}
		LoggerUtil.logInfo(message); // Log the same message to console
	}

	public static void logFail(String message) {
		if (stepTest.get() != null) {
			stepTest.get().fail(message); // Log failure to ExtentReports for current step
		} else if (scenarioTest.get() != null) {
			scenarioTest.get().fail(message); // Log failure to ExtentReports for scenario
		}
		LoggerUtil.logError(message); // Log failure message to console
	}

	public static void logInfo(String message) {
		if (stepTest.get() != null) {
			stepTest.get().info(message); // Log info to ExtentReports for current step
		} else if (scenarioTest.get() != null) {
			scenarioTest.get().info(message); // Log info to ExtentReports for scenario
		}
		LoggerUtil.logInfo(message); // Log info to console
	}

	public static void attachScreenshot(WebDriver driver, String message) {
		try {
			String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
			if (stepTest.get() != null) {
				stepTest.get().info(message,
						MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			} else if (scenarioTest.get() != null) {
				scenarioTest.get().info(message,
						MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			}
		} catch (Exception e) {
			System.out.println("Screenshot capture failed: " + e.getMessage());
		}
	}
}
