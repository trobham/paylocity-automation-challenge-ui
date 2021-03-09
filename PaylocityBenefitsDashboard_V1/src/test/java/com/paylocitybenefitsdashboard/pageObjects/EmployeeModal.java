package com.paylocitybenefitsdashboard.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeModal {
	WebDriver driver;
	
	public EmployeeModal(WebDriver rdriver)
	{
		driver = rdriver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="employeeModal")
	@CacheLookup
	WebElement modalEmployee;
	
	@FindBy(id="firstName")
	@CacheLookup
	WebElement inputFirstName;
	
	@FindBy(id="lastName")
	@CacheLookup
	WebElement inputLastName;
	
	@FindBy(id="dependants")
	@CacheLookup
	WebElement inputDependents;
	
	@FindBy(id="addEmployee")
	@CacheLookup
	WebElement btnAddEmployee;
	
	@FindBy(id="updateEmployee")
	@CacheLookup
	WebElement btnUpdateEmployee;
	
	@FindBy(xpath="//div[@id='employeeModal']/descendant::button[text()='Cancel']")
	@CacheLookup
	WebElement btnCancel;
	
	public void setFirstName(String fname)
	{
		inputFirstName.clear();
		inputFirstName.sendKeys(fname);
	}
	
	public void setLastName(String lname)
	{
		inputLastName.clear();
		inputLastName.sendKeys(lname);
	}
	
	public void setDependents(String numDependents)
	{
		inputDependents.clear();
		inputDependents.sendKeys(numDependents);
	}
	
	public void clickAddBtn()
	{
		btnAddEmployee.click();
	}
	
	public void clickUpdateBtn()
	{
		btnUpdateEmployee.click();
	}
	
	public void clickCancelBtn()
	{
		btnCancel.click();
	}
	
	public boolean isEmployeeModalDisplayed()
	{
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("employeeModal")));		
		return modalEmployee.isDisplayed(); 
	}

}
