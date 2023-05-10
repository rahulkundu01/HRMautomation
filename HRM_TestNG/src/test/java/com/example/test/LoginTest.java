package com.example.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.example.util.*;

public class LoginTest implements utility{
	public ExtentTest logger;
 
  @BeforeTest
  public void beforeTest() throws InterruptedException {
	  System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\java\\drivers\\chromedriver1.exe");
	  	//driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		
			extent.attachReporter(spark2);
			extent.setSystemInfo("Host Name", "Orange HRM");
			extent.setSystemInfo("Environment", "Production");
			extent.setSystemInfo("User Name", "Rahulkundu");
			spark.config().setDocumentTitle("Verify Cases for Login Page ");
                // Name of the report
			spark.config().setReportName("HRM Login ");
                // Dark Theme
			spark.config().setTheme(Theme.DARK);
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
		
 @Test (priority = 0)
 public void navigate() throws InterruptedException {
			logger = extent.createTest("To verify user navigates to HRM home page");
			driver.get(url);
			Thread.sleep(2000);
			logger.log(Status.PASS, "Successfully navigated to Home page");
 }
  
  
  @Test (priority = 1)
  public void validate_Login() {
	  		logger = extent.createTest("To verify user enter valid username and password");
	  		driver.findElement(By.name(username)).sendKeys(uname);
	  		logger.createNode("Verify valid username is entered");
	  		logger.log(Status.PASS, "Valid Username");
	  		driver.findElement(By.name(password)).sendKeys(pwd);
	  		logger.createNode("Verify valid password is entered");
	  		logger.log(Status.PASS, "Valid Password");
	  		driver.findElement(By.xpath(xSumit)).click();
	  		logger.createNode("Submit button is clicked");
	  		logger.log(Status.PASS, "Submit button clicked");
  }
  
  
  @Test (priority = 2)
  public void validate_Dashboard_url() throws InterruptedException {
	  logger = extent.createTest("To verify user successfully navigated to correct dashboard");
	  String dash_cur = driver.getCurrentUrl();
	  Thread.sleep(2000);
	  System.out.println(dash_cur);
	  if (dash_cur == dash_url) {
		  logger.log(Status.PASS, "User navigated to correct dashboard link");
	  }
	  else {
		  logger.log(Status.FAIL, "User didn't navigated to correct dashboard link");
	  }
	  
	 
  }
  @Test (priority = 3)
  public void validate_Dashboard() {
	  logger = extent.createTest("To verify user successfully logged in and navigated to dashboard");
	  String homePageHeading = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/header/div[1]/div[1]/span/h6")).getText();
      
      //Verify new page - HomePage
      Assert.assertEquals(homePageHeading,"Dashboard");
      logger.log(Status.PASS, "User navigated to correct dashboard");
      
  }
  
  

  @AfterTest
  public void getResult(ITestResult result2) throws Exception{
			if(result2.getStatus() == ITestResult.FAILURE){
				//MarkupHelper is used to display the output in different colors
				logger.log(Status.FAIL, MarkupHelper.createLabel(result2.getName() + " - Test Case Failed", ExtentColor.RED));
				logger.log(Status.FAIL, MarkupHelper.createLabel(result2.getThrowable() + " - Test Case Failed", ExtentColor.RED));
				//To capture screenshot path and store the path of the screenshot in the string "screenshotPath"
				//We do pass the path captured by this method in to the extent reports using "logger.addScreenCapture" method.
				//String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
				String screenshotPath = getScreenShot(driver, result2.getName());
				//To add it in the extent report
				logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath));
			}
			else if(result2.getStatus() == ITestResult.SKIP){
				logger.log(Status.SKIP, MarkupHelper.createLabel(result2.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
			}
			else if(result2.getStatus() == ITestResult.SUCCESS)
			{
				logger.log(Status.PASS, MarkupHelper.createLabel(result2.getName()+" Test Case PASSED", ExtentColor.GREEN));
			}
			
			
  }

  @AfterTest
  public void endReport() {
	  	extent.flush();
	  	driver.quit();
  		//extent.flush();
  		logger.createNode("Test Complete");
  	}
}
