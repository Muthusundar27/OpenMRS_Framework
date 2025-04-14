package stepDefinitions;

import base.BaseTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import listeners.ExtentReportListener;
import objectRepository.RegisterPatientPage;
import pojo.PatientData;

public class RegisterPatientPageSD {
	private RegisterPatientPage registerPatientPage;
	private BaseTest baseTest;
	private PatientData patientData;
	private ExtentReportListener reportListener;

	public RegisterPatientPageSD(BaseTest baseTest, PatientData patientData) {
		this.baseTest = baseTest;
		this.patientData = patientData;
		this.registerPatientPage = new RegisterPatientPage(baseTest.getDriver(), patientData);
		this.reportListener = Hooks.getReportListener(); // Fetch the report listener
	}

	@When("user enters patient demographics details \\(Name, Gender, Birthdate) and contact information \\(Address, Phone number)")
	public void enterPatientDemographicsAndContactInfo() {
		try {
			reportListener.logStepInfo(
					"Entering patient demographics details (Name, Gender, Birthdate) and contact info (Address, Phone number)...");
			registerPatientPage.registerPatient();
			reportListener.logStepSuccess("Patient demographics and contact info entered successfully.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to enter patient demographics and contact info: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Then("user should verify the given Name, Gender, Birthdate, Address, and Phone number are populated correctly on the confirmation page")
	public void verifyPatientDetailsOnConfirmationPage() {
		try {
			reportListener.logStepInfo(
					"Verifying if the patient details (Name, Gender, Birthdate, Address, Phone number) are correct on the confirmation page...");
			registerPatientPage.verifyConfirmationPageDetails();
			reportListener.logStepSuccess("Patient details are correctly populated on the confirmation page.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to verify patient details on confirmation page: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@When("user clicks on Confirm")
	public void clickConfirmButton() {
		try {
			reportListener.logStepInfo("Clicking on 'Confirm' button...");
			registerPatientPage.clickConfirmButton();
			reportListener.logStepSuccess("Successfully clicked on 'Confirm' button.");
		} catch (Exception e) {
			reportListener.logStepFailure("Failed to click on 'Confirm' button: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

}
