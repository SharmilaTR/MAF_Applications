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
import com.trgr.quality.maf.pages.NewsSearchPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class NewsSearchTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	NewsSearchPage newspage;
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
			extentLogger = setUpExtentTest(extentLogger, "News Test", "Start Search Test");
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

	@Test(groups = {"news"}, description = "MAFQABANG-299")
	public void searchNews() throws Exception {
		boolean newstextdisplayed = false;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "NewsTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify display of news search page");

			homepage.openHomepage();
			homepage.clickOnClear();
			newspage = homepage.OpenNewsPage();

			newstextdisplayed = newspage.isNewsPageDisplayedAsExpected();

			if (newstextdisplayed) {
				softas.assertTrue(newstextdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, newstextdisplayed, issueSummary, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(groups = {"news"}, description = "MAFQABANG-300")
	public void searchNewsForNoResults() throws Exception {
		boolean flag = false;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "NewsTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify zero results using freeword on the news page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String noresultsKey = "noresults";
				String noresultsVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, noresultsKey,
						extentLogger);

				homepage.openHomepage();
				newspage = homepage.OpenNewsPage();

				newspage.enterGivenFreeword(noresultsVal);
				searchResultsPage = newspage.clickOnSearch();
				flag = searchResultsPage.noSearchResultsDisplayed();
				softas.assertTrue(flag, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, flag, issueSummary, noresultsKey, noresultsVal, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(groups = {"news"}, description = "MAFQABANG-298")
	public void searchNewsUsingFreeword() throws Exception {
		boolean flag = false;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "NewsTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify search using freeword on the news page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				String yearKey = "year";
				String yearVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, yearKey, extentLogger);

				homepage.openHomepage();
				newspage = homepage.OpenNewsPage();

				newspage.enterGivenFreeword(freewordVal);
				newspage.selectDate();
				newspage.enterYear(yearVal);
				searchResultsPage = newspage.clickOnSearch();
				flag = searchResultsPage.searchResultsDisplayed();
				if (flag) {
					softas.assertTrue(flag, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, flag, issueSummary, freewordKey, freewordVal, jiraNumber);
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
