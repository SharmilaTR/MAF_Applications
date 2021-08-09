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
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;



public class DocumentDisplayTest extends BaseTest {
	LoginPage loginpage;
	HomePage  homepage,homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	DocumentDisplayPage documentDisplayPage;
	DeliveryPage deliveryPage;
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
			homepage = loginpage.Login(username, password);
			homepagecopy = this.homepage;	
		
		}catch(Exception exc){
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", "StartDocumentDisplayTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the DocumentDisplay test are not run.<br>"+ takesScreenshot_Embedded()+ "<br>"+displayErrorMessage(exc));
			extentReports.endTest(extentLogger);			
			Assert.assertTrue(false);
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception
	{
		homepagecopy.clickSignOff();	
	}
	
	@Test(priority=0, groups={"document display"}, description = "MAFQABANG-356")
	public void fontIncrementAndFontDecrement() throws Exception{

		testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
		
			String jiraNumber = testResult.getMethod().getDescription();
			
			String issueSummary = getIssueTitle(jiraNumber,"Verify Increase / decrease Font for Document display page"); 
		
			try{
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				    String freewordKey = "freeword";
				    String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);                                     
						
						homepage.openHomepage();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						
						searchResultsPage.getfirstlinkinsearchresultText();
						documentDisplayPage = searchResultsPage.clickFirstLink();
						documentDisplayPage.isDeliveryOptionDisplayed();
						
						{
							boolean displayValidated = documentDisplayPage.isDeliveryOptionDisplayed();
							
							boolean fontIncremented =displayValidated && documentDisplayPage.incrementFontSizeAndReturnStatus(); 
							softas.assertTrue(fontIncremented, jiraNumber+": Incrementfontsize Feature for Document Display");
							logExtentStatus(extentLogger, fontIncremented, "Incrementfontsize Feature for Document Display" , jiraNumber);
						
						};
						{
							boolean displayValidated = documentDisplayPage.isDeliveryOptionDisplayed();
							
							boolean fontDecremented = displayValidated && documentDisplayPage.decrementFontSizeAndReturnStatus(); 
							softas.assertTrue(fontDecremented, jiraNumber+": Decrementfontsize Feature for Document Display");
							logExtentStatus(extentLogger, fontDecremented, "Decrementfontsize Feature for Document Display" , jiraNumber);
						};
				}
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=1, groups={"document display"}, description = "MAFQABANG-353")
	public void displayOfDocumentFromResultListPage() throws Exception{
			//Start Test & Subtests
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
					
			String jiraNumber = testResult.getMethod().getDescription();
			
			String issueSummary = getIssueTitle(jiraNumber,"Verify Document Display from Result List Page"); 
			
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
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						
						
						documentDisplayPage = searchResultsPage.clickFirstLink();
						boolean displayValidated = documentDisplayPage.isDeliveryOptionDisplayed();
						softas.assertTrue(displayValidated, jiraNumber+": Verify Document Display from Result List Page");
						logExtentStatus(extentLogger, displayValidated, "Verify Document Display from Result List Page" , jiraNumber);
							
				}
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=2, groups={"document display"}, description = "MAFQABANG-354")
	public void keyInformationDisplayOnDocumentPage() throws Exception{
			//Start Test & Subtests
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
					
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify Key information display on document page"); 
			
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
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						
						String firstlinkinsearchresultText = searchResultsPage.getfirstlinkinsearchresultText();
						documentDisplayPage = searchResultsPage.clickFirstLink();
					
						boolean keyInfoValidated = firstlinkinsearchresultText.equals(documentDisplayPage.getDocumentHeaderText());
						softas.assertTrue(keyInfoValidated, jiraNumber +":" + issueSummary);
						logExtentStatus(extentLogger, keyInfoValidated, issueSummary , jiraNumber);
					}
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=3, groups={"document display"}, description = "MAFQABANG-355")
	public void highlightOfSearchTermOnResults() throws Exception{
			//Start Test & Subtests
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
					
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify highlights of search term on results"); 
			
			try{
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				    String freewordKey = "freeword";
				    String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);                                     
						
						homepage.openHomepage();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						
						
						documentDisplayPage = searchResultsPage.clickFirstLink();
					
						boolean keyHighlighted =documentDisplayPage.isGivenTextHighlighted(freewordVal);
						softas.assertTrue(keyHighlighted, jiraNumber + ": Verify Highlight of Search Term is successful");
						logExtentStatus(extentLogger, keyHighlighted, issueSummary , jiraNumber);
					}
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=4, groups={"document display"}, description = "MAFQABANG-359")
	public void documentNavigationOnSearchResults() throws Exception{
			//Start Test & Subtests
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
					
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify navigation within search results"); 
			
			try{
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				    String freewordKey = "freeword";
				    String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);                                     
						
						homepage.openHomepage();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						
						documentDisplayPage = searchResultsPage.clickFirstLink();
						
						//Is Next button displayed and clickable
						
						boolean isNextButtonDisplayed  = documentDisplayPage.isDocNavNextButtonDisplayed();
						if(isNextButtonDisplayed)
							documentDisplayPage.clickDocNavNextButton();
						
						boolean isSearchTxtHighlightedOnNextPage = documentDisplayPage.isGivenTextHighlighted(freewordVal);
						
						softas.assertTrue(isNextButtonDisplayed & isSearchTxtHighlightedOnNextPage , jiraNumber + ": Doc navigation next button displayed and search text is highlighted on next page");
						logExtentStatus(extentLogger, isNextButtonDisplayed & isSearchTxtHighlightedOnNextPage, "Doc navigation next button displayed and search text is highlighted on next page" , jiraNumber);

						boolean isPrevButtonDisplayed  = documentDisplayPage.isDocPreviousButtonDisplayed();
						if(isPrevButtonDisplayed)
							documentDisplayPage.clickDocPreviousButton();
						
						boolean isSearchTxtHighlightedOnPrevPage = documentDisplayPage.isGivenTextHighlighted(freewordVal);
						softas.assertTrue(isPrevButtonDisplayed & isSearchTxtHighlightedOnPrevPage , jiraNumber + ":" + issueSummary);
						logExtentStatus(extentLogger, isPrevButtonDisplayed & isSearchTxtHighlightedOnPrevPage, "Doc navigation previous button displayed and search text is highlighted on previous page" , jiraNumber);	
					}
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	

	@Test(priority=5, groups={"document display"}, description = "MAFQABANG-411")
	public void documentNavigationOnDocumentPage() throws Exception{
			//Start Test & Subtests
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
					
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify navigation options on the document page"); 
			
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
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						documentDisplayPage = searchResultsPage.clickFirstLink();
	
						boolean navigationValidated = documentDisplayPage.ValidateDocdisplayPagenavigationLinks();
						softas.assertTrue(navigationValidated, jiraNumber + ":" + issueSummary + "-Previous link");
						logExtentStatus(extentLogger, navigationValidated, issueSummary , jiraNumber);
											
						navigationValidated =documentDisplayPage.ValidateDocdisplayPagenavigationLinksonNextdocument(); 
						softas.assertTrue(navigationValidated, jiraNumber + ":" + issueSummary  + "-Next link");
						logExtentStatus(extentLogger, navigationValidated, issueSummary + "-Next link", jiraNumber);
						
					}
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}


	@Test(priority=6, groups={"document display"},description = "MAFQABANG-358")
	public void searchWithInSearchOnDocument() throws Exception{
			//Start Test & Subtests
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
					
			String jiraNumber = testResult.getMethod().getDescription();
			
			String issueSummary = getIssueTitle(jiraNumber,"Verify search within feature on document page"); 
			
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
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						documentDisplayPage = searchResultsPage.clickFirstLink();
						boolean isSearchWithInOptionDisplayed = documentDisplayPage.isSearchWithInOptionDisplayed();
						if(isSearchWithInOptionDisplayed)
						{
							documentDisplayPage.enterSearchWithInTextOnDocument("international");
							documentDisplayPage.clickOnSearchWithInOption();
							boolean searchWithinValidated = documentDisplayPage.isGivenTextHighlighted("international");
							softas.assertTrue(searchWithinValidated, jiraNumber + ":" +  issueSummary);
							logExtentStatus(extentLogger, searchWithinValidated, issueSummary , jiraNumber);	
						}					
				}
			
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	
	@Test(priority=7,  groups={"document display"}, description = "MAFQABANG-329")
	public void saveDocumentUsingWordFormat() throws Exception{
		
		
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
		
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify save document in word format"); 
	
			try{
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				    String freewordKey = "freeword";
				    String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);                                     
						//Search
						homepage.openHomepage();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						
						documentDisplayPage = searchResultsPage.clickFirstLink();
						boolean deliveryAvailable = documentDisplayPage.isDeliveryOptionDisplayed();
						deliveryPage = documentDisplayPage.clickOnSaveDocumentOption();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						
						//once the save document option page is displayed, user needs to select the document type format
						if(deliveryAvailable){
							deliveryPage.clickOnRadioOptionForPDF();
							deliveryPage.clickOnRadioOptionForWord();
							deliveryPage.clickOnSubmit();
						}
						boolean wordDownloaded = 
								deliveryAvailable &&
								deliveryPage.isConfirmationMsgDisplayedForDelivery();
						logExtentStatus(extentLogger, wordDownloaded, issueSummary , jiraNumber);
						softas.assertTrue(wordDownloaded, jiraNumber+":" + issueSummary);
				}
		
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=8, groups={"document display"},description = "MAFQABANG-329")
	public void saveDocumentUsingPDFFormat() throws Exception{
		
		
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify save document in PDF format"); 
			try{
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				    String freewordKey = "freeword";
				    String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);                                     
				    
						homepage.openHomepage();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						
						documentDisplayPage = searchResultsPage.clickFirstLink();
						boolean deliveryAvailable = documentDisplayPage.isDeliveryOptionDisplayed();
						deliveryPage = documentDisplayPage.clickOnSaveDocumentOption();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						logExtentStatus(extentLogger, deliveryAvailable, "Validate DeliveryPage is Displayed for Document Display" , jiraNumber);
						softas.assertTrue(deliveryAvailable, jiraNumber+":Validate DeliveryPage is Displayed for Document Display");
						
						if(deliveryAvailable){
							//deliveryPage.saveDocumentInLocallink();
							deliveryPage.clickOnRadioOptionForWord();
							deliveryPage.clickOnRadioOptionForPDF();
							deliveryPage.clickOnSubmit();
						}
						boolean pdfDownloaded = 
								deliveryAvailable &&
								deliveryPage.isConfirmationMsgDisplayedForDelivery();
						logExtentStatus(extentLogger, pdfDownloaded, issueSummary , jiraNumber);
						softas.assertTrue(pdfDownloaded, jiraNumber+":" + issueSummary);
				}
		
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=9, groups={"document display"},description = "MAFQABANG-324")
	public void emailDocumentUsingPDFFormat() throws Exception{
		
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify delivery using email in PDF format"); 
			try{
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				    String freewordKey = "freeword";
				    String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);                                     

						String emailIdKey="emailid";
						String emailId = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailIdKey,extentLogger);
						
						homepage.openHomepage();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						
						documentDisplayPage = searchResultsPage.clickFirstLink();
						boolean deliveryAvailable = documentDisplayPage.isEmailDeliveryOptionDisplayed();
						deliveryPage = documentDisplayPage.clickOnEmailOption();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						logExtentStatus(extentLogger, deliveryAvailable, "Validate DeliveryPage is Displayed for Document Display" , jiraNumber);
						softas.assertTrue(deliveryAvailable, jiraNumber+":Validate DeliveryPage is Displayed for Document Display");
						
						if(deliveryAvailable){
							
							deliveryPage.enterEmailDetails(emailId);
							deliveryPage.clickOnRadioOptionForWord();
							deliveryPage.clickOnRadioOptionForPDF();
							deliveryPage.clickOnSubmit();
						}
						
						boolean pdfEmailSent = 
								deliveryAvailable &&
								deliveryPage.isConfirmationMsgDisplayedForDelivery();
						if (!pdfEmailSent)
						{
							testResult.setAttribute("defect", "MAFAUTO-287");
							logExtentStatusWithDefectInfo(extentLogger, pdfEmailSent, issueSummary,freewordKey,freewordVal, jiraNumber, "MAFAUTO-287");
						}
						
						deliveryPage.returnToDocumentUponCompletionOfDelivery();
						logExtentStatus(extentLogger, pdfEmailSent, issueSummary , jiraNumber);
						softas.assertTrue(pdfEmailSent, jiraNumber+ ":" + issueSummary);
				}
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(groups={"document display"},priority=10, description = "MAFQABANG-322")
	public void emailDocumentUsingWordFormat() throws Exception{
		
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
		
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Email document in word format"); 
			try{
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				    String freewordKey = "freeword";
				    String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);                                     
				    
						String emailIdKey="emailid";
						String emailId = jsonObjectChild.get(emailIdKey).toString();
						
						//Search
						homepage.openHomepage();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						
						documentDisplayPage = searchResultsPage.clickFirstLink();
						boolean deliveryAvailable = documentDisplayPage.isEmailDeliveryOptionDisplayed();
						deliveryPage = documentDisplayPage.clickOnEmailOption();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						logExtentStatus(extentLogger, deliveryAvailable, "Validate DeliveryPage is Displayed for Document Display" , jiraNumber);
						softas.assertTrue(deliveryAvailable, jiraNumber+":Validate DeliveryPage is Displayed for Document Display");
						
						if(deliveryAvailable){
							
							deliveryPage.enterEmailDetails(emailId);
							deliveryPage.clickOnRadioOptionForPDF();
							deliveryPage.clickOnRadioOptionForWord();
							deliveryPage.clickOnSubmit();
						}
						
						boolean wordEmailSent = 
								deliveryAvailable &&
								deliveryPage.isConfirmationMsgDisplayedForDelivery();
						if (!wordEmailSent)
						{
							testResult.setAttribute("defect", "MAFAUTO-287");
							logExtentStatusWithDefectInfo(extentLogger, wordEmailSent, issueSummary,freewordKey,freewordVal, jiraNumber, "MAFAUTO-287");
						}
						
						deliveryPage.returnToDocumentUponCompletionOfDelivery();
						logExtentStatus(extentLogger, wordEmailSent, issueSummary , jiraNumber);
						softas.assertTrue(wordEmailSent, jiraNumber+":" + issueSummary);
				}
		}catch(Exception exc){
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	
	@Test(groups={"document display"}, priority=11, description = "MAFQABANG-327")
	public void printDocumentContent() throws Exception{
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			
			String issueSummary = getIssueTitle(jiraNumber,"Delivery using Print option"); 
			try{			
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				    String freewordKey = "freeword";
				    String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);                                                                             
				    	
				    	//Search
						homepage.openHomepage();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						
						documentDisplayPage = searchResultsPage.clickFirstLink();
						boolean deliveryAvailable = documentDisplayPage.isPrintOptionDisplayed();
						deliveryPage = documentDisplayPage.clickOnPrintOption();
						deliveryAvailable = deliveryAvailable && (deliveryPage!=null);
						logExtentStatus(extentLogger, deliveryAvailable, "Validate DeliveryPage is Displayed for Document Display" , jiraNumber);
						softas.assertTrue(deliveryAvailable, jiraNumber+":Validate DeliveryPage is Displayed for Document Display");
						
						
						boolean documentPrinted = 
								deliveryAvailable &&
								deliveryPage.isConfirmationDisplayedForDeliveryUsingPrint();
						deliveryPage.returnToDocumentUponCompletionOfDelivery();
						
						logExtentStatus(extentLogger, documentPrinted, issueSummary , jiraNumber);
						softas.assertTrue(documentPrinted, jiraNumber+":" + issueSummary);
				}
		
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=12, groups={"document display"}, description = "MAFQABANG-357")
	public void addDocumentToFavoritesByCreatingNewFolder() throws Exception{
		
		
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay Test", testResult.getMethod().getMethodName());
				
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify Add to favourites from Document Display Page"); 
		
			try{
				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
                
				 for (Object searchString : listOfSearchData) {
				   JSONObject jsonObjectChild = (JSONObject) searchString;
				    String freewordKey = "freeword";
				    String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);                                     
						
						homepage.openHomepage();
						homepage.enterGivenFreeword(freewordVal);
						searchResultsPage = homepage.clickOnSearch();
						
						boolean searchResultsDisplayed = searchResultsPage != null
								&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						if (!searchResultsDisplayed) {
							boolean isErrorDisplayed = homepage.isErrorBlockDisplayed();
							if(isErrorDisplayed)
							{
							logExtentStatus(extentLogger, !isErrorDisplayed,"Search failed :" + freewordVal, jiraNumber);
							softas.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :" + freewordVal);
							}
							else
							{
								boolean noResultsFound = homepage.noSearchResultsDisplayed();
								if (noResultsFound)
									logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
											freewordVal + " -resulted in no search results", jiraNumber);
							}
							
							continue; // Skip this & Continue with next iteration
						}
						
						documentDisplayPage = searchResultsPage.clickFirstLink();
						boolean isSaveOptionDisplayed = documentDisplayPage.isSaveMydocumentsFolderDisplayed();
								
						if(isSaveOptionDisplayed){
							documentDisplayPage.clickOnAddToFavourites();
							String foldername = documentDisplayPage.SaveMydocument("Test");
							softas.assertTrue(foldername!=null, jiraNumber+":" + issueSummary);
							logExtentStatus(extentLogger, foldername!=null, issueSummary , jiraNumber);
							
							boolean folderCreated = documentDisplayPage.isFolderCreatedWithGivenName(foldername);
							softas.assertTrue(folderCreated, jiraNumber+": New folder is created successfully");
							logExtentStatus(extentLogger, folderCreated, "New folder is created successfully" , jiraNumber);
							
						
							boolean docDeleteValidated = documentDisplayPage.deleteGivenFolderAndReturnStatus(foldername);
							softas.assertTrue(docDeleteValidated, jiraNumber+": Document is validated in folder");
							logExtentStatus(extentLogger, docDeleteValidated, "Document is validated in folder" , jiraNumber);
						}
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
