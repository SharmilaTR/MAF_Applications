package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

/*
 * 
 */
public class JurisdictionPage extends HomePage
{

	public JurisdictionPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

		
	/*
	 * Enter given title on the tile field on the Jurisdiction search page
	 */
	public void enterTitle(String title)
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".title")).sendKeys(title);	
		}	
		catch(Exception exc)
		{
			extentLogger.log(LogStatus.INFO, "Error in : enterTitleToSearch <br>"+displayErrorMessage(exc));
		}			
	}
}
