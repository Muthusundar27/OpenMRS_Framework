package stepDefinitions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import listeners.ExtentReportListener;
import utils.ExtentReportManager;
import utils.LoggerUtil;

public class Hooks {

	private static  ExtentReportListener reportListener;
	BaseTest baseTest = new BaseTest();

	@BeforeAll
	public static void beforeAll() {
		ExtentReportManager.initReport();
	}

	@AfterAll
	public static void afterAll() {
		ExtentReportManager.flushReports();
	}

	@Before
	public void beforeScenario(Scenario scenario) throws IOException {
		// Launch the application
		BaseTest baseTest = new BaseTest();
		baseTest.launchApplication();

		  // First create the test node
	    ExtentReportManager.createScenario(scenario.getName());

	    // THEN initialize the listener with the now-created ExtentTest
	    reportListener = new ExtentReportListener(
	        ExtentReportManager.getExtent(),
	        ExtentReportManager.getTest(), // Now this won't be null
	        BaseTest.getDriver()
	    );
	}

	@AfterStep
	public void afterStep(Scenario scenario) {
		WebDriver driver = BaseTest.getDriver();
		if (scenario.isFailed()) {
			LoggerUtil.logError("Step failed");
			reportListener.logStepFailure("Step failed");
			ExtentReportManager.attachScreenshot(driver, "Failure Screenshot");
		} else {
			LoggerUtil.logInfo("Step passed");
			reportListener.logStepSuccess("Step passed");
			ExtentReportManager.attachScreenshot(driver, "Step Screenshot");
		}
	}

	@After
	public void afterScenario(Scenario scenario) {
		WebDriver driver = BaseTest.getDriver();
		if (scenario.isFailed()) {
			LoggerUtil.logError("Scenario failed");
			reportListener.logStepFailure("Scenario failed");
			ExtentReportManager.attachScreenshot(driver, "Failure Screenshot");
		} else {
			LoggerUtil.logInfo("Scenario passed");
			reportListener.logStepSuccess("Scenario passed");
			ExtentReportManager.attachScreenshot(driver, "Scenario Screenshot");
		}

		// Close the driver
		BaseTest.quitDriver();
	}

	public static ExtentReportListener getReportListener() {
		return reportListener;
	}

}
