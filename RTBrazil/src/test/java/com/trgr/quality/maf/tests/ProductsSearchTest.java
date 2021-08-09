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
import com.trgr.quality.maf.pages.ProductsPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class ProductsSearchTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	ProductsPage productspage;
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

	
	@Test(groups = {"products"}, description = "MAFQABANG-295")
	public void searchProductsUsingFreeword() throws Exception {
		ProductsPage productsPage;
		boolean searchResultsDisplayed = false;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "ProductsTest", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify search using freeword on Products page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				homepage.openHomepage();
				productsPage = homepage.openProductsPage();
				productsPage.clickProdutoEssenciaistab();
				productsPage.enterGivenFreeword(freewordVal);
				searchResultsPage = productsPage.clickOnSearch();

				if (!(homepage.isErrorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed();

					softas.assertTrue(searchResultsDisplayed, jiraNumber + freewordVal);
					logExtentStatus(extentLogger, searchResultsDisplayed, "Search using freeword on Products Page",
							freewordKey, freewordVal, jiraNumber);

					boolean isNavigationBarDisplayed = searchResultsPage.DocumentCountDisplayed();
					softas.assertTrue(isNavigationBarDisplayed, jiraNumber + freewordVal);
					logExtentStatus(extentLogger, isNavigationBarDisplayed,
							"Verify Result is showing total document count on the result list", freewordKey,
							freewordVal, jiraNumber);

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

	
									
	@Test(groups = {"products"}, description = "MAFQABANG-294")
	public void searchProductTabLegislation() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();

		extentLogger = setUpExtentTest(extentLogger, "ProductsTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		try {
			String issueSummary = getIssueTitle(jiraNumber, "Verify search using freeword on the Products page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				homepage.openHomepage();
				productspage = homepage.openProductsPage();
				productspage.clicklegislacaotab();

				productspage.enterGivenFreeword(freewordVal);
				searchResultsPage = productspage.clickOnSearch();
				if (searchResultsPage != null) {
					boolean searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed();
					if (searchResultsDisplayed) {
						softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
						logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, freewordKey, freewordVal,
								jiraNumber);
					} else {
						softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
						logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
								freewordVal + " -resulted in no search results", jiraNumber);

					}

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

	
	@Test(groups = {"products"}, description = "MAFQABANG-296")
	public void searchProductTabPracticalSolutions() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "ProductsTest", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, "");
			issueSummary = "Verify search using freeword in the Product page when Practical solutions tab is highlighted";

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				homepage.openHomepage();
				productspage = homepage.openProductsPage();
				productspage.clickProdutosSolucoestab();

				productspage.enterGivenFreeword(freewordVal);
				searchResultsPage = productspage.clickOnSearch();
				if (searchResultsPage != null) {
					boolean searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed();
					if (searchResultsDisplayed) {
						softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
						logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, freewordKey, freewordVal,
								jiraNumber);
					} else {
						softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
						logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
								freewordVal + " -resulted in no search results", jiraNumber);

					}

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
