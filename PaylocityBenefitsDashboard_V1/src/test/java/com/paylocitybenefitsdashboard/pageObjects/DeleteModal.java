package com.paylocitybenefitsdashboard.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeleteModal {
	WebDriver driver;
	
	public DeleteModal(WebDriver rdriver)
	{
		driver = rdriver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="deleteModal")
	@CacheLookup
	WebElement deleteModal;
	
	@FindBy(xpath="div[@id='deleteModal']/descendant::div[@class='modal-header']")
	@CacheLookup
	WebElement header;

	@FindBy(xpath="//div[@id='deleteModal']/descendant::div[@class='modal-body']")
	@CacheLookup
	WebElement body;

	@FindBy(id="deleteEmployee")
	@CacheLookup
	WebElement btnDelete;

	@FindBy(xpath="div[@id='deleteModal']/descendant::button[text()='Cancel']")
	@CacheLookup
	WebElement btnCancel;
	

	public void clickDeleteBtn()
	{
		btnDelete.click();
	}
	
	public void clickCancelBtn()
	{
		btnCancel.click();
	}
	
	public boolean isDeleteModalDisplayed()
	{
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteModal")));
		return deleteModal.isDisplayed(); 
	}
}
