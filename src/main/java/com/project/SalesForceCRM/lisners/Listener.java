package com.project.SalesForceCRM.lisners;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.project.SalesForceCRM.BaseTest;

public class Listener extends BaseTest implements ITestListener
{
	
	public void onTestFailure(ITestResult result) 
	{
		if(!result.isSuccess())
		{
			Reporter.log("<a href='" + System.getProperty("user.dir")+"//failure//"+screenshotFileName + "'> <img src='" + System.getProperty("user.dir")+"//failure//"+screenshotFileName + "' height='100' width='100'/> </a>");		
		}
	}
	
	
	public void onTestSuccess(ITestResult obj1) 
	{
		if(obj1.isSuccess())
		{
			try 
			{
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileHandler.copy(scrFile, new File(System.getProperty("user.dir")+"//success//"+screenshotFileName));
			
				Reporter.log("<a href='" + System.getProperty("user.dir")+"//success//"+screenshotFileName + "'> <img src='" + System.getProperty("user.dir")+"//success//"+screenshotFileName + "' height='100' width='100'/> </a>");	
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	
	public void onTestStart(ITestResult arg0) 
	{
		Reporter.log("Test started Running:" + arg0.getMethod().getMethodName());
	}
	
		
	public void onTestSkipped(ITestResult result) 
	{
		Reporter.log("Test is skipped:" + result.getMethod().getMethodName());
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) 
	{
	
		
	}

	public void onFinish(ITestContext arg0) 
	{
		//Reporter.log("Test is finished:" + ((ITestResult) arg0).getMethod().getMethodName());
		
	}

		
}
