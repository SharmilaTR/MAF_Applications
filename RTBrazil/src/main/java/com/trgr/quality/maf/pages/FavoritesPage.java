package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.RandomUtils;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class FavoritesPage extends BasePage {

	public FavoritesPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
	}

	/*
	 * validate if Favorite page is displayed
	 * Return true / false based on the element display
	 */
	public boolean isFavoritesTextDisplayed() {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".favoritetext")), 20);
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".favoritetext"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isFavoritesTextDisplayed <br>" + displayErrorMessage(e));
			return false;
		}

	}

	/*
	 * This method takes folder name as parameter and checks to see if the given folder is displayed on the page.
	 * Returns true / false based on the folder display on page
	 */
	public boolean isFolderDisplayed(String folderName) {
		boolean flag = false;
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".folderslist")), 20);
			List<WebElement> foldernames = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".folderslist"));
			for (int i = 0; i < foldernames.size(); i++) {
				String actualfoldername = foldernames.get(i).getText();
				if (actualfoldername.contains(folderName)) {
					flag = true;
					break;
				}
			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isFolderDisplayed <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}
	
	
	
	/*
	 * This method is used to rename the given folder. Takes folder name as parameter and performs the rename action
	 * Returns the renamed foldername as string.
	 */
	public String renameGivenFolderAndReturnFolderName(String folderName) throws Exception {
	try {
		
			String renameText = folderName + RandomUtils.getUniqueNumber();
			List<WebElement> renamefirstfolder = elementhandler.findElements(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".fav_listofrename"));		
			
			WebElement element = elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".folderslist"));
			Actions action = new Actions(driver);
	        action.moveToElement(element).build().perform();
			
			renamefirstfolder.get(0).click();
			Thread.sleep(2000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".renameinput")), 20);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renameinput")).clear();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renameinput")).sendKeys(renameText);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".renamesave")), 20);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renamesave")), 20);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".renamesave")), 20);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renamesave"));

			return renameText;

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : renameGivenFolderAndReturnFolderName <br>" + displayErrorMessage(e));
            return "";
		}

	}

	/*
	 * This method selects the first folder on the folders page and returns the foldername as String
	 */
	public String selectFirstFolderAndReturnFolderName() {
		String subfoldername = null;
		try {
			List<WebElement> foldername = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".folderslist"));
			subfoldername = foldername.get(0).getText();
			Thread.sleep(2000);
			//foldername.get(0).click();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : selectFirstFolderAndReturnFolderName <br>" + displayErrorMessage(e));

		}
		return subfoldername;
	}

	/*
	 * This method deletes the given folder. Takes the folder name as parameter to perform delete operation
	 */
	public void deleteGivenFolder(String folderNameToDelete) throws Exception {
	try {

			List<WebElement> deletefirstfolder = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".fav_listofdelete"));
			
			//Perform mouse over for the parent element to display the delete option
			WebElement element = elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".folderslist"));
			Actions action = new Actions(driver);
	        action.moveToElement(element).build().perform();
	            
			deletefirstfolder.get(0).click();
			
			if (IsPopUpWindowPresent()) {
				clickOnAlertPopUP();
			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : deleteGivenFolder <br>" + displayErrorMessage(e));
		}
	}

	
    /*
     * This method checks to see if the given folder is deleted
     * Returns true / false based on the deleted folder display on the page
     */
	public boolean isGivenFolderDeleted(String deletefoldername) {
	 try {
			List<WebElement> foldername = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".folderslist"));
			return foldername.get(0).getText().contains(deletefoldername);

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isGivenFolderDeleted <br>" + displayErrorMessage(e));
			return false;
		}

	}

}
