package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class JurisprudencePage extends SearchPage{

	public JurisprudencePage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Enters the value given on the number field
	 */
	public void enterNumberValue(String numberVal) 
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".numero")).sendKeys(numberVal);
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterNumberValue <br>"+displayErrorMessage(exc));

			}
		
	}

	/*
	 * Checks to see if the expected search string is displayed on the document
	 */
	public boolean isExpectedDocDisplayed(String searchString) 
	{
		try
		{
			if(WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".firstdocfromresultset"))))
			{
       			return	elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".firstdocfromresultset")).getText().contains(searchString);
			}
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isExpectedDocDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
		return false;
	}

	/*
	 * Enter the search string for the Relator field on the page
	 */
	public void enterRelatorValue(String relator)
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".relator")).sendKeys(relator);	
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterRelatorValue <br>"+displayErrorMessage(exc));
		}
		
	}

	/*
	 * Enter value of the index search on the page
	 */
	public void enterIndexSearch(String indexVal)
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".indexsearch")).sendKeys(indexVal);
		
		}catch(Exception exc){
		extentLogger.log(LogStatus.INFO, "Error in : enterIndexSearch <br>"+displayErrorMessage(exc));
		}
		
	}


	public boolean isErrorBlockDisplayedForNoResults() {
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".errorblock")).isDisplayed();
		
		}catch(Exception exc){
		extentLogger.log(LogStatus.INFO, "Error in : isErrorBlockDisplayedForNoResults <br>"+displayErrorMessage(exc));
		return false;
		}
	}
	
	

	public void selectItemFromAutoSuggest() 
	{
		// TODO Auto-generated method stub
		
	}

	public boolean isThematicDropdownDisplayedWithResults() {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdownresults")),
					30);
			return WebDriverFactory.isDisplayed(driver,
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdown")))
					&& WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdownresults")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isThematicDropdownDisplayedWithResults <br>"+displayErrorMessage(ex));
			return false;
		}
	}

}
