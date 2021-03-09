package com.paylocitybenefitsdashboard.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.paylocitybenefitsdashboard.helpers.BenefitDashboardHelpers;
import com.paylocitybenefitsdashboard.utilities.XLUtil;

public class TC_DeleteEmployee extends BaseClass 
{

	String uname = "TestUser76"; 
	String pwd = "/GHr*Ub;CjCn";
	boolean isAccValid = true;	
	/*
	 * ****************************************************************
	 * This is DATA DRIVEN test. Uses employee data from excel sheet
	 * **************************************************************
	 * **************************************************************
	 */	
	 
	@Test(dataProvider="employees")
	public void deleteEmployeeTest(String lastName, String firstName, String dependents)
	{			
		BenefitDashboardHelpers dashboardHelpers = new BenefitDashboardHelpers(driver);

		//Delete employee		  
		boolean deleteEmployeeResult = dashboardHelpers.deleteEmployee(lastName, firstName);
				
		//Assert delete action is success
		Assert.assertEquals(deleteEmployeeResult, true, "Delete employee action failed (fname: " + firstName +", lname: " + lastName + ")");	
	  
		//Verify deleted employee no longer exist on dashboard	
		Assert.assertEquals(dashboardHelpers.doesEmployeeExist(lastName, firstName), false, "Deleted employee still exists on dashboard (fname: " + firstName +", lname: " + lastName + ")");		
	}
	
	@DataProvider(name="employees")
	String [][] data() throws IOException
	{
		String xlsFileSrc = System.getProperty("user.dir") + "\\src\\test\\java\\com\\paylocitybenefitsdashboard\\testData\\Employees.xlsx";		
		String employees[][] = XLUtil.getSheetData(xlsFileSrc, "employees-updated",2);
		
		System.out.println("Number of test employees: " + employees.length);
		
		logger.info("Number of test employees: " + employees.length);
		return employees;		
	}
}
