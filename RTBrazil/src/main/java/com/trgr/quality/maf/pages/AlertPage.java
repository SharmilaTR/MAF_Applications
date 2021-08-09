
package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class AlertPage extends BasePage
{

	public AlertPage(WebDriver driver) throws IOException,	IllegalArgumentException 
	{
		super(driver);
		//WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".myalertslink")), 20);
	}
	
	
	/*
	 * Creating alert with given alertname and email address
	 */
	public void createAlert(String AlertName, String email) 
	{
		try{
			clickCreateNewAlert();
			Thread.sleep(300);
			//selectPredesignAlert();
			isPredesignAlertByNameSelected("Administrativo");
			clickNextButtonInPredesignAlert();
			Thread.sleep(500);
			enterAlertName(AlertName);
			enterEmailId(email);
			clickSaveOnCreateAlertPage();
			Thread.sleep(2000);
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : CreateAlert <br>"+displayErrorMessage(exc));
		}
		
	}
	
	/*
	 * Modifying the given alert by searching on the alert page to filter for the given alert
	 * and then clicking on edit to modify
	 */
	public boolean ModifyAlert(String alertName) throws InterruptedException
	{
		try{	
				Thread.sleep(1000);
				WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".alerttablefilter")), 20);
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alerttablefilter")).clear();
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alerttablefilter")).sendKeys(alertName);
				elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".modifyalertlink"));
				Select select=new Select(driver.findElement(By.id("freq")));
				select.selectByIndex(2);
				elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alertsavebutton"));
				return true;
		} catch(Exception exc)
		{
			extentLogger.log(LogStatus.INFO, "Error in : ModifyAlert <br>"+displayErrorMessage(exc));
			return false;
		}
		
		
	}
	
	/*
	 * This method clicks on the delete alert link once the selected alert is displayed
	 */
	public void deleteAnAlert() throws InterruptedException
	{

		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".removealertlink")), 20);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".removealertlink"));
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".removealertyesbutton"));
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alerttableinfoforzeroresults")).isDisplayed();
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : DeleteAlert <br>"+displayErrorMessage(exc));
		}

	}
	
	/*
	 * This method is called once the user is on the selected alert
	 * This method takes the date the alert has to be suspended till
	 */
	public void suspendAlertToGivenDate(String suspendtodate)
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".suspendalertlink")), 20);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".suspendalertlink"));
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".suspendradiobutton")).isSelected();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".suspendtodate")).sendKeys(suspendtodate);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".modifiedalertsavelink"));

		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : Suspend Alert <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method clicks on the reactivate selected alert link
	 */
	public void reactivateAlert() throws InterruptedException
	{
		try{
			
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".reactivatealertlink")), 30);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".reactivatealertlink")).isDisplayed();
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".reactivatealertlink"));
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".activatealertradiobutton")).isSelected();
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".modifiedalertsavelink"));
			Thread.sleep(2000);
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : ReactivateAlert <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * Get a unique random number based on the date time of the day
	 */
	public String getUniqueAlertName(String name)
	{
		  Date dNow = new Date();
	      SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmss");
	      String datetime = ft.format(dNow);
	      return name+datetime;
	 }
	
		
	/*
	 * This method clicks on Sort image - sort by date asc
	 */
	public void sortAlertByDateAsc()
	{
		try{
			sortAlertByDate();
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".sort_modifydate_asc")), 30);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".sort_modifydate_asc"));
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : sortAlertByDateAsc <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method clicks on Sort image - sort by date desc
	 */
	public void sortAlertByDateDesc()
	{
		try{
			sortAlertByDate();
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".sort_modifydate_desc")), 30);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".sort_modifydate_desc"));
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : sortAlertByDateDesc <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method clicks on Sort image - sort by date
	 */
	public void sortAlertByDate()
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".sort_modifydate")), 30);
			String locator = PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".sort_modifydate");
			WebElement webElement = elementhandler.findElement(locator);
			if(webElement.isDisplayed())
				elementhandler.clickElement(locator);
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : sortAlertByDate <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method verifies the type of first alert
	 * Returns true if alert type is 'Follow Document'
	 */
	public boolean isFirstAlertEqualsFollowDocument()
	{
		boolean flag = false;
		
		try{
			
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".alerttype_firstrow")), 30);
			String locator = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".alerttype_firstrow");
			WebElement webElement = elementhandler.getElement(locator);
			String message = webElement.getAttribute("title");
			flag = message.contains("Documentos en seguimiento") || message.contains("Acompanhamento de documento");
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isFirstAlertEqualsFollowDocument <br>"+displayErrorMessage(exc));
			flag = false;
		}
		
		return flag;
	}
	
	/*
	 * This method returns the modified date of alert in first row
	 */
	public String getModifiedDateOfFirstAlert()
	{
		String message = null;
		
		try{
			
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".modified_date_firstrow")), 30);
			String locator = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".modified_date_firstrow");
			message = elementhandler.getText(locator);
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : getModifiedDateOfFirstAlert <br>"+displayErrorMessage(exc));
		}
		
		return message;
	}
	
	/*
	 * This method filters the  list of alerts
	 */
	public void filterAlertListByText(String filter)
	{
		
		try{
			Thread.sleep(2000);		
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".alerttablefilter")), 50);
			Thread.sleep(2000);
			String locator = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".alerttablefilter");
			WebElement webElement = elementhandler.getElement(locator);
			webElement.clear();
			webElement.sendKeys(filter);
			}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : filterAlertListByText <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method verifies if alert title is displayed
	 */
	public boolean isAlertPageTitleDisplayed()
	{
		boolean flag=false;
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".alert_page_title")), 50);
			String title = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".alert_page_title"));
			if(title.contains("Alertas")){
					flag = true;
			}else{
				flag=false;
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isAlertPageTitleDisplayed <br>"+displayErrorMessage(exc));
			flag=false;
		}
		return flag;
	}
	
	/*
	 * This method is created for back up for deletion alert method when create alert fails. 
	 * Takes the first alert name which starts with "Test" in the first 10 records displayed on the page
	 */
	public String getFstAlertNameStartingWithTest()
	{
		String actualName ="";
		try
		{
			actualName = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".alertname_firstrow"));
			if(!actualName.contains("Test"))
			{
				for(int i=2;i< 10;i++)
				{
					if(elementhandler.getElement("xpath=.//tr[" + i +"]/td").getText().contains("Test"))
					{
						return elementhandler.getElement("xpath=.//tr[" + i +"]/td").getText();
					}
				}
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : getFstAlertNameStartingWithTest <br>"+displayErrorMessage(exc));
			return "";

		}

		return actualName;
	}
	
	/*
	 * This method verifies if an alert exist in the alert list
	 * returns true on success
	 */
	public boolean isAlertNameExist(String alertName)
	{
		boolean flag=false;
		try{			
			
			filterAlertListByText(alertName);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".alertname_firstrow")), 50);
			String actualName = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".alertname_firstrow"));
			if(actualName.contains(alertName))
			{
				flag = true;
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isAlertNameExist <br>"+displayErrorMessage(exc));
			 flag=false;
		}
		return flag;
	}
	
	/*
	 * This method clicks on 'create new alert' link in the alert list
	 */
	public void clickCreateNewAlert() 
	{
		try{
			Thread.sleep(2000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".createnewalertlink")), 50);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".createnewalertlink"));
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickCreateNewAlert <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method checks if the 'PredesignAlerts' is present or not
	 * return true on success
	 */
	public boolean isPredesignAlertsPresent() 
	{
		boolean isTitlePresent=false;
		
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".alert_predesign_title")), 50);
			String actualTitle =elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alert_predesign_title"));
			if(actualTitle.equalsIgnoreCase("Alertas Pré-definidos"))
				isTitlePresent = true;
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isPredesignAlertsPresent <br>"+displayErrorMessage(exc));
		}
		
		return isTitlePresent;
	}
	
	/*
	 * This method checks if the 'Reactivate' alert link is present or not
	 * return true on success
	 */
	public boolean isReactivateAlertLinkPresent() 
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".reactivatealertlink")), 50);
			String elementText = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".reactivatealertlink"));
			return elementText.equals("Reativar");		
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isReactivateAlertLinkPresent <br>"+displayErrorMessage(exc));
			return false;
		}
		
	
	}
	
	
	/*
	 * This method checks to see if the suspend alert link is displayed
	 */
	public boolean isSuspendAlertLinkDisplayed()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".suspendalertlink")).isDisplayed();	
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isSuspendAlertLinkDisplayed <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	
	/*
	 * This method clicks on 'Reactivate' alert link
	 */
	public void clickReactivateAlert() 
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".reactivatealertlink")), 50);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".reactivatealertlink"));
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickReactivateAlert <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method clicks on 'Suspend' alert link
	 */
	public void clickSuspendAlert() 
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".suspendalertlink")), 50);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".suspendalertlink"));
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickSuspendAlert <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method clicks on 'Cancel' on Suspend/Reactivate page
	 */
	public void clickCancelOnSuspendOrReactivatePage() 
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".alert_suspend_or_reactivate_cancel")), 50);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alert_suspend_or_reactivate_cancel"));
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickCancelOnSuspendOrReactivatePage <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method verifies that no element with text 'ID Cliente' exist in the page
	 * returns true on success
	 */
	public boolean isClientIDNotDisplayed() 
	{
		boolean clientIDNotDisplayed=false;
		try{
			Thread.sleep(3000);
			
			clientIDNotDisplayed = !(WebDriverFactory.isDisplayed(driver, elementhandler.getElement("//*[text()='ID Cliente']")));
		}
		catch(Exception exc){
			//Exception is expected for success scenario
			clientIDNotDisplayed = true;
		}
		return clientIDNotDisplayed;
	}
	
	/*
	 * This method verifies that no element with text 'Formato' exist in the page
	 * returns true on success
	 */
	public boolean isFormatNotDisplayed() 
	{
		boolean formatNotDisplayed=false;
		try{
			Thread.sleep(3000);
			
			formatNotDisplayed = !(WebDriverFactory.isDisplayed(driver, elementhandler.getElement("//*[text()='Formato']")));
		}
		catch(Exception exc){
			//Exception is expected for success scenario
			formatNotDisplayed = true;
		}
		return formatNotDisplayed;
	}
	
	/*
	 * This method verifies that no element with text 'Notificacion' exist in the page
	 * returns true on success
	 */
	public boolean isNoticeNotDisplayed() 
	{
		boolean noticeNotDisplayed=false;
		try{
			Thread.sleep(3000);
			
			noticeNotDisplayed = !(WebDriverFactory.isDisplayed(driver, elementhandler.getElement("//*[text()='Notificacion']")));
		}
		catch(Exception exc){
			//Exception is expected for success scenario
			noticeNotDisplayed = true;
		}
		return noticeNotDisplayed;
	}
	
	/*
	 * This method clicks on 'Cancel' on PredesignAlert page
	 */
	public boolean clickCancelButtonInPredesignAlert() 
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".alert_predesign_cancel")), 50);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alert_predesign_cancel"));
			return true;
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickCancelButtonInPredesignAlert <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	
	/*
	 * This method clicks on 'Next' on PredesignAlert page
	 */
	public void clickNextButtonInPredesignAlert() 
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".nextbutton")), 50);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".nextbutton"));
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickNextButtonInPredesignAlert <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method selects the 'PredesignAlerts' by name
	 * return true on success
	 * selectPredesignAlertByName
	 */
	public boolean isPredesignAlertByNameSelected(String predesignName) 
	{
		boolean predesignNameSelected=false;
		
		WebElement radioButton = null;
		
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".alert_predesign_labels_xpath")), 50);
			String allPredesignLabelsXpath = PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".alert_predesign_labels_xpath");
			String pathOfRadioButton = allPredesignLabelsXpath + "[normalize-space(text())='"+predesignName+"']/..//input";
			radioButton = elementhandler.getElement(pathOfRadioButton);
			
			if(radioButton.getAttribute("type").equals("radio")){
				if(!radioButton.isSelected())
					radioButton.click();
				predesignNameSelected = true;
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isPredesignAlertByNameSelected <br>"+displayErrorMessage(exc));
			predesignNameSelected=false;
		}
		return predesignNameSelected;
	}
	
	
		
	/*
	 * This method writes name on create alert page
	 */
	public void enterAlertName(String alertName) 
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".enteralertnamefield")), 50);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteralertnamefield")).clear();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteralertnamefield")).sendKeys(alertName);
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : writeNameField <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method enters the given email on create alert page
	 */
	public void enterEmailId(String email) 
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".enteremailidfield")), 50);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteremailidfield")).clear();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteremailidfield")).sendKeys(email);
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : writeEmailField <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method clicks on save button on create alert page
	 */
	public void clickSaveOnCreateAlertPage() 
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".savebuuton_oncreatealertpage")), 50);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".savebuuton_oncreatealertpage"));
			
			//introducing sleep here as the element to wait for object needs to be found
			Thread.sleep(2000);
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickSaveOnCreateAlertPage <br>"+displayErrorMessage(exc));
		}
	}
	
	
	/*
	 * This method is used for creating alert from search results list page
	 * Takes Alertname and email id for creation of the alert
	 */
	public void createAlertfromResultList(String AlertName, String email) 
	{
		try{
			
			enterAlertName(AlertName);
			enterEmailId(email);
			clickSaveOnCreateAlertPage();
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : CreateAlertfromResultList <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method is used to pass the driver back to Homepage.
	 * TODO: Investigate and delete this method. If the handle is passed during the test this method is not needed
	 */
	public HomePage PasstheDriver() 
	{
		try{
			
			return new HomePage(driver); 
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : PasstheDriver to HomePage <br>"+displayErrorMessage(exc));
			return null;
		}

	}
}
	
