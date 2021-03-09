package com.paylocitybenefitsdashboard.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig 
{
	//Reads configuration properties from file
	Properties properties;
	
	public ReadConfig()
	{
		File src = new File("./configuration/config.properties");		
		try {
			FileInputStream inputStream = new FileInputStream(src);			
			properties = new Properties();
			properties.load(inputStream);
		} catch (Exception e) {
			System.out.println("Exception opening fie: " + e.getMessage());
		}
	}
	
	public String getApplicationURL() {
		String url = properties.getProperty("baseURL");
		return url;
	}
	
	public String getUserName() {
		String uname = properties.getProperty("username");
		return uname;
	}

	public String getPWD() {
		String pwd = properties.getProperty("password");
		return pwd;
	}

	public String getPageTitle() {
		String pageTitle = properties.getProperty("pageTitle");
		return pageTitle;
	}
	
	public String getChromeDriverPath() {
		String driverPath = properties.getProperty("chromedriverpath");
		return driverPath;
	}

	public String getIEDriverPath() {
		String driverPath = properties.getProperty("iedriverpath");
		return driverPath;
	}
	
	public String getGeckoDriverPath() {
		String driverPath = properties.getProperty("geckodriverpath");
		return driverPath;
	}
}
