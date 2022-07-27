

package xcom.sc4j ;


import java.util.concurrent.TimeUnit ;

import org.junit.Assert ;
import org.junit.Before ;
import org.junit.Test ;
import org.openqa.selenium.By ;
import org.openqa.selenium.WebDriver ;
import org.openqa.selenium.WebElement ;
import org.openqa.selenium.chrome.ChromeDriver ;


public class _Test_Smoke {

	WebDriver driver ;

	@Before
	public void runDriver() {
		System.setProperty("webdriver.chrome.driver", "C:/_PDi/_cwg-base/Selenium/chromedriver_win32-v103.0.5060.134.exe") ;
		driver = new ChromeDriver() ;
	}

	@Test
	public void anySmoke() {

		driver.get("https://google.com") ;

		String title = driver.getTitle() ;
		Assert.assertEquals("Google", title) ;

		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS) ;

		WebElement searchBox = driver.findElement(By.name("q")) ;
		WebElement searchButton = driver.findElement(By.name("btnK")) ;

		searchBox.sendKeys("Selenium") ;
		searchButton.click() ;

		searchBox = driver.findElement(By.name("q")) ;
		String value = searchBox.getAttribute("value") ;
		Assert.assertEquals("Selenium", value) ;

		driver.quit() ;
	}
}
