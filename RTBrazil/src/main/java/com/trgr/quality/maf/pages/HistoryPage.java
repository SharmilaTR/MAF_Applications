package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class HistoryPage extends BasePage {

	public HistoryPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
	}

	/*
	 * This method checks for the History Page display Returns true / false
	 * based on the page display
	 */
	public boolean isHistoryPageDisplayed() {
		try {
			String historyLinkText = elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".historyicononhistorypage"));

			return historyLinkText.equalsIgnoreCase("Histórico");
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isHistoryPageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method checks if the History Page displayed with expected columns
	 * Returns true / false based on the element display
	 */
	public boolean isHistoryPageDisplayedWithExpectedDetails() throws InterruptedException {
		boolean flag;
		try {

			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toviewcolumnonhistorypage"))
					.isDisplayed();
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".namecolumnonhistorypage"))
					.isDisplayed();
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".custidcolumnonhistorypage"))
					.isDisplayed();
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastmodifieddateonhistorypage"))
					.isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString(
					"com.trgr.maf." + BaseTest.productUnderTest + ".daysuntilhistoryisdeletedcolumnonhistorypage"))
					.isDisplayed();
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deletecolumnonhistorypage"))
					.isDisplayed();
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renamecolumnonhistorypage"))
					.isDisplayed();
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".startanewhistorylink"))
					.isDisplayed();
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliverylinkonhistorypage"))
					.isDisplayed();
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isHistoryPageDisplayedWithExpectedDetails <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method starts new history with given customer id and history name
	 */
	public void startNewHistory(String customerid, String name) {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".startanewhistorylink"));
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newhistoryenterclientidtextbox"))
					.sendKeys(customerid);
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newhistoryenternametextbox"))
					.sendKeys(name);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newhistorysavebutton"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : startNewHistory <br>" + displayErrorMessage(exc));
		}

	}

	/*
	 * This method is used to return back to history page upon actions like
	 * create / edit / delete history
	 */
	public void returnBackToHistoryPage() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".backtohistorypagelink"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : returnBackToHistoryPage <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks 'Yes' button on Delete History confirmation.
	 */
	public boolean clickDeleteConfirmButton() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deletehistoryyesbutton"));
			return true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDeleteConfirmButton <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method finds the row number of 'eventToFind' from the list of
	 * history events checks in sequential order and returns the first match
	 * returns row number, or -1 on failure.
	 */
	public int getHistoryRowNumber(String eventToFind) throws InterruptedException {
		int eventRowNumber = -1;

		try {
			List<WebElement> rows = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			for (int rowNum = 2; rowNum <= rows.size(); rowNum++) {
				String nameColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ rowNum + "]" + "/td[@class='nameHeader']";
				String eventName = elementhandler.findElement(nameColumXpath).getText();
				if (eventName.equals(eventToFind)) {
					eventRowNumber = rowNum;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getHistoryRowNumber <br>" + displayErrorMessage(exc));
			eventRowNumber = -1;
		}
		return eventRowNumber;

	}

	/*
	 * This method finds the presence of 'Delete' link for the item in list of
	 * history events returns true on success
	 */
	public boolean isDeleteLinkPresentForItem(int eventRowNumber) throws InterruptedException {
		boolean deleteLinkExistForEvent = false;

		try {
			String deleteColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='deleteHeader']";
			String deleteLinkText = elementhandler.findElement(deleteColumXpath).getText();
			String deleteLinkExpected = "Deletar";
			if (deleteLinkExpected.equals(deleteLinkText))
				deleteLinkExistForEvent = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDeleteLinkPresentForItem <br>" + displayErrorMessage(exc));
			deleteLinkExistForEvent = false;
		}
		return deleteLinkExistForEvent;
	}

	/*
	 * This method finds the presence of 'Reset' link for the item in list of
	 * history events returns true on success
	 */
	public boolean isResetLinkPresetForItem(int eventRowNumber) {
		boolean resetLinkExistForEvent = false;

		try {
			String resetColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='lifetimeHeader']";
			String resetLinkText = elementhandler.findElement(resetColumXpath).getText();
			String resetLinkExpected[] = { "Reiniciar", "Resetear", "Reset" };
			if (resetLinkText != null)
				for (String expectedText : resetLinkExpected)
					if (resetLinkText.endsWith(expectedText)) {
						resetLinkExistForEvent = true;
						break;
					}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isResetLinkPresetForItem <br>" + displayErrorMessage(exc));
			resetLinkExistForEvent = false;
		}
		return resetLinkExistForEvent;
	}

	/*
	 * This method finds the presence of 'Rename' link for the item in list of
	 * history events returns true on success
	 */
	public boolean isRenameLinkPresentForItem(int eventRowNumber) {
		boolean renamelinkpresent = false;
		try {
			String renamecolumnxpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='renameTrail']/a";
			String renamelinktext = elementhandler.findElement(renamecolumnxpath).getText();
			String renamelinktextexp = "Renomear";
			if (renamelinktext != null && (renamelinktext.contains(renamelinktextexp))) {
				renamelinkpresent = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isResetLinkPresetForItem <br>" + displayErrorMessage(exc));
			renamelinkpresent = false;
		}
		return renamelinkpresent;

	}

	/*
	 * This method finds the presence of '14 Reset' for the item in list of
	 * history events. (reset after 14 days.) returns true on success
	 */
	public boolean isDeleteAfterTwoWeeks(int eventRowNumber) {
		boolean resetToTwoWeek = false;

		try {
			String resetColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='lifetimeHeader']";
			String resetLinkText = elementhandler.findElement(resetColumXpath).getText();
			String resetLinkExpected[] = { "14 Reiniciar", "14 Resetear", "14 Reset" };
			if (resetLinkText != null)
				for (String expectedText : resetLinkExpected)
					if (resetLinkText.contains(expectedText)) {
						resetToTwoWeek = true;
						break;
					}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isResetAfterTwoWeeks <br>" + displayErrorMessage(exc));
			resetToTwoWeek = false;
		}
		return resetToTwoWeek;
	}

	/*
	 * This method clicks 'Reset' link for the item in 'eventRowNumber' for the
	 * list of history events
	 */
	public void clickResetLinkForItem(int eventRowNumber) {
		try {
			String resetColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='lifetimeHeader']/a";
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(resetColumXpath));
			elementhandler.clickElement(resetColumXpath);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickResetLinkForItem <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks on the Rename link for given history record
	 */
	public void clickRenameHistoryForItem(int eventRowNumber) {
		try {
			String renameColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='renameTrail']/a";
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(renameColumXpath));
			elementhandler.clickElement(renameColumXpath);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickRenameHistoryForItem <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks 'Delete' link for the item in 'eventRowNumber' for the
	 * list of history events
	 */
	public void clickDeleteLinkForItem(int eventRowNumber) {
		try {
			String deleteColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='deleteHeader']/a";
			elementhandler.clickElement(deleteColumXpath);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDeleteLinkForItem <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks if 'eventToFind' exist the list of history events
	 * returns true on success
	 */
	public boolean isHistoryNamePresent(String eventToFind) throws InterruptedException {
		boolean historyFound = false;
		try {
			List<WebElement> rows = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			System.out.println(rows.size());
			for (int rowNum = 2; rowNum <= rows.size(); rowNum++) {
				String nameColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ rowNum + "]" + "/td[@class='nameHeader']";
				String eventName = elementhandler.findElement(nameColumXpath).getText();
				if (eventName.equals(eventToFind)) {
					historyFound = true;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isHistoryItemPresent <br>" + displayErrorMessage(exc));
			historyFound = false;
		}
		return historyFound;
	}

	/*
	 * This method finds the presence of 'Delete' link for the item in list of
	 * history events returns History Name
	 */
	public String getFirstHistoryWithDeleteLink() {
		String firstHistoryWithDeleteLink = null;
		try {
			List<WebElement> rows = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			for (int rowNum = 2; rowNum <= rows.size(); rowNum++) {
				if (isDeleteLinkPresentForItem(rowNum)) {
					String nameColumXpath = PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
							+ rowNum + "]" + "/td[@class='nameHeader']";
					firstHistoryWithDeleteLink = elementhandler.findElement(nameColumXpath).getText();
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : getFirstHistoryWithDeleteLink <br>" + displayErrorMessage(exc));
			firstHistoryWithDeleteLink = null;
		}
		return firstHistoryWithDeleteLink;
	}

	/*
	 * This method finds the name of the event for 'rowNum' in the list of
	 * history events returns name, or null on failure.
	 */
	public String getHistoryNameAtRow(int rowNum, List<WebElement> rowsList) {
		String historyName = null;

		try {
			if ((rowNum >= 2) && (rowNum <= rowsList.size())) {
				String nameColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ rowNum + "]" + "/td[@class='nameHeader']";
				historyName = elementhandler.findElement(nameColumXpath).getText();
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getHistoryNameAtRow <br>" + displayErrorMessage(exc));
		}
		return historyName;
	}

	/*
	 * This method finds the name of the event for 'rowNum' in the list of
	 * history events returns name, or null on failure.
	 */
	public String getHistoryNameAtRow(int rowNum) {
		String historyName = null;

		try {
			List<WebElement> rowsList = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			if ((rowNum >= 2) && (rowNum <= rowsList.size())) {
				String nameColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ rowNum + "]" + "/td[@class='nameHeader']";
				historyName = elementhandler.findElement(nameColumXpath).getText();
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getHistoryNameAtRow <br>" + displayErrorMessage(exc));
		}
		return historyName;
	}

	/*
	 * This method finds the total number of events in the list of history
	 * events returns number based on the number of history records on the page
	 */
	public int getCountOfHistoryItems() {
		int totalItems = 0;
		try {
			List<WebElement> rowsList = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			totalItems = rowsList.size() - 1;

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : countOfHistoryItems <br>" + displayErrorMessage(exc));
		}
		return totalItems;
	}

	/*
	 * This method clicks 'Rename' link for the item in 'eventRowNumber' for the
	 * list of history events
	 */
	public void clickRenameLinkForItem(int eventRowNumber) {
		try {
			String renameColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='renameTrail']/a";
			elementhandler.clickElement(renameColumXpath);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickRenameLinkForItem <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks the presence of 'Rename' title in the Rename History
	 * page
	 */
	public boolean isRenameHistoryTitlePresent() {
		boolean titlePresent = false;
		try {
			boolean elementPresent = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rename_history_title")));
			if (elementPresent) {
				String title = elementhandler.getText(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rename_history_title"));
				if (title != null && title.contains("Renomear"))
					titlePresent = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isRenameHistoryTitlePresent <br>" + displayErrorMessage(exc));
		}
		return titlePresent;
	}

	/*
	 * This method writes the name in the edit box
	 */
	public void enterGivenTextOnRenameField(String name) {

		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".rename_history_name_inputbox");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.writeText(locator, name);
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterGivenTextOnRenameField<br>" + displayErrorMessage(exc));

		}
	}

	/*
	 * This method clicks 'Save' button in Rename History
	 */
	public void clickSaveRenameButton() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rename_history_save_button"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSaveRenameButton <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks 'Cancel' button in Rename History
	 */
	public void clickCancelRenameButton() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rename_history_cancel_button"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCancelRenameButton <br>" + displayErrorMessage(exc));
		}
	}

	// Click on Delivery options
	public void clickOnDeliveryIcon() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliveryicononhistorypage"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickonDeliveryIcon <br>" + displayErrorMessage(exc));
		}
	}

	// Click on Print Delivery Option
	public void clickOnPrintDeliveryOption() {
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".printdeliveryoption")),
							20);
			elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".printdeliveryoption")).click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickprintDeliveryOption <br>" + displayErrorMessage(exc));
		}
	}

	// Click on Email Delivery Option
	public boolean clickOnEmailDeliveryOption() {
		boolean flag = false;
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".emaildeliveryoption")),
							20);
			elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".emaildeliveryoption")).click();
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickemailDeliveryOption <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	// Click on Save Delivery Option
	public boolean clickOnSaveDeliveryOption() {
		boolean flag = false;
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".savedeliveryoption")),
							20);
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".savedeliveryoption"))
					.click();
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clicksaveDeliveryOption <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method selects the email as delivery option and enters the parameters for Email Id and Keyword 
	 * 
	 */
	public void sendEmailDetails(String emailTo, String keyword) {

		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".tofieldemaildeliveryoption"))
					.clear();
			elementhandler.writeText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".tofieldemaildeliveryoption"), emailTo);
			elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".subjectfieldemaildeliveryoption"))
					.clear();
			elementhandler.writeText(PropertiesRepository.getString(
					"com.trgr.maf." + BaseTest.productUnderTest + ".subjectfieldemaildeliveryoption"), keyword);
			elementhandler.writeText(
					PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".messageboxemaildeliveryoption"),
					"Email sent to:" + emailTo);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : sendEmailDetails <br>" + displayErrorMessage(exc));

		}

	}

	/*
	 * This method clicks Save on the Email delivery option.
	 */
	public void clickSaveOnEmailDelivery() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".emailsavebutton");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			
			elementhandler.clickElement(locator);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSaveOnEmailDelivery <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method selects the file format as RTF
	 */
	public boolean isFileFormatSelectedIsRTF() {
		boolean flag = false;
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository.getString(
									"com.trgr.maf." + BaseTest.productUnderTest + ".rtfradiobtnemaildeliveryoption")),
							20);
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rtfradiobtnemaildeliveryoption"))
					.isSelected()) {
				flag = true;
			} else {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rtfradiobtnemaildeliveryoption"));
				flag = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFileFormatSelectedIsRTF <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method selects the file format as PDF
	 */
	public boolean isFileFormatSelectedIsPDF() {
		boolean flag = false;
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository.getString(
									"com.trgr.maf." + BaseTest.productUnderTest + ".pdfradiobtnemaildeliveryoption")),
							20);
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".pdfradiobtnemaildeliveryoption"))
					.isEnabled()) {
				flag = true;
			} else {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".pdfradiobtnemaildeliveryoption"));
				flag = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFileFormatSelectedIsPDF <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	// click on come back to return back to the History Page
	public void clickOnReturnToHistoryFromDelivery() {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".returntohistoryfrmdelivery")),
					20);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".returntohistoryfrmdelivery"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnReturnToHistoryFromDelivery <br>" + displayErrorMessage(exc));
		}

	}

	/*
	 * This method checks to see if the email confirmation message is displayed
	 * Returns true / false based on the element display
	 */
	public boolean isEmailSentMsgDisplayed() {
		boolean flag = false;
		try {
			Thread.sleep(4000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".successmessage")),
					40);
			String actmsg = elementhandler.getText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".successmessage"));
			Thread.sleep(2000);
			System.out.println("actmsg---information-" + actmsg);
			if ((actmsg != null) && actmsg.contains(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".emailsentmessage"))) {
				flag = true;
			} else
				flag = false;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isEmailSentMsgDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks to see if the confirmation message is displayed upon sucessful delivery
	 */
	public boolean isDeliverySuccessMsgDisplayed() {
		boolean flag = false;
		try {
			Thread.sleep(4000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".successmessage")),
					40);
			String actmsg = elementhandler.getText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".successmessage"));
			Thread.sleep(4000);
			System.out.println("actmsg---information-" + actmsg);
			if ((actmsg != null) && actmsg.contains(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliverysavemessage"))) {
				flag = true;
			} else
				flag = false;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDeliverySuccessMsgDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
}
