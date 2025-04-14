package objectRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import pojo.PatientData;

public class PatientDetailsPage extends BasePage {

	private PatientData patientData;

	// Locators
	private By patientAge = By.xpath("//div[contains(@class, 'gender-age')]/span[2]");
	private By toastMessage = By.xpath(
			"//div[@class='toast-container toast-position-top-right']//*[contains(text(),'Created Patient Record:')]");
	private By startVisitOptions = By.xpath("(//div[@class='col-11 col-lg-10'])[1]");
	private By confirmButton = By.id("start-visit-with-visittype-confirm");
	private By attachmentThumbnail = By.cssSelector(".att_thumbnail-container img");
	private By recentVisitDate = By.xpath("//a[@class=\"ng-binding\"]");
	private By attachmentUploadTag = By.xpath("//*[contains(text(),'Attachment Upload')]");
	private By endVisitButton = By.xpath("(//div[@class=\"col-11 col-lg-10\"])[6]");
	private By confirmEndVisit = By.xpath("(//button[text()=\"Yes\"])[2]");
	private By deleteIcon = By.xpath("//div[@class='col-1 col-lg-2']//i[@class='icon-remove']");
	private By deleteReasonInput = By.id("delete-reason");
	private By confirmDeleteButton = By.xpath("(//div[@class=\"dialog-content\"]//button[text()=\"Confirm\"])[3]");
	private By deletionToasterMessage = By.xpath(
			"//div[@class=\"toast-container toast-position-top-right\"]//*[contains(text(),\"Patient has been deleted successfully\")]");

	public PatientDetailsPage(WebDriver driver, PatientData patientData) {
		super(driver);
		this.patientData = patientData;
	}

	public void validateToastMessageDisplayed() {
		Assert.assertTrue(isElementVisible(toastMessage), "Toast message not visible!");
	}

	public void validatePatientAgeFromDOB() {
		String dobString = String.join(", ", patientData.getBirthDay(), patientData.getBirthMonth(),
				patientData.getBirthYear());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d, MMMM, yyyy");
		LocalDate dob = LocalDate.parse(dobString, formatter);
		int expectedAge = Period.between(dob, LocalDate.now()).getYears();

		String ageText = getElementText(patientAge);
		int actualAge = extractAge(ageText);

		Assert.assertEquals(actualAge, expectedAge, "Age mismatch between UI and DOB!");
	}

	private int extractAge(String ageText) {
		for (String part : ageText.split(" ")) {
			if (part.matches("\\d+")) {
				return Integer.parseInt(part);
			}
		}
		return -1; // fallback if no age found
	}

	public void clickStartVisitLink() {
		clickElement(startVisitOptions);
	}

	public void confirmButton() {
		clickElement(confirmButton);
	}

	// Attachment Verification
	public void verifyAttachmentIsVisible() {
		Assert.assertTrue(isElementVisible(attachmentThumbnail), "Attachment not visible!");
	}

	public void verifyRecentVisitWithAttachment() {
		String expectedDate = new SimpleDateFormat("dd.MMM.yyyy").format(new Date());
		Assert.assertEquals(getText(recentVisitDate), expectedDate, "Recent visit date mismatch!");
		Assert.assertEquals(getText(attachmentUploadTag), "Attachment Upload", "Attachment tag mismatch!");
	}

	// Visit & Deletion
	public void endPatientVisit() {
		clickElement(endVisitButton);
		clickElement(confirmEndVisit);
	}

	public void deletePatient(String reason) {
		int retry = 0;
		while (retry < 3) {
			try {
				clickElement(deleteIcon);
				break;
			} catch (StaleElementReferenceException e) {
				retry++;
			}
		}
		sendKeysToElement(deleteReasonInput, reason);
		clickElement(confirmDeleteButton);
	}

	public void validateDeleteToastMessageDisplayed() {
		Assert.assertTrue(isElementVisible(deletionToasterMessage), "Deletion toaster not visible!");
	}

}
