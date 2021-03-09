package com.paylocitybenefitsdashboard.helpers;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonObject;
import com.paylocitybenefitsdashboard.pageObjects.BenefitsDashboardPage;
import com.paylocitybenefitsdashboard.pageObjects.DeleteModal;
import com.paylocitybenefitsdashboard.pageObjects.EmployeeModal;

public class BenefitDashboardHelpers
{
	private static Logger logger;
	WebDriver driver;
	
	public BenefitDashboardHelpers(WebDriver rdriver)
	{
		driver = rdriver;
		PageFactory.initElements(driver, this);
	} 
	
	//Gets table column indexes by name
	private JsonObject getTableHeaderColIndexes() 
	{
		JsonObject colIndexes = new JsonObject();
		
		List<WebElement> columnHeaders = driver.findElements(By.xpath("//table[@id='employeesTable']/thead/tr/th"));

		for(int i = 1; i <= columnHeaders.size(); i++)
		{
			String headerTxt = driver.findElement(By.xpath("//table[@id='employeesTable']/thead/tr/th[" + i +"]")).getText();
			switch (headerTxt)
			{
				case "Id":
					colIndexes.addProperty("id", i);
					break;
				case "Last Name":
					colIndexes.addProperty("lname", i);
					break;
				case "First Name":
					colIndexes.addProperty("fname", i);
					break;
				case "Dependents":
					colIndexes.addProperty("dependents", i);
					break;
				case "Salary":
					colIndexes.addProperty("salary", i);
					break;
				case "Gross Pay":
					colIndexes.addProperty("grossPay", i);
					break;
				case "Benefits Cost":
					colIndexes.addProperty("benefitsCost", i);
					break;
				case "Net Pay":
					colIndexes.addProperty("netPay", i);
					break;
				case "Actions":
					colIndexes.addProperty("actions", i);
					break;				
			}			
		}		
		
		return colIndexes;
	}	
	
	//Checks if employee exists using last and first names
	public boolean doesEmployeeExist(String lname, String fname)
	{		
		JsonObject tableHeaderColIndexes = getTableHeaderColIndexes();
		
		/*Let's break our xpath into pieces then reconstruct it using employee id and name as searching string */
		String xpathLeft = "//td[contains(text(), '";
		String xpathRight = "')]/parent::tr/td["; //+ tableHeaderColIndexes.get("id") +"]";
					
		//Find first name on the page and use the parent row to check if the name is present on 'First Name' column		
		if(!driver.findElement(By.xpath(xpathLeft + fname + xpathRight + tableHeaderColIndexes.get("fname") + "]")).getText().equals(fname))
			return false;
	    //Find last name on the page and use the parent row to check if the name is present on 'Last Name' column
		if(!driver.findElement(By.xpath(xpathLeft + lname + xpathRight + tableHeaderColIndexes.get("lname") + "]")).getText().equals(lname))
			return false;		
		
		return true;
	}
	//This method is workaround as employee id is not user friendly
	//Otherwise we would have used id provided by users when calling methods from this class
	public String getEmployeeId(String lname, String fname)
	{
		//First check if employee exists
		if(doesEmployeeExist(lname, fname) != true)
			return null;
		
		JsonObject tableHeaderColIndexes = getTableHeaderColIndexes();
		
		/*Let's break our xpath into pieces then reconstruct it using employee id and name as searching string */		
		String xpathLeft = "//td[contains(text(), '";
		String xpathRight = "')]/parent::tr/td[" + tableHeaderColIndexes.get("id") +"]";
		 			
		String employeeIdUsingLastName = driver.findElement(By.xpath(xpathLeft + lname + xpathRight)).getText();
		String employeeIdUsingFirstName = driver.findElement(By.xpath(xpathLeft + fname + xpathRight)).getText();
		
		//Check if id value retrieved using first name and last name matches
		if(employeeIdUsingLastName.equals(employeeIdUsingFirstName))
			return employeeIdUsingFirstName;		
	
		return null;
	}	
	
	public Employee getEmployee(String lname, String fname)
	{
		/* In this method we use employee's first and last name to find it on the benefit dashboard.
		 * We assumes there is not duplicate employee entry with matching first and last names. This is a workaround
		 * If employee id was user friendly we could have uniquely find the employee on the dashboard using the id
		 */
		JsonObject tableHeaderColIndexes = getTableHeaderColIndexes();
		
		Employee employee = null;//This holds employee detail
		 
		String employeeId = getEmployeeId(lname,fname);		
		
		if(employeeId != null) {	
			/*Let's break our xpath into pieces then reconstruct it using employee id as searching string */
			String xpathLeft = "//td[contains(text(), '" + employeeId + "')]/parent::tr/td[";
			String xpathRight = "]";
			
			int dependents = Integer.parseInt(driver.findElement(By.xpath(xpathLeft + tableHeaderColIndexes.get("dependents") +xpathRight)).getText());			
			double salary = Double.parseDouble(driver.findElement(By.xpath(xpathLeft + tableHeaderColIndexes.get("salary") +xpathRight)).getText());
			double grossPay = Double.parseDouble(driver.findElement(By.xpath(xpathLeft + tableHeaderColIndexes.get("grossPay") +xpathRight)).getText());			
			double benefitsCost = Double.parseDouble(driver.findElement(By.xpath(xpathLeft + tableHeaderColIndexes.get("benefitsCost") +xpathRight)).getText());			
			double netPay = Double.parseDouble(driver.findElement(By.xpath(xpathLeft + tableHeaderColIndexes.get("netPay") +xpathRight)).getText());
			
			//Generate the employee object and return it
			employee = new Employee(employeeId, fname, lname, dependents, salary, grossPay, benefitsCost, netPay); 
		}
		
		return employee;
	}
	//Add Employee: Launches employee modal and enters provided input values
	public boolean addEmployee(String lname, String fname, String numDependents)
	{
		//Launch Employee modal
		BenefitsDashboardPage dashboardPage = new BenefitsDashboardPage(driver); 
		dashboardPage.clickAddEmployeeBtn();
		
		//Enter employee information
		EmployeeModal employeeModal = new EmployeeModal(driver);		
				
		if(!employeeModal.isEmployeeModalDisplayed())
		{
			logger.error("Employee modal is not displayed, unable to add employee");
			System.out.println("Employee modal is not displayed");
			return false;			
		}		
		employeeModal.setFirstName(fname);
		employeeModal.setLastName(lname);
		employeeModal.setDependents(numDependents);
		employeeModal.clickAddBtn();
		
		return true;
	}

