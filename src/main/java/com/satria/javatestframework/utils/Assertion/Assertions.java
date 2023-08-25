package com.satria.javatestframework.utils.Assertion;

import com.satria.javatestframework.utils.Logger.Log;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import static com.satria.javatestframework.FE.WebBaseMethod.screenshotElement;
import static com.satria.javatestframework.utils.Utils.GateMath.roundDown4DP;
import static com.satria.javatestframework.utils.Utils.GateMath.roundHalfUp4DP;


/**
 * All the validation methods and method to take screenshot 
 * are defined in this class.
 */
public class Assertions {
	public static boolean testCaseStatus = true;
	private static WebDriver driver;
	private File file;
	private String testScreenshotDir;
	private static boolean testStatus;

	public Assertions(WebDriver driver) {
		file = new File("");
		testScreenshotDir = file.getAbsoluteFile()
				+ "//src//test//java//outputFiles//";
	}

	/**
	 * method to take screenshot
	 * @return path where screenshot has been saved
	 */
	public String screenShot() {
		String screenshotPath = "screenshot" + new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss")
		.format(new GregorianCalendar().getTime())
		+ ".png";

		System.out.println(screenshotPath);
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File( testScreenshotDir + screenshotPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			screenshotPath = "";
		}
		return screenshotPath;
	}
	
	/**
	 * overloaded method to take screenshot with desired screenshot name passed
	 * @param message string passed to save as name of a screenshot
	 */
	public void screenShot(String message) {
		String screenshotPath = message+ "screenshot" + new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss")
		.format(new GregorianCalendar().getTime())+
		".png";

		System.out.println(screenshotPath);
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File( testScreenshotDir + screenshotPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			screenshotPath = "";
		}
	}

	
	/**
	 * method to verify the actual value with expected value
	 * @param actual actual text displayed
	 * @param expected expected text to be displayed
	 * @param message message should be displayed on failure of assertion
	 */
	public static boolean verifyEquals(Object actual,
								Object expected,
								String message,
								boolean screenshotOnFailure,
								boolean exitOnFailure,
								boolean screenshootOnPassed,
								WebDriver driver,
								WebElement element) {
		testStatus=true;
		try {
			Assert.assertEquals(actual, expected, message);
			Log.info("PASS - " + message);
			if (screenshootOnPassed) {
				screenshotElement(element);
			}

		} catch (AssertionError e) {
			testStatus = false;
			Log.info( "FAIL - " + message + " Actual: "+ actual.toString() + " Expected: " + expected.toString());
			if (screenshotOnFailure) {
				screenshotElement(element);
			}
			if (exitOnFailure) {
				Log.info("Exiting this function as exitOnFail flag is set to True. Will move to next test.");
				throw e;
			}
		}
		return testStatus;
	}

	
	/**
	 * method to verify if the condition is true
	 * @param condition statement to verify
	 * @param message message should be displayed on failure of assertion
	 * @param screenshotOnFailure true if screenshot has to be taken in case of failure
	 * @param exitOnFailure true if execution to be stopped in case of failure 
	 * @return true if assertion passes, false if fails
	 */
	public static boolean verifyTrue(boolean condition,
							  String message,
							  boolean screenshotOnFailure,
							  boolean screenshootOnPassed,
							  boolean exitOnFailure,
							  WebDriver driver,
							  WebElement element) {

		try {
			Assert.assertTrue(condition, message);
			Log.info("PASS - " + message);
			if (screenshootOnPassed) {
				screenshotElement(element);
			}
		} catch (AssertionError e) {
			testCaseStatus = false;
			Log.info( "FAIL - " + message + " Actual: FALSE Expected: TRUE.");
			if (screenshotOnFailure) {
				screenshotElement(element);
			} else {
				Log.info("FAIL - " + message);
			}
			if (exitOnFailure) {
				Log.info("Exiting this function as exitOnFail flag is set to True.");
				throw e;
			}
		}
		return testCaseStatus;
	}


	public static void assertUrlEquals(String urlExpect) throws InterruptedException {
		Thread.sleep(2000);
			Assert.assertEquals(driver.getCurrentUrl(),urlExpect);
			Log.info("================ Assert URL ================");
			Log.info("Actual URL: "+driver.getCurrentUrl());
			Log.info("Expected URL: "+urlExpect);
			Log.info("Result: PASS");
			Log.info("============================================");
	}

	public static void assertNumberDown(String numExpect, String numActual) throws InterruptedException {
		Assert.assertEquals(numActual,roundDown4DP(numExpect));
		Log.info("================ Assert number ================");
		Log.info("Actual number: "+numActual);
		Log.info("Expected number: "+roundDown4DP(numExpect));
		Log.info("Result: PASS");
		Log.info("===============================================");
	}

	public static void assertNumberHalfUp(String numExpect, String numActual) throws InterruptedException {
		Assert.assertEquals(numActual,roundHalfUp4DP(numExpect));
		Log.info("================ Assert number ================");
		Log.info("Actual number: "+numActual);
		Log.info("Expected number: "+roundHalfUp4DP(numExpect));
		Log.info("Result: PASS");
		Log.info("===============================================");
	}

	public static void assertEquals(Object Actual, Object Expected){
		Assert.assertEquals(Actual,Expected);
		Log.info("================ Assert equals ================");
		Log.info("Actual : "+Actual);
		Log.info("Expected : "+Expected);
		Log.info("Result: PASS");
		Log.info("===============================================");
	}

}


