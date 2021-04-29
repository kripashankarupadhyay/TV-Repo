package PageObjects;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import base.Constant;
import base.TestBase;
import common.ReadExcel;
import io.restassured.RestAssured;

public class Home {

	static By searchButton = By.xpath("//a[@id='h_sub_menu']");
	static By selectWeather = By.xpath("//a[text()='WEATHER']");
	static By searchBox = By.xpath("//div[@class='searchContainer']//input[@id='searchBox']");
	static By cityCheckBox = By.xpath("//div[@class='message']//label/input[@id='New Delhi']");
	static By tempinCelcius = By.xpath("(//div[@class='outerContainer' and @title='New Delhi']//following::span)[1]");
	static By tempinFerenheight = By.xpath("//div[@class='outerContainer' and @title='New Delhi']//following-sibling::span");	
	
	public static void loginND(WebDriver driver) {
		try {

			// Click on three ... button
			driver.findElement(searchButton).click();
			TestBase.waitFor(1);
			String cityName = ReadExcel.getCellData(Constant.TestCase, Constant.CityName);
			
			// Click on weather tab
			driver.findElement(selectWeather).click();
			TestBase.waitFor(1);

			// Enter city Name
			driver.findElement(searchBox).click();
			driver.findElement(searchBox).sendKeys(ReadExcel.getCellData(Constant.TestCase, Constant.CityName));
			TestBase.waitFor(1);

			// Click on city check box if not selected
			if(driver.findElement(By.xpath("//div[@class='message']//label/input[@id='"+cityName+"']")).isSelected() == false)
			{
			driver.findElement(By.xpath("//div[@class='message']//label/input[@id='"+cityName+"']")).click();
			TestBase.waitFor(1);
			}
			
			String str1 = driver.findElement(By.xpath("(//div[@class='outerContainer' and @title='"+cityName+"']//following::span)[1]")).getText();
			String s_C = str1.substring(0, str1.length()-1);;
			int tempvalueinC=Integer.parseInt(s_C);  
			System.out.println(tempvalueinC);
			
			String str2 = driver.findElement(By.xpath("//div[@class='outerContainer' and @title='"+cityName+"']//following-sibling::span")).getText();
			String s_F = str2.substring(0, str2.length()-1);
			int tempvalueinF=Integer.parseInt(s_F);  
			System.out.println(tempvalueinF);
			TestBase.waitFor(1);
			
			driver.close();
			
			io.restassured.response.Response response = RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=New Delhi&appid=7fe67bf08c80ded756e598d6f8fedaea");
			System.out.println(response.statusCode());
			
			System.out.println(response.getBody().asString());
			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
	        String json = response.asString();
	        
	        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
	        String temp1 = jsonObject.getAsJsonObject("main").get("temp_min").getAsString();
	        float temp_min=Float.parseFloat(temp1); 
	        float t_min = temp_min - 273.15F;
	        int celsius_temp_min = Math.round(t_min);
			System.out.println("Celsius:min "+ celsius_temp_min);
			
			String temp2 = jsonObject.getAsJsonObject("main").get("temp_max").getAsString();
		    float temp_max=Float.parseFloat(temp2); 
		    float t_max = temp_max - 273.15F;
		    int celsius_temp_max = Math.round(t_max);
		    System.out.println("Celsius:max "+ celsius_temp_max);
		    
		    if(tempvalueinC - celsius_temp_max <= 5)
		    {
		    	System.out.println("Temp magnitude validation Passed");
		    }
		    else
		    {
		    	System.out.println("Temp magnitude validation Failed");
		    }
			
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
