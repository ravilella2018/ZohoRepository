package com.project.SalesForceCRM;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest 
{
	//initializing the property file Reading
	public static Properties config=null;
	public static Properties or=null;
	public static Properties logs=null;
	public static Properties eReports=null;
	
	
	public static FileInputStream fis=null;
	public static WebDriver driver=null;
	public static EventFiringWebDriver eventDriver=null;
	public static boolean isLoggedIn=false; 
	public static WebDriverWait wait=null;
	public static String screenshotFileName=null;
	
	//Extent Report Object
	//public ExtentReports rep=ExtentManager.getInstance();
	public ExtentTest test;
	
	
	static 
	{
		Date dt=new Date();
		screenshotFileName = dt.toString().replace(":", "_").replace(" ", "_")+".png";
	}
	
	
	public static void initialize(String browserKey, String urlKey) throws IOException
	{
		if(driver==null)
		{
			//Config property file
			config=new Properties();
			fis=new FileInputStream(System.getProperty("user.dir")+"//src//main//java//com//project//SalesForceCRM//config//config.properties");
			config.load(fis);
			
			//OR property file
			or=new Properties();
			fis=new FileInputStream(System.getProperty("user.dir")+"//src//main//java//com//project//SalesForceCRM//config//OR.properties");
			or.load(fis);
			
			//log4j property file
			logs=new Properties();
			fis=new FileInputStream(System.getProperty("user.dir")+"//src//main//java//com////project//SalesForceCRM//config//log4j.properties");
			PropertyConfigurator.configure(fis);
						
			
			launchBrowser(browserKey);
			
			navigate(urlKey);
		}	
	}
	
	
	public static void launchBrowser(String browserKey)
	{
		//Initialize the WebDriver & EventFiringWebDriver
		if(config.getProperty(browserKey).equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "D:\\Browser_Drivers\\chromedriver.exe");
			driver=new ChromeDriver();
		}else if(config.getProperty(browserKey).equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "D:\\Browser_Drivers\\geckodriver.exe");
			driver=new FirefoxDriver();
		}
		//eventDriver=new EventFiringWebDriver(driver);
		driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	
	public static void navigate(String url)
	{
		driver.get(config.getProperty(url));
	}
	
	
	public static WebElement getElement(String locatorKey) 
	{
		WebElement element=null;
		try {
			if(locatorKey.endsWith("_id")) {
				element=driver.findElement(By.id(or.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("_name")) {
				element=driver.findElement(By.name(or.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("_classname")) {
				element=driver.findElement(By.className(or.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("_link")) {
				element=driver.findElement(By.linkText(or.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("_xpath")) {
				element=driver.findElement(By.xpath(or.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("_css")) {
				element=driver.findElement(By.cssSelector(or.getProperty(locatorKey)));
			}
			return element;
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
	
	
	public static List<WebElement> getElements(String locatorKey) 
	{
		List<WebElement> element=null;
		try {
			if(locatorKey.endsWith("_id")) {
				element=driver.findElements(By.id(or.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("_name")) {
				element=driver.findElements(By.name(or.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("_classname")) {
				element=driver.findElements(By.className(or.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("_link")) {
				element=driver.findElements(By.linkText(or.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("_xpath")) {
				element=driver.findElements(By.xpath(or.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("_css")) {
				element=driver.findElements(By.cssSelector(or.getProperty(locatorKey)));
			}
			return element;
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
	
	
	public static void type(String locatorKey, String valueKey)
	{
		getElement(locatorKey).sendKeys(valueKey);
	}
	
	
	public static void click(String locatorKey)
	{
		getElement(locatorKey).click();
	}

	
	public static void selectOption(String locatorKey,int item)
	{
		Select s=new Select(getElement(locatorKey));
		s.selectByIndex(item);
	}
	
	
	public static void verifyElement(String locatorKey, String expectedValue)
	{
		try 
		{
			String actualValue = getElement(locatorKey).getText();
			System.out.println(actualValue);
			if(actualValue.equals(expectedValue))
			{
				isLoggedIn=true;
			}
			else 
			{
				isLoggedIn=false;
			}
		} 
		catch (Exception e) 
		{
			isLoggedIn=false;
		}
	}
	
		
	
	public static Iterator<String> getAllWindows() 
	{
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> itr = windows.iterator();
		return itr;
	}


	//Switch to frame	
	public static void switchToFrame(String frameName)
	{
		driver.switchTo().frame(frameName);		
	}

	
	public static void switchToDefaultContent()
	{
		driver.switchTo().defaultContent();
	}
	
	
	public static void mouseOver(String locatorKey)
	{
		//Do mouse-hovers
		Actions actObj=new Actions(driver);
		actObj.moveToElement(getElement(locatorKey)).build().perform();
	}
	
	
	public static boolean waitForElement(String waitFor,String locatorKey,int timeInSeconds)
	{
		try {
			if(waitFor.equalsIgnoreCase("clickable")){
				wait=new WebDriverWait(driver, timeInSeconds);
				wait.until(ExpectedConditions.elementToBeClickable(getElement(locatorKey)));
			}else if(waitFor.equalsIgnoreCase("selectable")){
				wait=new WebDriverWait(driver, timeInSeconds);
				wait.until(ExpectedConditions.elementToBeSelected(getElement(locatorKey)));
			}else if(waitFor.equalsIgnoreCase("visible")){
				wait=new WebDriverWait(driver, timeInSeconds);
				wait.until(ExpectedConditions.visibilityOf(getElement(locatorKey)));
			}
		} 
		catch (Exception e) 
		{
			return false;
		}
		return true;	
	}
	
	
	
	//********************* Validation Function *****************
	
		public boolean verifyTitle()
		{
			return false;
		}
		
		
		/*public static boolean isElementPresent(String locatorKey)
		{
			int s = getElements(locatorKey).size();
			if(s==0)
				return false;
			else
				return true;
		}*/
		
		
		public boolean isElementPresent(String locatorKey) throws Exception
		{
			List<WebElement> elementList=null;
			
			if(locatorKey.endsWith("_id"))	
				elementList=driver.findElements(By.id(or.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_name"))
				elementList=driver.findElements(By.name(or.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_xpath"))
				elementList=driver.findElements(By.xpath(or.getProperty(locatorKey)));
			else
			{
				reportFailure("Locator not correct -- " + locatorKey);
				Assert.fail("Locator not correct -- " + locatorKey);
			}
			
			if(elementList.size()==0)
				return false;
			else
				return true;
		}
		
		
		public boolean verifyText(String locatorKey,String expectedTextKey) throws Exception
		{
			String actualText=getElement(locatorKey).getText().trim();
			String expectedText=or.getProperty(expectedTextKey);
			if(actualText.equals(expectedText))
				return true;
			else
				return false;
		}
		
		
		
		//********************* Reporting Functions *****************
		
		public void reportPass(String msg)
		{
			test.log(LogStatus.PASS, msg);
		}
		
		
		
		public void reportFailure(String msg) throws Exception
		{
			test.log(LogStatus.FAIL, msg);
			takeScreenShot();
			Assert.fail(msg);
		}
		
		
		
		public void takeScreenShot() throws Exception
		{
			//Date dt=new Date();
			//String screenshotFileName = dt.toString().replace(":", "_").replace(" ", "_")+".png";
			//FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+screenshotFileName));
			
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileHandler.copy(scrFile, new File(System.getProperty("user.dir")+"//failure//"+screenshotFileName));
			
			//put screen shot file in extent reports
			test.log(LogStatus.INFO, "Screenshot --> "+ test.addScreenCapture(System.getProperty("user.dir"))+"//failure//"+screenshotFileName);
		}
		
}
