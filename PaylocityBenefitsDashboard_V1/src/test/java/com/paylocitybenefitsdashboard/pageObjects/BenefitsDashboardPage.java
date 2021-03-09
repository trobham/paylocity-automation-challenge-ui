package com.paylocitybenefitsdashboard.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BenefitsDashboardPage {

	WebDriver ldriver;
	
	public BenefitsDashboardPage(WebDriver rdriver)
	{		
		ldriver = rdriver;
		PageFactory.initElements(ldriver, this);
	}
	

	@FindBy(xpath="a[text()='Log Out']")
	@CacheLookup
	WebElement logoutBtn;
	
	@FindBy(id="add")
	@CacheLookup
	WebElement addEmployeeBtn;
	
	@FindBy(id="employeesTable")
	@CacheLookup
	WebElement employeesTable;

	public boolean isEmployeeTableDisplayed()
	{
		new WebDriverWait(ldriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("employeesTable")));		
		System.out.println("Logout displayed: " + employeesTable.isDisplayed());
		return employeesTable.isDisplayed();
	}	

	public void clickLogoutBtn()
	{
		logoutBtn.click();
	}	

	public void clickAddEmployeeBtn()
	{
		addEmployeeBtn.click();
	}
	
	
}
