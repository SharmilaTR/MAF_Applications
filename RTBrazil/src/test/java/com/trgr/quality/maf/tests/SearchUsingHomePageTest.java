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
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class SearchUsingHomePageTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	JSONObject jsonObject;
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

	// Search using Freeword on Home page
	@Test(groups ={"search"}, description = "MAFQABANG-276")
	public void searchUsingFreewordAndNaturalLanguageOnHomepage() throws Exception {
		boolean searchResultsDisplayed = false;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "search", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		try {
			String issueSummaryFreeword = getIssueTitle(jiraNumber, "Verify search using freeword on the homepage");
			String issueSummarynatural = getIssueTitle(jiraNumber,
					"Verify search using NaturalLanguage on the homepage");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				String naturalKey = "naturalLanguage";
				String naturalVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, naturalKey, extentLogger);

				homepage.openHomepage();
				homepage.clickOnClear();

				homepage.enterGivenFreeword(freewordVal);
				searchResultsPage = homepage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(freewordVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummaryFreeword);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummaryFreeword, freewordKey,
							freewordVal, jiraNumber);

				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummaryFreeword, freewordKey,
							freewordVal + " -resulted in no search results", jiraNumber);
				}

				// search with natural language

				homepage.openHomepage();
				homepage.clickOnClear();

				homepage.clickOnNaturalLanguage();
				homepage.enterGivenNaturalLanguage(naturalVal);
				searchResultsPage = homepage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(naturalVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummarynatural);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummarynatural, naturalVal, naturalVal,
							jiraNumber);

				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummarynatural, naturalVal,
							naturalVal + " -resulted in no search results", jiraNumber);

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

	@Test(groups ={"search"}, description = "MAFQABANG-352")
	public void displaySearchResultsBasedOnContentType() throws Exception {
		boolean searchResultsDisplayed = false;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "search", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify display search results based on content type");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				homepage.openHomepage();
				homepage.clickOnClear();
				homepage.enterGivenFreeword(freewordVal);
				searchResultsPage = homepage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(freewordVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + freewordVal);
					logExtentStatus(extentLogger, searchResultsDisplayed, "Search returned results as expected",
							freewordKey, freewordVal, jiraNumber);

					searchResultsDisplayed = searchResultsPage.resultsDisplayedBasedOnContentType("Doutrina");
					softas.assertTrue(searchResultsDisplayed, jiraNumber + freewordVal);
					logExtentStatus(extentLogger, searchResultsDisplayed,
							"Search results are displayed based on content type", freewordKey, freewordVal, jiraNumber);
					searchResultsPage.clickOnSpecificContentTypeOnLeftPage("Doutrina");
					boolean searchTextIsHighlighted = searchResultsPage.isSearchStringHighlighted(freewordVal);

					softas.assertTrue(searchTextIsHighlighted, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchTextIsHighlighted, "search text is highlighted on the results",
							freewordKey, freewordVal, jiraNumber);

				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
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

	@Test(groups ={"search"}, description = "MAFQABANG-281")
	public void uncheckViewContextInSearch() throws Exception {

		boolean stringhighlighted = false;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "search", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		try {
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify search with check box view context in search result page is unchecked");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				homepage.openHomepage();
				homepage.clickOnClear();

				homepage.clickViewContextOnSearchResult();
				homepage.enterGivenFreeword(freewordVal);
				searchResultsPage = homepage.clickOnSearch();

				stringhighlighted = searchResultsPage.isSearchStringHighlighted(freewordVal);
				if (!stringhighlighted) {
					softas.assertTrue(!stringhighlighted, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, !stringhighlighted, issueSummary, freewordKey, freewordVal,
							jiraNumber);

				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
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
