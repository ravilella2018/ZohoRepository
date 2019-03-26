package com.project.SalesForceCRM.genericUtils;

import java.util.Hashtable;

import com.project.SalesForceCRM.excelAPIs.ExcelAPI;

public class DataUtil 
{
	public static Object[][] getTextData(ExcelAPI xls, String testCaseName, String sheetName)
	{
		//String sheetName="data";		
		int testStartRowNum=0;
		
		while(!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) 
		{
			testStartRowNum++;
		}
		System.out.println("Test Starts from row :-  " + testStartRowNum);
		
		
		int colStartRowNum=testStartRowNum+1;
		int dataStartRowNum=testStartRowNum+2;
		
		//calculate rows of data
		int rows=0;
		while(!xls.getCellData(sheetName, 0, dataStartRowNum+rows).equals("")) 
		{
			rows++;
		}
		System.out.println("Total rows are :-  "+ rows);
		
		//Calculate total Columns
		int cols=0;
		while(!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) 
		{
			cols++;
		}
		System.out.println("Total Cols are :-  "+ cols);
		
		
		//read the data
		int dataRow=0;
		Object[][] data=new Object[rows][1];
		
		Hashtable<String,String> table=null;
		for(int rNum=dataStartRowNum;rNum<dataStartRowNum+rows;rNum++)
		{
			table=new Hashtable<String,String>();
			for(int cNum=0;cNum<cols;cNum++) 
			{
				String key=xls.getCellData(sheetName, cNum, colStartRowNum);
				String value=xls.getCellData(sheetName, cNum, rNum);
				table.put(key, value);
			}	
			data[dataRow][0]=table;
			dataRow++;
		}
		return data;	 

	}
	
	public static boolean isRunnable(String runnableSheet,String testName,ExcelAPI xls)
	{
		//String sheet="TestCases";
		int rows=xls.getRowCount(runnableSheet);
		for(int r=2;r<rows;r++)
		{
			String tName=xls.getCellData(runnableSheet, "TCID", r);
			if(tName.equals(testName))
			{
				String runMode=xls.getCellData(runnableSheet, "RunMode", r);
				if(runMode.equalsIgnoreCase("Y"))
					return true;
				else
					return false;
			}
		}
		return false;
	}
	
	
}
