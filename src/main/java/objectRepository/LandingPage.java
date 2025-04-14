package objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LandingPage extends BasePage{

	private By usernameField = By.id("username");
	private By passwordField = By.id("password");
	private By loginButton = By.xpath("//*[@value=\"Log In\"]");
	private By laboratoryButton = By.id("Laboratory");

	public LandingPage(WebDriver driver) {
		 super(driver);
	}
	
	public void enterUsername(String username) {
        sendKeysToElement(usernameField, username);
    }

    public void enterPassword(String password) {
        sendKeysToElement(passwordField, password);
    }

    public void clickLoginButton() {
        clickElement(loginButton);
    }
	public void clickLaboratory() {
		clickElement(laboratoryButton);
	}

}
