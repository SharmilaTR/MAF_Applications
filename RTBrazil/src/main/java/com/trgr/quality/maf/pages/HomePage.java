package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		if (isAcceptTermsAndConditionsDisplayed())
		{
			clickOnAcceptTermsAndConditions();
		}
	}

	/*
	 * This method checks to se if the home page is displayed Returns true /
	 * false based on the page display
	 */
	public boolean isHomePageDisplayed() {
		try {
			Thread.sleep(500);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab")), 40);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productlogo")), 40);
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productlogo"))
					.isDisplayed()
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"))
							.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isHomePageDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	/*
	 * This method checks to see if the Accept Terms and Conditions option is displayed on the page
	 * Returns true / false based on the element display
	 */
	public boolean isAcceptTermsAndConditionsDisplayed() {
		try {
			boolean flag = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".termsandcondition")).isDisplayed();
		   return flag;
		} catch (Exception ex) {
			//extentLogger.log(LogStatus.INFO, "Error in : isAcceptTermsAndConditionsDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	/*
	 * This method clicks on the Accept Terms and Conditions field
	 */
	public void clickOnAcceptTermsAndConditions() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".termsandcondition"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnAcceptTermsAndConditions <br>" + displayErrorMessage(ex));
		}

	}

	/*
	 * This method checks to see if the freeword and index search fields are reset
	 * Returns true if the elements are empty else returns false
	 */
	public boolean isQuickSearchFieldsReset() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage"))
					.getText().contains("")
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indexsearch"))
							.getText().contains("");

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isQuickSearchFieldsReset <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * Clicks Sign Off link on the home page
	 */
	public SignOffPage clickSignOff() throws Exception {
		try {
			if (IsPopUpWindowPresent()) {
				clickOnAlertPopUP();
			}

			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productlogo")), 40);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab")), 40);
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignOff")),
					40);
			Thread.sleep(500);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignOff"));
			return new SignOffPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSignOff <br>" + displayErrorMessage(exc));
			driver.manage().deleteAllCookies();
			driver.navigate().refresh();
			driver.get(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".signoffurl"));
			return new SignOffPage(driver);
		}

	}

	/*
	 * Checks to see if the Sign Off link is displayed on the home page
	 */
	public boolean isSignOffLinkDisplayed() {
		boolean flag = true;
		try {
			elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignOff"))
					.isDisplayed();
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSignOffLinkDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	
	/*
	 * Enter given freeword on the search field on the homepage
	 */
	public void enterGivenFreeword(String freeword) {
		try {
		
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordtxtbox")),
					40);
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordtxtbox"))
					.sendKeys(freeword);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterGivenFreeword <br>" + displayErrorMessage(exc));
		}

	}

	
	public HomePage openHomepage() throws Exception {
		try {
			Thread.sleep(1000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productlogo")), 40);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab")), 40);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"));
			return new HomePage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : openHomepage <br>" + displayErrorMessage(ex));
			return null;
		}

	}

	/*
	 * Click on Clear to reset the search fileds on the home page
	 */
	public void clickOnClear() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonhomepage"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnClear <br>" + displayErrorMessage(ex));
		}

	}
	
	public void clickOnNaturalLanguage() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".naturallanguagehomepage"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnClear <br>" + displayErrorMessage(ex));
		}

	}
	
	public void enterGivenNaturalLanguage(String naturalLanguage) {
		try {
		
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".naturallanguageSearchPage")),
					40);
			elementhandler
			.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".naturallanguageSearchPage")).clear();
			
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".naturallanguageSearchPage"))
					.sendKeys(naturalLanguage);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterGivenFreeword <br>" + displayErrorMessage(exc));
		}

	}

	/*
	 * Enters the given search string on the Index Value field on home page
	 */
	public void enterIndexValue(String thematicSearchString) {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indexsearch"))
					.sendKeys(thematicSearchString);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterIndexValueOnQuickSearch <br>" + displayErrorMessage(ex));
		}

	}

	/*
	 * This method checks to see of the freeword and index search fields on the home page 
	 * are not retaining the previously entered values
	 * Returns true / false based on the elements are reset
	 */
	public boolean isModifySearchRetainsSearchStrings() {
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage"))
					.getAttribute("value").length() > 0
					| elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indexsearch"))
							.getAttribute("value").length() > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isModifySearchRetainsSearchStrings <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	public boolean searchResultsOptionsDisplayedOnQuickSearch() {
		try {
			return WebDriverFactory.isDisplayed(driver,
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indexsearchradiobtn")))
					&& WebDriverFactory.isDisplayed(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".naturallangradiobtn")))
					&& WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contextinsearchresults")));
		} catch (Exception ex) {
			return false;
		}
	}

	public DoctrineSearchPage OpenDoctrinePage() throws Exception {
		try {
			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage")))) {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".doctrine"));
				return new DoctrineSearchPage(driver);
			}
		} catch (Exception ex) {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"));
			Thread.sleep(500);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".doctrine"));

		}
		return new DoctrineSearchPage(driver);

	}

	public LegislationPage OpenLegislationPage() throws Exception {
		try {
			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage")))) {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".legislation"));
				return new LegislationPage(driver);
			}
		} catch (Exception ex) {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"));
			Thread.sleep(500);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".legislation"));

		}
		return new LegislationPage(driver);
	}

	public JurisprudencePage OpenJurisprudencePage() throws Exception {
		try {
			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage")))) {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudence"));
				return new JurisprudencePage(driver);
			}
		} catch (Exception ex) {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"));
			Thread.sleep(500);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudence"));

		}
		return new JurisprudencePage(driver);
	}

	// =====================================================================

	public AlertPage openAlertsPage() {
		try {
			clickonServicesLink();
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".myalertslink"));
			Thread.sleep(2000);
			return new AlertPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonAlertLink <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	public void refresh() {
		try {
			driver.navigate().refresh();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : refresh <br>" + displayErrorMessage(exc));
		}
	}

	public void clickonServicesLink() {
		try {
			if (IsPopUpWindowPresent()) {
				clickOnAlertPopUP();
			}
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab")), 40);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productlogo")), 40);
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".services")),
					40);
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".services"));
			if (IsPopUpWindowPresent()) {
				clickOnAlertPopUP();
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickonServicesLink <br>" + displayErrorMessage(exc));
		}
	}

	// ====================================================

	public HistoryPage ClickonHistoryLink() throws IllegalArgumentException, IOException {
		try {
			clickonServicesLink();
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".historylinkverify"));
			Thread.sleep(2000);
			return new HistoryPage(driver);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonHistoryLink <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	// ==============================================================================

	public boolean verifywidgetsinhomepage() {
		boolean widgetsFound = false;
		try {

			String Expected_widget[] = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expectedwidgets").split(",");

			List<WebElement> Actual_Widget = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widgetonhomepage"));

			for (int i = 0; i < Expected_widget.length; i++) {
				widgetsFound = false;
				for (int j = 0; j < Actual_Widget.size(); j++) {
					if (WebDriverFactory.isDisplayed(driver, Actual_Widget.get(j))) {
						String actualitem = Actual_Widget.get(j).getText();
						if (Expected_widget[i].equals(actualitem.trim())) {
							widgetsFound = true;
							break;
						} else if (j == Actual_Widget.size()) {
							widgetsFound = false;
							break;
						}
					}
				}
				if (!widgetsFound)
					break;
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : verifywidgetsinhomepage <br>" + displayErrorMessage(exc));
			widgetsFound = false;
		}
		return widgetsFound;
	}

	public boolean isSearchTabsPresent() {
		boolean flag = false;
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"))
					.isDisplayed()
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".doctrine"))
							.isDisplayed())
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".legislation"))
							.isDisplayed())
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudence"))
							.isDisplayed())
					&& (elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Sumulas"))
							.isDisplayed())
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Produtos"))
							.isDisplayed())
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Noticias"))
							.isDisplayed())
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".JurisTendencia"))
							.isDisplayed())
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Trabalhista"))
							.isDisplayed())
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Administrativo"))
							.isDisplayed());
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchTabsPresent <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean headerSectionLinksPresent() {
		boolean flag = false;
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".PrimeiraHora"))
					.isDisplayed()
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Tutoriais"))
							.isDisplayed())
					&& (elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".help"))
							.isDisplayed())
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".services"))
							.isDisplayed())
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignOff"))
							.isDisplayed());
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : headerSectionLinksPresent <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isProductNameandProductLogoPresent() {
		boolean flag = false;
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productlogo"))
					.isDisplayed() &&

					(elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productname"))
							.isDisplayed());
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isProductNameandProductLogoPresent <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isLogedinUserDetailsPresent() {
		boolean flag = false;
		try {
			return elementhandler
					.getText(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginuserdetails"))
					.contains(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".welcometext"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLogedinUserDetailsPresent <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean verifyFooterSectioninHomePage() {
		{
			boolean flag = false;
			try {
				return elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".onlinesupport"))
						.isDisplayed()
						&& (elementhandler
								.getElement(PropertiesRepository
										.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus"))
								.isDisplayed())
						&& (elementhandler
								.getElement(PropertiesRepository
										.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productinformation"))
								.isDisplayed())
						&& (elementhandler
								.getElement(PropertiesRepository
										.getString("com.trgr.maf." + BaseTest.productUnderTest + ".copyright"))
								.isDisplayed());
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO,
						"Error in : verifyFooterSectioninHomePage <br>" + displayErrorMessage(exc));
				flag = false;
			}
			return flag;
		}
	}

	public boolean isCustomerCareContactNoPresentinFooterSection() {
		boolean flag = false;
		try {
			return elementhandler
					.getText(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".customercarecontactno"))
					.contains(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".customercarecontactnumber"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLogedinUserDetailsPresent <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public AdministrativePage OpenAdministrativePage() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Administrativo"));
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtn")),
					20);
			return new AdministrativePage(driver);
		} catch (Exception exc) {
			// Fail
			extentLogger.log(LogStatus.INFO, "Error in : OpenAdministrativePage <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	public LaborPage OpenLaborPage() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Trabalhista"));
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtn")),
					20);
			return new LaborPage(driver);
		} catch (Exception exc) {
			// Fail
			extentLogger.log(LogStatus.INFO, "Error in : OpenLaborPage <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	public PreferencePage clickPreferenceLink() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencelink")),
					30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencelink"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPreferenceLink <br>" + displayErrorMessage(exc));

		}
		return new PreferencePage(driver);
	}

	public FavoritesPage clickFavoriteLink() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".favoriteslink")),
					30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".favoriteslink"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFavoriteLink <br>" + displayErrorMessage(exc));

		}
		return new FavoritesPage(driver);
	}

	public SuplementSearchPage OpenSuplementPage() throws Exception {
		try {
			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".suplement")))) {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".suplement"));

			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : OpenSuplementPage <br>" + displayErrorMessage(exc));
			return null;
		}
		return new SuplementSearchPage(driver);

	}

	public DigitalLibraryPage OpenDigitalLibraryPage() throws Exception {
		try {
			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".digitallibrary")))) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".digitallibrary"));

			}
		} catch (Exception ex) {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"));
			Thread.sleep(500);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".digitallibrary"));

		}
		return new DigitalLibraryPage(driver);
	}

	/*
	 * This method checks to see if no search results element is displayed on the page
	 * Returns true / false based on the element display
	 */
	public boolean noSearchResultsDisplayed() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".noresults"))
					.isDisplayed();
		} catch (Exception exc) {
			return false;

		}
	}

	/*
	 * This method checks to see if the error block is displayed on the page
	 * Returns true / false based on the element display
	 */
	public boolean isErrorBlockDisplayed() {
		try {

			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".errorblock"))
					.isDisplayed();
		} catch (Exception exc) {
			return false;

		}
	}

	/*
	 * Click on the Products link and Return the Products Page handle
	 */
	public ProductsPage openProductsPage() throws Exception {
		try {
			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".products")))) {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".products"));

			}
			return new ProductsPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : OpenSuplementPage <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	/*
	 * Clicks on the Help link on the home page
	 */
	public void clickHelpLink() throws Exception {
		try {
			
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".help"));
			
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helplink")),
					30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helplink"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickHelpLink <br>" + displayErrorMessage(exc));

		}

	}

	/*
	 * This method checks to see the help text displayed on the Help page as expected
	 * Returns true / false based on the element display
	 */
	public boolean isHelpPageDisplayedAsExpected() {

		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helptext")).isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isHelpPageDisplayedAsExpected <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	/*
	 * This method clicks on the Help online support option
	 */
	public void clickHelpOnlineSupport() throws Exception {
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helponlinesupport")),
							30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helponlinesupport"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickHelpOnlineSupport <br>" + displayErrorMessage(exc));

		}

	}

	/*
	 * Checks to see if the Chat Page is displayed as expected with the expectced title on the page
	 */
	public boolean isChatPageDisplayedAsExpected() {
		boolean flag = false;
		try {
			Thread.sleep(1000);
			driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
			WebElement locator = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".titlechat"));
			flag = WebDriverFactory.isDisplayed(driver, locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateChatpage <br>" + displayErrorMessage(exc));
			flag = false;
		} finally {
			try {
				// Switches to main window
				driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
				Thread.sleep(1000);
			} catch (Exception ex) {
			}
		}
		return flag;
	}
	
	/*
	 * This method selects the check box to View Context details on the search results
	 */
	public void clickViewContextOnSearchResult() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".viewcontextcheckbox"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickViewContextOnSearchResult <br>" + displayErrorMessage(exc));

		}
	}

	
	/*
	 * This method Clicks on the News Page option and Returns handle to NewsPage
	 */
	public NewsSearchPage OpenNewsPage() throws Exception {
		try {
			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Noticias")))) {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Noticias"));

			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : OpenNewsPage <br>" + displayErrorMessage(exc));
			return null;
		}
		return new NewsSearchPage(driver);

	}

	/*
	 * This method Clicks on the Jurisdiction Page option and Returns handle to Jurisdiction
	 */
	public JurisdictionPage OpenJurisdictionPage() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".juridictiontab"));
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtn")),
					20);
			return new JurisdictionPage(driver);
		} catch (Exception exc) {

			extentLogger.log(LogStatus.INFO, "Error in : OpenJuridictionPage <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	// TO verify fields of search on search page
	
		 public boolean verifySearchWidgetFields()
		 {
			  
			 boolean flag =false;
			try {  flag=elementhandler
				.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordtxtbox"))
				.isDisplayed()&& elementhandler
				.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indexsearch"))
				.isDisplayed()&&
			 elementhandler
				.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonhomepage"))
				.isDisplayed()&&
			 elementhandler
				.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage"))
				.isDisplayed()&&
			 elementhandler
				.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indexsearchradiobtn"))
				.isDisplayed()&&
			 elementhandler
				.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".naturallangradiobtn"))
				.isDisplayed()&&
			 elementhandler
				.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contextinsearchresults"))
				.isDisplayed();
			}
			catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : verifySearchWidgetFields <br>" + displayErrorMessage(exc));
				flag = false;
			}
			return flag;
		 }
}