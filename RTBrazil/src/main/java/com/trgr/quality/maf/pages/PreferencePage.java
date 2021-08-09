package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.handlers.BaseHandler;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class PreferencePage extends BasePage {

	BaseHandler Basehandler;
	HomePage homepage;
	public PreferencePage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		
	}
	
	public void writeEmailInPreferencePage(String email)
	{
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".emailnotification")).clear();
			elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".emailnotification"), email);			 
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : writeEmailInPreferencePage <br>"+displayErrorMessage(ex));
		}
	}
	
	/*
	 * Clicks on the save preferences option on the preference page.
	 * Returns Home Page once the save is successful
	 */
	public HomePage savePreference() throws Exception
	{
		try{			  	    
		    WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".savepreferences")), 20);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".savepreferences"));
			Thread.sleep(1000);
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : savePreference <br>"+displayErrorMessage(ex));
		}
		return new HomePage(driver);
	}
	
	/*
	 * This method returns the Email value saved on the preference page
	 */
	public String getEmail()
	{
	  try{
		  return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".emailnotification")).getAttribute("value");
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : getEmail <br>"+displayErrorMessage(ex));
			return "error message"+ex.getMessage();
		}
	}
	
	
	/*
	 * This method checks if the PDF is the default else set the default format as PDF
	 */
	public boolean checkAndSetPDFAsDefaultFormat() 
	{
		try{						
		if(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".deliveryoptionpdf")).isSelected())
		{		
			return true;
		}else
			{ 			
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".deliveryoptionpdf")).click();
				savePreference();
				Thread.sleep(2000);
				return true;
			}
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : validateDeliveryOptionsPdf <br>"+displayErrorMessage(ex));
			return false;
		}
	 }

	/*
	 * Used for clicking on the return to home page option
	 * Returns Home Page handle upon successful return to home page
	 */
	public HomePage goToHomePage() throws IllegalArgumentException, IOException
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".returnhomepage"));		
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : goToHomePage <br>"+displayErrorMessage(ex));
		}
		return new HomePage(driver);
	}
	
	/*
	 * This method checks to see if radio button is selected for 10 documents per page.
	 * If the option is not set, then set the option and return the success / failure.
	 */
	public boolean isRadioButton10Enabled() {
		try{
		
		Thread.sleep(2000);			
		WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".radiobutton10")), 40);
		if(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".radiobutton10")).isSelected())
		{
				return true;
		}else{
				WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".radiobutton10")), 40);
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".radiobutton10")).click();				
				savePreference();
				return true;
			 }
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isRadioButton10Enabled <br>"+displayErrorMessage(ex));
			return false;
		}
	}
	
	/*
	 * Clear the email id field
	 */
	public void ClearEmailOnPreferencePage()
	{
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".emailnotification")).clear();
					 
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : ClearEmailInPreferencePage <br>"+displayErrorMessage(ex));
		}
	}
	
	/*
	 * This method checks to see if the error message is displayed on the page.
	 */
	public boolean isErrorMessageDisplayed()
	{ 
	try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".errorblock_emailaddress")).isDisplayed();
			
							 
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : ClearEmailInPreferencePage <br>"+displayErrorMessage(ex));
			return false;
		}
		
	}

	/*
	 * This method checks to see if the Preference Page is displayed as expected.
	 * Return true / false based on the element display
	 */
	public boolean isPreferencePageDisplayed()
	{
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".emailnotification")).isDisplayed() &&
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".radiobutton10")).isDisplayed() &&
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".savepreferences")).isDisplayed();
	
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isPreferencePageDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}
}