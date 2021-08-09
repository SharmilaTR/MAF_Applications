package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class DeliveryPage extends BasePage {

	public DeliveryPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
	}
	
	/*
	 * This method clicks on the radio option to select Word as format for delivery of the document
	 */
	public void clickOnRadioOptionForWord()
	{
		try
		{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".RTFlabelradiobutton")), 20);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".RTFlabelradiobutton")).click();
				
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickOnRadioOptionForWord <br>" + displayErrorMessage(ex));
		}
		
		
	}
	

	/*
	 * This method clicks on the radio option to select PDF as format for delivery of the document
	 */
	public void clickOnRadioOptionForPDF()
	{
		try
		{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".PDFlabelradiobutton")), 20);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".PDFlabelradiobutton")).click();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickOnRadioOptionForPDF <br>" + displayErrorMessage(ex));
		}	
		
	}
	
	/*
	 * This method clicks on OK option on the document download option.
	 */
	public void clickOnSubmit()
	{
		try
		{
			String locator = PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".clickokondocdownload");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickOnSubmit <br>" + displayErrorMessage(ex));
		}	
		
	}
	
	/*
	 * This method checks to see if the document delivery confirmation message and icon are displayed as expected
	 * this method can be used for any delivery confirmation validation like download / save / email delivery options
	 */
	public boolean isConfirmationMsgDisplayedForDelivery(){
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".returntodoc")), 20);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".DocumentoSubmitbuttonSuccessicon")), 20);
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".DocumentoSubmitbuttonSuccessicon")).isDisplayed() &&
		   elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".returntodoc")).isDisplayed();

		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isConfirmationMsgDisplayedForDelivery <br>"+displayErrorMessage(exc));
			return false;
		}
		
	}
	
	/*
	 * This method clicks on the return to document / voltar option displayed on the page upon document download process is completed
	 */
	public void returnToDocumentUponCompletionOfDelivery()
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".returntodoc")).click();
		}catch(Exception exc)
		{
			extentLogger.log(LogStatus.INFO, "Error in : returnToDocumentUponCompletionOfDelivery <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method checks to see if the confirmation message is displayed for the delivery using Print option
	 */	
	public boolean isConfirmationDisplayedForDeliveryUsingPrint(){
		try{
			boolean success = false;
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".returntodoc")), 20);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".DocumentoSubmitbuttonSuccessicon")), 40);
			success = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".DocumentoSubmitbuttonSuccessicon")).isDisplayed();
			success = success && elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".returntodoc")).isDisplayed();
			String deliveryStatus = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".deliveryStatus"));
			success = success && 
					(deliveryStatus!=null) &&
					deliveryStatus.contains(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".printDocumentStatusText"));
			
			return success;
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isConfirmationDisplayedForDeliveryUsingPrint <br>"+displayErrorMessage(exc));
			return false;
		}
		
	}
	
	/*
	 * This method enters the given email information on the delivery email related fields
	 */
	public void enterEmailDetails(String EmailTo)
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sendemailto")).clear();
			elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sendemailto"),EmailTo);
			elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sendemailtowithmessage"),"Email sent to:"+EmailTo);	
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterEmailDetails <br>"+displayErrorMessage(exc));

		}
		
	}
	
	/*
	 * This method is used to click on the print option
	 */
	public void clickOnPrint()
	{
		try
		{	
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".clickprint")), 20);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".clickprint")).click();
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickOnPrint <br>"+displayErrorMessage(exc));
	
		}
		
	}
	
	/*
	 * This method selects the radio option to delivery the complete document
	 */
	public void clickToSelectFullDocumentForDelivery()
	{
		try
		{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".completedocradiobutton")), 20);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".completedocradiobutton")).click();
				
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickToSelectFullDocumentForDelivery <br>"+displayErrorMessage(exc));
	
		}
		
	}
	
	
	/*
	 * This method selects the radio option to delivery the Result list
	 */
	public void clickToSelectResultListForDelivery()
	{
		try
		{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".resultlistradiobutton")), 20);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".resultlistradiobutton")).click();
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickToSelectResultListForDelivery <br>"+displayErrorMessage(exc));
	
		}
	}
	
	/*
	 * This method cancels on the email delivery option while the process is in progress
	 */
	public void clickCancelOnDeliveryUsingEmail()
	{
		try
		{
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".cancelemailbutton");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickCancelOnDeliveryUsingEmail <br>"+displayErrorMessage(exc));
	
		}
		
	}
}
