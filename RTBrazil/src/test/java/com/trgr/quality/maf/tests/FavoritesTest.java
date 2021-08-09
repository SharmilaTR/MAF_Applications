package com.trgr.quality.maf.tests;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.JiraConnector;
import com.trgr.quality.maf.commonutils.RandomUtils;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.FavoritesPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class FavoritesTest extends BaseTest {

	/*
	 * Module Name : Favorites Feature Names : Description Favorites_TC_001 :
	 * VerifyFavorites Link Page Favorites_TC_002 : Verify_Favorites Create
	 * Favorites_TC_003 : Verify_Favorites _Rename_delete My Documents
	 */
	HomePage homepage, homepagecopy;
	LoginPage loginpage;
	FavoritesPage favpage;
	SearchResultsPage searchresultpage;
	DocumentDisplayPage Docdisplaypage;
	JSONObject jsonObject;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)

	public void beforeClass() throws IllegalArgumentException, IOException {
		try {

			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".password");
			jsonReader = new JsonReader();
			homepage = loginpage.Login(username, password);
			homepagecopy = this.homepage;
			homepage.refresh();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Favorites Test", "Start Favorites Test");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the Favorites test are not run.<br>"
							+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
		homepagecopy.clickSignOff();

	}

	/*
	 * Favorites_TC_002 : Verify_Add to Favorites This test has to be run first
	 */
	@Test(priority = 0, groups = { "favorites" }, description = "MAFQABANG-350")
	public void addDocumentToFavorites() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Favorites Test", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Add document to favorites");

		try {

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				String Expname = "expname";
				String expname = jsonObjectChild.get(Expname).toString() + "_" + RandomUtils.getUniqueNumber();

				homepage.enterGivenFreeword(freewordVal);
				searchresultpage = homepage.clickOnSearch();

				searchresultpage.clickOnAddToFav(expname);
				homepage.clickonServicesLink();
				favpage = homepage.clickFavoriteLink();
				boolean flag = favpage.isFolderDisplayed(expname);

				softas.assertTrue(flag, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, flag, issueSummary, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * My Documents_TC_001 : Verify_Favorites Link Page
	 */
	@Test(priority = 1, groups = { "favorites" }, description = "MAFQABANG-334")
	public void checkFavoritesLink() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Favorites Test", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify favorites links is working");

		try {
			homepage.clickonServicesLink();
			favpage = homepage.clickFavoriteLink();
			boolean flag = favpage.isFavoritesTextDisplayed();
			softas.assertTrue(flag, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, flag, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	/*
	 * My Documents_TC_003 : Verify_Favorites Rename Folder
	 */
	@Test(priority = 2, groups = { "favorites" }, description = "MAFQABANG-333")
	public void renameFolder() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Favorites Test", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify rename of folder is working");
		try {
			homepage.clickonServicesLink();
			favpage = homepage.clickFavoriteLink();
			String folderName = favpage.selectFirstFolderAndReturnFolderName();
			folderName = favpage.renameGivenFolderAndReturnFolderName(folderName);
			boolean flag = favpage.isFolderDisplayed(folderName);
			softas.assertTrue(flag, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, flag, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * My Documents_TC_003 : Verify_Favorites Delete Folder
	 */
	@Test(priority = 3, groups = { "favorites" }, description = "MAFQABANG-333")
	public void deleteFolder() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Favorites Test", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify delete of folder is working");

		try {
			homepage.clickonServicesLink();
			favpage = homepage.clickFavoriteLink();
			String deletefoldername = favpage.selectFirstFolderAndReturnFolderName();
			favpage.deleteGivenFolder(deletefoldername);
			boolean flag = favpage.isGivenFolderDeleted(deletefoldername);
			
			//check for the false flag as the isGivenFolderDeleted returns True if the folder found
			softas.assertFalse(flag, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, !flag, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
}
