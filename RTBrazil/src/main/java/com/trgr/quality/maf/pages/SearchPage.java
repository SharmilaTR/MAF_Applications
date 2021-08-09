package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class SearchPage extends BasePage {

	public SearchPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".searchbtn")), 20);
	}
	
	/*
	 * This method checks to see if the search results displayed.
	 * Returns true / false based on the container display
	 */
	public boolean searchResultsDisplayed() 
	{
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".Legislaci�n")).isDisplayed();
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	
	}
	
	/*
	 * This method checks to see if the search results header container is displayed.
	 * Returns true / false based on the container display
	 */
	public boolean searchResultsHeaderContainerDisplayed()
	{
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".resultsheadercontainer")).isDisplayed();	
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsHeaderContainerDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * This method checks to see if the suggestions dropdown is displayed on the search page
	 * Returns true /false based on the element display	
	 */
	public boolean isTheSuggestionsDropdownDisplayed() 
	{
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".thdropdown")).isDisplayed() || 
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".thdropdown")).isEnabled();
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isTheSuggestionsDropdownDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}

	}
	
	/*
	 * This method checks to see if the search term is highlighted on the results dropdown displayed
	 *  Returns true / false based on the container display
	 */
	public boolean isSearchStringhighlightedOnCombo(String searchString) 
	{
		try
		{
			if( elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".dropdownresulthighlight")).isEnabled())
			{
				return searchString.contains(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".dropdownresulthighlight"))
						.getText());
			}
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchStringhighlightedOnCombo<br>"+displayErrorMessage(ex));
			return false;
		}
		return false;
	}
	
	/*
	 * This method is used to scroll to the given string on the drop down displayed on the suggestions
	 * If the search data has any spaces that is trimmed and only the first word is taken to scroll to the value.
	 */
	public void ScrollToGivenSearchString(String searchData) 
	{
		searchData = searchData.split("\\s")[0];
		try
		{
			WebElement baseList  =	elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".scrollvalue"));
			List<WebElement> suggestionList =  baseList.findElements(By.tagName("div"));
			java.util.Iterator<WebElement> iterator = suggestionList.iterator();
			
			int iteratorCount=0;
			while(iterator.hasNext())
			{
				WebElement value = iterator.next();
				iteratorCount++;
				if(iteratorCount==10) // instead of scrolling through the entire list trying to restrict the count and click on the 10th item.
				{
					if(value.isDisplayed())
					{
						value.click();
						return;
					}
					else
					{
						iteratorCount--;
					}
					return;
				}
				else if(value.getText().contains(searchData))
				{
					value.click();
					return;
				}
				
			}
			 

			
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : Jurisprudence Page <br>"+displayErrorMessage(ex));
			
		}
			
	}


	/*
	 * Clicks on the Clean button on the search page
	 */
	public void clickOnCleanSearch() 
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".clearbtnonsearchpage")).click();
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnCleanSearch <br>"+displayErrorMessage(ex));
		}
				
	}

	/*
	 * This method checks to see if the no search results message is displayed on the page
	 * Returns true / false based on the element display
	 */
	public boolean noSearchResultsMessageDisplayed() 
	{
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".noresults")).isDisplayed() &&
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".searchbtn")).isDisplayed();
		}
		catch(Exception ex)
		{
			//no need of the message for this method
			return false;
			
		}
	}
	
	/*
	 * Enters the given freeword on the page
	 */
	public void enterGivenFreeword(String freeword)
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".freewordtxtbox")).sendKeys(freeword);	
		}	
		catch(Exception exc)
		{
			extentLogger.log(LogStatus.INFO, "Error in : enterFreewordToSearch <br>"+displayErrorMessage(exc));
		}
		
		
	}

	/*
	 * Enters the given freeword on the page
	 */
	public void enterGivenTitle(String title)
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".titletxtbox")).sendKeys(title);	
		}	
		catch(Exception exc)
		{
			extentLogger.log(LogStatus.INFO, "Error in : enterFreewordToSearch <br>"+displayErrorMessage(exc));
		}
		
		
	}

}
