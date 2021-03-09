package com.paylocitybenefitsdashboard.helpers;

public class Employee {	
	final static double standardSalary = 52000;
	final static double standardGrossPay = 2000.00;	
	final static double numPaychecks = 26.00;
	final static double standardAnnualBenefitCost = 1000.00;	
	final static double standardAnnualDependentCost = 500.00;
	
	String id;
	String firstName;
	String lastName;
	int dependents;
	double salary;
	double grossPay;
	double benefitsCost;
	double netPay;
	
	public Employee(String empId, String empFirstName, String empLastName, int empDependents, double empSalary, double empGrossPay, double empBenefitsCost, double empNetpay)
	{
				  id = empId;
		   firstName = empFirstName;		
		    lastName = empLastName;
		  dependents = empDependents;
			  salary = empSalary;
		    grossPay = empGrossPay;
		benefitsCost = empBenefitsCost;
		      netPay = empNetpay;
	}
	
	public String getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public int getDependents() {
		return dependents;
	}
	public double getSalary() {
		return salary;
	}
	public double getGrossPay() {
		return grossPay;
	}
	public double getBenefitsCost() {
		return benefitsCost;
	}
	public double getNetPay() {
		return netPay;
	}
}
