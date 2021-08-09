package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class NewsSearchPage extends SearchPage {

	public NewsSearchPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		
	}

	/*
	 * This method checks to see if the News Page is displayed as expected
	 */
	public boolean isNewsPageDisplayedAsExpected() {
		boolean flag = false;
		try {
			WebElement locator = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newstext"));
			flag = WebDriverFactory.isDisplayed(driver, locator);
			return flag;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateNewsPage <br>" + displayErrorMessage(exc));
			return false;
		}

	}
	
	/*
	 * This method selects the date from the date widget given on the News Page	
	 */
	public void selectDate()
	{
		try
		{
			String selector=PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".newsdate");
			elementhandler.selectByValue(selector, "year");	
		}
	catch(Exception exc){
		
		extentLogger.log(LogStatus.INFO, "Error in : selectDate <br>"+displayErrorMessage(exc));
		}
		
	}
	
	/*
	 * Enter the year value on the field on News Page
	 */
	public void enterYear(String year)
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".newsyear")).sendKeys(year);
		
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterYear <br>"+displayErrorMessage(exc));
		}
	}
}
