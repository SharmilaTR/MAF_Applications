package com.trgr.quality.maf.tests;

import java.util.List;

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
import com.trgr.quality.maf.pages.DoctrineSearchPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class DoctrineSearchTest extends BaseTest {
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
			extentLogger = setUpExtentTest(extentLogger, "Doctrine Test", "Start Search Test");
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

	@Test(groups = { "doctrine" }, description = "MAFQABANG-341")
	public void newAndmodifysearchLinksOnSearchResultsPage() throws Exception {
		boolean searchResultsDisplayed = false;
		DoctrineSearchPage doctrinePage;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "DoctrineTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify New and Modify search links on the results page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				homepage.openHomepage();
				doctrinePage = homepage.OpenDoctrinePage();

				doctrinePage.enterGivenFreeword(freewordVal);
				searchResultsPage = doctrinePage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(freewordVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + freewordVal);
					logExtentStatus(extentLogger, searchResultsDisplayed, "Search returned results as expected",
							freewordKey, freewordVal, jiraNumber);

					homepage = searchResultsPage.ClickOnModifySearchLink();

					searchResultsDisplayed = homepage.isHomePageDisplayed()
							&& homepage.isModifySearchRetainsSearchStrings();

					softas.assertTrue(searchResultsDisplayed, jiraNumber + freewordVal);
					logExtentStatus(extentLogger, searchResultsDisplayed,
							"Clicking edit search is retaining the prior search data", freewordKey, freewordVal,
							jiraNumber);

					searchResultsPage = homepage.clickOnSearch();

					homepage = searchResultsPage.ClickOnNewSearchLink();
					searchResultsDisplayed = homepage.isHomePageDisplayed() && homepage.isQuickSearchFieldsReset();
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed,
							"Search fields are reset upon clicking new search link", freewordKey, freewordVal,
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

	@Test(groups = { "doctrine" }, description = "MAFQABANG-344")
	public void docNavigationUsingFreewordAndTitleOnDoctrine() throws Exception {
		boolean searchResultsDisplayed = false;
		DoctrineSearchPage doctrineSearchpage;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "DoctrineTest", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		try {

			String issueSummary = getIssueTitle(jiraNumber,
					"Verify document navigation links using search on the Doctrine page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				String titleKey = "title";
				String titleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, titleKey, extentLogger);

				homepage.openHomepage();
				doctrineSearchpage = homepage.OpenDoctrinePage();

				doctrineSearchpage.enterGivenFreeword(freewordVal);
				doctrineSearchpage.enterGivenTitle(titleVal);

				searchResultsPage = doctrineSearchpage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(freewordVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + freewordVal);
					logExtentStatus(extentLogger, searchResultsDisplayed,
							"Verified search results for  doctrine search using title", freewordKey, freewordVal,
							jiraNumber);

					boolean isDocNavigationDisplayed = searchResultsPage.isDocNavigationDisplayed();

					softas.assertTrue(isDocNavigationDisplayed, jiraNumber + freewordVal);
					logExtentStatus(extentLogger, isDocNavigationDisplayed, "Document navigation options displayed",
							freewordKey, freewordVal, jiraNumber);
					String totalDocCount = searchResultsPage.getDocCountFromResultSet();
					if (totalDocCount.contains("4000")) {
						boolean infoMessageFor4Kdocs = searchResultsPage.infoMessageForLargeNumberInResultSet();
						softas.assertTrue(infoMessageFor4Kdocs, jiraNumber + freewordVal);
						logExtentStatus(extentLogger, infoMessageFor4Kdocs,
								"Info to narrow down search results is displayed when documents > 4000 are returned",
								freewordKey, freewordVal, jiraNumber);
					}

					if (isDocNavigationDisplayed) {
						List<String> fstDocOnFstPage = searchResultsPage.getDocumentTitleFromSearchResultsList(1);
						// click on next link
						searchResultsPage.clickOnNextPageWithDocNavBar();

						List<String> fstDocOnSndPage = searchResultsPage.getDocumentTitleFromSearchResultsList(1);
						boolean nextPageOptionOnDocNav = !fstDocOnFstPage.get(0).contains(fstDocOnSndPage.get(0));
						softas.assertTrue(nextPageOptionOnDocNav, jiraNumber + freewordVal);
						logExtentStatus(extentLogger, nextPageOptionOnDocNav,
								"Clicking NEXT on document navigation moving to next page", freewordKey, freewordVal,
								jiraNumber);

						searchResultsPage.clickOnPreviousPageWithDocNavBar();
						fstDocOnSndPage = searchResultsPage.getDocumentTitleFromSearchResultsList(1);
						boolean previousPageOptionOnDocNav = fstDocOnFstPage.get(0).contains(fstDocOnSndPage.get(0));
						softas.assertTrue(previousPageOptionOnDocNav, jiraNumber + freewordKey);
						logExtentStatus(extentLogger, previousPageOptionOnDocNav,
								"Clicking PREVIOUS on document navigation moving to previous page", freewordKey,
								freewordVal, jiraNumber);

						searchResultsPage.clickOnLastPageWithDocNavBar();
						fstDocOnSndPage = searchResultsPage.getDocumentTitleFromSearchResultsList(1);
						boolean lastPageOnDocNav = !fstDocOnFstPage.get(0).contains(fstDocOnSndPage.get(0))
								&& !searchResultsPage.isLastPageOptionDisplayed();

						softas.assertTrue(lastPageOnDocNav, jiraNumber + ":" + issueSummary);
						logExtentStatus(extentLogger, lastPageOnDocNav,
								"Clicking LAST on document navigation moving to last page", freewordKey, freewordVal,
								jiraNumber);

					}
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

	// Verify search Doctrine using valid and invalid title
	@Test(groups = { "doctrine" }, description = "MAFQABANG-285")
	public void searchDoctrineUsingTitle() throws Exception {
		boolean searchResultsDisplayed = false;
		DoctrineSearchPage doctrinePage;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "DoctrineTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify search Doctrine using title field");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String titleKey = "title";
				String titleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, titleKey, extentLogger);

				homepage.openHomepage();
				doctrinePage = homepage.OpenDoctrinePage();

				doctrinePage.enterTitle(titleVal);

				searchResultsPage = doctrinePage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(titleVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, titleKey, titleVal, jiraNumber);

					searchResultsDisplayed = searchResultsPage.isClassificationSectionDisplayedWithInfo();
					softas.assertTrue(searchResultsDisplayed,
							jiraNumber + ":" + "Classification Section displayed on the result set");
					logExtentStatus(extentLogger, searchResultsDisplayed,
							"Classification Section displayed on the result set", titleKey, titleVal, jiraNumber);

				} else {
					issueSummary = getIssueTitle("MAFQABANG-287", "Verify search Doctrine using invalid title field");
					softas.assertTrue(true, " MAFQABANG-287:" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummary, titleKey,
							titleVal + " -resulted in no search results", " MAFQABANG-287");

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

	@Test(groups = { "doctrine" }, description = "MAFQABANG-286")
	public void searchDoctrineUsingAuthor() throws Exception {
		boolean searchResultsDisplayed = false;
		DoctrineSearchPage doctrinePage;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "DoctrineTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify search using author on the Doctrine page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String authorKey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, authorKey, extentLogger);
				
				homepage.openHomepage();
				doctrinePage = homepage.OpenDoctrinePage();
				doctrinePage.enterAuthor(authorVal);
				searchResultsPage = doctrinePage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(authorVal);

					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, authorKey, authorVal,
							jiraNumber);

				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, issueSummary, authorKey,
							authorVal + " -resulted in no search results", jiraNumber);

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
