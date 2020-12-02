package Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class BrokenLinks {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "//Users//alesia.zhiliaeva//Desktop//Selenium//chromedriver1");
		driver = new ChromeDriver();
		driver.get("https://www.pilulka.cz/");
	}

	@Test
	
	public void brokenLinks() {

		List<WebElement> links = driver.findElements(By.tagName("a"));
		

		for (int i = 0; i < links.size(); i++) {

			WebElement ele = links.get(i);

			String url = ele.getAttribute("href");
			
			verifyLinkActive(url);
		}

	}

		private static void verifyLinkActive(String linkUrl) {

		try {
			URL url = new URL (linkUrl);

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
