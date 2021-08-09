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
import com.trgr.quality.maf.pages.DeliveryPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;



public class DeliveryTest extends BaseTest {
	LoginPage loginpage;
	HomePage  homepage,homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	DeliveryPage deliveryPage;
	JsonReader fileReader;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JsonReader jsonReader;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception
	{
		try{
			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + "." + Envirnomentundertest + ".password");
			jsonReader = new JsonReader();
			homepage=loginpage.Login(username, password);
			homepagecopy = this.homepage;
			homepage.refresh();
		
		}catch(Exception exc){
			extentLogger = setUpExtentTest(extentLogger, "Delivery Test", "StartDeliveryTest");
			
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the DeliveryTest are not run.<br>"+ takesScreenshot_Embedded()+ "<br>"+displayErrorMessage(exc));
			extentReports.endTest(extentLogger);			
			Assert.assertTrue(false);
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception
	{
		homepagecopy.clickSignOff();	
	}
	
	@Test(groups={"delivery"}, description = "MAFQABANG-325")
	public void saveSearchResultsInWordFormat() throws Exception{

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();	
			extentLogger = setUpExtentTest(extentLogger, "Delivery Test", testResult.getMethod().getMethodName());
		  				
			String jiraNumber = testResult.getMethod().getDescription();
	
			String issueSummary = getIssueTitle(jiraNumber,"Verify the document print by selecting the ResultList"); 
				
			try{
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
					
					 JSONObject jsonObjectChild = (JSONObject) searchString;
				
					 String key = "freeword";
					   String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,key,extentLogger);   
						
						homepage.openHomepage();
						homepage.clickOnClear();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						
						boolean deliveryAvailable = searchResultsPage.isDeliveryOptionDisplayed();
						deliveryPage = searchResultsPage.clickDeliveryOptionicon();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						
						softas.assertTrue(deliveryAvailable, jiraNumber + "Delivery Options are Displayed for SearchResults");
						logExtentStatus(extentLogger, deliveryAvailable, "Delivery Options are Displayed for SearchResults" , jiraNumber);
						
						//Save document in RTF - Complete document
						if(deliveryAvailable){
							
							deliveryPage.clickToSelectResultListForDelivery();
							deliveryPage.clickToSelectFullDocumentForDelivery();
							deliveryPage.clickOnRadioOptionForPDF();
							deliveryPage.clickOnRadioOptionForWord();
							deliveryPage.clickOnSubmit();
						}
						
						boolean wordDownloaded = 
								deliveryAvailable &&
								deliveryPage.isConfirmationMsgDisplayedForDelivery();
						
						softas.assertTrue(wordDownloaded, "MAFQABANG-329 : Validate Word Document(complete document) Downloadsuccessfully for SearchResults");
						logExtentStatus(extentLogger, wordDownloaded, "Validate Word Document(complete document) Downloadsuccessfully for SearchResults" , "MAFQABANG-329");
						
						deliveryPage.returnToDocumentUponCompletionOfDelivery();
						deliveryAvailable = searchResultsPage.isDeliveryOptionDisplayed();
						deliveryPage = searchResultsPage.clickDeliveryOptionicon();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						//Save document in RTF - List of document
						if(deliveryAvailable){
							//deliveryPage.saveDocumentInLocallink();
							deliveryPage.clickToSelectFullDocumentForDelivery();
							deliveryPage.clickToSelectResultListForDelivery();
							deliveryPage.clickOnRadioOptionForPDF();
							deliveryPage.clickOnRadioOptionForWord();
							deliveryPage.clickOnSubmit();
						}
						wordDownloaded = 
								deliveryAvailable &&
								deliveryPage.isConfirmationMsgDisplayedForDelivery();
						logExtentStatus(extentLogger, wordDownloaded, "Validate Word Document(List of document) Downloadsuccessfully for SearchResults" , "MAFQABANG-329");
						softas.assertTrue(wordDownloaded, "Validate Word Document(List of documents) Downloadsuccessfully for SearchResults");
				}
				
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
			
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=1,groups={"delivery"}, description = "MAFQABANG-329")
	public void saveSearchResultsInPdfFormat() throws Exception{
		
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();	
			extentLogger = setUpExtentTest(extentLogger, "Delivery Test", testResult.getMethod().getMethodName());
		  				
			String jiraNumber = testResult.getMethod().getDescription();
			
			String issueSummary = getIssueTitle(jiraNumber,"Verify the export document in Document Display"); 
	
			try{
				
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				   String key = "freeword";
				   String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,key,extentLogger);                   
						//Search
						homepage.openHomepage();
						homepage.clickOnClear();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						
						boolean deliveryAvailable = searchResultsPage.isDeliveryOptionDisplayed();
						deliveryPage = searchResultsPage.clickDeliveryOptionicon();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						logExtentStatus(extentLogger, deliveryAvailable, "Delivery Options are Displayed for SearchResults" , new String[]{jiraNumber,"MAFQABANG-352"});
						softas.assertTrue(deliveryAvailable, jiraNumber+",MAFQABANG-352 : Delivery Options are Displayed for SearchResults");
						
						//Save document in PDF - Complete document
						if(deliveryAvailable){
							
							deliveryPage.clickToSelectResultListForDelivery();
							deliveryPage.clickToSelectFullDocumentForDelivery();
							deliveryPage.clickOnRadioOptionForWord();
							deliveryPage.clickOnRadioOptionForPDF();
							deliveryPage.clickOnSubmit();
						}
						boolean pdfDownloaded = 
								deliveryAvailable &&
								deliveryPage.isConfirmationMsgDisplayedForDelivery();
						logExtentStatus(extentLogger, pdfDownloaded, "Validate PDF Document(complete document) Downloadsuccessfully for SearchResults" , jiraNumber);
						softas.assertTrue(pdfDownloaded, jiraNumber+" : Validate PDF Document(complete document) Downloadsuccessfully for SearchResults");
						
						deliveryPage.returnToDocumentUponCompletionOfDelivery();
						deliveryAvailable = searchResultsPage.isDeliveryOptionDisplayed();
						deliveryPage = searchResultsPage.clickDeliveryOptionicon();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						//Save document in PDF - List of document
						if(deliveryAvailable){
							//deliveryPage.saveDocumentInLocallink();
							deliveryPage.clickToSelectFullDocumentForDelivery();
							deliveryPage.clickToSelectResultListForDelivery();
							deliveryPage.clickOnRadioOptionForWord();
							deliveryPage.clickOnRadioOptionForPDF();
							deliveryPage.clickOnSubmit();
						}
						pdfDownloaded = 
								deliveryAvailable &&
								deliveryPage.isConfirmationMsgDisplayedForDelivery();
						softas.assertTrue(pdfDownloaded, jiraNumber+": Validate PDF Document(List of document) Downloadsuccessfully for SearchResults");
						logExtentStatus(extentLogger, pdfDownloaded, "Validate PDF Document(List of document) Downloadsuccessfully for SearchResults" , jiraNumber);

				}
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	/*
	 * This test case covers features for MAFQABANG-318, MAFQABANG-319, MAFQABANG-320 and MAFQABANG-324
	 */
	@Test(priority=2,groups={"delivery"}, description = "MAFQABANG-319")
	public void emailSearchResultsInPDFFormat() throws Exception{
		
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Delivery Test", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify delivery using email of result list and complete document with PDF as format"); 
		
