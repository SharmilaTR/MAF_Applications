package com.trgr.quality.maf.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.DigitalLibraryPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class DigitalLibraryTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	DigitalLibraryPage digitallibrarypage;
	JSONObject jsonObject;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		try {

			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".usr");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".pwd");
			jsonReader = new JsonReader();
			homepage = loginpage.Login(username, password);
			homepagecopy = this.homepage;
			homepage.refresh();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Digitallibrary", "StartDigital Library Test");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the Signoff test are not run.<br>"
							+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
		homepagecopy.clickSignOff();

	}

	@Test(groups = { "digitallibrary" }, description = "MAFQABANG-412")
	public void advancedSearchDigitalLibrary() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "Digitallibrary", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		try {

			String issueSummary = getIssueTitle(jiraNumber, "Verify advance search Digital Library using freeword");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				homepage = homepage.openHomepage();
				digitallibrarypage = homepage.OpenDigitalLibraryPage();

				digitallibrarypage.enterGivenFreeword(freewordVal);
				searchResultsPage = digitallibrarypage.clickOnSearch();
				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					boolean searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(freewordVal);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, freewordKey, freewordVal,
							jiraNumber);
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":Search DigitalLibrary using freeword");

				} else {
					softas.assertTrue(true, jiraNumber + "- resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
							freewordVal + " -resulted in no search results", jiraNumber);

				}

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
