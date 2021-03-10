Paylocity automation challenge - benefits dashboard selenium UI tests

This automation challenge repository contains Selenium Java UI automation tests using TestNG. The test framework is Data Driven. 
It reads xml files to add/delete/edit employees through Paylocity Employees Benefits dashboard ui. Contains three tests cases and helper/utility classes:
 1. TC_AddEmployee: Adds employees from excel file and validates data accuracy on the benefit dashboard
 2. TC_DeleteEmployee: Deletes pre created employees.
 3. TC_EditEmployee: Edits employees added by previous test

Since the tests are TestNG tests, you need to run them from the xml file. You can include/exclude tests from the Test Suite from TestNG.xml file.
