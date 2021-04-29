package TestCases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import PageObjects.Home;
import base.TestBase;

public class Login {

	public static WebDriver driver = null;

	@BeforeClass
	public void beforeMethod() {
		driver = TestBase.OpenBrowser("chrome", "Sheet1");
	}

	@Test
	public void login() throws InterruptedException {
		Home.loginND(driver);
	}

	
	@AfterClass
	public void afterMethod() {
		TestBase.waitFor(2);
		driver.close();
	}

}
