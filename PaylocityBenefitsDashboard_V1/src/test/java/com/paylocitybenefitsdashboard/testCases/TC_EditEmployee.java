package com.paylocitybenefitsdashboard.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.paylocitybenefitsdashboard.helpers.BenefitDashboardHelpers;
import com.paylocitybenefitsdashboard.utilities.XLUtil;

public class TC_EditEmployee extends BaseClass {	
	/*
	 * ****************************************************************
	 * This is DATA DRIVEN test. Uses employee data from excel sheet
	 * **************************************************************
	 * **************************************************************
	 */	
	@Test(dataProvider="employees")
	public void editEmployeeTest(String previousLastName, String previousFirstName, String newLastName, String newFirstName, String dependents)	
	{			
		BenefitDashboardHelpers dashboardHelpers = new BenefitDashboardHelpers(driver);
		
		//Edit employee
		boolean editEmployeeResult = dashboardHelpers.editEmployee(previousFirstName, previousLastName, newFirstName, newLastName, dependents);		                             
		  
 		//Assert edit action is success 
		Assert.assertEquals(editEmployeeResult, true, "Edit employee failed (FirstName: " + previousFirstName + ", LastName: " + previousLastName + ")");
			
		//Validate that benefit calculation is correct
		Assert.assertEquals(dashboardHelpers.isEmployeeDataCorrect(newLastName, newFirstName, Integer.parseInt(dependents)), true, "Employee benefit cost calculation is not accurate (FirstName: " + newFirstName + ", LastName: " + newLastName + ")");
	}
	
	@DataProvider(name="employees")
	String [][] data() throws IOException
	{
		String xlsFileSrc = System.getProperty("user.dir") + "\\src\\test\\java\\com\\paylocitybenefitsdashboard\\testData\\Employees.xlsx";		
		String employees[][] = XLUtil.getSheetData(xlsFileSrc, "employees-updated",0);
		
		System.out.println("Number of test employees: " + employees.length);
		logger.info("Number of test employees: " + employees.length);
		return employees;		
	}
}
