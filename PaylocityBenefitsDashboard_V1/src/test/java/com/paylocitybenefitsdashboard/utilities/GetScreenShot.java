package com.paylocitybenefitsdashboard.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.paylocitybenefitsdashboard.testCases.BaseClass;


public class GetScreenShot extends BaseClass {
	
	public String screenCapture(String name) throws IOException {		
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String imgPath = System.getProperty("user.dir") + "\\ScreenShots\\" + name + ".png";
		File path = new File(imgPath);
		FileUtils.copyFile(srcFile, path);
		return imgPath;		
	}

}
