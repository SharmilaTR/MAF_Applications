package com.trgr.quality.maf.tests;
import java.io.IOException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.JiraConnector;
import com.trgr.quality.maf.commonutils.RandomUtils;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.pages.AlertPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchResultsPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.trgr.quality.maf.jsonreader.JsonReader;

/* Alerts_TC_002 Verify create Alert Functionality ,
 * Alerts_TC_004 Verify Suspend Alert Functionality ,
 * Alerts_TC_004 Verify Reactivate Alert Functionality
 * Alerts_TC_006 Verify Error Message creating an alert 
 * Alerts_TC_003 Verify Delete Alert Functionality , 
 */

public class AlertTest extends BaseTest {

	LoginPage loginpage;
	HomePage homepage,homepagecopy;
	AlertPage alertPage;
	
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JsonReader jsonReader;
	String alertNamecopy;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IllegalArgumentException, IOException
	{
		try{
			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".password");
			jsonReader = new JsonReader();
			homepage=loginpage.Login(username, password);
			homepagecopy = this.homepage;
			homepage.refresh();
			
			
		   }catch(Exception exc){
			
			    extentLogger = setUpExtentTest(extentLogger, "Alerts Test", "StartAlertTest");
				extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the Alert test are not run.<br>"+ takesScreenshot_Embedded()+ "<br>"+displayErrorMessage(exc));
				extentReports.endTest(extentLogger);			
				Assert.assertTrue(false);
		}
	}
	

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception
	{
		homepagecopy.clickSignOff();	
	}
	
	@Test(priority=0,groups={"alerts"}, description = "MAFQABANG-335")
	public void displayOfAlertsPage() throws Exception
	{
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Alerts Test", testResult.getMethod().getMethodName());
	  
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Display of Alerts Page"); 
		try{
					alertPage=homepage.openAlertsPage();
					Thread.sleep(1000);
					alertPage.clickCreateNewAlert();
					
					boolean predesignAlertValidated = alertPage.isPredesignAlertsPresent();
					
					softas.assertTrue(predesignAlertValidated,jiraNumber + ": Predesign Alerts validated");
					logExtentStatus(extentLogger, predesignAlertValidated, issueSummary, jiraNumber);
					
					//Verifying the cancel option
					alertPage.clickCancelButtonInPredesignAlert();
					Thread.sleep(1000);
					boolean createAlertCancelled =alertPage.isAlertPageTitleDisplayed();
					
					softas.assertTrue(createAlertCancelled,jiraNumber + ": Alerts Cancel button is validated");
					logExtentStatus(extentLogger, createAlertCancelled, "Alerts Cancel button is validated",jiraNumber);						
						
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
}
	
	/* Alerts_TC_002 Verify create Alert Functionality
	 *
	 */

	@Test(priority=1,groups={"alerts"}, description = "MAFQABANG-336")
	public void createAlert() throws Exception
	{
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Alerts Test", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify creation of Alert"); 
	  
		boolean alertCreated = false;
		
		try{
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
            
			 for (Object searchString : listOfSearchData) {
			   JSONObject jsonObjectChild = (JSONObject) searchString;
			
				String alertNameKey="alertname";
				String alertName =	jsonReader.readKeyValueFromJsonObject(jsonObjectChild,alertNameKey,extentLogger); 
				alertName = alertName+RandomUtils.getUniqueNumber();
				//Assigning this to get the precondition met for the delete alert test case.
				alertNamecopy = alertName;
				
									
				String emailIdKey="emailid";
				String emailId = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailIdKey,extentLogger); 
				
				alertPage=homepage.openAlertsPage();
				Thread.sleep(2000);
				alertPage.createAlert(alertName, emailId);
				homepage = alertPage.PasstheDriver();
				alertPage=homepage.openAlertsPage();
				Thread.sleep(2000);
				alertCreated = alertPage.isAlertNameExist(alertName);					
				softas.assertTrue(alertCreated,jiraNumber + ": Alert created");
				logExtentStatus(extentLogger, alertCreated, issueSummary , jiraNumber);
					
			}
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
}

	// Alerts_TC_004 Verify Suspend Alert Functionality
	
	@Test(priority=2,groups={"alerts"}, description = "MAFQABANG-337")
	public void suspendAndReactivateAlert() throws Exception
	{
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Alerts Test", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify suspending an existing Alert"); 
		try{
				
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
            
			 for (Object searchString : listOfSearchData) {
			   JSONObject jsonObjectChild = (JSONObject) searchString;
				String alertNameKey="alertname";
				String alertName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,alertNameKey,extentLogger);   
				
				String SuspendtodateKey="suspendtodate";
				String suspendtodate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,SuspendtodateKey,extentLogger); 
				
				alertPage=homepage.openAlertsPage();
				alertPage.filterAlertListByText(alertName);
				alertPage.suspendAlertToGivenDate(suspendtodate);
				boolean isalertsuspended = alertPage.isReactivateAlertLinkPresent();

				softas.assertTrue(isalertsuspended,jiraNumber+ "Alert is suspended");
				logExtentStatus(extentLogger, isalertsuspended, "Alert is suspended" , jiraNumber);
				
				//validating client id field absence in Suspend state
				boolean fieldsNotPresentInPage = false; 
				fieldsNotPresentInPage = alertPage.isClientIDNotDisplayed();
				
				softas.assertTrue(fieldsNotPresentInPage,jiraNumber + "validating client id field absence in Suspend state");
				logExtentStatus(extentLogger, fieldsNotPresentInPage, "validating client id field absence in Suspend state", jiraNumber);
				
				alertPage.reactivateAlert();
				boolean isalertreactivated = alertPage.isSuspendAlertLinkDisplayed();
				softas.assertTrue(isalertreactivated,jiraNumber + ": Alert is reactivated");
				logExtentStatus(extentLogger, isalertreactivated, "Alert is reactivated" , jiraNumber);
			}
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
}

	// Alerts_TC_003 Verify Delete Alert Functionality

	@Test(priority=3,groups={"alerts"}, description = "MAFQABANG-338")
	public void deleteExistingAlert() throws Exception
	{		
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Alerts Test", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify deleting an existing Alert");
		try{
			alertPage = homepage.openAlertsPage();
	         Thread.sleep(1000);
	         
	         if(alertNamecopy==null)
	         {
	        	 alertNamecopy = alertPage.getFstAlertNameStartingWithTest();
	         }
			alertPage.filterAlertListByText(alertNamecopy);
			
			alertPage.deleteAnAlert();
			boolean isalertdeleted = alertPage.isAlertNameExist(alertNamecopy);
			softas.assertFalse(isalertdeleted,jiraNumber + issueSummary);
			logExtentStatus(extentLogger, !isalertdeleted, issueSummary , jiraNumber);
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=4,groups={"alerts"}, description = "MAFQABANG-351")
	public void creationOfAlertFromResultList() throws Exception
	{
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		SearchResultsPage searchResultsPage;
   	
		extentLogger = setUpExtentTest(extentLogger, "Alerts Test",testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify creation of Alert from Result Lists");
		try{
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
            
			 for (Object searchString : listOfSearchData) {
			   JSONObject jsonObjectChild = (JSONObject) searchString;
			   
			   String freewordKey = "freeword";
			    String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);  
			    
				String alertNameKey="alertname";
				 String alertName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,alertNameKey,extentLogger);
				 alertName = alertName +" "+RandomUtils.getUniqueNumber();
				
				String emailIdKey="emailid";
				String emailId = jsonObjectChild.get(emailIdKey).toString();
				
				String contenttypeKey = "contenttype";
				String contenttypeVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,contenttypeKey,extentLogger);  
				
				//Performing Search
				homepage.openHomepage();
				homepage.enterGivenFreeword(freewordVal);
				
				searchResultsPage = homepage.clickOnSearch();
				
				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsPage.clickOnSpecificContentTypeOnLeftPage(contenttypeVal);
					
					// Open Save and Schedule Search Page				
					alertPage = searchResultsPage.clickAddToAlert();
					Thread.sleep(1000);
					alertPage.createAlertfromResultList(alertName, emailId);
					
					Thread.sleep(2000);
					boolean alertCreated = alertPage.isAlertNameExist(alertName);
					softas.assertTrue(alertCreated,jiraNumber+ issueSummary);
					logExtentStatus(extentLogger, alertCreated, issueSummary , jiraNumber);
				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
							freewordVal + " -resulted in no search results", jiraNumber);

				}
				
			}
									
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

}
