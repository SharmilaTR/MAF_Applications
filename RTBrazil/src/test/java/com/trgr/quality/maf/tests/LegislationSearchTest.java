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
import com.trgr.quality.maf.pages.LegislationPage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class LegislationSearchTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	LegislationPage legislationPage;
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
			extentLogger = setUpExtentTest(extentLogger, "Legislation Test", "Start Search Test");
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

	@Test(groups = {"legislation"}, description = "MAFQABANG-283")
	public void searchLegislationUsingNumberAndArticle() throws Exception {

		boolean searchResultsDisplayed = false;

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "LegislationTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		try {
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify search using standard number and Article on Legislation page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String numberKey = "number";
				String numberVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, numberKey, extentLogger);
				String articleKey = "article";
				String articleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, articleKey, extentLogger);

				homepage.openHomepage();
				legislationPage = homepage.OpenLegislationPage();

				legislationPage.enterNumber(numberVal);
				legislationPage.enterArticle(articleVal);
				searchResultsPage = legislationPage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(numberVal)
							&& searchResultsPage.searchReturnedResultsAsExpected(articleVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, numberKey, numberVal,
							jiraNumber);

				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummary, numberKey,
							numberVal + " -resulted in no search results", jiraNumber);

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

	// validating no result message while searching with invalid number
	@Test(groups = {"legislation"}, description = "MAFQABANG-284")

	public void searchLegislationForReturingNoResults() throws Exception {
		boolean searchResultsDisplayed = false;

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "LegislationTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,
				"Verify no result message using standard number on Legislation page");

		try {
						
				homepage.openHomepage();
				legislationPage = homepage.OpenLegislationPage();
				legislationPage.enterNumber("ttpy");
				searchResultsPage = legislationPage.clickOnSearch();
				searchResultsDisplayed = searchResultsPage.noSearchResultsDisplayed();

				softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary,  jiraNumber);


		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(groups = {"legislation"}, description = "MAFQABANG-282")
	public void searchLegislationUsingIndex() throws Exception {
		boolean searchResultsDisplayed = false;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "LegislationTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify search using index on Legislation page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String indexsearchKey = "indexsearch";
				String indexsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, indexsearchKey,
						extentLogger);

				homepage.openHomepage();
				legislationPage = homepage.OpenLegislationPage();

				legislationPage.enterIndexSearchValue(indexsearchVal);

				boolean suggestionsDisplayed = legislationPage.isTheSuggestionsDropdownDisplayed()
						&& legislationPage.isSearchStringhighlightedOnCombo(indexsearchVal);

				softas.assertTrue(suggestionsDisplayed,
						jiraNumber + indexsearchVal + "-Auto suggest dropdown displayed");
				logExtentStatus(extentLogger, suggestionsDisplayed, "Auto suggestion dropdown is displayed",
						indexsearchKey, indexsearchVal, jiraNumber);

				legislationPage.ScrollToGivenSearchString(indexsearchVal);

				searchResultsPage = legislationPage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(indexsearchVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, "search returned results as expected",
							indexsearchKey, indexsearchVal, jiraNumber);

				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummary, indexsearchKey,
							indexsearchVal + " -resulted in no search results", jiraNumber);

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

	@Test(groups = {"legislation"}, description = "MAFQABANG-318")
	public void deliveryOptionOnSearchResultsPage() throws Exception {
		boolean searchResultsDisplayed = false;
		LegislationPage legislatonPage;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "LegislationTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify delivery option on search results page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String indexsearchKey = "indexsearch";
				String indexsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, indexsearchKey,
						extentLogger);

				homepage.openHomepage();
				legislatonPage = homepage.OpenLegislationPage();

				// enter type of order
				legislatonPage.enterIndexSearchValue(indexsearchVal);
				searchResultsPage = legislatonPage.clickOnSearch();
				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(indexsearchVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, "search returned results as expected",
							indexsearchKey, indexsearchVal, jiraNumber);

					Boolean optionsDisplayedOnResults = searchResultsPage.isSaveDeliveryAlertOptionsDisplayed();
					softas.assertTrue(optionsDisplayedOnResults,
							jiraNumber + ":" + "Save, delivery, alert and Return to list options are displayed");
					logExtentStatus(extentLogger, optionsDisplayedOnResults,
							"Save, delivery, alert and Return to list options are displayed", indexsearchKey,
							indexsearchVal, jiraNumber);

				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummary, indexsearchKey,
							indexsearchVal + " -resulted in no search results", jiraNumber);

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

	@Test(groups = {"legislation"}, description = "MAFQABANG-348")
	public void performSearchWithInOnResultSet() throws Exception {
		boolean searchResultsDisplayed = false;
		LegislationPage legislationPage;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "LegislationTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify search within on the results");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String indexsearchKey = "indexsearch";
				String indexsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, indexsearchKey,
						extentLogger);

				homepage.openHomepage();
				legislationPage = homepage.OpenLegislationPage();

				legislationPage.enterIndexSearchValue(indexsearchVal);

				searchResultsPage = legislationPage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(indexsearchVal);

					// get content from result set to submit for the search
					// within
					String searchWithinText = searchResultsPage.getTextFirstDocumentViewTerms();

					searchResultsPage.enterSearchWithinTerm(searchWithinText);
					searchResultsPage.clickOnSearchWithInSearch();

					searchResultsDisplayed = searchResultsPage.isSearchStringHighlighted(searchWithinText);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed,
							"Search with in is working as expected and search term is highlighted", indexsearchKey,
							indexsearchVal, jiraNumber);

				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummary, indexsearchKey,
							indexsearchVal + " -resulted in no search results", jiraNumber);

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
