package objectRepository;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pojo.PatientData;

public class FindPatientPage extends BasePage {

	// Patient data object
	private PatientData patientData;

	// Locators
	private By searchField = By.id("patient-search");
	private By patientTable = By.xpath("//table[@id='patient-search-results-table']/tbody/tr/td");
	private By noRecordsFound = By.xpath("(//*[contains(text(),'No matching records found')])[2]");

	public FindPatientPage(WebDriver driver, PatientData patientData) {
		super(driver); // Call the constructor of BasePage
		this.patientData = patientData;
	}

	// Method to enter patient name in the search field and search
	public void navigateToFindPatientPage() {
		sendKeysToElement(searchField, patientData.getFirstName() + " " + patientData.getLastName());
	}

	// Method to verify if patient is listed in the results or not
	public boolean isPatientListed() {
		List<WebElement> cells = driver.findElements(patientTable);
		WebElement noRecords = driver.findElement(noRecordsFound);
		return cells.size() == 0 || isElementVisible(noRecordsFound);
	}

}
