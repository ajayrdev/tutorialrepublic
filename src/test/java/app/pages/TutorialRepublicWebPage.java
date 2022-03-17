package app.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import automation.utilities.SeleniumUtils;

public class TutorialRepublicWebPage {
	WebDriver driver;
	SeleniumUtils utils;

	public static By pageHeading = By.xpath("(//img[@alt='TutorialRepublic'])[1]");
//			("(//img[@alt='TutorialRepublic'])[1]");
//			("//div[@class ='logo']");
	public static By home = By.xpath("//a[@title='Home Page']");
	public static By html = By.xpath("//a[@title='HTML Tutorial']");
	public static By css = By.xpath("//a[@title='CSS Tutorial']");
	public static By javascript = By.xpath("//a[@title='JavaScript Tutorial']");
	public static By jQuery = By.xpath("//a[@title='jQuery Tutorial']");
	public static By bootstrap = By.xpath("//a[@title='Bootstrap 5 Tutorial']");
	public static By v4 = By.xpath("//a[@title='Bootstrap 4.6 Tutorial']");
	public static By php = By.xpath("//a[@title='PHP Tutorial']");
	public static By sql = By.xpath("//a[@title='SQL Tutorial']");
	public static By references = By.xpath("//a[@title='Web References']");
	public static By examples = By.xpath("//a[@title='Practice Examples and Demos']");
	public static By faq = By.xpath("//a[@title='Frequently Asked Questions and Answers']");

	public static By facebook = By.xpath("//a[@title='Join us on Facebook']//i");
	public static By twitter = By.xpath("//a[@title='Follow us on Twitter']//i");
	public static By mail = By.xpath("//a[@title='Send us an Email']//i");

	public static By socialMedia = By.xpath("//div[@class='social']//a");

	public static By chapterLinks = By.xpath("//div[@class='chapters']//a");

	public static By segment = By.xpath("(//div [@class='segment'])[1]");

	public TutorialRepublicWebPage(SeleniumUtils utils, WebDriver driver) {

		this.utils = utils;
		this.driver = driver;
	}

	public void launchTRWebPage(String appUrl) {
		driver.get(appUrl);
	}

	public void TRWebPageTitle(String pageTitle) {
//		System.out.println(driver.getTitle());
		Assert.assertEquals(driver.getTitle().contains(pageTitle), true, "Tutorial Republic page verification failed");
	}

	public void verifyPageHeading(String headingExpected) {
		String headingActual = utils.getElements(pageHeading).get(0).getAttribute("alt");
		System.out.println(headingActual);
		Assert.assertEquals(headingActual.contains(headingExpected), true, "Page heading verification failed");
	}

	public boolean verifyMenuOptions() {
		boolean isDisplayed = false;
		if (utils.isDisplayed(home) && utils.isDisplayed(html) && utils.isDisplayed(css)
				&& utils.isDisplayed(javascript) && utils.isDisplayed(jQuery) && utils.isDisplayed(bootstrap)
				&& utils.isDisplayed(v4) && utils.isDisplayed(php) && utils.isDisplayed(sql)
				&& utils.isDisplayed(references) && utils.isDisplayed(examples) && utils.isDisplayed(faq)) {
			isDisplayed = true;
		} else {

			System.out.println("Page Menu Options are mismatched");
		}

		return isDisplayed;
	}

	public boolean verifySocialMediaIcon() {
		boolean isDisplayed = false;
		if (utils.isDisplayed(facebook) && utils.isDisplayed(twitter) && utils.isDisplayed(mail)) {
			isDisplayed = true;
		} else {
			System.out.println("Page web options are mismatched");
		}
		return isDisplayed;
	}

	public void verifySocialMediaLinks() {
		List<WebElement> socialMediaIcons = utils.getElements(socialMedia);
		List<String> socialMediaLinks = new ArrayList<String>();

		for (WebElement socialMediaElement : socialMediaIcons) {
			socialMediaLinks.add(socialMediaElement.getAttribute("href").trim());
		}
		for (String socialMediaSourceUrl : socialMediaLinks) {

			driver.get(socialMediaSourceUrl);
			System.out.println(socialMediaSourceUrl);
		}
	}

	public void displayChapterLinks() {
		List<WebElement> chapterElements = utils.getElements(chapterLinks);
		int chapterElementsCount = chapterElements.size();
		int chapterUrlsCount = 0;

		Map<String, String> chatersObj = new HashMap<String, String>();
		for (WebElement chapterElement : chapterElements) {
			chatersObj.put(chapterElement.getAttribute("href").trim(), chapterElement.getText().trim());
		}

		for (Map.Entry<String, String> entry : chatersObj.entrySet()) {
			driver.get(entry.getKey());

			if (driver.getTitle().contains(entry.getValue())) {
				chapterUrlsCount++;
			}
			System.out.print("href:" + entry.getKey() + " -> ");
			System.out.println("chapter Name:" + entry.getValue());
		}

		if (chapterElementsCount == chapterUrlsCount) {
			System.out.println("All " + chapterUrlsCount + " page links are loading without issues");
		} else {
			System.out.println(chapterElementsCount);
			System.out.println(chapterUrlsCount);
			Assert.fail("Something went wrong. Chapter links are not working properly");
		}
		System.out.println("Url Count:" + chapterUrlsCount);
		System.out.println("source Links count:" + chapterElementsCount);

	}

	public void verifySegmentValue() {
		Map<String, String> menuListExpected = new HashMap<String, String>();
		menuListExpected.put("HOME", "WEb TUTORIALS");
		menuListExpected.put("HOME", "WEb TUTORIALS");
		menuListExpected.put("HOME", "WEb TUTORIALS");
		
		Map<String, String> menuListActual = new HashMap<String, String>();
		
		List<WebElement> menuLinks = utils.getElements(By.xpath("//div[@class='menu']//a"));
		
		for (WebElement menuLink : menuLinks) {
			if(!(menuLink.getText().trim().equals("Snippets"))) {
			WebElement element = menuLink;
			element.click();
			String menuKey = element.getText(); 
			String menuValue = utils.getText(By.xpath("(//div[@class='segment'])[1]")); 
			menuListActual.put(menuKey, menuValue);
			}
		}
		//Assertion.. need to verify if both the map objects are same
	}
}
