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
import com.trgr.quality.maf.jsonreader.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.trgr.quality.maf.pages.HistoryPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;

public class HistoryTest extends BaseTest {
	/*
	 * Historail_001 Verify History Page Historail_002 Start a New History
	 * Historail_003 Delete a Single History Historial_004 Rename History
	 * Historial_005 Restart History Historial_006 Verify user can Print/ Email/
	 * Save history
	 */

	LoginPage loginpage;
	HomePage homepage,homepagecopy;
	HistoryPage historypage;
	String historyName;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
    JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IllegalArgumentException, IOException {
		try {
				loginpage = new LoginPage(driver, ProductUrl);
				String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".username");
				String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".password");
				jsonReader = new JsonReader();
				homepage = loginpage.Login(username, password);
				homepagecopy = this.homepage;
				homepage.refresh();
				
		} catch(Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "History Test", "startHistoryTest");
			extentLogger.log(LogStatus.ERROR,"Due to PreRequest Failed : Validations on the History test are not run.<br>"+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
		homepagecopy.clickSignOff();

	}

	/*
	 * Historail_001 Verify History Page
	 */
	@Test(priority = 0, groups={"history"}, description="MAFQABANG-317")
	public void displayOfHistoryPage() throws IOException {

		try {

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "History Test", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify History page display");
			
			// Validate History Link

			historypage = homepage.ClickonHistoryLink();

			boolean historyLinkValidated = historypage.isHistoryPageDisplayed();
			softas.assertTrue(historyLinkValidated, jiraNumber + "History Link Validated");
			logExtentStatus(extentLogger, historyLinkValidated, "History Link Validated", jiraNumber);

			// Validate History Page
			boolean historyPageValidated = historypage.isHistoryPageDisplayedWithExpectedDetails();
			softas.assertTrue(historyPageValidated, jiraNumber + issueSummary);
			logExtentStatus(extentLogger, historyLinkValidated, issueSummary, jiraNumber);

		} catch (Exception exc) {
			
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
			
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority=1,groups={"history"}, description = "MAFQABANG-312")
	public void createNewHistory() throws IOException, Exception
	{

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "History Test", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Creating new history"); 
	
		try{
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
            
			 for (Object searchString : listOfSearchData) {
			   JSONObject jsonObjectChild = (JSONObject) searchString;
			   String sessionNameKey = "sessionname";
			    String sessionNameVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,sessionNameKey,extentLogger);                                       
				
			    sessionNameVal = sessionNameVal+ RandomUtils.getUniqueNumber();
				
				String customerIdKey = "customerid";
				String customerIdVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,customerIdKey,extentLogger);
				
			historypage = homepage.ClickonHistoryLink();	
			historypage.startNewHistory(customerIdVal, sessionNameVal);
			historypage.returnBackToHistoryPage();
			boolean isnewhistorycreated = historypage.isHistoryPageDisplayed();
			softas.assertTrue(isnewhistorycreated, jiraNumber+":" + issueSummary);
			logExtentStatus(extentLogger, isnewhistorycreated, issueSummary, jiraNumber);
			
			//Re-login to get history updated.
			homepage.clickSignOff();
			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".password");
			
			homepage = loginpage.Login(username, password);
			homepage.refresh();
			}
		}catch(Exception exc){
			
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
			
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * Historail_004 Delete a Single History
	 */
	@Test(priority=2,groups={"history"}, description = "MAFQABANG-313")
	public void deleteSingleHistory() throws IOException, InterruptedException {
		try{

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();	
			extentLogger = setUpExtentTest(extentLogger, "History Test", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Delete existing single history");
		  			
			historypage=homepage.ClickonHistoryLink();
			//historypage.clickViewAllEventsLink();
			//Select history name to delete, if it is not already set.
			if(historyName==null||historyName.equals("")){
				historyName = historypage.getFirstHistoryWithDeleteLink();
			softas.assertTrue(historyName!=null, jiraNumber + ":Validate History Name selected to delete");
			logExtentStatus(extentLogger, historyName!=null, "Validate History Name selected to delete", jiraNumber);
			}
			//Delete history
			boolean historyDeleted =false;
			historypage = homepage.ClickonHistoryLink();
			//historypage.clickViewAllEventsLink();
			if(historypage.isHistoryNamePresent(historyName)){
				int eventRowToDelete =historypage.getHistoryRowNumber(historyName);
				historypage.clickDeleteLinkForItem(eventRowToDelete);
				historypage.clickDeleteConfirmButton();
				historyDeleted = !historypage.isHistoryNamePresent(historyName);
				softas.assertTrue(historyDeleted, jiraNumber +": Validate Deleting single history");
				logExtentStatus(extentLogger, historyDeleted, issueSummary, jiraNumber);
				
			}
			
			if(historyDeleted)
				historyName = null;
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Rename History
	@Test(priority=3,groups={"history"}, description = "MAFQABANG-314")
	public void renameExistingHistory() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "History Test", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Rename existing history");
		
	  try{
			historypage=homepage.ClickonHistoryLink();
			
			int lastItemRow = historypage.getCountOfHistoryItems()-2;
			historyName = historypage.getHistoryNameAtRow(lastItemRow);
			
			boolean renameLinkPresent=false;
			//validating Rename Link is Present or not
			if(renameLinkPresent = historypage.isRenameLinkPresentForItem(lastItemRow))
			{
				historypage.clickRenameHistoryForItem(lastItemRow);
				softas.assertTrue(renameLinkPresent, jiraNumber + ": Rename link is present");
				logExtentStatus(extentLogger, renameLinkPresent, "Rename link is present", jiraNumber);
			
			}
			 String historyNewName = "EditedName"+ RandomUtils.getUniqueNumber();
			
			 //Validate Renaming History
			boolean historyRenamed=false;
			historypage.enterGivenTextOnRenameField(historyNewName);
			historypage.clickSaveRenameButton();
			historyRenamed = historypage.isHistoryNamePresent(historyNewName);
			softas.assertTrue(historyRenamed, jiraNumber + ": Failed to Rename history");
			logExtentStatus(extentLogger, historyRenamed, issueSummary, jiraNumber);
			
			if(historyRenamed){
				historyName = historyNewName;
			}
				
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * Verify reset History,MAFQABANG-315
	 */
	@Test(priority=4,groups={"history"}, description = "MAFQABANG-315")
	public void resetHistory() throws IOException {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "History Test", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		
		try{
			String issueSummary = getIssueTitle(jiraNumber, "Reset history");
		  			
			historypage=homepage.ClickonHistoryLink();
			
			//Select the oldest history item to reset
			int lastItemRow = historypage.getCountOfHistoryItems()+1;
			historyName = historypage.getHistoryNameAtRow(lastItemRow);
			
			//Reset & verify the item is moved to top of the list
			boolean resetLinkPresent = false;
			if(resetLinkPresent = historypage.isResetLinkPresetForItem(lastItemRow)){
				historypage.clickResetLinkForItem(lastItemRow);
			}
			softas.assertTrue(historyName!=null && resetLinkPresent, jiraNumber + ":Reset Link not found for the selected history :"+historyName);
			logExtentStatus(extentLogger, historyName!=null && resetLinkPresent, "Reset Link not found for the selected history :"+historyName, jiraNumber);
			
			//Verify the event reset is successful
			boolean resetSuccess = false;
			if(resetLinkPresent){
				String firstHistoryName = historypage.getHistoryNameAtRow(2);
				if(historyName.equals(firstHistoryName))
					resetSuccess = historypage.isDeleteAfterTwoWeeks(2);
				softas.assertTrue(resetSuccess, jiraNumber+" : Failed to verify reset history");
				logExtentStatus(extentLogger, resetSuccess, issueSummary, jiraNumber);
			}
			
			
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 5, groups={"history"}, description = "MAFQABANG-316")
	public void deliveryOptionsOnHistory() throws IOException,Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		
		extentLogger = setUpExtentTest(extentLogger, "History Test", testResult.getMethod().getMethodName());
		
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Delivery options from history page"); 
		
		try {

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
            
			 for (Object searchString : listOfSearchData) {
			   JSONObject jsonObjectChild = (JSONObject) searchString;
			   String emailToKey = "emailid";
			    String emailToVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailToKey,extentLogger);                                       
				
				String Keyword = "keyword";
				String keyword = jsonObjectChild.get(Keyword).toString();
			

				historypage = homepage.ClickonHistoryLink();
				historypage.clickOnDeliveryIcon();
	
				historypage.clickOnEmailDeliveryOption();
				historypage.sendEmailDetails(emailToVal, keyword);
				historypage.isFileFormatSelectedIsRTF();
				historypage.clickSaveOnEmailDelivery();
	
				boolean emailsendmsg = historypage.isEmailSentMsgDisplayed();
				softas.assertTrue(emailsendmsg, jiraNumber+" : Email Send Successfully");
				logExtentStatus(extentLogger, emailsendmsg, "Email Send Successfully", jiraNumber);
	
				historypage.clickOnReturnToHistoryFromDelivery();
	
				historypage.clickOnDeliveryIcon();
	
				historypage.clickOnSaveDeliveryOption();
				historypage.isFileFormatSelectedIsRTF();
	
				historypage.clickSaveOnEmailDelivery();
				boolean savemsg = historypage.isDeliverySuccessMsgDisplayed();
				softas.assertTrue(savemsg, jiraNumber+" : Document Saved Successfully");
				logExtentStatus(extentLogger, savemsg, "Document Saved Successfully", jiraNumber);
	
				historypage.clickOnReturnToHistoryFromDelivery();
			
			}
		} catch (Exception exc) {
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
}
