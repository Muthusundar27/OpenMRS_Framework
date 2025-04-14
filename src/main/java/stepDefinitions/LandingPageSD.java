package stepDefinitions;

import base.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import listeners.ExtentReportListener;
import objectRepository.LandingPage;

public class LandingPageSD {
	private LandingPage landingPage;
	private BaseTest baseTest;
	private ExtentReportListener reportListener;

	public LandingPageSD(BaseTest baseTest) {
		this.baseTest = baseTest;
		this.reportListener = Hooks.getReportListener(); // Get initialized listener from Hooks
	}

	@Given("user is on the OpenMRS login page")
	public void userIsOnOpenMRSLoginPage() {
		try {
			reportListener.logStepInfo("Launching the OpenMRS login page...");

			// Launch application
			baseTest.launchApplication();

			// Initialize page object
			reportListener.logStepInfo("Initializing LandingPage object.");
			landingPage = new LandingPage(BaseTest.getDriver());

			reportListener.logStepSuccess("User successfully navigated to the OpenMRS login page.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to load the OpenMRS login page: " + e.getMessage());
			throw new RuntimeException("Error while opening login page: " + e.getMessage(), e);
		}
	}

	@When("user enters username {string} and password {string}")
	public void userEnterUsernameAndPassword(String username, String password) {
		try {
			reportListener.logStepInfo("Entering credentials: username = " + username + ", password = " + password);

			landingPage.enterUsername(username);
			landingPage.enterPassword(password);

			reportListener.logStepSuccess("Entered username and password successfully.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to enter credentials: " + e.getMessage());
			throw new RuntimeException("Error while entering login credentials: " + e.getMessage(), e);
		}
	}

	@When("user clicks on a location and logs in")
	public void userSelectLocationAndLogin() {
		try {
			reportListener.logStepInfo("Clicking on location and login button...");

			landingPage.clickLaboratory();
			landingPage.clickLoginButton();

			reportListener.logStepSuccess("Successfully clicked location and logged in.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to log in: " + e.getMessage());
			throw new RuntimeException("Error while selecting location and logging in: " + e.getMessage(), e);
		}
	}
}
