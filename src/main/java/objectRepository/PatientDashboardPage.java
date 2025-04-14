package objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PatientDashboardPage extends BasePage {

	// Locators
	private By attachmentsTab = By.xpath("//*[text()=' Attachments']");
	private By uploadInputField = By.xpath("//input[@type='file']");
	private By captionField = By.xpath("//*[@placeholder='Enter a caption']");
	private By uploadButton = By.xpath("//button[@ng-click='uploadFile()']");
	private By successToastMessage = By.xpath(
			"//div[@class='toast-container toast-position-top-right']//*[contains(text(),'The attachment was successfully uploaded')]");
	private By clickPatientName = By.xpath("//*[@class='PersonName-givenName']");

	public PatientDashboardPage(WebDriver driver) {
		super(driver);
	}

	// Click on the Attachments tab
	public void clickOnAttachmentsTab() {
		clickElement(attachmentsTab);
	}

	// Upload a file using input field
	public void uploadFile(String filePath) {
		sendKeysToFile(uploadInputField, filePath);
	}

	// Enter caption for uploaded file
	public void enterCaption(String caption) {
		sendKeysToElement(captionField, caption);
	}

	// Click the upload button
	public void clickUploadButton() {
		clickElement(uploadButton);
	}

	// Verify success toast message after file upload
	public boolean verifyFileUploadSuccess() {
		return isElementVisible(successToastMessage);
	}

	// Redirect to Patient Details screen by clicking name label
	public void clickPatientNameLabel() {
		clickElement(clickPatientName);
	}
}
