package com.paylocitybenefitsdashboard.testCases;

import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.paylocitybenefitsdashboard.pageObjects.BenefitsDashboardPage;
import com.paylocitybenefitsdashboard.pageObjects.LoginPage;
import com.paylocitybenefitsdashboard.utilities.ReadConfig;

public class BaseClass {
	
	ReadConfig readConfig = new ReadConfig();
	public String baseURL = readConfig.getApplicationURL();
	public String userName = readConfig.getUserName();
	public String password = readConfig.getPWD();
	public String pageTitle = readConfig.getPageTitle();
	
	public static Logger logger;
	public static WebDriver driver;
	
	@Parameters("browser")
	@BeforeSuite
	public void setup(String browser)
	{
		logger = Logger.getLogger("benefitsdashboard");
		PropertyConfigurator.configure("Log4j.properties");
		
		if(browser.contentEquals("IE"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ readConfig.getChromeDriverPath());
			driver = new ChromeDriver();
		}
		else if(browser.contentEquals("firefox"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ readConfig.getGeckoDriverPath());
			driver = new FirefoxDriver();		
		}
		else 
		{
			//Make Chrome default
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ readConfig.getChromeDriverPath());
			driver = new ChromeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseURL);	
		
		//Login to application and assert is successful before continuing test execution
		Assert.assertTrue(login(),"Login failed. Unable to continue test"); 
	}
	
	public boolean isAlertPresent()
	{		
		try {
			driver.switchTo().alert();
			return true;
		}
		catch (NoAlertPresentException Ex)
		{
			return false;
		}
	}
	
	public boolean login()
	{	
		LoginPage loginPage = new LoginPage(driver);
		
		loginPage.setUserName(userName);		
		loginPage.setPassWord(password);		
		loginPage.clickLogin();
				
	  	if(isAlertPresent()) 
	  	{
		  //Log alert text and switch focus to the alert
		  logger.error("Alert is present, unable to login to application. Alert text: '" + driver.switchTo().alert().getText() + "'");
		  
		  driver.switchTo().alert().accept(); 
		  driver.switchTo().defaultContent();		  
		  return false; 
	  	} 
	  	else 
	  	{	
	  	  //Check employees benefits dashboard is displayed to confirm login is a success
		  BenefitsDashboardPage dashboardPage = new BenefitsDashboardPage(driver); 	  	  
		  if(!dashboardPage.isEmployeeTableDisplayed())
		  {
			  logger.error("Benefit dashboard is not displayed, unable to continue test");			  
			  return false;	
		  }
		   
		  return true; 
	  	}		 
	}
	
	
	@AfterSuite
	public void tearDown()
	{
		driver.quit();
	}
}
