package serenitybdd.Autotest.WebMyPham.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("/customer/dang-ky")
public class HomePage extends PageObject {

    @FindBy(id="name")
    private WebElementFacade inputName;

    @FindBy(id="email")
    private WebElementFacade inputEmail;

    
    @FindBy(name="phone")
    private WebElementFacade inputPhone;
    
    @FindBy(id="password")
    private WebElementFacade inputPassword;
    
    @FindBy(xpath="//button[@value='DANG KY']")
    private WebElementFacade buttonSignUp;

    @FindBy(xpath="//div[contains(@class,'alert-success')]")
    private WebElementFacade divAlert;
    
	public void enterName(String name) {
		inputName.type(name);
		
	}

	public void enterEmail(String email) {
		inputEmail.type(email);
		
	}

	public void enterPhone(String phone) {
		inputPhone.type(phone);
		
	}

	public void enterPassword(String password) {
		inputPassword.type(password);
		
	}

	public void clickSignUpButton() {
		buttonSignUp.click();
		
	}

	public boolean check_signup_sucsess(String result) {
		return divAlert.getText().contains(result);
		
	}

	
	public boolean check_signup_fail(String result) {
		return getDriver().findElement(By.xpath("//li[text()='"+result+"']")).isDisplayed();
	}

	
	public boolean check_email_fail(String result) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
		String s =(String) jsExecutor.executeScript("return arguments[0].validationMessage;", inputEmail);
		return s.equalsIgnoreCase(result);
	}

	public boolean check_phone_fail(String result) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
		String s =(String) jsExecutor.executeScript("return arguments[0].validationMessage;", inputPhone);
		return s.equalsIgnoreCase(result);
	}

	

}