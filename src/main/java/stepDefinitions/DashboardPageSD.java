package stepDefinitions;

import org.testng.Assert;

import base.BaseTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import listeners.ExtentReportListener;
import objectRepository.DashboardPage;

public class DashboardPageSD {
	private DashboardPage dashboardPage;
    private BaseTest baseTest;
    private ExtentReportListener reportListener;

    public DashboardPageSD(BaseTest baseTest) {
        this.baseTest = baseTest;
        this.dashboardPage = new DashboardPage(baseTest.getDriver());
        this.reportListener = Hooks.getReportListener(); // Fetch the report listener
    }

    @Then("user should be redirected to the dashboard page and the page should be validated using an assertion")
    public void validateUserIsOnDashboardPage() {
        try {
            reportListener.logStepInfo("Validating if user is on the Dashboard page...");

            boolean isLoggedIn = dashboardPage.isloggedIn();
            Assert.assertTrue(isLoggedIn, "User is not logged in");

            reportListener.logStepSuccess("User is successfully redirected to the Dashboard page.");
        } catch (AssertionError e) {
            reportListener.logStepFailure("Dashboard page validation failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            reportListener.logStepFailure("Exception during dashboard validation: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @When("user clicks on {string} menu")
    public void userClicksOnMenu(String menuName) {
        try {
            reportListener.logStepInfo("Clicking on the menu item: " + menuName);

            // Add a switch if there are multiple menu options, for now hardcoded:
            if (menuName.equalsIgnoreCase("Register a patient")) {
                dashboardPage.clickRegisterPatient();
                reportListener.logStepSuccess("Clicked on 'Register a patient' menu.");
            } else {
                reportListener.logStepFailure("Menu option not supported: " + menuName);
                throw new UnsupportedOperationException("Unsupported menu: " + menuName);
            }
        } catch (Exception e) {
            reportListener.logStepFailure("Failed to click on menu item: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