			try{
				
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				   String freewordKey = "freeword";
				   String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);           
					
				   String emailIdKey = "emailid";
				   String emailId = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailIdKey,extentLogger);   
				   
					//Search
					homepage.openHomepage();
					homepage.clickOnClear();
					homepage.enterGivenFreeword(freewordVal);
					searchResultsPage = homepage.clickOnSearch();
					boolean deliveryAvailable = searchResultsPage.isDeliveryOptionThroughEmailisDisplayed();
					deliveryPage = searchResultsPage.clickSendEmailOptionicon();
					deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
					
					softas.assertTrue(deliveryAvailable, "Delivery options are Displayed for Search results");
					logExtentStatus(extentLogger, deliveryAvailable, "Delivery options are Displayed for Search results" , jiraNumber);
					
					//Send PDF - Complete document
					if(deliveryAvailable){
						
						deliveryPage.clickToSelectResultListForDelivery();
						deliveryPage.clickToSelectFullDocumentForDelivery();
						deliveryPage.enterEmailDetails(emailId);
						deliveryPage.clickOnRadioOptionForWord();
						deliveryPage.clickOnRadioOptionForPDF();
						deliveryPage.clickOnSubmit();
					}
						
					boolean pdfEmailSent = 
							deliveryAvailable &&
							deliveryPage.isConfirmationMsgDisplayedForDelivery();
					deliveryPage.returnToDocumentUponCompletionOfDelivery();
					logExtentStatus(extentLogger, pdfEmailSent, "PDF(Complete Document) is Sent through Email" , jiraNumber);
					softas.assertTrue(pdfEmailSent, jiraNumber+": PDF(Complete Document) is Sent through Email");
					
					deliveryAvailable = searchResultsPage.isDeliveryOptionThroughEmailisDisplayed();
					deliveryPage = searchResultsPage.clickSendEmailOptionicon();
					deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
					//Send PDF - List of document
					if(deliveryAvailable){
						
						deliveryPage.clickToSelectFullDocumentForDelivery();
						deliveryPage.clickToSelectResultListForDelivery();
						deliveryPage.enterEmailDetails(emailId);
						deliveryPage.clickOnRadioOptionForWord();
						deliveryPage.clickOnRadioOptionForPDF();
						deliveryPage.clickOnSubmit();
					}
						
					pdfEmailSent = 
							deliveryAvailable &&
							deliveryPage.isConfirmationMsgDisplayedForDelivery();
					deliveryPage.returnToDocumentUponCompletionOfDelivery();
					softas.assertTrue(pdfEmailSent, jiraNumber + ":PDF(List of Document) is Sent through Email");
					logExtentStatus(extentLogger, pdfEmailSent, "PDF(List of Document) is Sent through Email" , jiraNumber);
					
					deliveryAvailable = searchResultsPage.isDeliveryOptionThroughEmailisDisplayed();
					deliveryPage = searchResultsPage.clickSendEmailOptionicon();
					deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
					//Validate Cancel sending mail
					if(deliveryAvailable){
						//deliveryPage.saveDocumentThroughEmaillink();
						deliveryPage.clickCancelOnDeliveryUsingEmail();
					}
					boolean deliveryCancelled = searchResultsPage.isDeliveryOptionThroughEmailisDisplayed();
					softas.assertTrue(deliveryCancelled, jiraNumber + ": Validate Cancel Email is successful");
					logExtentStatus(extentLogger, deliveryCancelled, "Validate Cancel Email is successful" , jiraNumber);
				}	

		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	
	/*
	 * This test case covers features for MAFQABANG-318, MAFQABANG-320 and MAFQABANG-324
	 */
	@Test(priority=5,groups={"delivery"} ,description = "MAFQABANG-321")
	public void emailSearchResultsInWordFormat() throws Exception{
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();	
			extentLogger = setUpExtentTest(extentLogger, "Delivery Test", testResult.getMethod().getMethodName());
		  			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify delivery using email of result list and complete document with Word as format"); 
			try{
				
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				   String freewordKey = "freeword";
				   String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);   
						
				String emailIdKey="emailid";
				String emailId = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailIdKey,extentLogger); 
				//Search
				homepage.openHomepage();
				homepage.clickOnClear();
				homepage.enterGivenFreeword(freewordVal);
				searchResultsPage = homepage.clickOnSearch();
				boolean deliveryAvailable = searchResultsPage.isDeliveryOptionThroughEmailisDisplayed();
				deliveryPage = searchResultsPage.clickSendEmailOptionicon();
				deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
			
				softas.assertTrue(deliveryAvailable, "Delivery options are Displayed for Search Results");
				logExtentStatus(extentLogger, deliveryAvailable, "Delivery options are Displayed for Search Results" , jiraNumber);
				
						//Send mail RTF - Complete document 
						if(deliveryAvailable){
							
							deliveryPage.clickToSelectResultListForDelivery();
							deliveryPage.clickToSelectFullDocumentForDelivery();
							deliveryPage.enterEmailDetails(emailId);
							deliveryPage.clickOnRadioOptionForPDF();
							deliveryPage.clickOnRadioOptionForWord();
							deliveryPage.clickOnSubmit();
						}
						
						boolean wordEmailSent = 
								deliveryAvailable &&
								deliveryPage.isConfirmationMsgDisplayedForDelivery();
						deliveryPage.returnToDocumentUponCompletionOfDelivery();
						softas.assertTrue(wordEmailSent, jiraNumber+": WordDocument(Complete Document) is Sent through Email");
						logExtentStatus(extentLogger, wordEmailSent, "WordDocument(Complete Document) is Sent through Email" , jiraNumber);
						
						deliveryAvailable = searchResultsPage.isDeliveryOptionThroughEmailisDisplayed();
						deliveryPage = searchResultsPage.clickSendEmailOptionicon();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						//Send mail RTF - List Of document 
						if(deliveryAvailable){
							
							deliveryPage.clickToSelectResultListForDelivery();
							deliveryPage.clickToSelectFullDocumentForDelivery();
							deliveryPage.enterEmailDetails(emailId);
							deliveryPage.clickOnRadioOptionForPDF();
							deliveryPage.clickOnRadioOptionForWord();
							deliveryPage.clickOnSubmit();
						}
						
						wordEmailSent = 
								deliveryAvailable &&
								deliveryPage.isConfirmationMsgDisplayedForDelivery();
						deliveryPage.returnToDocumentUponCompletionOfDelivery();
						softas.assertTrue(wordEmailSent, jiraNumber + "WordDocument(List Of Document) is Sent through Email");
						logExtentStatus(extentLogger, wordEmailSent, "WordDocument(List Of Document) is Sent through Email" ,jiraNumber);
						
				}
						
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=4, groups={"delivery"},description = "MAFQABANG-326")
	public void printSearchResults() throws Exception{

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();	
			extentLogger = setUpExtentTest(extentLogger, "Delivery Test", testResult.getMethod().getMethodName());
		  				
			String jiraNumber = testResult.getMethod().getDescription();
			
			String issueSummary = getIssueTitle(jiraNumber,"Verify full document delivery with Print Option"); 
		
			try{
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				   String freewordKey = "freeword";
				   String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);                  
						
						homepage.openHomepage();
						homepage.clickOnClear();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						boolean deliveryAvailable = searchResultsPage.isPrintOptionDisplayed();
						deliveryPage = searchResultsPage.clickPrintOptionicon();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						
						softas.assertTrue(deliveryAvailable, "Validate DeliveryPage is Displayed for Document Display");
						logExtentStatus(extentLogger, deliveryAvailable, "Validate DeliveryPage is Displayed for Document Display" , jiraNumber);
						
						//Print Complete Document
						if(deliveryAvailable){
							
							deliveryPage.clickToSelectResultListForDelivery();
							deliveryPage.clickToSelectFullDocumentForDelivery();
							deliveryPage.clickOnSubmit();
						}
						boolean documentPrinted = 
								deliveryAvailable &&
								deliveryPage.isConfirmationDisplayedForDeliveryUsingPrint();
						deliveryPage.returnToDocumentUponCompletionOfDelivery();
						softas.assertTrue(documentPrinted, jiraNumber+"Print Document(Complete Document) is successful");
						logExtentStatus(extentLogger, documentPrinted, "Print Document(Complete Document) is successful" , jiraNumber);
						
						
						deliveryAvailable = searchResultsPage.isDeliveryOptionDisplayed();
						deliveryPage = searchResultsPage.clickDeliveryOptioniconforPrint();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						//Print List Of Document
						if(deliveryAvailable){
							
							deliveryPage.clickToSelectFullDocumentForDelivery();
							deliveryPage.clickToSelectResultListForDelivery();
							deliveryPage.clickOnSubmit();
						}
						documentPrinted = 
								deliveryAvailable &&
								deliveryPage.isConfirmationDisplayedForDeliveryUsingPrint();
						deliveryPage.returnToDocumentUponCompletionOfDelivery();
						
						softas.assertTrue(documentPrinted, jiraNumber + "Print Document(List Of Documents) is successful ");
						logExtentStatus(extentLogger, documentPrinted, "Print Document(List Of Documents) is successful" , jiraNumber);
					
			}
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	
}