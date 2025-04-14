package stepDefinitions;

import base.BaseTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import listeners.ExtentReportListener;
import objectRepository.PatientDetailsPage;
import pojo.PatientData;

public class PatientDetailsSD {
	private PatientData patientData;
	private PatientDetailsPage patientDetailsPage;
	private BaseTest baseTest;
	private ExtentReportListener reportListener;

	public PatientDetailsSD(BaseTest baseTest, PatientData patientData) {
		this.baseTest = baseTest;
		this.patientData = patientData;
		this.patientDetailsPage = new PatientDetailsPage(baseTest.getDriver(), patientData);
		this.reportListener = Hooks.getReportListener(); // Fetch the report listener
	}

	@Then("user should be redirected to the patient details page and the age should be calculated correctly based on the provided Birthdate")
	public void validatePatientDetailsPageAndAge() {
		try {
			reportListener.logStepInfo("Validating toast message displayed after redirect...");
			patientDetailsPage.validateToastMessageDisplayed();

			reportListener.logStepInfo("Validating patient age from the provided DOB...");
			patientDetailsPage.validatePatientAgeFromDOB();

			reportListener.logStepSuccess("Patient details page validated, and age calculated correctly.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to validate patient details page or age: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@When("user clicks on Start Visit and confirms the visit")
	public void startAndConfirmVisit() {
		try {
			reportListener.logStepInfo("Clicking on 'Start Visit' link...");
			patientDetailsPage.clickStartVisitLink();

			reportListener.logStepInfo("Confirming the visit...");
			patientDetailsPage.confirmButton();

			reportListener.logStepSuccess("Successfully started and confirmed the visit.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to start or confirm the visit: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Then("user should verify that the attachment section contains the uploaded attachment")
	public void verifyUploadedAttachmentInSection() {
		try {
			reportListener.logStepInfo("Verifying the uploaded attachment in the attachment section...");
			patientDetailsPage.verifyAttachmentIsVisible();
			reportListener.logStepSuccess("Uploaded attachment is correctly visible in the attachment section.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to verify uploaded attachment: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Then("user should verify that the recent visit has one entry with the current date and Attachment Upload tag")
	public void verifyRecentVisitWithAttachmentTag() {
		try {
			reportListener.logStepInfo("Verifying recent visit entry with current date and 'Attachment Upload' tag...");
			patientDetailsPage.verifyRecentVisitWithAttachment();
			reportListener.logStepSuccess("Recent visit entry is correctly displayed with 'Attachment Upload' tag.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to verify recent visit entry: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@When("user clicks on the End Visit action at the right-hand side")
	public void clickEndVisitAction() {
		try {
			reportListener.logStepInfo("Clicking on 'End Visit' action...");
			patientDetailsPage.endPatientVisit();
			reportListener.logStepSuccess("Successfully clicked on 'End Visit' action.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to click on 'End Visit' action: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@When("user deletes the patient with reason {string}")
	public void deletePatientWithReason(String reason) {
		try {
			reportListener.logStepInfo("Deleting patient with reason: " + reason);
			patientDetailsPage.deletePatient(reason);
			reportListener.logStepSuccess("Patient deleted successfully with reason: " + reason);
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to delete patient: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Then("user should see a toaster message confirming the successful deletion of the patient")
	public void verifyPatientDeletionSuccessMessage() {
		try {
			reportListener.logStepInfo("Verifying toaster message for successful deletion...");
			patientDetailsPage.validateDeleteToastMessageDisplayed();
			reportListener.logStepSuccess("Toaster message confirmed for successful deletion.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to verify toaster message: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
