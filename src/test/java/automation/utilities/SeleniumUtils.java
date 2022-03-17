package automation.utilities;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {

	WebDriver driver = null;
	public static Logger logger = LogManager.getLogger(SeleniumUtils.class);

	public SeleniumUtils() {
		String baseDirectory = System.getProperty("user.dir");
		String driverPath = "\\src\\test\\resources\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", baseDirectory + driverPath);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		// Implicit wait and this will be applicable to whole webdriver
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	public void closeBroswer() {
		try {
			driver.close();
			driver.quit();
			logger.info("Bowser closed");
		} catch (Exception e) {
			logger.error("Caught an exception at closeBroswer() " + e);
			throw e;
		}
	}

	public void getPageTitle() {
		driver.getTitle();
	}

	public void clickElement(By by) {
		try {
			logger.info("Clicking on element " + by);
			ReturnElement(by, "Clickable").click();
			logger.info("Clicked on element " + by);
		} catch (Exception e) {
			logger.error("Caught an exception at clickElement() " + e);
			throw e;
		}
	}

	public void enterText(By by, String text) {
		ReturnElement(by, "Visible").sendKeys(text);
	}

	public String getText(By by) {
		logger.info("Getting text on the element " + by);
		return ReturnElement(by, "Visible").getText();
	}

	public String getTextEntered(By by) {
		try {
			logger.info("Getting text on the element " + by);
			
			return ReturnElement(by, "Visible").getAttribute("value").trim();
		} catch (Exception e) {
			logger.error("Caught an exception at getTextEntered() " + e);
			throw e;
		}
	}

	public List<WebElement> getElements(By by) {
		logger.info("Getting elements from list"+ by );
		return ReturnElements(by, "Present");
	}

	public boolean isDisplayed(By by) {
		logger.info("Getting Displayed element info" + by);
		return ReturnElement(by, "Visible").isDisplayed();
	}

	public boolean isEnabled(By by) {
		logger.info("Getting enabled element info" + by);
		return ReturnElement(by, "Visible").isEnabled();
	}

	public boolean isSelected(By by) {
		logger.info("Getting selected list"+ by);
		return ReturnElement(by, "Visible").isSelected();
	}

	public void selectByValue(By by, String value) {
		Select select = new Select(ReturnElement(by, "Visible"));
		select.selectByValue(value);
	}

	public void selectByVisibleText(By by, String visibleText) {
		Select select = new Select(ReturnElement(by, "Visible"));
		select.selectByVisibleText(visibleText);
	}

	public void selectByIndex(By by, int index) {
		Select select = new Select(ReturnElement(by, "Visible"));
		select.selectByIndex(index);
	}

	public List<WebElement> ReturnElements(By by, String action) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		List<WebElement> elements = null;
		switch (action) {
		case "Visible":
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
			elements = driver.findElements(by);
			break;
		case "Present":
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
			elements = driver.findElements(by);
			break;
		default:
			break;
		}
		return elements;
	}

	public void captureScreenshot() {
		try {
			TakesScreenshot ts = ((TakesScreenshot) driver);
			File srcFile = ts.getScreenshotAs(OutputType.FILE);
			File DestFile = new File(
					"./src/test/resources/screenshots" + "/screenshot_" + RandomStringUtils.randomNumeric(5) + ".png");
			FileUtils.copyFile(srcFile, DestFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Explicit wait
	public WebElement ReturnElement(By by, String action) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement element = null;
		switch (action) {
		case "Visible":
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			element = driver.findElement(by);
			break;
		case "Present":
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			element = driver.findElement(by);
			break;
		case "Clickable":
			wait.until(ExpectedConditions.elementToBeClickable(by));
			element = driver.findElement(by);
			break;
		default:
			break;
		}
		return element;
	}

	public void scrollToElement(By locator) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement element = ReturnElement(locator, "Visible");
		jse.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void clickElementJS(By locator) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement element = ReturnElement(locator, "Visible");
		jse.executeScript("arguments[0].click();", element);
	}

	public void waitForPageToLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("return document.readyState").toString().equals("complete");
	}

	public void launchNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open()");
	}

}
