package app.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import app.pages.TutorialRepublicWebPage;
import automation.utilities.SeleniumUtils;

public class TutorialRepublicTest {

	TutorialRepublicWebPage webPage;

	SeleniumUtils utils;
	WebDriver driver;
	Properties prop;

	@BeforeClass
	public void loadData() throws IOException {
		String baseDirectory = System.getProperty("user.dir");
		String fileRelativePath = "\\src\\test\\resources\\file.properties";
		FileInputStream fis = new FileInputStream(baseDirectory + fileRelativePath);
		prop = new Properties();
		prop.load(fis);
	}

	@BeforeMethod
	public void setup() {
		utils = new SeleniumUtils();
		driver = utils.getDriver();
		webPage = new TutorialRepublicWebPage(utils, driver);

	}

	@Test(enabled = false)
	public void verifyTRWebPage() {

		String Url = prop.getProperty("TRUrl");
		String pageTitle = prop.getProperty("pageTitle");
		String pageHeading = prop.getProperty("pageheading");

		webPage.launchTRWebPage(Url);
		webPage.TRWebPageTitle(pageTitle);
		webPage.verifyPageHeading(pageHeading);
		webPage.verifyMenuOptions();
		webPage.verifySocialMediaIcon();
		webPage.verifySocialMediaLinks();
	}

	@Test(enabled = true)
	public void verifyTRChapterList() {

		String Url = prop.getProperty("TRUrl");
		webPage.launchTRWebPage(Url);
		webPage.displayChapterLinks();
	}

	@Test(enabled = false)
	public void verifyEachMenu() {
		String Url = prop.getProperty("TRUrl");

	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
