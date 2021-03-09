package com.paylocitybenefitsdashboard.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.paylocitybenefitsdashboard.helpers.BenefitDashboardHelpers;
import com.paylocitybenefitsdashboard.utilities.XLUtil;

public class TC_AddEmployee extends BaseClass 
{
	/*
	 * ****************************************************************
	 * This is DATA DRIVEN test. Uses employee data from excel sheet
	 * **************************************************************
	 * **************************************************************
	 */	
	@Test(dataProvider="employees")
	public void addEmployeeTest(String lastName, String firstName, String dependents)
	{					
		BenefitDashboardHelpers dashboardHelpers = new BenefitDashboardHelpers(driver);
		
		//Add employee
		boolean addEmployeeResult = dashboardHelpers.addEmployee(lastName, firstName, dependents);
		 
		//Assert add action is success
		Assert.assertEquals(addEmployeeResult, true, "Add employee failed (fname: " + firstName +", lname: " + lastName + ")");
			
		//Validate that benefit calculation is correct
		boolean isEmployeeDataCorrect = dashboardHelpers.isEmployeeDataCorrect(lastName, firstName, Integer.parseInt(dependents));
		Assert.assertEquals(isEmployeeDataCorrect, true, "Employee benefit cost calculation is not accurate (firstName: " + firstName + ", lastName: " + lastName + ")");
	}	

	@DataProvider(name="employees")
	String [][] data() throws IOException
	{
		String xlsFileSrc = System.getProperty("user.dir") + "\\src\\test\\java\\com\\paylocitybenefitsdashboard\\testData\\Employees.xlsx";		
		String employees[][] = XLUtil.getSheetData(xlsFileSrc, "employees-valid",0);
		
		System.out.println("Number of valid test employees: " + employees.length);
		logger.info("Number of valid test employees: " + employees.length);
		return employees;		
	}
}
