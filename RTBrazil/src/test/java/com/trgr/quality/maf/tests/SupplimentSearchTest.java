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
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;
import com.trgr.quality.maf.pages.SuplementSearchPage;


public class SupplimentSearchTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	SuplementSearchPage suplementSearchPage;
	JSONObject jsonObject;
	public ITestResult testResult;
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

		} catch (Exception exc) {
			extentLogger = setUpExtentTest(extentLogger, "Search Test", "Start Search Test");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the Search test are not run.<br>"
							+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
		homepagecopy.clickSignOff();

	}

	// search Using Number and Index SupplimentPage
	@Test(groups = { "suppliment" }, description = "MAFQABANG-292")
	public void searchSupplimentUsingNumberAndIndex() throws Exception {
		boolean searchResultsDisplayed = false;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "SupplimentTest", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify search using norm number and index on Suplement page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String normIdKey = "normid";
				String normIdVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, normIdKey, extentLogger);
				String indexKey = "index";
				String indexVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, indexKey, extentLogger);

				homepage.openHomepage();
				suplementSearchPage = homepage.OpenSuplementPage();

				suplementSearchPage.enterNormNumber(normIdVal);
				searchResultsPage = suplementSearchPage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(normIdVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, normIdKey, normIdVal,
							jiraNumber);

					boolean isNavigationBarDisplayed = searchResultsPage.DocumentCountDisplayed();
					softas.assertTrue(isNavigationBarDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, isNavigationBarDisplayed,
							"Suplement is showing total document count on the result list", normIdKey, normIdVal,
							jiraNumber);

				}
				// search using index value
				suplementSearchPage = searchResultsPage.clickToGoBackOnTheBreadCrumb();
				suplementSearchPage.clickOnCleanSearch();
				suplementSearchPage.enterIndex(indexVal);

				searchResultsPage = suplementSearchPage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(indexVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, indexKey, indexVal, jiraNumber);

					boolean isNavigationBarDisplayed = searchResultsPage.DocumentCountDisplayed();
					softas.assertTrue(isNavigationBarDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, isNavigationBarDisplayed,
							"Suplement is showing total document count on the result list", indexKey, indexVal,
							jiraNumber);

				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummary, indexKey,
							indexVal + " -resulted in no search results", jiraNumber);

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

	// No Result message validation using invalid 'norm number' search in
	// Supplement Page

	@Test(groups = {"suppliment" }, description = "MAFQABANG-293")
	public void searchSupplementsForReturningNoResults() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "SupplimentTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Verify no search results message on Supplements Page");
		try {
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String noresultsKey = "noresults";
				String noresultsVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, noresultsKey,
						extentLogger);
				
				homepage.openHomepage();
				suplementSearchPage = homepage.OpenSuplementPage();
				suplementSearchPage.enterNormNumber(noresultsVal);

				searchResultsPage = suplementSearchPage.clickOnSearch();
				boolean noResultsMsgDisplayed = searchResultsPage.noSearchResultsDisplayed();
				softas.assertTrue(noResultsMsgDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, noResultsMsgDisplayed, issueSummary, noresultsKey, noresultsVal,
						jiraNumber);
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
