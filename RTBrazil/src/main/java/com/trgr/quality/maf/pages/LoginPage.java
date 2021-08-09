package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class LoginPage extends BasePage
{
		
	public LoginPage(WebDriver driver, String productUrl) throws IOException, IllegalArgumentException 
	{
		super(driver);
		driver.get(productUrl);
		
	}
	
	public void loadLoginPage(String productUrl) throws IOException, IllegalArgumentException 
	{
		
		driver.get(productUrl);
		WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".LoginButton")), 20);

	}
	
	public HomePage Login(String Username, String Password) throws IllegalArgumentException, IOException
    {
		try{
	   	 
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".LoginUsername")), 40);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".LoginPassword")), 40);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".LoginButton")), 40);
			elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".LoginUsername"),Username);
	   	 	elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".LoginPassword"),Password);
	   	 	elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".LoginButton"));
	   	 	Thread.sleep(1000);
		   	if(isMarketingPopUpDisplayed())
		   	 {
		   		closeMarketingPopUpOnHome();
		   	 }
		   	return new HomePage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : Login <br>"+displayErrorMessage(exc));
			return null;
	}
    }

	public boolean invalidCredentialsMsgDisplayed(Boolean isOnePassEnabled)
	{
		try{
			String errorMsg = "";
			if(isOnePassEnabled)
				 errorMsg = elementhandler.getText(PropertiesRepository.getString(("com.trgr.maf." + BaseTest.productUnderTest+".onepassInvalidLoginMsg")));
			else
				 errorMsg = elementhandler.getText(PropertiesRepository.getString(("com.trgr.maf." + BaseTest.productUnderTest+".InvalidLoginMsg")));
				switch(BaseTest.productUnderTest)
				{
					case "rtbrazil":
						return errorMsg.contains("Por favor")|errorMsg.contains("Vuelva a intentarlo");
					 default:
						return false;
				}
				
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : invalidCredentialsMsgDisplayed <br>"+displayErrorMessage(exc));
			return false;
		}
			
	}

	public boolean SelectSaveUsernameAndPassword(Boolean isOnePassEnabled)
	{
		try{
			if(isOnePassEnabled){
				elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".onepasssaveboth"));
				return true;
			}else{
				WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".saveboth")), 40);
				elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".saveboth"));
				return true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : SelectSaveUsernameAndPassword <br>"+displayErrorMessage(exc));
			return false;
			
		}
	}
	
	public void SelectSaveUsername(Boolean isOnePassEnabled)
	{
		try{
			
				WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".onepasssaveusernameonly")), 40);
				elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".onepasssaveusernameonly"));
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : SelectSaveUsername <br>"+displayErrorMessage(exc));
			
		}
	}
	
	public void Selectcustomerid()
	{
		String Randomnumber="";
		try{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
			Randomnumber = "Test"+"_"+dtf.format(localDate);
			
			 elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".customerid"),Randomnumber);
			 
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : Selectcustomerid <br>"+displayErrorMessage(exc));
			
		}
		
	}

	public Boolean isUsernameAndPasswordSaved(String username, String password) 
	{
		try{
			
			if(username.isEmpty()){
				return elementhandler.getTextFromValueAttribute(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".LoginUsername")).isEmpty();
			}else{
				return elementhandler.getTextFromValueAttribute(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".LoginUsername")).contains(username)
			   &&	elementhandler.getTextFromValueAttribute(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".LoginPassword")).contains(password);
			}
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isUsernameAndPasswordSaved <br>"+displayErrorMessage(exc));
			return false;
			
		}	
	   	 
	}
	
	public Boolean isUsernameSaved(String username, String password) 
	{
		try{
			
			
				return elementhandler.getTextFromValueAttribute(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".LoginUsername")).contains(username)
			   &&	elementhandler.getTextFromValueAttribute(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".LoginPassword")).contains("");
			
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isUsernameSaved <br>"+displayErrorMessage(exc));
			return false;
			
		}	
	   	 
	}
	
	public boolean isOnePassSignOnEnbaled() throws Exception
	{
		try
		{			
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".onepassloginbtn")).isDisplayed();
		}
		catch(Exception exc)
		{
			
			return false;
		}		
		
	}
	
	public HomePage Login(String Username, String Password, boolean isOnePassEnabled) throws IllegalArgumentException, IOException
    {
		try{
		if(isOnePassEnabled)
		{
			 elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".onepassusernametxtbox"),Username);
		   	 elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".onepasspwdtxtbox"),Password);
		   	 JavascriptExecutor jse = (JavascriptExecutor) this.driver;
		     jse.executeScript("window.scrollBy(0,250)", "");
		   	 elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".onepassloginbtn"));
		   /*	if(isMarketingPopUpDisplayed())
		   	 {
		   		closeMarketingPopUpOnHome();
		   	 }*/
		   	 return new HomePage(driver);
		}
		else
		{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".LoginButton")), 40);
			 elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".LoginUsername"),Username);
		   	 elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".LoginPassword"),Password);
		   	 JavascriptExecutor jse = (JavascriptExecutor) this.driver;
		     jse.executeScript("window.scrollBy(0,250)", "");
		   	 elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".LoginButton"));
		   	 if(isMarketingPopUpDisplayed())
		   	 {
		   		closeMarketingPopUpOnHome();
		   	 }
		   	 return new HomePage(driver);
		}
	
		}catch(Exception exc)
		{
			extentLogger.log(LogStatus.INFO, "Error in : HomePage driver- Login <br>"+displayErrorMessage(exc));
			return null;
		}
    }
	
	public boolean isMarketingPopUpDisplayed() 
	{
		try{
			return WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".marketingpopUp")), 40).isDisplayed();
			/*String locator = PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".marketingpopUp");
			element = elementhandler.getElement(locator);
			return WebDriverFactory.isDisplayed(driver, element);*/
		}catch(Exception exc){
			return false;
		}
	}
	
	public void closeMarketingPopUpOnHome() 
	{
		try{
			if(WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".marketingpopUpclose")), 40).isDisplayed())
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".marketingpopUpclose")).click();
				
				/*String locator = PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".marketingpopUpclose");
				element = elementhandler.getElement(locator);
				if(WebDriverFactory.isDisplayed(driver, element))
					elementhandler.clickElement(locator);*/
			}catch(Exception exc){
				//Normal click fails. Try javascript click
				//((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			}
	}

	
	public Boolean validateforgotmypassword() 
	{
		try{
			
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".forgotmypassword")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".forgotmypassword")).click();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateforgotmypasswordfield")).isDisplayed();
			driver.navigate().back();
			return true;
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateforgotmypassword <br>"+displayErrorMessage(exc));
			return false;
			
		}	
	   	 
	}
	
	public Boolean validatechangemypassword() 
	{
		try{
			
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".changemypassword")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".changemypassword")).click();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validatechangemypasswordfield")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validatechangemypasswordfields")).isDisplayed();
			driver.navigate().back();
			return true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validatechangemypassword <br>"+displayErrorMessage(exc));
			return false;
			
		}	
	   	 
	}
	
	public Boolean validateonepassaccount() 
	{
		try{
			
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassaccount")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassaccount")).click();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".onepassloginbtn")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".onepasssaveboth")).isDisplayed();
			driver.navigate().back();
			return true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateonepassaccount <br>"+displayErrorMessage(exc));
			return false;
			
		}	
	   	 
	}
	
	public Boolean validateonepassforgotusername() 
	{
		try{
			
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassforgotusernameandpasswordlinks")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassforgotusername")).click();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassforgotusernamepageandpassword")).isDisplayed();
			driver.navigate().back();
			return true;
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateonepassforgotusername <br>"+displayErrorMessage(exc));
			return false;
			
		}	
	   	 
	}
	public Boolean validateonepassforgotpassword() 
	{
		try{
			
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassforgotusernameandpasswordlinks")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassforgotpassword")).click();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassforgotusernamepageandpassword")).isDisplayed();
			driver.navigate().back();
			return true;
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateonepassforgotpassword <br>"+displayErrorMessage(exc));
			return false;
			
		}	
	   	 
	}
	
	public Boolean validateonepassrtonlinelink() 
	{
		try{
			
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassRtonlinelink")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassRtonlinelink")).click();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".changemypassword")).isDisplayed();
			driver.navigate().back();
			return true;
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateonepassrtonlinelink <br>"+displayErrorMessage(exc));
			return false;
			
		}	
	   	 
	}
	public Boolean validateonepasscreateprofile() 
	{
		try{
			
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepasspassprofilelink")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepasscreateprofile")).click();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassprofilepage")).isDisplayed();
			driver.navigate().back();
			return true;
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateonepasscreateprofile <br>"+displayErrorMessage(exc));
			return false;
			
		}	
	   	 
	}
	public Boolean validateonepassupdateprofile() 
	{
		try{
			
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepasspassprofilelink")).isDisplayed();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassupdateprofile")).click();
			driver.getTitle().contains(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".validateonepassupdateprofilepagetitle"));
			driver.navigate().back();
			return true;
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateonepassupdateprofile <br>"+displayErrorMessage(exc));
			return false;
			
		}	
	   	 
	}
	
	public boolean clickonClientIDLink(){
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".loginpage_clientid_link")).isDisplayed();
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".loginpage_clientid_link"));
			
			return elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".loginpage_enterclientid_txtbox")).isDisplayed();
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickonClientIDLink <br>"+displayErrorMessage(exc));
			return false;
		}	
	}
	
	public void enterClientID(String clientid){
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".loginpage_enterclientid_txtbox")).sendKeys(clientid);
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterClientID <br>"+displayErrorMessage(exc));
	          }
	}

	
	public boolean isLoginPageDisplayed()
	{
		boolean flag = false;
		
		try{
			flag = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".LoginUsername")).isDisplayed();
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isLoginPageDisplayed <br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
}
