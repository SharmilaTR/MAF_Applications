package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class AdministrativePage extends HomePage{

	public AdministrativePage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * Enter the given index search data on the index search filed
	 */
	public void enterIndexSearch(String searchData)
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".indexsearch")).sendKeys(searchData);
		
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterIndexSearch <br>"+displayErrorMessage(exc));
		}
		
	}

}
