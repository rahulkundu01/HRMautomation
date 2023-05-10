package com.example.test;

import org.testng.annotations.Test;
import com.example.util.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;

public class NavigateHome implements utility {
	/*public WebDriver driver;
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest logger;
  */
	public ExtentTest logger;
  @BeforeTest
  public void beforeTest() {
	  
	  System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\java\\drivers\\chromedriver1.exe");
	  	//driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		
	  // Create an object of Extent Reports
			///extent = new ExtentReports();
	 
			//spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/RKExtentReport.html");
			extent.attachReporter(spark);
			extent.setSystemInfo("Host Name", "Orange HRM");
	         extent.setSystemInfo("Environment", "Production");
	         extent.setSystemInfo("User Name", "Rahulkundu");
	         spark.config().setDocumentTitle("Title of the Report Comes here ");
	                // Name of the report
	         spark.config().setReportName("Name of the Report Comes here ");
	                // Dark Theme
	         spark.config().setTheme(Theme.STANDARD);
	}
	//This method is to capture the screenshot and return the path of the screenshot.
			public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException {
				String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				TakesScreenshot ts = (TakesScreenshot) driver;
				File source = ts.getScreenshotAs(OutputType.FILE);
				// after execution, you could see a folder "FailedTestsScreenshots" under src folder
				String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
				File finalDestination = new File(destination);
				FileUtils.copyFile(source, finalDestination);
				return destination;
				
				

  }
			
@Test
 public void navigate() throws InterruptedException {
	logger = extent.createTest("To verify user navigates to HRM home page");
	driver.get("https://opensource-demo.orangehrmlive.com");
	Thread.sleep(2000);
}

@Test
public void verify_Title() {
	logger = extent.createTest("To verify Orange HRM Title");
	Assert.assertEquals(driver.getTitle(),"OrangeHRM");
	
		logger.createNode("Title matches the expetaction.");
	
	//driver.getCurrentUrl(),https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
	
}

@Test
public void verify_URL() {
	logger = extent.createTest("To verify Orange HRM login URL");
	Assert.assertEquals(driver.getCurrentUrl(),"https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	
		logger.createNode("URL matches the expetaction.");	
	
}




  @AfterTest
  public void getResult(ITestResult result) throws Exception{
			if(result.getStatus() == ITestResult.FAILURE){
	//MarkupHelper is used to display the output in different colors
				logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
				logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
	//To capture screenshot path and store the path of the screenshot in the string "screenshotPath"
	//We do pass the path captured by this method in to the extent reports using "logger.addScreenCapture" method.
	//String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
				String screenshotPath = getScreenShot(driver, result.getName());
	//To add it in the extent report
				logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath));
			}
			else if(result.getStatus() == ITestResult.SKIP){
				logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
			}
			else if(result.getStatus() == ITestResult.SUCCESS)
			{
				logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
			}
			extent.flush();
  }

  @AfterTest
  public void endReport() {
  		driver.quit();
  		extent.flush();
  	}
  }
