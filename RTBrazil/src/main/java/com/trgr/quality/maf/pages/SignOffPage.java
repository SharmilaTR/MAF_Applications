package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class SignOffPage extends BasePage {

	String keyword;
	boolean flag = false;

	public SignOffPage(WebDriver driver, String productUrl) throws IllegalArgumentException, IOException {
		super(driver);
	}

	public SignOffPage(WebDriver driver) throws Exception {
		super(driver);
	}

	public boolean issignoffpagedisplay() {

		flag = false;
		try {
			String keyword = elementhandler.getText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignoffText"));
			String acttext = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignoffText1");

			if (keyword.contains(acttext)) {
				flag = true;
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : issignoffpagedisplay <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean validateSignoffSummary() {
		boolean flag = false;
		try {
			keyword = elementhandler.getText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Signoffsummary"));
			String actmsg = PropertiesRepository
					.getString(".com.trgr.maf." + BaseTest.productUnderTest + ".Signoffsummary1");

			if (keyword.contains(actmsg)) {
				flag = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateSignoffSummary <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isNewsesssionlinkPresent() {
		try {
			Thread.sleep(3000);
			flag = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newsessionlink"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isNewsesssionlinkPresent <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void clikNewSession() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newsessionlink"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clikNewSession <br>" + displayErrorMessage(exc));

		}

	}

}
