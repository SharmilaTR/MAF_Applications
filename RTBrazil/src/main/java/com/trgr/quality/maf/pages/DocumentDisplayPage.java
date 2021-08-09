package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class DocumentDisplayPage extends BasePage {
	
	String ActualText;
	
	public DocumentDisplayPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Deliveryoptions")), 20);
		
	}
	
	/*
	 * This method checks to see if the Email delivery option is displayed on the document page
	 * Returns true/ false based on the element display
	 */
	public boolean isEmailDeliveryOptionDisplayed()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".saveDocumentInEmail")).isDisplayed();
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isEmailDeliveryOptionDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}
	
	/*
	 * This method clicks on the email option on the document page
	 */
	public DeliveryPage clickOnEmailOption() throws IllegalArgumentException, IOException
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".saveDocumentInEmail"));
			Thread.sleep(1000);
			return new DeliveryPage(driver);
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnEmailOption <br>"+displayErrorMessage(ex));
			return null;
		}
		 
	}
	
	/*
	 * This method checks to see if the Print option is displayed on the document page
	 * Returns true/ false based on the element display 
	 */
	public boolean isPrintOptionDisplayed()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".clickprint")).isDisplayed();
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isPrintOptionDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}
	
	/*
	 * This method clicks on the Print option on the document page
	 */
	public DeliveryPage clickOnPrintOption() throws IllegalArgumentException, IOException
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".clickprint"));
			Thread.sleep(1000);
			return new DeliveryPage(driver);
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnPrintOption <br>"+displayErrorMessage(ex));
			return null;
		}
		 
	}
	
	/*
	 * This method checks to see if the delivery options are displayed on the document page
	 * Returns true/ false based on the element display 
	 */
	public boolean isDeliveryOptionDisplayed()
	{
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".Deliveryoptions")).isDisplayed();
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isDeliveryOptionDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
		
	}
	
	
	/*
	 * This method checks to see if the Save to My Documents option is displayed on the document page
	 * Returns true/ false based on the element display 
	 */
	public boolean isSaveMydocumentsFolderDisplayed()
	{
		
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".addtofavourites")).isDisplayed();
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isSaveMydocumentsFolderDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
		
	}
	
	public DeliveryPage clickOnSaveDocumentOption() throws IllegalArgumentException, IOException
	{
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".Deliveryoptions")).click();
			Thread.sleep(1000);
			return new DeliveryPage(driver);
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSaveDocumentOption <br>"+displayErrorMessage(exc));
			return null;
		}
		 
	}
	
	/*
	 * This method clicks to add document Favourites
	 */
	public void clickOnAddToFavourites()
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".addtofavourites")).click();
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickOnAddToFavourites <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method checks to see if selecting folder type is displayed
	 */
	public boolean isFolderTypeDisplayed()
	{
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".choosefoldertype")).isDisplayed();
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isFolderTypeDisplayed <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	
	/*
	 * This method saves the selected document to folders.
	 */
	public String SaveMydocument(String folderName){
		try{	
			if(isFolderTypeDisplayed())
			{
				
				//Creation of new folder to save the folder
				Select dropdown = elementhandler.getDropdown(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".choosecreatefolder"));
				dropdown.selectByValue("_newFolder");	
				
				//enter the folder name
				folderName = folderName +Long.toString(System.currentTimeMillis()/1000);
				elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".NewfolderName"),folderName );
				
				//clicking on the save button to save the doc to folder
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".NewfolderSubmit")).click();
				
				Thread.sleep(1000);
				Alert alert = driver.switchTo().alert();
				if(alert.getText().contains("Pasta criada com"))
				{
					alert.accept();
				}

				Thread.sleep(1000); // End of folder creation

				//clicking on the submission of the document save after the folder creation
				WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Submitfavourites")), 20);
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".Submitfavourites")).click();
				Thread.sleep(1000);
				alert = driver.switchTo().alert();
				if(alert.getText().contains("Documento salvo com"))
				{
					Thread.sleep(1000);
					alert.accept();
				}

				Thread.sleep(500);
			}
			return folderName;
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : SaveMydocument <br>"+displayErrorMessage(exc));
			return null;
		}
	}
	
	/*
	 * This method deletes the folder and returns the status based on the deletion action status
	 */
	public boolean deleteGivenFolderAndReturnStatus(String Foldername){
		boolean Flag=false ;
		try{
				
				WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".services")), 40);
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".services")).click();
				WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".favourites")), 40);
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".favourites")).click();
				Thread.sleep(4000);
				String path = "xpath=.//*[@id='favouritesFolderContainer']/div[1]/ul/li";
				List<WebElement> element = baseHandler.findElements(path);
				for ( int i=1 ; i<element.size();i++) { 
					String  k = elementhandler.getElement(path+"["+i+"]/span/span[1]").getText();
					Thread.sleep(500);
					if(k.equalsIgnoreCase(Foldername)){
						Thread.sleep(1000);
						elementhandler.getElement(path+"["+i+"]/span/span[1]").click();
						String documentname = elementhandler.getElement(path+"["+i+"]/ul/li/span/a").getText();
						if(documentname.contains("O BRASIL")){
							elementhandler.getElement(path+"["+i+"]/span/span[1]").click();
							elementhandler.getElement(path+"["+i+"]/span/span[2]/a[1]/div").click();
							Alert alert = driver.switchTo().alert();
							alert.accept();
							Flag=true;
							break;
						}
						else{
							Flag=false;
						}
						
					}
				}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : deleteGivenFolderAndReturnStatus <br>"+displayErrorMessage(exc));
					 Flag = false;
				}
		return Flag;
	
	}
	
	/*
	 * This method checks to see if the new folder is created with given name
	 */
	public boolean isFolderCreatedWithGivenName(String Foldername){
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".addtofavourites")).click();
			Select dropdown = elementhandler.getDropdown(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".choosecreatefolder"));
			List<WebElement> options = dropdown.getOptions();
			for(WebElement we:options)  
			{  
				if (we.getText().equalsIgnoreCase(Foldername))
				{
					//closing the save to document pop up and return true
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".addtofavouritesclosebutton")).click();
			        return true;
				}
				
			} 
			
		
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isFolderCreatedWithGivenName <br>"+displayErrorMessage(exc));
			return false;
		}
		return false;
	}
	
	/*
	 * This method clicks on the increment font size and returns the true if the action is sucessful
	 */
	public boolean incrementFontSizeAndReturnStatus(){
		
		try
		{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".fontUp")), 20);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".fontUp")).isDisplayed();
			String  Currentfont = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".getfontsize")).getCssValue("font-size");
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".fontUp")).click(); 
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".fontUp")).click();
			String  incrementedfont = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".getfontsize")).getCssValue("font-size");
			Currentfont = parseAlphaNumericValue(Currentfont);
			incrementedfont = parseAlphaNumericValue(incrementedfont);
			if(Integer.parseInt(Currentfont)<Integer.parseInt(incrementedfont)){
				return true;
			}
			else
				return false;
	
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : incrementFontSizeAndReturnStatus <br>"+displayErrorMessage(exc));
			return false;
		}

	}
	
	/*
	 * This method clicks on the decrement font size and returns the true if the action is sucessful
	 */
	public boolean decrementFontSizeAndReturnStatus(){
		
		try
		{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".fontDown")), 20);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".fontDown")).isDisplayed();
			String  Currentfont = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".getfontsize")).getCssValue("font-size");
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".fontDown")).click(); 
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".fontDown")).click();
			String  incrementedfont = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".getfontsize")).getCssValue("font-size");
			Currentfont = parseAlphaNumericValue(Currentfont);
			incrementedfont = parseAlphaNumericValue(incrementedfont);
			if(Integer.parseInt(Currentfont)>Integer.parseInt(incrementedfont)){
				return true;
			}
			else
				return false;
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : decrementFontSizeAndReturnStatus <br>"+displayErrorMessage(exc));
			return false;
		}
	
	}
	
	public String parseAlphaNumericValue(String parse){
	
		String someString = parse;
	    String regex = "((?<=[a-zA-Z])(?=[0-9]))|((?<=[0-9])(?=[a-zA-Z]))";
	    String k = Arrays.asList(someString.split(regex)).get(0);
	    return k;
	}
	
	/*
	 * This method returns the document header text
	 */
	public String getDocumentHeaderText(){
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".documentheader")).getText();
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : decrementFontSizeAndReturnStatus <br>"+displayErrorMessage(ex));
			return "";
		}
		
	}
	
	/*
	 * This method takes search data to enter on the search within text field
	 */
	public void enterSearchWithInTextOnDocument(String searchWithInText)
	{
		try{
			elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docSearch"), searchWithInText);
			//elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docSearch"),"international");
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterSearchWithInTextOnDocument <br>"+displayErrorMessage(exc));
		}
	}
	
	
	/*
	 * This method clicks on the search button to perform search within option
	 */
	public void clickOnSearchWithInOption()
	{
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".docSearchbutton")).click();
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSearchWithInOnDocument <br>"+displayErrorMessage(exc));
		}
	}
	
	
	/*
	 * This method checks to see of the search within text box is displayed and search within button is displayed
	 */
	public boolean isSearchWithInOptionDisplayed(){
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".docSearchbutton")), 20);
			 return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".docSearch")).isDisplayed() &&
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".docSearchbutton")).isDisplayed();

		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isSearchWithInOptionDisplayed <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	
	/*
	 * This method checks to see if the highlighted text is displayed and highlighted text contains the given string
	 */
	public boolean isGivenTextHighlighted(String highlightedTxt)
	{
		try {
			List<WebElement> locator = driver.findElements(org.openqa.selenium.By.xpath("//span[contains(@class,'highlight termNavigation')]"));
			
			if (locator.size() >0) 
			{
				for (WebElement webElement : locator) 
				{
					if(webElement.getText().toUpperCase().contains(highlightedTxt.toUpperCase()) || 
							highlightedTxt.toUpperCase().contains(webElement.getText().toUpperCase()))
							{
						      return true;
							}		
				}
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isGivenTextHighlighted <br>" + displayErrorMessage(ex));
			return false;
		}
		return false;

	}
		
	/*
	 * This method checks to see if the document navigation displays the next button
	 */
	public boolean isDocNavNextButtonDisplayed()
	{
		try
		{
			WebElement nextButton  = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docSearchNext"));
			return nextButton.isDisplayed();
						
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocNavNextButtonDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}
	
	/*
	 * This method clicks on the document navigation next button
	 */
	public void clickDocNavNextButton()
	{
		try
		{
			WebElement nextButton  = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docSearchNext"));
			nextButton.click();
						
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDocNavNextButton <br>"+displayErrorMessage(ex));
		}
	}
	
	
	/*
	 * This method checks to see if the document navigation displays the previous button
	 */
	public boolean isDocPreviousButtonDisplayed()
	{
		try
		{
			WebElement previousButton  = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docSearchPrev"));
			return previousButton.isDisplayed();
						
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocPreviousButtonDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}
	
	/*
	 * This method clicks on the document navigation previous button
	 */
	public void clickDocPreviousButton()
	{
		try
		{
			WebElement previousButton  = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docSearchPrev"));
			previousButton.click();
						
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDocPreviousButton <br>"+displayErrorMessage(ex));
		}
	}
	
	public boolean ValidateDocdisplayPagenavigationLinks(){
		boolean flag=false;
		elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docCountpage1")).isDisplayed();
		String expected = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docCountpage1")).getText();
		if(expected.contains("Resultado 1")){
			flag=true;
		}
		elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextdocumentlink")).isDisplayed();
		elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Lastdocumentlink")).isDisplayed();
		elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docSearchPrev")).isDisplayed();
		elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docSearchNext")).isDisplayed();
		return flag;
	}
	
	public boolean ValidateDocdisplayPagenavigationLinksonNextdocument(){
		boolean flag=false;
		elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docCountpage1")).isDisplayed();
		elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextdocumentlink")).isDisplayed();
		elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextdocumentlink")).click();
		WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".docCountpage2")), 20);
		String expected2 = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docCountpage2")).getText();
		if(expected2.contains("Resultado 2")){
			flag=true;
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docSearch")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docSearchbutton")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextdocumentlink")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Lastdocumentlink")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docSearchPrev")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".prevDocImgpage2")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstDocImgpage2")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstDocImgpage2")).click();
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".docCountpage1")), 20);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docCountpage1")).isDisplayed();
			String expected3 = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docCountpage1")).getText();
			if(expected3.contains("Resultado 1")){
				flag=true;
			}
			else{
				flag=false;
			}
		}
		return flag;
	}
	
	
	public boolean verifyDocumentSavedinPDF()  
	{
		boolean flag=false;	
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".DocDisplay_deliveryoption")),30);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".DocDisplay_deliveryoption")).click();
			if(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".DocDisplay_pdfradiobutton")).isSelected())
			{
				flag = true;				
				elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".returnhomepage"));
			}
			else
					flag=false;
			}catch(Exception e)
			{
				extentLogger.log(LogStatus.INFO, "Error in : verifyDocumentSavedinPDF <br>"+displayErrorMessage(e));
				flag= false;
			}	
		return flag;
		
	}
}
