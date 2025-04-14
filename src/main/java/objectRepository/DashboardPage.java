package objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

	 // Locators for the elements
	private By registerPatientButton = By.xpath("//*[contains(@id, 'registerPatient')]");
	private By isLoggedIn = By.xpath("//*[contains(text(),'Logged in as Super User ')]");

	public DashboardPage(WebDriver driver) {
		 super(driver); // Call the BasePage constructor
	}

	 // Method to click on the 'Register Patient' button
    public void clickRegisterPatient() {
        clickElement(registerPatientButton); // Reusing clickElement from BasePage
    }

    // Method to check if the user is logged in (using visibility check)
    public boolean isloggedIn() {
        return isElementVisible(isLoggedIn); // Reusing isElementVisible from BasePage
    }
}
