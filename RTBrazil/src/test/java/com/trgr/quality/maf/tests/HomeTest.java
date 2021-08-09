package com.trgr.quality.maf.tests;

import org.json.simple.parser.JSONParser;
import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.gson.JsonArray;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.JiraConnector;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;

public class HomeTest extends BaseTest {

	LoginPage loginpage;
	HomePage homepage,homepagecopy;
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

			extentLogger = setUpExtentTest(extentLogger, "HomePage Test", "startHomeTest");

			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the Home test are not run.<br>"
					+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			softas.assertTrue(false, "Exception in Test");
			softas.assertAll();
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
		homepagecopy.clickSignOff();

	}

	// ======================================================================================================

	@Test(groups ={"home"}, priority = 0, description = "MAFQABANG-271")
	public void widgetDisplayOnHomePage() throws Exception {

		boolean flag = false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage Test", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();   
			String issueSummary = getIssueTitle(jiraNumber, "Widget display on Home page");

			homepage.openHomepage();
			flag = homepage.verifywidgetsinhomepage();

			softas.assertTrue(flag, jiraNumber + ": Verify  Widget Displayed");
			logExtentStatus(extentLogger, flag, issueSummary, jiraNumber);

		} catch (NoSuchElementException exc) {
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
					
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(groups ={"home"}, priority = 1, description = "MAFQABANG-274")
	public void searchTabsDisplayedOnHomePage() throws Exception {

		boolean flag = false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage Test", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();   
			String issueSummary = getIssueTitle(jiraNumber, "Verify search tabs displayed on home page");       


			homepage.openHomepage();
			flag = homepage.isSearchTabsPresent();
			softas.assertTrue(flag, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, flag, issueSummary, jiraNumber);

		} catch (NoSuchElementException exc) {
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(groups ={"home"}, priority = 2, description = "MAFQABANG-272")
	public void displayOfHeadersectionOnHomepage() throws Exception {

		boolean flag = false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage Test", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();   
			String issueSummary = getIssueTitle(jiraNumber, "Verify the header section is displayed with expected links");       

			homepage.openHomepage();
			flag = homepage.headerSectionLinksPresent();
			softas.assertTrue(flag, jiraNumber + ": Verify header section links Displayed");
			logExtentStatus(extentLogger, flag, "Verify header section links Displayed", jiraNumber);

			flag = homepage.isProductNameandProductLogoPresent();
			softas.assertTrue(flag, jiraNumber + ":Verify Product Name and  Product Logo in header section ");
			logExtentStatus(extentLogger, flag, "Verify Product Name and  Product Logo in header section",
					jiraNumber);

			flag = homepage.isLogedinUserDetailsPresent();
			softas.assertTrue(flag, jiraNumber + ": Verify Logged in user details present in header section ");
			logExtentStatus(extentLogger, flag, "Verify Logged in user details present in header section",
					jiraNumber);

		} catch (NoSuchElementException exc) {
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(groups ={"home"}, priority = 3, description = "MAFQABANG-273")
	public void displayOfFooterSectionOnHomePage() throws Exception {

		boolean flag = false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage Test", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();   
			String issueSummary = getIssueTitle(jiraNumber, "Verify the footer section is displayed with expected info");       


			homepage.openHomepage();
			flag = homepage.verifyFooterSectioninHomePage();
			softas.assertTrue(flag, jiraNumber +": Verify  footer section in home page");
			logExtentStatus(extentLogger, flag, "Verify  footer section in home page",jiraNumber);

			homepage.openHomepage();
			flag = homepage.isCustomerCareContactNoPresentinFooterSection();
			softas.assertTrue(flag, jiraNumber + ": Verify  Customer contact number is present in footer section");
			logExtentStatus(extentLogger, flag, "Verify  Customer contact number is present in footer section",	jiraNumber);

		} catch (NoSuchElementException exc) {
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(groups ={"home"}, priority = 4, description = "MAFQABANG-310")
	public void displayOfHelpLinks() throws Exception {

		boolean flag = false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage Test", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();   
			String issueSummary = getIssueTitle(jiraNumber, "Verify the help links are working as expected");       

			homepage.openHomepage();
			homepage.clickHelpLink();
			flag = homepage.isHelpPageDisplayedAsExpected();
			softas.assertTrue(flag,jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, flag, issueSummary, jiraNumber);

		} catch (NoSuchElementException exc) {
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	// verify search widget fields on home page
@Test(groups= {"home"},description="MAFQABANG-275")
public void verifySearchfieldsOnHomePage() throws Exception {
boolean flag =false;

try {
	testResult = Reporter.getCurrentTestResult();
	softas = new SoftAssert();
	extentLogger = setUpExtentTest(extentLogger, "HomePage Test", testResult.getMethod().getMethodName());
	String jiraNumber = testResult.getMethod().getDescription();   
	String issueSummary = getIssueTitle(jiraNumber, " Verify Search Widget fields on Home page");

	homepage.openHomepage();
	flag = homepage.verifySearchWidgetFields();
	softas.assertTrue(flag, jiraNumber + ": Verify Search Widget fields on Home page");
	logExtentStatus(extentLogger, flag, issueSummary, jiraNumber);

} catch (NoSuchElementException exc) {
	 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			
	softas.assertTrue(false, "Exception in Test");

} finally {
	extentReports.endTest(extentLogger);
	softas.assertAll();
}
}
	/*
	 * Per Sousa, Maria Anilza S. (TR Technology & Ops) on 23/07: This functionality no longer exists, has been excluded from the RTO
	 */
	//@Test(groups ={"home"}, priority = 4, description = "MAFQABANG-311")
	public void onlineSupportHelpLink() throws Exception {

		boolean flag = false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage Test", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();   
			String issueSummary = getIssueTitle(jiraNumber, "Verify the online support help link is working");       

			homepage.openHomepage();
			homepage.clickHelpLink();
			homepage.clickHelpOnlineSupport();

			flag = homepage.isChatPageDisplayedAsExpected();
			softas.assertTrue(flag, jiraNumber+ ": Verify  Help Online Support");
			logExtentStatus(extentLogger, flag, " Verify  Help Link in home page", jiraNumber);

		} catch (NoSuchElementException exc) {
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
}
