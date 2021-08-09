package com.trgr.quality.maf.tests;

import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
//import com.gargoylesoftware.htmlunit.javascript.host.Set;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.JiraConnector;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SignOffPage;

public class SignoffTest extends BaseTest {

	/* Help_TC_001-Help Link_Verify Help Link 
	 * MAFAUTO-221
	 */
	HomePage homepage,homepagecopy;
	LoginPage loginpage;
	SignOffPage signoffpage;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JSONParser parser = new JSONParser();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
	try{
		
		loginpage = new LoginPage(driver, ProductUrl);
		String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".username");
		String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".password");
		homepage = loginpage.Login(username, password);
		homepage.refresh();
		
	 }catch(Exception exc){
			
		  extentLogger = setUpExtentTest(extentLogger, "SignOff Test", "Start SignOff Test");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the Signoff test are not run.<br>"+ takesScreenshot_Embedded()+ "<br>"+displayErrorMessage(exc));
			extentReports.endTest(extentLogger);			
			Assert.assertTrue(false);
		}
	}

	
	
	
	@Test(priority=0, groups={"signoff"}, description = "MAFQABANG-402")
	public void signoffSummaryAndNewSessionLink() throws  IOException {
		try{
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();	
			extentLogger = setUpExtentTest(extentLogger, "SignOff Test", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Delivery options from history page"); 
			
			
			// This verifies whether the signoff link present in homepage or not
			boolean signOffLinkPresent = homepage.isSignOffLinkDisplayed();
			softas.assertTrue(signOffLinkPresent, jiraNumber + ":Signoff Link available");
			logExtentStatus(extentLogger, signOffLinkPresent, "Signoff Link is displayed", jiraNumber);
			if (signOffLinkPresent)
				signoffpage = homepage.clickSignOff();
				
					
			/*This verifies whether the sign off summary, sign off keyword and New session link 
			are present in the sign off Page or not.*/
			
			boolean signOffPageDisplayed = signoffpage.issignoffpagedisplay()
					&& signoffpage.validateSignoffSummary()
					&& signoffpage.isNewsesssionlinkPresent();
			
			softas.assertTrue(signOffPageDisplayed, jiraNumber + ":Verify signoff Page");
			logExtentStatus(extentLogger, signOffPageDisplayed, "SignOff summary page is verified", jiraNumber);
			
			//This verifies whether the New Session link in available  or not if available then click on it
			
	        boolean isnewsessionlinkpresent = signoffpage.isNewsesssionlinkPresent();
	      
			softas.assertTrue(isnewsessionlinkpresent, jiraNumber + ":Verify New Session Link");
			logExtentStatus(extentLogger, isnewsessionlinkpresent, "Start new session link is displayed on SignOff Summary page", jiraNumber);
			
			signoffpage.clikNewSession();
	
			//This verifies whether a new loginpage displayed or not once the new session link is clicked
			boolean loginPageDisplayed =loginpage.isLoginPageDisplayed(); 
			softas.assertTrue(loginPageDisplayed, jiraNumber + ":New session login page is displayed");
			logExtentStatus(extentLogger, loginPageDisplayed, "New session login page is displayed", jiraNumber);
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
}
