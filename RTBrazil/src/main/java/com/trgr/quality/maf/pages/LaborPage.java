package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class LaborPage extends HomePage{

	public LaborPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public SearchResultsPage clickOnSearch() throws Exception 
	{
		try
		{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".searchbtn"));
			return new SearchResultsPage(driver);	
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSearch <br>"+displayErrorMessage(exc));
			return null;
			}
		
	}
	
	/*
	 * Clicking on search. this method does not return the search results as the next step for this method call expects the no 
	 * search results message
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

	/*
	 * Entering index search test data
	 */
	public void enterIndexSearch(String testData)
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".indexsearch")).sendKeys(testData);
		
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterIndexSearch <br>"+displayErrorMessage(exc));
		}
		
	}

	
	

}
