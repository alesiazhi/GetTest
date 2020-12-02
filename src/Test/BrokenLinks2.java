package Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.WebElement;

public class BrokenLinks2 {
	
	WebDriver driver;

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "//Users//alesia.zhiliaeva//Desktop//Selenium//chromedriver1");
		driver = new ChromeDriver();
		driver.get("https://www.pilulka.cz/");
	}
	
	@Test
	
	public void brokenLinks2() throws MalformedURLException, IOException {
		
		List<WebElement> links =  driver.findElements(By.cssSelector("div[class='footer__bottom footer__bottom--cs']"));

	SoftAssert a =new SoftAssert();

 
    for(WebElement link : links)


         {

         
             String url= link.getAttribute("href");

           
             HttpURLConnection   conn= (HttpURLConnection)new URL(url).openConnection();

             conn.setRequestMethod("HEAD");

             conn.connect();

             int respCode = conn.getResponseCode();

             System.out.println(respCode);

             a.assertTrue(respCode<400, "The link with Text"+link.getText()+" is broken with code" +respCode);

           

         }

 
    a.assertAll();



}}
