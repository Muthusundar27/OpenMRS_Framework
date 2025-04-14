package objectRepository;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.github.javafaker.Faker;

import pojo.PatientData;

public class RegisterPatientPage extends BasePage {

	private Faker faker = new Faker();
	private PatientData patientData;

	// Locators
	private By givenNameField = By.xpath("//input[@name=\"givenName\"]");
	private By familyNameField = By.xpath("//input[@name=\"familyName\"]");
	private By genderLabel = By.id("genderLabel");
	private By genderDropdown = By.id("gender-field");
	private By birthDateDayField = By.id("birthdateDay-field");
	private By birthDateMonthDropdown = By.id("birthdateMonth-field");
	private By birthDateYearField = By.id("birthdateYear-field");
	private By address1Field = By.id("address1");
	private By address2Field = By.id("address2");
	private By cityField = By.id("cityVillage");
	private By stateField = By.id("stateProvince");
	private By countryField = By.id("country");
	private By postalCodeField = By.id("postalCode");
	private By phoneNumberField = By.xpath("//input[@name=\"phoneNumber\"]");
	private By birthDateLabel = By.id("birthdateLabel");
	private By addressLabel = By.xpath("//span[text()=\"Address\"]");
	private By phoneLabel = By.xpath("//span[text()=\"Phone Number\"]");
	private By confirmationLabel = By.id("confirmation_label");
	private By confirmButton = By.xpath("//input[@value=\"Confirm\"]");

	public RegisterPatientPage(WebDriver driver, PatientData patientData) {
		super(driver);
		this.patientData = patientData;
	}

	// Registration Flow
	public void registerPatient() {
		generateTestData();
		enterPatientDetailsFromPojo();
	}

	private void generateTestData() {
		patientData.setFirstName(faker.name().firstName());
		patientData.setLastName(faker.name().lastName());
		patientData.setGender(faker.demographic().sex());

		LocalDate dob = LocalDate.of(faker.number().numberBetween(1990, 2010), faker.number().numberBetween(1, 13),
				faker.number().numberBetween(1, 28));
		patientData.setBirthDay(String.valueOf(dob.getDayOfMonth()));
		patientData.setBirthMonth(dob.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
		patientData.setBirthYear(String.valueOf(dob.getYear()));

		patientData.setAddress(faker.address().streetAddress());
		patientData.setAddress2(faker.address().secondaryAddress());
		patientData.setCity(faker.address().city());
		patientData.setState(faker.address().state());
		patientData.setCountry(faker.address().country());
		patientData.setPostalCode(faker.address().zipCode());
		patientData.setPhoneNumber(faker.phoneNumber().subscriberNumber(10));
	}

	private void enterPatientDetailsFromPojo() {
		sendKeysToElement(givenNameField, patientData.getFirstName());
		sendKeysToElement(familyNameField, patientData.getLastName());

		clickElement(genderLabel);
		selectDropdownByVisibleText(genderDropdown, patientData.getGender());

		clickElement(birthDateLabel);
		sendKeysToElement(birthDateDayField, patientData.getBirthDay());
		selectDropdownByVisibleText(birthDateMonthDropdown, patientData.getBirthMonth());
		sendKeysToElement(birthDateYearField, patientData.getBirthYear());

		clickElement(addressLabel);
		sendKeysToElement(address1Field, patientData.getAddress());
		sendKeysToElement(address2Field, patientData.getAddress2());
		sendKeysToElement(cityField, patientData.getCity());
		sendKeysToElement(stateField, patientData.getState());
		sendKeysToElement(countryField, patientData.getCountry());
		sendKeysToElement(postalCodeField, patientData.getPostalCode());

		clickElement(phoneLabel);
		sendKeysToElement(phoneNumberField, patientData.getPhoneNumber());

		clickElement(confirmationLabel);
	}

	public void clickConfirmButton() {
		clickElement(confirmButton);
	}

	// Verification
	public void verifyConfirmationPageDetails() {
		// Actual values from confirmation page
		String actualFirstName = driver.findElement(By.xpath("//div[@id='dataCanvas']//p[span[text()='Name: ']]"))
				.getText().replace("Name: ", "").trim().split(",")[0];
		String actualFamilyName = driver.findElement(By.xpath("//div[@id='dataCanvas']//p[span[text()='Name: ']]"))
				.getText().replace("Name: ", "").trim().split(",")[1].trim();
		String actualGender = driver.findElement(By.xpath("//div[@id='dataCanvas']//p[span[text()='Gender: ']]"))
				.getText().replace("Gender: ", "").trim();

		String[] actualBirthDate = driver
				.findElement(By.xpath("//div[@id='dataCanvas']//p[span[text()='Birthdate: ']]")).getText()
				.replace("Birthdate: ", "").trim().split(", ");
		List<String> actualBirthDateList = new ArrayList<>(Arrays.asList(actualBirthDate));

		String[] actualAddress = driver.findElement(By.xpath("//div[@id='dataCanvas']//p[span[text()='Address: ']]"))
				.getText().replace("Address: ", "").trim().split(", ");
		List<String> actualAddressList = new ArrayList<>(Arrays.asList(actualAddress));

		String actualPhoneNum = driver
				.findElement(By.xpath("//div[@id='dataCanvas']//p[span[text()='Phone Number: ']]")).getText()
				.replace("Phone Number: ", "").trim();

		// Expected values from POJO
		Assert.assertEquals(actualFirstName, patientData.getFirstName(), "First name doesn't match.");
		Assert.assertEquals(actualFamilyName, patientData.getLastName(), "Last name doesn't match.");
		Assert.assertEquals(actualGender, patientData.getGender(), "Gender doesn't match.");

		List<String> expectedBirthDate = new ArrayList<>();
		expectedBirthDate.add(patientData.getBirthDay());
		expectedBirthDate.add(patientData.getBirthMonth());
		expectedBirthDate.add(patientData.getBirthYear());

		Assert.assertEquals(actualBirthDateList, expectedBirthDate, "Birth date doesn't match.");

		List<String> expectedAddress = new ArrayList<>();
		expectedAddress.add(patientData.getAddress());
		expectedAddress.add(patientData.getAddress2());
		expectedAddress.add(patientData.getCity());
		expectedAddress.add(patientData.getState());
		expectedAddress.add(patientData.getCountry());
		expectedAddress.add(patientData.getPostalCode());

		Assert.assertEquals(actualAddressList, expectedAddress, "Address doesn't match.");
		Assert.assertEquals(actualPhoneNum, patientData.getPhoneNumber(), "Phone number doesn't match.");
	}

}
