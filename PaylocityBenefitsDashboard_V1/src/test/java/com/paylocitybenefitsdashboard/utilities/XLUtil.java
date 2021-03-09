package com.paylocitybenefitsdashboard.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.mail.EmailException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtil 
{
	//Utility class to read excel data
	public static FileInputStream inputStream;
	public static FileOutputStream outputStream;
	public static XSSFWorkbook xlWorkbook;
	public static XSSFSheet xlSheet;
	public static XSSFRow row;
	public static XSSFCell cell;

	public static int getRowCount(String fileSrc, String sheet) throws IOException
	{
		inputStream = new FileInputStream(fileSrc);
		xlWorkbook = new XSSFWorkbook(inputStream);
		xlSheet = xlWorkbook.getSheet(sheet);		
		
		int rowCount = xlSheet.getLastRowNum();	
		
		xlWorkbook.close();
		inputStream.close();
		
		return rowCount;
	}
	
	public static int getColCount(String fileSrc, String sheet) throws IOException
	{
		inputStream = new FileInputStream(fileSrc);
		xlWorkbook = new XSSFWorkbook(inputStream);
		xlSheet = xlWorkbook.getSheet(sheet);
		
		row = xlSheet.getRow(1);
		
		int colCount = row.getLastCellNum();
		
		xlWorkbook.close();
		inputStream.close();
		
		return colCount;
	}
	
	public static String getCellData(String fileSrc, String sheet, int rowIndex, int colIndex) throws IOException
	{
		inputStream = new FileInputStream(fileSrc);
		xlWorkbook = new XSSFWorkbook(inputStream);
		xlSheet = xlWorkbook.getSheet(sheet);
		
		String cellData = xlSheet.getRow(rowIndex).getCell(colIndex).getStringCellValue();
		
		xlWorkbook.close();
		inputStream.close();
		
		return cellData;
	}
	
	public static String[][] getSheetData(String fileSrc, String sheet, int startColumn) throws IOException
	{
		int rowCount = getRowCount(fileSrc, sheet);
		int colCount = getColCount(fileSrc, sheet);
		
		//Return null if startColumn is out of bound
		if(startColumn >= colCount)
			return null;
		
		String data[][] = new String[rowCount][colCount - startColumn];
				
		int writeIndexRow = 0;
		for (int row = 1; row <= rowCount; row++)
		{
			int writeIndexCol = 0;
			for(int col = startColumn; col < colCount; col++)
			{
				data[writeIndexRow][writeIndexCol] = getCellData(fileSrc,sheet,row,col);
				writeIndexCol++;
			}
			writeIndexRow++;
		}

		return data;			
	}
	
	public static void emailReport() throws EmailException {
		//To do...
	}
}
