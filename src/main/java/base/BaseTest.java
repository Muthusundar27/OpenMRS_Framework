package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {

	public static WebDriver driver;

	public void launchApplication() throws IOException {
		try {
			if (driver == null) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--incognito");
				// options.addArguments("--disable-infobars");
				options.addArguments("--disable-notifications");

				driver = new ChromeDriver(options);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
				driver.manage().window().maximize();

				// Launch the application URL
				driver.get(getGlobalValue("app_url"));
			}
		} catch (Exception e) {
			// Log exception if any issue occurs while launching the application
			throw new IOException("Failed to launch the application: " + e.getMessage());
		}
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	// Method to fetch the value of a global configuration property
	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		try (FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\config-files\\config.properties")) {
			prop.load(fis);
		} catch (IOException e) {
			throw new IOException("Failed to load configuration properties: " + e.getMessage());
		}
		return prop.getProperty(key);
	}
}
