package PageObjects;
import java.io.IOException;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import base.TestBase;

public class APITest {

		public void Test1(WebDriver driver) throws NumberFormatException, IOException {

		Response response = RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=New Delhi&appid=7fe67bf08c80ded756e598d6f8fedaea");
		System.out.println(response.statusCode());
		
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
        String json = response.asString();
        
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        String temp1 = jsonObject.getAsJsonObject("main").get("temp_min").getAsString();
        float temp_min=Float.parseFloat(temp1); 
        float celsius_temp_min = temp_min - 273.15F;
		System.out.println("Celsius:min "+ celsius_temp_min);
		
		String temp2 = jsonObject.getAsJsonObject("main").get("temp_max").getAsString();
	    float temp_max=Float.parseFloat(temp2); 
	    float celsius_temp_max = temp_max - 273.15F;
	    System.out.println("Celsius:max "+ celsius_temp_max);
	    
	  
	}
}
