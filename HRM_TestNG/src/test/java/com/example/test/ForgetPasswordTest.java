package com.example.test;

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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.example.util.utility;

public class ForgetPasswordTest implements utility{
	public ExtentTest logger;
	 
	  @BeforeTest
	  public void beforeTest() throws InterruptedException {
		  System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\java\\drivers\\chromedriver1.exe");
		  	//driver = new ChromeDriver();
			driver.manage().window().maximize();
			
			
				extent.attachReporter(spark3);
				extent.setSystemInfo("Host Name", "Orange HRM");
				extent.setSystemInfo("Environment", "Production");
				extent.setSystemInfo("User Name", "Rahulkundu");
				spark.config().setDocumentTitle("Verify Cases for Forget Password");
	                // Name of the report
				spark.config().setReportName("HRM Forget Password ");
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
			
 @Test (priority = 0)
 public void navigate() throws InterruptedException {
				logger = extent.createTest("To verify user navigates to HRM home page");
				driver.get(url);
				Thread.sleep(2000);
				logger.log(Status.PASS, "Successfully navigated to Home page");
}
	 
@Test (priority = 1)
public void validate_FP01() throws InterruptedException {
				logger = extent.createTest("To verify forget password link is present or not");
				if(driver.findElement(By.xpath(fpwd)).isDisplayed()) {
					
					logger.log(Status.PASS, "Forget Password Link is present");
				}
				else {
					logger.log(Status.FAIL, "Forget Password Link is not Present");
				}
				
}
@Test (priority = 2)
public void validate_FP02() throws InterruptedException {
			logger = extent.createTest("To verify thst forget password link is enabled or not");
			if(driver.findElement(By.xpath(fpwd)).isEnabled()) {
				
				logger.log(Status.PASS, "Forget Password Link is enabled");
			}
			else {
				logger.log(Status.FAIL, "Forget Password Link is not enabled");
			}
			
}

@Test (priority = 3)
public void validate_FP03() throws InterruptedException {
	logger = extent.createTest("To verify that on clicking forget password link it redirected to Forget password page");
	driver.findElement(By.xpath(fpwd)).click();
	Thread.sleep(2000);
	
	String FPPageHeading = driver.findElement(By.xpath("//h6[contains(.,'Reset Password')]")).getText();
    
    //Verify new page - HomePage
    Assert.assertEquals(FPPageHeading,"Reset Password");
    logger.log(Status.PASS, "User navigated to Reset Password page");
		
}
@Test (priority = 4)
public void validate_ResetPwd() {
	logger = extent.createTest("To verify reset password");
	if(driver.findElement(By.name(resetpwduname)).isEnabled()) {
		logger.log(Status.PASS, "Username Text field is enabled");
	}
	else {
		logger.log(Status.FAIL, "Username Text field is not enabled");
	}
	driver.findElement(By.name(resetpwduname)).sendKeys(runame);
	logger.createNode("Username is entered in text field");
	driver.findElement(By.xpath("//button[contains(.,'Reset Password')]")).click();
	logger.createNode("Reset password button is clicked");
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
