package com.trgr.quality.maf.pages;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class SuplementSearchPage extends SearchPage {

	public SuplementSearchPage(WebDriver driver) throws Exception {
		super(driver);
		WebDriverFactory.waitForElementUsingWebElement(driver,
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtn")),
				20);
	}

	/*
	 * Enter Norm number
	 */
	public void enterNormNumber(String normid) {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".suplement_normno"))
					.sendKeys(normid);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterNormNumber <br>" + displayErrorMessage(exc));

		}
	}

	/*
	 * Enter Index
	 */
	public void enterIndex(String index) {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".suplement_index"))
					.sendKeys(index);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterIndex <br>" + displayErrorMessage(exc));

		}
	}

	public void clickSearch() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtn"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSearch <br>" + displayErrorMessage(ex));

		}

	}

	public boolean isErrorBlockDisplayedForNoResults() {
		boolean flag = false;
		try {
			flag = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".noresultmesg"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isErrorBlockDisplayedForNoResults <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

}
