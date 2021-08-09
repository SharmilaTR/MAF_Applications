package com.trgr.quality.maf.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class LegislationPage extends SearchPage {

	public LegislationPage(WebDriver driver) throws Exception {
		super(driver);
	}

	/*
	 * Enter Index search on the Legislation page
	 */
	public void enterIndexSearchValue(String indexSearch) {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indexsearch"))
					.sendKeys(indexSearch);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterIndexSearchValue <br>" + displayErrorMessage(ex));

		}

	}

	/*
	 * Enter Article number to search with on the legislation page
	 */
	public void enterArticle(String articleVal) {

		try {
			Thread.sleep(5000);
			WebElement x = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".article"));
			Thread.sleep(5000);
			x.click();
			x.clear();
			x.sendKeys(articleVal);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterNumber <br>" + displayErrorMessage(ex));

		}
	}

	/*
	 * Enter number to search with on the legislation page
	 */
	public void enterNumber(String number) {
		try {
			elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".number"))
					.sendKeys(number);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterNumber <br>" + displayErrorMessage(ex));

		}

	}

}
