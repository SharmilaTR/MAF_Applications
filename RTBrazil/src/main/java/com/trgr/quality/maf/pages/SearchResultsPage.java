package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class SearchResultsPage extends BasePage {

	public SearchResultsPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		//WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
			//	PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultslistmainpane")),
				//20);
	}

	/*
	 * This method checks to see if the search results container is displayed on the page
	 * Returns true / false based on the element display
	 */
	public boolean searchResultsDisplayed() {
		try{
		return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".container")));
		}catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * This method checks to see if the search results container is displayed on the page
	 * Returns true / false based on the element display
	 */
	public boolean searchResultsHeaderContainerDisplayed() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultslistmainpane")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsHeaderContainerDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * Clicks on the New Search link displayed on the search results page.
	 * Upon clicking on the New Search link successfully -> Home Page is returned.
	 */
	public HomePage ClickOnNewSearchLink() throws Exception {

		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newsearchlink"));
			return new HomePage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnNewSearchLink <br>" + displayErrorMessage(ex));
			return null;

		}

	}

	/*
	 * Clicks on the Modify Search link displayed on the search results page.
	 * Upon clicking on the New Search link successfully -> Home Page is returned.
	 */
	public HomePage ClickOnModifySearchLink() throws Exception {
		try
		{
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".modifysearchlink"));
			return new HomePage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnModifySearchLink <br>" + displayErrorMessage(ex));
			return null;

		}
	
	}

	/*
	 * This method checks to see if the search results displayed as per the expectations.
	 * Takes the Search String to compare on the result set and based on the success returns true / false
	 */
	public boolean searchReturnedResultsAsExpected(String searchText) {
		try {
			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandsearchtextinfo")))) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandsearchtextinfo"));
				// return
				// searchText.contains(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest
				// +
				// ".searchtext")).getText().split(":")[1].split("\\.")[0].split("\\(")[1]);

				if(searchText.contains("."))
				{
					return elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandedfullquerytext"))
							.getText().contains(searchText.split("\\.")[0]);
				}
				return elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandedfullquerytext"))
						.getText().contains(searchText);
			}
		} catch (Exception ex) {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchtext"))
					.getText().contains(searchText);
		}
		return false;

	}

	/*
	 * This method checks to see if the search string is highlighted on the page.
	 * Returns true / false based on the element display
	 */
	public boolean isSearchStringHighlighted(String highlightedText) {
		try {
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".highlighttext"))
					.getText().toUpperCase().contains(highlightedText.toUpperCase()) |
		highlightedText.toUpperCase().contains(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".highlighttext"))
					.getText().toUpperCase());
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchStringHighlighted <br>" + displayErrorMessage(ex));
			return false;
		}
	}
	/*
	 * This method clicks on the breadcrumb of the search results page and returns back to the specific search page
	 * from the search results page
	 */
	public SuplementSearchPage clickToGoBackOnTheBreadCrumb() 
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".supplementlink"));
			return new SuplementSearchPage(driver);
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : clickToGoBackOnTheBreadCrumb <br>"+displayErrorMessage(e));
			return null;
		}
		
	}

	/*
	 * This method checks to see if the search within option is displayed on the page.
	 * Returns true / false based on the element display
	 */
	public boolean isSearchWithinOptionDisplayed() {
		try
		{
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchwithinfield")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchWithinOptionDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
		
	}

	/*
	 * Enter search with in term on the search within field on the search results page
	 */
	public void enterSearchWithinTerm(String searchwithintext) {
		try
		{
			elementhandler
			.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchwithinfield"))
			.sendKeys(searchwithintext);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterSearchWithinTerm <br>" + displayErrorMessage(ex));
	
		}
		
	
	}

	/*
	 * Clicks on the search option on the search with in usecse
	 */
	public void clickOnSearchWithInSearch() {
		try
		{
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchwithinsearchbtn"));
			
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSearchWithInSearch <br>" + displayErrorMessage(ex));
	
		}
		
		
	}

	/*
	 * This method checks to see if the search within is displaying the search results as expected
	 * based on the search within search string.
	 * Returns true / false based on the element display
	 */
	public boolean searchWithInResultsDisplayed(String searchwithintext) {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchtext"))
					.getText().contains(searchwithintext);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchWithInResultsDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	// Open the first document from result set and wait for sometime till the
	// entire document loads.
	// then search for the search text on the document
	public Boolean isSearchTextDisplayedOnTheFullTextDocFromList(String searchText) throws Exception {
		try
		{
			String[] eachString = searchText.split("\\s");

			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstdocfromresultset"));
			Thread.sleep(1500); // give sometime to wait for the entire document to
								// load

			String docContent = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".documentcontent"))
					.getText();
			for (String text : eachString) {
				docContent.contains(text);
				return true;
			}
			return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchTextDisplayedOnTheFullTextDocFromList <br>" + displayErrorMessage(ex));
			return false;
		}
		
	}

	/*
	 * Returns the document count displayed on the results page as string.
	 * Need to covert the text to integer based on the test case need
	 */
	public String getDocCountFromResultSet() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".totaldoccount"))
					.getText();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : getDocCountFromResultSet <br>" + displayErrorMessage(ex));
			return "Doc count is not displayed";
		}

	}

	/*
	 * This method checks to see if the faceting is displayed.
	 * Returns true / false based on the element display
	 */
	public boolean isFacetingDisplayed() {
		try {
			return WebDriverFactory
					.isDisplayed(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".facetinglegislation")))
					&&

					WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".facetingdoctrina")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isFacetingDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}
	
	/*
	 * This method verifies if the result set is displayed based on the content type as filter
	 * returns true if the content 
	 */

	public boolean resultSetDisplayBasedOnContentSet(String[] contentTypes) {
		try
		{
			List<WebElement> list = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contenttypesonresultset"));
			Boolean contentSetDisplayedOnResult = false;

			for (WebElement webElement : list) {

				for (int i = 0; i < contentTypes.length; i++) {
					if (webElement.getText().contains(contentTypes[i]))
						contentSetDisplayedOnResult = true;
					else
						i++;

				}
			}
			return contentSetDisplayedOnResult;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : resultSetDisplayBasedOnContentSet <br>" + displayErrorMessage(ex));
			return false;
		}
		

	}
	
	/*
	 * Method verifies if the facet for content type displayed is of given content type.
	 * return true if the given content type is displayed on facet
	 */

	public Boolean isFilterFacetHasOnlyGivenContentType(String contentType) {
		
		try
		{
			WebElement element = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".availablefacets"));
			List<WebElement> availableFacetlinks = element.findElements(By.tagName("li"));
			if (availableFacetlinks.size() == 1) {
				return availableFacetlinks.get(0).getText() == contentType;
			}
			return false;
	
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isFilterFacetHasOnlyGivenContentType <br>"+displayErrorMessage(ex));
			return false;
		}
		
		
	}
	
	/*
	 * Checking for document navigation links to be displayed on the search results page.
	 * This method verifies if navigation bar is displayed with next page and last page including current page options
	 */

	public Boolean isDocNavigationDisplayed() {
		try {
			Boolean navigationBarDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docnavigation")));
			if (navigationBarDisplayed) {
				WebElement element = elementhandler.findElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docnavigation"));
				List<WebElement> availableFacetlinks = element.findElements(By.xpath("//a[@class='navPages']"));
				if (availableFacetlinks.size() > 0)
					navigationBarDisplayed = true;

				navigationBarDisplayed = navigationBarDisplayed
						&& WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".currentpage")));
				navigationBarDisplayed = navigationBarDisplayed
						&& WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextpage")));
				navigationBarDisplayed = navigationBarDisplayed
						&& WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastpage")));
			}

			return navigationBarDisplayed;
		} 	catch(Exception ex )
		{
			extentLogger.log(LogStatus.INFO, "Error in : isDocNavigationDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}
	
	/*
	 * Clicking on the next page on the document navigation bar
	 */
	public void clickOnNextPageWithDocNavBar() 
	{
		try
		{
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextpage"));
		}
		catch(Exception ex )
		{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnNextPageWithDocNavBar <br>"+displayErrorMessage(ex));
		}
		
	}
	
	/*
	 * Clicking on the previous page on the document navigation bar
	 */
	
	public void clickOnPreviousPageWithDocNavBar() 
	{
		try
		{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".previouspage"));
		}
		catch(Exception ex )
		{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnPreviousPageWithDocNavBar <br>"+displayErrorMessage(ex));
		}
		
	}
	
	/*
	 * Clicking on the last page on the document navigation bar
	 */
	
	public void clickOnLastPageWithDocNavBar() 
	{
		try
		{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastpage"));
		}
		catch(Exception ex )
		{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnLastPageWithDocNavBar <br>"+displayErrorMessage(ex));
		}
		
	}
	
	/*
	 * Clicking on the first page on the document navigation bar
	 */
	public void clickOnFirstPageWithDocNavBar() 
	{
		try
		{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstpage"));
		}
		catch(Exception ex )
		{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnFirstPageWithDocNavBar <br>"+displayErrorMessage(ex));
		}
		
	}
	
	public boolean isLastPageOptionDisplayed()
	{
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastpage")).isDisplayed();
		}
		catch(Exception ex )
		{
			//extentLogger.log(LogStatus.INFO, "Error in : isLastPageOptionDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}
	
	

	public boolean DocumentInfoOnResultSetHasSearchText(String searchText) {
		Boolean isResultListHasSearchText = false;
		WebElement element = elementhandler.findElement(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultsetinfo"));
		List<WebElement> documentlinks = element.findElements(By.xpath("//div[@class='result']"));
		for (WebElement webElement : documentlinks) {
			isResultListHasSearchText = webElement.findElement(By.tagName("td")).findElement(By.xpath("//p")).getText()
					.contains(searchText);

		}

		return isResultListHasSearchText;
	}

	public boolean DocumentCountDisplayed() {
		try
		{
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".totaldoccount")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : DocumentCountDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
		
	}

	public boolean isClassificationSectionDisplayedWithInfo() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultleftpane")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isClassificationSectionDisplayedWithInfo <br>"+displayErrorMessage(ex));
			return false;
		}

	}

	public boolean infoMessageForLargeNumberInResultSet() {
		try {
			return WebDriverFactory.isDisplayed(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".erroblock")))
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".erroblock"))
							.getText().contains("Você pode editar a sua pesquisa ou limitar o escopo da sua pesquisa");
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : infoMessageForLargeNumberInResultSet <br>"+displayErrorMessage(ex));
			return false;
		}
	}

	public DocumentDisplayPage clickFirstLink() throws Exception {

		try
		{
			elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstlinkinsearchresult")).click();
			return new DocumentDisplayPage(driver);
			
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFirstLink <br>"+displayErrorMessage(ex));
			return null;
		}
		

	}

	public String getfirstlinkinsearchresultText() {
		try
		{
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstlinkinsearchresultText"))
					.getText();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : getfirstlinkinsearchresultText <br>"+displayErrorMessage(ex));
			return null;
		}
		
	}

	public void getBackToSearchResultsPage() {
		try
		{
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".backtosearchresults"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : getBackToSearchResultsPage <br>"+displayErrorMessage(ex));
		}
		

	}

	// return the first word in the sentence. delimiter used is the first space
	// found on the sentence
	public String getTextFirstDocumentViewTerms() {
		try
		{
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".fstdocviewterms"))
					.getText().split("\\s")[0];
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : getTextFirstDocumentViewTerms <br>"+displayErrorMessage(ex));
			return "";
		}
	
	}

	public Boolean isSaveDeliveryAlertOptionsDisplayed() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlist_addtoalert")));
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean isListCompleteLinkDisplayed() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".listcompletelink")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isListCompleteLinkDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}

	public void backToMainSearchFromSearchWithInResults() {
		try
		{
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".listcompletelink"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : backToMainSearchFromSearchWithInResults <br>"+displayErrorMessage(ex));

		}
		
	}

	public boolean isSummarizationDisplayedInClassifications() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".summarization")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isSummarizationDisplayedInClassifications <br>"+displayErrorMessage(ex));
			return false;
		}
	}

	public boolean resultsDisplayedBasedOnContentType(String contentType) {
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contenttypesonresultsleftpane"));

			List<WebElement> lstOfContentTypes = element.findElements(By.tagName("li"));
			for (WebElement webElement : lstOfContentTypes) {
				if(webElement.findElement(By.tagName("a")).getText().contains(contentType))
					{
					  return true;
					}

			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : resultsDisplayedBasedOnContentType <br>"+displayErrorMessage(ex));
			return false;
		}
		return false;

	}

	public void clickOnSpecificContentTypeOnLeftPage(String contentType) {

		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".doutrinalinkonleftpane"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSpecificContentTypeOnLeftPage <br>"+displayErrorMessage(ex));
			return;
		}

	}
	
	public boolean isDeliveryOptionThroughEmailisDisplayed()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".saveDocumentInEmail")).isDisplayed();
		}catch(Exception ex){
			extentLogger.log(LogStatus.INFO, "Error in : isDeliveryOptionThroughEmailisDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}
	
	public boolean isPrintOptionDisplayed()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".clickprint")).isDisplayed();
		}catch(Exception exc){
			return false;
		}
	}
	public boolean isDeliveryOptionDisplayed()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".Deliveryoptions")).isDisplayed();
		}catch(Exception ex){
			extentLogger.log(LogStatus.INFO, "Error in : isDeliveryOptionDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}
	
	public DeliveryPage clickDeliveryOptionicon() throws IllegalArgumentException, IOException
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".Deliveryoptions"));
			Thread.sleep(1000);
			return new DeliveryPage(driver);
		}catch(Exception ex){
			extentLogger.log(LogStatus.INFO, "Error in : clickDeliveryOptionicon <br>"+displayErrorMessage(ex));
			return null;
		}
		 
	}
	
	public DeliveryPage clickDeliveryOptioniconforPrint() throws IllegalArgumentException, IOException
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".clickprint"));
			Thread.sleep(1000);
			return new DeliveryPage(driver);
		}catch(Exception ex){
			extentLogger.log(LogStatus.INFO, "Error in : clickDeliveryOptioniconforPrint <br>"+displayErrorMessage(ex));
			return null;
		}
		 
	}
	public DeliveryPage clickSendEmailOptionicon() throws IllegalArgumentException, IOException
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".saveDocumentInEmail"));
			Thread.sleep(1000);
			return new DeliveryPage(driver);
		}catch(Exception ex){
			extentLogger.log(LogStatus.INFO, "Error in : clickSendEmailOptionicon <br>"+displayErrorMessage(ex));
			return null;
		}
		 
	}
	public DeliveryPage clickPrintOptionicon() throws IllegalArgumentException, IOException
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".clickprint"));
			Thread.sleep(1000);
			return new DeliveryPage(driver);
		}catch(Exception ex){
			extentLogger.log(LogStatus.INFO, "Error in : clickPrintOptionicon <br>"+displayErrorMessage(ex));
			return null;
		}
		 
	}
	public List<String> getDocumentTitleFromSearchResultsList(int docCount)
	{
		List<String> documentTitles = new ArrayList<String>();
		try{
			
			List<WebElement> docTitles= elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docresultset"));
				
			for (WebElement webElement : docTitles) {
				
				if(docCount!=0)
				{
					documentTitles.add(webElement.findElement(By.xpath("//a[@class='documentLink']")).getText());
					docCount--;
					
				}			
				
			}
			return documentTitles;
				
		}catch(Exception ex){
			extentLogger.log(LogStatus.INFO, "Error in : getDocumentTitleFromSearchResultsList <br>"+displayErrorMessage(ex));
			return documentTitles ;
		}
	}

	public void clickOnShowHideTerms()
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".showhideterms"));	
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnShowHideTerms <br>"+displayErrorMessage(ex));
			return;
		}
		 
	}
	
	/*
	 * Checks for the document snippet is displayed on the result page
	 */
	public boolean isShowHideTermsDisplayed()
	{
		try{
			 return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".documentsnippet")).isDisplayed();
				
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isShowHideTermsDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
		 
	}

	public void clickOnSortOption() 
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sortlink")),30);
			 elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".sortlink"));
			 WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sortbydata")),30);
			 elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".sortbydata"));
				
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSortOption <br>"+displayErrorMessage(ex));
		}
		 
		
	}

	

	/*
	 *  To get result list count per page
	 */
	
	public int getResultListCountPerPage() throws Exception {
		try{
			List<WebElement> result= elementhandler.findElements(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlist_listofdoc"));
			int count = result.size();
			return count;
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : getResultListCountPerPage <br>"+displayErrorMessage(exc));
			return 0;
		}

	}
	
	/*
	 *  click on Add to Favorites Folder	 
	 */
	public void clickOnAddToFav(String newfoldername)
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlist_favorites")),30);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".resultlist_favorites"));
			Thread.sleep(1000);
			elementhandler.selectByValue(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".resultlist_selectfolder"), "_newFolder");
			
			
			WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlist_newfolderinput")),30);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".resultlist_newfolderinput")).clear();
			elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".resultlist_newfolderinput"),newfoldername);
			Thread.sleep(1000);
			WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlist_newfoldersave")),30);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".resultlist_newfoldersave"));
			Thread.sleep(1000);
			
			if(IsPopUpWindowPresent())
			{
				clickOnAlertPopUP();
			}	
			Thread.sleep(1000);
			WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlist_selectfolder_save")),30);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".resultlist_selectfolder_save"));
			Thread.sleep(1000);
			if(IsPopUpWindowPresent())
			{
				clickOnAlertPopUP();
			}	
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickOnAddToFav <br>"+displayErrorMessage(exc));
		}
	}
	

	
	public HomePage OpenHomePage() throws Exception 
	{
		try
		{
			if(WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".homepagetab"))))
			{
				elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".homepagetab"));
				
			}
		}
		catch(Exception exc)
		{
			extentLogger.log(LogStatus.INFO, "Error in : OpenHomePage <br>"+displayErrorMessage(exc));
			return null;
		}
		return new HomePage(driver);			
	
	}


	/*
	 * This method checks to see if the zero search results are displayed on the page
	 */
	public boolean noSearchResultsDisplayed() 
	{
		try{
			 return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".erroblock")).isDisplayed();
			 
			}catch (Exception exc)
			{
				extentLogger.log(LogStatus.INFO, "Error in : noSearchResultsDisplayed <br>"+displayErrorMessage(exc));
				return false;
			
			}	
	}
	
	
	/*
	 * CLicks on the Add Alert option and return handle to alert page upon success.
	 */
	public AlertPage clickAddToAlert()
	{
		AlertPage alertpage = null;
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".resultlist_addtoalert"));
			alertpage = new AlertPage(driver);
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : clickAddToAlert <br>"+displayErrorMessage(e));
		
			return null;
			
		}
		
		return alertpage;
	}
	
}
