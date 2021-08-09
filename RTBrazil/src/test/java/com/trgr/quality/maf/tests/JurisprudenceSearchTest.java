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
import com.trgr.quality.maf.pages.JurisprudencePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class JurisprudenceSearchTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	JurisprudencePage jurisprudencePage;
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
			extentLogger = setUpExtentTest(extentLogger, "Jurisprudence Test", "Start Search Test");
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

	// Search using Relator and index
	@Test(groups = {"jurisprudence" }, description = "MAFQABANG-288")
	public void searchJurisprudenceUsingIndexAndRelator() throws Exception {
		boolean searchResultsDisplayed = false;

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "JurisprudenceTest", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		try {
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify search jurisprudence using relator  and Index field");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String relatorKey = "relator";
				String relatorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, relatorKey, extentLogger);
				String indexKey = "index";
				String indexVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, indexKey, extentLogger);
				String combinedkey = relatorKey + " and " + indexKey;
				String combinedVal = relatorVal + " and " + indexVal;

				homepage.openHomepage();
				jurisprudencePage = homepage.OpenJurisprudencePage();

				jurisprudencePage.enterRelatorValue(relatorVal);
				jurisprudencePage.enterIndexSearch(indexVal);
				searchResultsPage = jurisprudencePage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(relatorVal)
							&& searchResultsPage.searchReturnedResultsAsExpected(indexVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, combinedkey, combinedVal,
							jiraNumber);

				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummary, combinedkey,
							combinedVal + " -resulted in no search results", jiraNumber);

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

	// searchUsingNumberJurisprudencePage
	@Test(groups = { "jurisprudence" }, description = "MAFQABANG-289")
	public void searchJurisprudenceUsingNumber() throws Exception {

		boolean searchResultsDisplayed = false;

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "JurisprudenceTest", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify search using standard number on Jurisprudence page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String numberKey = "number";
				String numberVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, numberKey, extentLogger);

				homepage.openHomepage();
				jurisprudencePage = homepage.OpenJurisprudencePage();

				jurisprudencePage.enterNumberValue(numberVal);
				searchResultsPage = jurisprudencePage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(numberVal);

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

	@Test(groups = { "jurisprudence" }, description = "MAFQABANG-290")
	public void searchJurisprudenceForReturningNoResults() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "JurisprudenceTest", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify no search results message on  Jursiprudence Page");

			     homepage.openHomepage();
				jurisprudencePage = homepage.OpenJurisprudencePage();
				jurisprudencePage.enterGivenFreeword("ttpy");

				searchResultsPage = jurisprudencePage.clickOnSearch();

				boolean noResultsMsgDisplayed = searchResultsPage.noSearchResultsDisplayed();

				softas.assertTrue(noResultsMsgDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, noResultsMsgDisplayed, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(groups = { "jurisprudence" }, description = "MAFQABANG-278")
	public void SearchUsingIndexOnJurisprudenceForAutoSuggest() throws Exception {
		boolean searchResultsDisplayed = false;
		JurisprudencePage jurisprudence;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "JurisprudenceTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify search using index field and check auto suggest feature");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String indexsearchKey = "indexsearch";
				String indexsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, indexsearchKey,
						extentLogger);

				homepage.openHomepage();
				jurisprudence = homepage.OpenJurisprudencePage();

				jurisprudence.enterIndexSearch(indexsearchVal);

				boolean suggestionsDisplayed = jurisprudence.isTheSuggestionsDropdownDisplayed()
						&& jurisprudence.isSearchStringhighlightedOnCombo(indexsearchVal);

				softas.assertTrue(suggestionsDisplayed,
						jiraNumber + indexsearchVal + "-Auto suggest dropdown displayed");
				logExtentStatus(extentLogger, suggestionsDisplayed, issueSummary, indexsearchKey, indexsearchVal,
						jiraNumber);

				jurisprudence.ScrollToGivenSearchString(indexsearchVal);

				searchResultsPage = jurisprudence.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed();
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, "Verified Thematic search results",
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

	@Test(groups = { "jurisprudence" }, description = "MAFQABANG-345")
	public void showAndhideTermsOnResultSet() throws Exception {
		boolean searchResultsDisplayed;

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "JurisprudenceTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify show and hide terms on the search results");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				homepage.openHomepage();
				jurisprudencePage = homepage.OpenJurisprudencePage();

				jurisprudencePage.enterGivenFreeword(freewordVal);
				searchResultsPage = jurisprudencePage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(freewordVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + freewordVal);
					logExtentStatus(extentLogger, searchResultsDisplayed, "Search returned results as expected",
							freewordKey, freewordVal, jiraNumber);

					// By default terms are displayed. Clicking here means to
					// hide
					// terms
					searchResultsPage.clickOnShowHideTerms();
					boolean isShowHideTermsWorking = searchResultsPage.isShowHideTermsDisplayed();
					softas.assertTrue(!isShowHideTermsWorking, jiraNumber + freewordVal);
					logExtentStatus(extentLogger, !isShowHideTermsWorking, "Hide Terms option is working as expected",
							freewordKey, freewordVal, jiraNumber);

					// click for showing the terms
					searchResultsPage.clickOnShowHideTerms();
					isShowHideTermsWorking = searchResultsPage.isShowHideTermsDisplayed();
					softas.assertTrue(isShowHideTermsWorking, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, isShowHideTermsWorking, "Show terms option is working as expected",
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

}