	//Edit Employee
	public boolean editEmployee(String oldFirstName, String oldLastName, String newFirstName, String newLastName, String newDependents)
	{
		// ***** Here we find the employee using first and last names for same reason as described in getEmployee()		
		String employeeId = getEmployeeId(oldLastName,oldFirstName);
		if(employeeId == null) {	
			logger.error("Employee with first name '" + oldFirstName + "' and last name '" + oldLastName + "' is not found");
			System.out.println("Employee with first name '" + oldFirstName + "' and last name '" + oldLastName + "' is not found");
			return false;
		}		
		JsonObject tableHeaderColIndexes = getTableHeaderColIndexes();

		//Launch Employee modal to update employee information
		String xpathLeft = "//td[contains(text(), '" + employeeId + "')]/parent::tr/td[";
		String xpathRight = "]/i[contains(@class, 'edit')]";
		driver.findElement(By.xpath(xpathLeft + tableHeaderColIndexes.get("actions") + xpathRight)).click();		

		EmployeeModal employeeModal = new EmployeeModal(driver);
		if(!employeeModal.isEmployeeModalDisplayed())
		{
			logger.error("Employee modal is not displayed");		
			System.out.println("Employee modal is not displayed");
			return false;			
		}
		
		//Set fields if specified
		if(newFirstName!="")
			employeeModal.setFirstName(newFirstName);
		if(newLastName!="")
			employeeModal.setLastName(newLastName);
		if(newDependents!="")
			employeeModal.setDependents(newDependents);		
		employeeModal.clickUpdateBtn();		
		
		//Wait until employee modal disappears.
		new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(By.id("employeeModal")));			
		
		return true;
	}
	
	//Delete Employee
	public boolean deleteEmployee(String lastName, String firstName) {
		// ***** Here we find the employee using first and last names for same reason as described in getEmployee() method		
		String employeeId = getEmployeeId(lastName,firstName);
		if(employeeId == null) {
			logger.error("Employee with first name '" + firstName + "' and last name '" + lastName + "' is not found");			
			System.out.println("Employee with first name '" + firstName + "' and last name '" + lastName + "' is not found");
			return false;
		}
		JsonObject tableHeaderColIndexes = getTableHeaderColIndexes();		
		
		//Launch Delete modal
		String xpathLeft = "//td[contains(text(), '" + employeeId + "')]/parent::tr/td[";
		String xpathRight = "]/i[contains(@class, 'times')]";
		
		driver.findElement(By.xpath(xpathLeft + tableHeaderColIndexes.get("actions") + xpathRight)).click();		

		DeleteModal deleteModal = new DeleteModal(driver);
		if(!deleteModal.isDeleteModalDisplayed())
		{
			logger.error("Delete modal is not displayed");
			System.out.println("Delete modal is not displayed");
			return false;			
		}
		  
		deleteModal.clickDeleteBtn();
		//Wait until employee modal disappears.		
		new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(By.id("employeeModal")));	
		
		return true;
	}
	//Verifies Benefit cost and net pay
	public boolean isEmployeeDataCorrect(String lname, String fname, int numDependents)
	{				
		//Gets standard values from Employee object and calculate expected benefit cost and net pay
		double dependentCostPerPaycheck = Employee.standardAnnualDependentCost / Employee.numPaychecks; 	
		double benefitCostPerPaycheck = Employee.standardAnnualBenefitCost / Employee.numPaychecks;			
		double totalDependentsCost 	= dependentCostPerPaycheck * numDependents;		 
		double totalBenefitsCost	= benefitCostPerPaycheck + totalDependentsCost;		
		double netPay = Employee.standardGrossPay - totalBenefitsCost;
		
		//Round final payouts to two decimal places
		netPay = Math.round((Employee.standardGrossPay - totalBenefitsCost)* 100.00)/100.00;
		totalBenefitsCost	= Math.round((benefitCostPerPaycheck + totalDependentsCost)* 100.00)/100.00;
		 
		Employee actualEmployeeData = getEmployee(lname, fname);
		
		if(actualEmployeeData == null)
		{
			logger.error("Employee with first name '" + fname + "' and last name '"+ lname + "' is not found.");
			System.out.println("Employee with first name '" + fname + "' and last name '"+ lname + "' is not found.");
			return false;
		}
		
		if( actualEmployeeData.getSalary() != Employee.standardSalary)
			return false;		
		if( actualEmployeeData.getGrossPay() != Employee.standardGrossPay)
			return false;
		if( actualEmployeeData.getDependents() != numDependents)
			return false;
		if( actualEmployeeData.getBenefitsCost() != totalBenefitsCost)
			return false;
		if( actualEmployeeData.getNetPay() != netPay)
			return false;
		
		return true;
	}	
}
