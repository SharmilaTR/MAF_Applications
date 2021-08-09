package com.trgr.quality.maf.pages;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class ProductsPage extends SearchPage {

	public ProductsPage(WebDriver driver) throws Exception {
		super(driver);
		WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productsheader")), 20);
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


	/*
	 * This method checks to see if the error block is displayed for no results search case
	 */
	public boolean isErrorBlockDisplayedForNoResults() {
		boolean flag = false;
		try {
			flag= elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".noresultmesg"))
					.isDisplayed();
			return flag;

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isErrorBlockDisplayedForNoResults <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	/*
	 * This method checks to see if the legislacaotab is displayed and is clickable.
	 */
	public void clicklegislacaotab() {

		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".legislacaotab")),
					20);
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".legislacaotab"))
					.isDisplayed();
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".legislacaotab"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clicklegislacaotab <br>" + displayErrorMessage(exc));

		}

	}

	/*
	 * This method checks to see if the ProdutosSolucoestab is displayed and is clickable.
	 */
	public void clickProdutosSolucoestab() {

		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ProdutosSolucoestab")),
							20);
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ProdutosSolucoestab"))
					.isDisplayed();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ProdutosSolucoestab"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickProdutosSolucoestab <br>" + displayErrorMessage(exc));

		}

	}

	/*
	 * This method checks to see if the clickProdutoEssenciaistab is displayed and is clickable.
	 */
	public void clickProdutoEssenciaistab() {

		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ProdutosEssenciaistab")),
							20);
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ProdutosEssenciaistab"))
					.isDisplayed();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ProdutosEssenciaistab"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickProdutoEssenciaistab <br>" + displayErrorMessage(exc));

		}

	}
}
