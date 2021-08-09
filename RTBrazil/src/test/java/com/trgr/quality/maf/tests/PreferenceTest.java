package com.trgr.quality.maf.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.JiraConnector;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.DoctrineSearchPage;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.JurisprudencePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.PreferencePage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class PreferenceTest extends BaseTest {
	/*
	 * Module Name : Preferences Feature Names : Description Preferences_TC_001
	 * : Preferences_Verify Preferences link Preferences_TC_002 :
	 * Preferences_Verify Email Notification services Preferences_TC_003 :
	 * Preferences_Verify Search Results per page is enabled Preferences_TC_004
	 * : Preferences_Verify Options Delivery in preferences is enabled
	 * Preferences_TC_005 : Preferences_Verify Print options
	 */

	LoginPage loginpage;
	HomePage homepage,homepagecopy;
	PreferencePage preferencepage;
	SearchResultsPage searchresultpage;
	DocumentDisplayPage Docdisplaypage;
	DoctrineSearchPage doctrinesearchpage;
	JurisprudencePage jurisprudencePage;
	//String searchkey, freesearchword, number;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		
		try {
			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".password");
			jsonReader = new JsonReader();
			homepage = loginpage.Login(username, password);
			homepagecopy = this.homepage;
			homepage.refresh();


		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Preference Test", "StartPreferenceTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the Home test are not run.<br>"+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			softas.assertTrue(false, "Exception in Test");
			softas.assertAll();
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
		homepagecopy.clickSignOff();

	}

	@Test(groups={"preference"}, description = "MAFQABANG-376")
	public void displayOfPreferencePage() throws Exception {
		
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Preference Test", testResult.getMethod().getMethodName());
	  
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify preference page is displayed as expected"); 
	
		try {
			
			homepage.clickonServicesLink();
			preferencepage = homepage.clickPreferenceLink();
			boolean isPageDisplayedAsExpected = preferencepage.isPreferencePageDisplayed();
			softas.assertTrue(isPageDisplayedAsExpected, jiraNumber+":" + issueSummary);
			logExtentStatus(extentLogger, isPageDisplayedAsExpected, issueSummary, jiraNumber);
			
		} catch (Exception exc) {
			
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
			
		} finally {
			
			extentReports.endTest(extentLogger);
			softas.assertAll();
			
		}
	}

	@Test(groups={"preference"}, description = "MAFQABANG-377")
	public void setSearchResultsCountPerPage() throws Exception {
		
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Preference Test", testResult.getMethod().getMethodName());
	  
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify search results count is set per page"); 
	
		try {
			
			homepage.clickonServicesLink();
			preferencepage = homepage.clickPreferenceLink();
			boolean isPreferenceSavedAsExpected = preferencepage.isRadioButton10Enabled();
			
			softas.assertTrue(isPreferenceSavedAsExpected,jiraNumber + ":Search result per page is set to 10 in the preference page");
			logExtentStatus(extentLogger, isPreferenceSavedAsExpected, "Search result per page is set to 10 in the preference page", jiraNumber);
			
			if (isPreferenceSavedAsExpected) {
				homepage = preferencepage.goToHomePage();
				jurisprudencePage = homepage.OpenJurisprudencePage();
				jurisprudencePage.enterNumberValue("68.857");
				searchresultpage=jurisprudencePage.clickOnSearch();
				int count = searchresultpage.getResultListCountPerPage();
				softas.assertTrue(count>0, jiraNumber + ": Updated number of documents to 10 on the preference page");
				logExtentStatus(extentLogger, count>0, "Updated number of documents to 10 on the preference page", jiraNumber);
			
			} else{
				softas.assertTrue(false, jiraNumber + ": Radio button 10 is not enabled  in the preference page");
				logExtentStatus(extentLogger, false, "Radio button 10 is not enabled  in the preference page", jiraNumber);
			}
		} catch (Exception exc) {
			
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
			
		} finally {
			
			extentReports.endTest(extentLogger);
			softas.assertAll();
			
		}
	}

	@Test(groups={"preference"}, description = "MAFQABANG-381")
	public void setDefaultDeliveryDocFormatAsPDF() throws Exception {
		
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Preference Test", testResult.getMethod().getMethodName());
	  
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify default delivery document format is set as PDF"); 
	
		try {
			
			homepage.clickonServicesLink();
			preferencepage = homepage.clickPreferenceLink();
			
			boolean isPreferenceSavedAsExpected = preferencepage.checkAndSetPDFAsDefaultFormat();
			if (isPreferenceSavedAsExpected) {
				
				homepage = preferencepage.goToHomePage();
				homepage.enterGivenFreeword("internacional");
				searchresultpage = homepage.clickOnSearch();
				Docdisplaypage = searchresultpage.clickFirstLink();
				isPreferenceSavedAsExpected = Docdisplaypage.verifyDocumentSavedinPDF();
			
				softas.assertTrue(isPreferenceSavedAsExpected, jiraNumber + ":" +  issueSummary);
				logExtentStatus(extentLogger, isPreferenceSavedAsExpected, issueSummary, jiraNumber);
				
			} else{
				softas.assertTrue(false, jiraNumber + ": " + issueSummary);
				logExtentStatus(extentLogger, false, issueSummary, jiraNumber);
			}
			
			
		} catch (Exception exc) {
			
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
			
		} finally {
			
			extentReports.endTest(extentLogger);
			softas.assertAll();
			
		}
	}

	
	@Test(groups={"preference"}, description = "MAFQABANG-382")
	public void errorMsgOnSavingEmptyEmailId() throws Exception {
		
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Preference Test", testResult.getMethod().getMethodName());
	  
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify error message upon saving with no email id"); 
		try {
			
			homepage.clickonServicesLink();
			preferencepage = homepage.clickPreferenceLink();
			preferencepage.ClearEmailOnPreferencePage();

			preferencepage.savePreference();

			boolean isErrorMsgDisplayed = preferencepage.isErrorMessageDisplayed();

			softas.assertTrue(isErrorMsgDisplayed, jiraNumber+": " + issueSummary);
			logExtentStatus(extentLogger, isErrorMsgDisplayed, issueSummary, jiraNumber);
		
		
		} catch (Exception exc) {
			
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
			
		} finally {
			
			extentReports.endTest(extentLogger);
			softas.assertAll();
			
		}
	}

	
	@Test(groups={"preference"}, description = "MAFQABANG-383")
	public void UpdatesToPreferencesPage() throws Exception {
		
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Preference Test", testResult.getMethod().getMethodName());
	  
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify to set email id on the preference page"); 
		try {
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
            
			 for (Object searchString : listOfSearchData) {
			   JSONObject jsonObjectChild = (JSONObject) searchString;
			    String freewordKey = "freeword";
			    String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);                                       
			
			    String emailKey = "emailid";
			    String emailVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailKey,extentLogger);                                       
			
			    String accordionKey = "accordion";
			    String accordionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,accordionKey,extentLogger);                                       
			
			homepage.clickonServicesLink();
			preferencepage = homepage.clickPreferenceLink();
			
			preferencepage.savePreference();

			preferencepage.writeEmailInPreferencePage(emailVal);
			preferencepage.savePreference();

			String actualEmailId = preferencepage.getEmail();
			boolean isPreferenceSavedAsExpected = emailVal.equals(actualEmailId);
			softas.assertTrue(isPreferenceSavedAsExpected, jiraNumber + ":" +  issueSummary);
			logExtentStatus(extentLogger, isPreferenceSavedAsExpected, issueSummary, jiraNumber);
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
