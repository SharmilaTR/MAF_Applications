package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.handlers.BaseHandler;
import com.trgr.quality.maf.handlers.ElementHandler;


public class BasePage extends BaseTest
{
	protected static WebDriver driver;
	protected static ElementHandler elementhandler;
	protected static BaseHandler baseHandler;
		
	public BasePage(WebDriver driver)throws IOException, IllegalArgumentException
	{
		super();
		BasePage.driver = driver;
		elementhandler = new ElementHandler(driver);	
		baseHandler = new BaseHandler(driver);
	}
	
	public boolean IsPopUpWindowPresent()
	{
		 try {
		        driver.switchTo().alert();
		        return true;
		    } // try
	    catch (Exception e) {
	        return false;
	    } // catch
		
	}
	
	public void clickOnAlertPopUP() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			// exception handling
		}
	}
	
	/*
	 * Click on the search button and return search results page upon successful search
	 */
	public SearchResultsPage clickOnSearch() throws Exception 
	{
		try
		{
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtn");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			//elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".searchbtn"));
			return new SearchResultsPage(driver);	
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSearch <br>"+displayErrorMessage(exc));
			return null;
			}
		
	}
	
	/*
	 * Click on search button but this method is called for test data/ use case that does not return any result set
	 * Hence this method should have return type as void
	 */
	public void clickOnSearchForNoResult() throws Exception
	{
		try
		{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".searchbtn"));
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSearchForNoResult <br>"+displayErrorMessage(exc));
		}
		
	}


	
	
}
