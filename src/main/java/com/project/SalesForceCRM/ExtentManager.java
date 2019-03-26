package com.project.SalesForceCRM;

import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager
{
	public static ExtentReports extent;
	
	public static ExtentReports getInstance()
	{
		if(extent == null)
		{
			Date dt=new Date();
			String fileName = dt.toString().replace(":", "_").replace(" ", "_")+".html";
			extent=new ExtentReports(System.getProperty("user.dir")+"//src//main//java//com//project//SalesForceCRM//HTMLReports//"+fileName);
			extent.loadConfig(new File(System.getProperty("user.dir")+"//src//main//java//com////project//SalesForceCRM//config//extentreportconfig.xml"));
			extent.addSystemInfo("Selenium Version", "3.8.1").addSystemInfo("Environment","QA" );
		}
		return extent;
	}
}
