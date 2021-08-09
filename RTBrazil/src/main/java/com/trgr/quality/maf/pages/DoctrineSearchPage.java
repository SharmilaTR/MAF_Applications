package com.trgr.quality.maf.pages;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;


public class DoctrineSearchPage extends SearchPage {

	public DoctrineSearchPage(WebDriver driver) throws Exception 
	{
		super(driver);
		//WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".searchbtn")), 20);
	}

	/*
	 * Enter given author string on the search page
	 */
	public void enterAuthor(String author) 
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".author")).sendKeys(author);	
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterAuthor <br>"+displayErrorMessage(exc));

			}	
	}
	
	

	/*
	 * Enter given title on the search page
	 */
	public void enterTitle(String title) 
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".title")).sendKeys(title);
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterTitle <br>"+displayErrorMessage(exc));

			}	
		
	}

}
