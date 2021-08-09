package com.trgr.quality.maf.tests;

import org.json.simple.parser.JSONParser;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.JiraConnector;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;

public class LoginTest extends BaseTest {


	/*
	 * Login_TC_001 Feature : Login_Verify SignIn Description : Verify Signin
	 * for Login Page
	 * 
	 * Login_TC_002 Feature : Login_Verify Invalid Login Description : Verify
	 * Invalid Signin for Login Page
	 */
	
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JSONParser parser = new JSONParser();
	

	@Test(priority = 0, groups = { "login"})
	public void InvalidLoginAndVerifyValidLoginIn() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Login Test", testResult.getMethod().getMethodName());
		String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".username");
		String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".password");
	
		try {

			LoginPage loginpage = new LoginPage(driver, ProductUrl);
			boolean isOnePassEnabled = loginpage.isOnePassSignOnEnbaled();
			if (isOnePassEnabled) {

					loginpage.Login(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".onepassusername")+ "test",
									PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".onepasspassword"),
									isOnePassEnabled);
					boolean flag = loginpage.invalidCredentialsMsgDisplayed(isOnePassEnabled);
					
					softas.assertTrue(flag, "MAFQABANG-367 : OnePass - Validated Invalid Login for the Login page");
					logExtentStatus(extentLogger, flag, "OnePass - Validated Invalid Login and Error Message for the Login page" , "MAFQABANG-367");
					
					HomePage homepage = loginpage.Login(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".onepassusername"),
														PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".onepasspassword"),
														isOnePassEnabled);
					flag = homepage.isHomePageDisplayed();
				
					logExtentStatus(extentLogger, flag, "OnePass - Logged into Home Page with valid credentials" , "MAFQABANG-366");
					softas.assertTrue(flag, "MAFQABANG-366 : Logged into Home Page with valid credentials");
					homepage.clickSignOff();
				
				}
			else {
						
					loginpage.Login(username + "test", password,
							isOnePassEnabled);
					 boolean flag=	loginpage.invalidCredentialsMsgDisplayed(isOnePassEnabled);
			
				
					logExtentStatus(extentLogger, flag, "OnePass - Logged into Home Page with valid credentials" , "MAFQABANG-361");
					softas.assertTrue(flag, "MAFQABANG-361 : Validated Invalid Login for the Login page");
				
					HomePage homepage = loginpage.Login(username,password,isOnePassEnabled);
					flag = homepage.isHomePageDisplayed();
				
					logExtentStatus(extentLogger, flag, "SignIn - Logged into Home Page with valid credentials" , "MAFQABANG-360");
					softas.assertTrue(flag, "MAFQABANG-360 : Logged into Home Page with valid credentials");
					homepage.clickSignOff();
					loginpage.loadLoginPage(ProductUrl);
					flag = loginpage.isUsernameAndPasswordSaved("", "");
						
					logExtentStatus(extentLogger, flag, "User Logged in with Checkbox Option Disabled" , new String[]{"MAFQABANG-369", "MAFQABANG-370"});
					softas.assertTrue(flag, "MAFQABANG-369, MAFQABANG-370 : User Logged in with Checkbox Option Disabled");
		}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.FAIL, "Something went wrong. Exiting test.<br>" + takesScreenshot_Embedded()
					+ "<br>" + displayErrorMessage(exc));
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * Login_TC_004 Feature : Login_Verify save user name and password when
	 * checkbox is checked Discription : Verify save user name and password for
	 * Login page when checkbox is marked
	 *
	 */
	@Test(priority = 1, groups = { "login"})
	public void loginUsingSaveUserNameAndPwdCheckBox() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Login Test", testResult.getMethod().getMethodName());
		String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".username");
		String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".password");
		try {

			LoginPage loginpage = new LoginPage(driver, ProductUrl);
			boolean isOnePassEnabled = loginpage.isOnePassSignOnEnbaled();
			if (isOnePassEnabled) {

				loginpage.SelectSaveUsernameAndPassword(isOnePassEnabled);
				logExtentStatus(extentLogger, true, "User Logged in with Checkbox Option Enabled" , "MAFQABANG-405");
				
				HomePage homepage = loginpage.Login(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".onepassusername"),
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".onepasspassword"),
						isOnePassEnabled);
				boolean flag = homepage.isHomePageDisplayed();
				
					logExtentStatus(extentLogger, flag, "Logged into Home Page with valid credentials" , "MAFQABANG-405");
					softas.assertTrue(flag, "Logged into Home Page with valid credentials");
				
				homepage.clickSignOff();
				loginpage.loadLoginPage(ProductUrl);
				boolean saveddetailsflag = loginpage.isUsernameAndPasswordSaved(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username"),
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password"));
				
				logExtentStatus(extentLogger, saveddetailsflag, "Username and password details are saved and available on the Login Screen" , "MAFQABANG-405");
				softas.assertTrue(saveddetailsflag,"MAFQABANG-405:Username and password details are saved and available on the Login Screen");
				

			} else {
				
					boolean isOnePassEnabled_flag = loginpage.SelectSaveUsernameAndPassword(isOnePassEnabled);
					softas.assertTrue(isOnePassEnabled_flag, "MAFQABANG-371 : Username and password details are not saved on the Login Screen");
					logExtentStatus(extentLogger, isOnePassEnabled_flag, "User Logged in with Checkbox Option Enabled" , "MAFQABANG-371");

					HomePage homepage = loginpage.Login(username,password,isOnePassEnabled);
					boolean flag = homepage.isHomePageDisplayed();
					
					logExtentStatus(extentLogger, flag, "Logged into Home Page with valid credentials" , "MAFQABANG-371");
					softas.assertTrue(flag, "MAFQABANG-371 : Logged into Home Page with valid credentials");
					
					homepage.clickSignOff();
					loginpage.loadLoginPage(ProductUrl);
					
					boolean isUsernameAndPasswordSaved_flag = loginpage.isUsernameAndPasswordSaved(username,password);
										
						logExtentStatus(extentLogger, isUsernameAndPasswordSaved_flag, "Username and password details are saved and available on the Login Screen" , "MAFQABANG-371");
						softas.assertTrue(isUsernameAndPasswordSaved_flag,
								"MAFQABANG-371 : Username and password details are saved and available on the Login Screen");
					

				}
			

		} catch (Exception exc) {
			extentLogger.log(LogStatus.FAIL, "Something went wrong. Exiting test.<br>" + takesScreenshot_Embedded()
					+ "<br>" + displayErrorMessage(exc));
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 2, groups = { "login"})
	public void LoginwithCustomerid() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Login Test", testResult.getMethod().getMethodName());
		String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".username");
		String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".password");
		
		try {

			LoginPage loginpage = new LoginPage(driver, ProductUrl);
			boolean isOnePassEnabled = loginpage.isOnePassSignOnEnbaled();
			if (isOnePassEnabled) {
				
				softas.assertTrue(isOnePassEnabled, "MAFQABANG-406 : OnePass - NA for LoginwithCustomerid");
				logExtentStatus(extentLogger, isOnePassEnabled, "OnePass - NA for LoginwithCustomerid" , "MAFQABANG-406");
				
			} else {
				
					loginpage.Selectcustomerid();
					HomePage homepage = loginpage.Login(username, password, isOnePassEnabled);
					boolean flag = homepage.isHomePageDisplayed();
					
						logExtentStatus(extentLogger, flag, "SignIn - Logged into Home Page with valid customerid" , "MAFQABANG-364");
						softas.assertTrue(flag, "MAFQABANG-364 : SignIn - Logged into Home Page with valid customerid");
						homepage.clickSignOff();
			}
		} catch (Exception exc) {

			extentLogger.log(LogStatus.FAIL, "Something went wrong. Exiting test.<br>" + takesScreenshot_Embedded()
					+ "<br>" + displayErrorMessage(exc));
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 3, groups = { "login"})
	public void Loginlinksverification() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Login Test", testResult.getMethod().getMethodName());
		try {
			LoginPage loginpage = new LoginPage(driver, ProductUrl);
			boolean isOnePassEnabled = loginpage.isOnePassSignOnEnbaled();
			if (isOnePassEnabled) {

				
				boolean flag = loginpage.validateonepassforgotusername() && loginpage.validateonepassforgotpassword()
				&& loginpage.validateonepasscreateprofile() && loginpage.validateonepassupdateprofile()
				&& loginpage.validateonepassrtonlinelink();
				
				logExtentStatus(extentLogger, flag, "Onepass - Loginlinksverification" , new String [] {"MAFQABANG-372", "MAFQABANG-373", "MAFQABANG-362", "MAFQABANG-363"});
				softas.assertTrue(flag, "MAFQABANG-372:MAFQABANG-373:MAFQABANG-362:MAFQABANG-363 : Onepass - Loginlinksverification");
					
				

			} else {
				
				boolean flag = loginpage.validateforgotmypassword() && loginpage.validatechangemypassword()
				&& loginpage.validateonepassaccount();
				
				logExtentStatus(extentLogger, flag, "SignIn - Loginlinksverification" , new String [] {"MAFQABANG-365", "MAFQABANG-374", "MAFQABANG-408"});
				softas.assertTrue(flag, "MAFQABANG-365 : MAFQABANG-374 : MAFQABANG-408 : SignIn - Loginlinksverification");
				

			}
		} catch (Exception exc) {

			extentLogger.log(LogStatus.FAIL, "Something went wrong. Exiting test.<br>" + takesScreenshot_Embedded()
					+ "<br>" + displayErrorMessage(exc));
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 4, groups = { "login"})
	public void loginUsingSaveUserNameCheckBox() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Login Test", testResult.getMethod().getMethodName());
		String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".username");
		String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".password");
		
		try {

			LoginPage loginpage = new LoginPage(driver, ProductUrl);
			boolean isOnePassEnabled = loginpage.isOnePassSignOnEnbaled();
			if (isOnePassEnabled) {

				loginpage.SelectSaveUsername(isOnePassEnabled);
				
				softas.assertTrue(isOnePassEnabled, "MAFQABANG-368 : User Logged in with Checkbox Option Enabled");
				logExtentStatus(extentLogger, isOnePassEnabled, "User Logged in with Checkbox Option Enabled" , "MAFQABANG-368");

				HomePage homepage = loginpage.Login(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".onepassusername"),
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".onepasspassword"),
						isOnePassEnabled);
				
				if (homepage.isHomePageDisplayed()) {
			
					logExtentStatus(extentLogger, homepage.isHomePageDisplayed(), "Logged into Home Page with valid credentials" , "MAFQABANG-368");
					softas.assertTrue(homepage.isHomePageDisplayed(), "MAFQABANG-368 : Logged into Home Page with valid credentials");
					
					homepage.clickSignOff();
					loginpage.loadLoginPage(ProductUrl);
					
					boolean flag = loginpage.isUsernameSaved(username, password);
					
					
						logExtentStatus(extentLogger, flag, "Username and password details are saved and available on the Login Screen" , "MAFQABANG-368");
						softas.assertTrue(flag,"MAFQABANG-368 : Username and password details are saved and available on the Login Screen");
					
						
				} else {
					
					logExtentStatus(extentLogger, false, "Login Failed" , "MAFQABANG-368");					
					softas.assertTrue(false, "MAFQABANG-368 : Login Failed");
				}

			} else {
				
				softas.assertTrue(true, "MAFQABANG-368 : OnePass - NA for loginUsingSaveUserNameCheckBox");
				logExtentStatus(extentLogger, true, "OnePass - NA for loginUsingSaveUserNameCheckBox" , "MAFQABANG-368");
				
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.FAIL, "Something went wrong. Exiting test.<br>" + takesScreenshot_Embedded()
					+ "<br>" + displayErrorMessage(exc));
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
}
