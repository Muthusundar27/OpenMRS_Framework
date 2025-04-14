package objectRepository;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// A utility method for waiting for an element to be visible
	public WebElement waitForElementToBeVisible(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// A utility method for waiting for an element to be clickable
	public WebElement waitForElementToBeClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	// A utility method for clicking an element
	public void clickElement(By locator) {
		WebElement element = waitForElementToBeClickable(locator);
		element.click();
	}

	// A utility method for getting the text of an element
	public String getElementText(By locator) {
		return waitForElementToBeVisible(locator).getText();
	}

	// A utility method for sending text to an input field
	public void sendKeysToElement(By locator, String text) {
		WebElement element = waitForElementToBeVisible(locator);
		element.clear(); // Clear the field before entering text
		element.sendKeys(text);
	}

	// A utility method for sending file to an input field
	public void sendKeysToFile(By locator, String text) {
		WebElement element = driver.findElement(locator);
		element.sendKeys(text);
	}

	// A utility method for clearing the input field
	public void clearElement(By locator) {
		WebElement element = waitForElementToBeVisible(locator);
		element.clear();
	}

	// Reusable method to check if an element is visible
	public boolean isElementVisible(By locator) {
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return element.isDisplayed();
		} catch (Exception e) {
			return false; // Return false if the element is not visible
		}
	}

	public void selectDropdownByVisibleText(By locator, String text) {
		new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator))).selectByVisibleText(text);
	}

	public String getText(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
	}
}
