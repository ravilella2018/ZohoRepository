package com.project.SalesForceCRM;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC_001 extends BaseTest
{
	
	@BeforeMethod
	public void startUp() throws IOException 
	{
		  initialize("chromebrowser", "zohourl");
	}
	
	@Test()
	public void launch() 
	{
	  
	}
  
 
	@AfterMethod
	public void endProcess() 
	{
	  
	}


  
}
