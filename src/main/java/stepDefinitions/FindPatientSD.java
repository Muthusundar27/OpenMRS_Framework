package stepDefinitions;

import org.testng.Assert;

import base.BaseTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import listeners.ExtentReportListener;
import objectRepository.FindPatientPage;
import pojo.PatientData;

public class FindPatientSD {
	private FindPatientPage findPatientPage;
	private BaseTest baseTest;
	private PatientData patientData;
	private ExtentReportListener reportListener;

	public FindPatientSD(BaseTest baseTest, PatientData patientData) {
		this.baseTest = baseTest;
		this.patientData = patientData;
		this.findPatientPage = new FindPatientPage(baseTest.getDriver(), patientData);
		this.reportListener = Hooks.getReportListener(); // Fetch the report listener
	}

	@When("user is redirected to the Find Patient Record menu")
	public void userIsRedirectedToFindPatientRecordMenu() {
		try {
			reportListener.logStepInfo("Navigating to 'Find Patient Record' menu.");
			findPatientPage.navigateToFindPatientPage(); // Use this for clear naming
			reportListener.logStepSuccess("Successfully navigated to 'Find Patient Record' page.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to navigate to Find Patient Record menu: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Then("user should verify that the deleted patient is not listed in the patient search results")
	public void verifyDeletedPatientNotListed() {
		try {
			reportListener.logStepInfo("Verifying deleted patient is not listed in search results...");

			boolean isPatientPresent = findPatientPage.isPatientListed();
			Assert.assertTrue(isPatientPresent, "Deleted patient is still listed in search results!");

			reportListener.logStepSuccess("Deleted patient is correctly not listed in search results.");
		} catch (AssertionError e) {
			reportListener.logStepFailure("Assertion failed: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			reportListener.logStepFailure("Exception during patient search validation: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

}
