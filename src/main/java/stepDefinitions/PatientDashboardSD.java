package stepDefinitions;

import org.testng.Assert;

import base.BaseTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import listeners.ExtentReportListener;
import objectRepository.PatientDashboardPage;

public class PatientDashboardSD {

	private PatientDashboardPage patientDashboardPage;
	private BaseTest baseTest;
	private ExtentReportListener reportListener;

	public PatientDashboardSD(BaseTest baseTest) {
		this.baseTest = baseTest;
		this.patientDashboardPage = new PatientDashboardPage(baseTest.getDriver());
		this.reportListener = Hooks.getReportListener(); // Fetch the report listener
	}

	@When("user clicks on Attachment and uploads an attachment with file path {string} and caption {string}")
	public void uploadAttachmentWithFileAndCaption(String filePath, String caption) {
		try {
			reportListener.logStepInfo("Clicking on Attachments tab...");
			patientDashboardPage.clickOnAttachmentsTab();

			reportListener.logStepInfo("Uploading file: " + filePath);
			patientDashboardPage.uploadFile(filePath);

			reportListener.logStepInfo("Entering caption: " + caption);
			patientDashboardPage.enterCaption(caption);

			reportListener.logStepInfo("Clicking on upload button.");
			patientDashboardPage.clickUploadButton();

			reportListener.logStepSuccess("Attachment uploaded successfully with caption: " + caption);
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to upload attachment: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Then("user should see a toaster message confirming the successful attachment upload")
	public void verifyAttachmentUploadSuccess() {
		try {
			reportListener.logStepInfo("Verifying toaster message for successful upload...");
			boolean isSuccess = patientDashboardPage.verifyFileUploadSuccess();

			Assert.assertTrue(isSuccess, "Attachment upload success message not visible.");
			reportListener.logStepSuccess("Toaster message confirmed for successful upload.");
		} catch (AssertionError e) {
			reportListener.logStepFailure("Upload verification failed: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			reportListener.logStepFailure("Exception during upload verification: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@When("user redirects to the Patient details screen")
	public void redirectToPatientDetailsScreen() {
		try {
			reportListener.logStepInfo("Redirecting to Patient Details screen by clicking patient name label...");
			patientDashboardPage.clickPatientNameLabel();
			reportListener.logStepSuccess("Successfully redirected to Patient Details screen.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to redirect to Patient Details screen: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

}
