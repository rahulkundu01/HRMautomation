package com.example.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public interface utility {
	
	public static String url = "https://opensource-demo.orangehrmlive.com";
	
	public static WebDriver driver = new ChromeDriver();
	public ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/RKExtentReport_nav.html");
	public ExtentSparkReporter spark2 = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/RKExtentReport_login.html");
	public ExtentSparkReporter spark3 = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/RKExtentReport_fwd.html");
	public ExtentReports extent = new ExtentReports();
	
	
	//Keys for Login Page
	public static String username = "username";
	public static String password = "password";
	public static String xSumit = "//button[contains(.,'Login')]";
	
	//Values for Login Page
	public static String uname = "Admin";
	public static String pwd = "admin123";
	
	//Dashboard url
	public static String dash_url = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
	
	//Forget password link key
	public static String fpwd = "//p[contains(.,'Forgot your password?')]";
	
	//Reset Password keys and Values
	public static String resetpwduname = "username";
	public static String runame = "Admin"; //values
	
}
