package Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MediaSledujteNas {
	WebDriver driver;

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "//Users//alesia.zhiliaeva//Desktop//Selenium//chromedriver1");
		driver = new ChromeDriver();
		driver.get("https://www.pilulka.cz/");
		driver.manage().window().maximize();

	}

	@Test
	public void workingLinks() throws InterruptedException {

		// Limiting WebDriver scope
		WebElement footdriver = driver
				.findElement(By.cssSelector("body > div.footer__bottom.footer__bottom--cs > div.container"));

		WebElement columndriver = footdriver.findElement(By.cssSelector(
				"body > div.footer__bottom.footer__bottom--cs > div.container > div:nth-child(1) > div:nth-child(4) > div"));

		List<WebElement> links = columndriver.findElements(By.tagName("a"));

		System.out.println("Total links: " + links.size());

		// Click on each link in the column and check if pages are opening
		for (int i = 1; i < columndriver.findElements(By.tagName("a")).size() - 3; i++) {

			String clickonlinkTab = Keys.chord(Keys.COMMAND, Keys.ENTER);

			columndriver.findElements(By.tagName("a")).get(i).sendKeys(clickonlinkTab);
			Thread.sleep(500L);

		}

		for (int i = 1; i < links.size(); i++) {

			WebElement ele = links.get(i);

			String url = ele.getAttribute("href");

			verifyLinkActive(url);
		}

		// Opens all the links
		Set<String> abc = driver.getWindowHandles();
		Iterator<String> it = abc.iterator();

		while (it.hasNext()) {

			driver.switchTo().window(it.next());
			Thread.sleep(1000L);
			System.out.println(driver.getTitle());

		}
	}

	private static void verifyLinkActive(String linkUrl) {

		try {

			URL url = new URL(linkUrl);

			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

			httpURLConnect.setConnectTimeout(3000);

			httpURLConnect.connect();

			if (httpURLConnect.getResponseCode() == 200) {
				System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
			}
			if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - "
						+ HttpURLConnection.HTTP_NOT_FOUND);
			}
		} catch (Exception e) {

		}
	}

	@AfterMethod
	public void tearDown() {

		driver.quit();
	}
}
