package serenitybdd.Autotest.WebMyPham.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.interactions.Actions;

@DefaultUrl("/")
public class LoginPage extends PageObject {


	@FindBy(xpath = "//input[@id='uname']")
    private WebElementFacade inputEmail;


	@FindBy(xpath = "//input[@id='password']")
    private WebElementFacade inputPassword;
    
    @FindBy(xpath="//p[contains(.,'Đăng nhập')]")
    private WebElementFacade buttonLogin;

	@FindBy(xpath = "//a[contains(@onclick,'NCTLoginJWT.login();')]")
	private WebElementFacade buttonLoginInPopup;

	@FindBy(xpath = "//a[@href='https://www.nhaccuatui.com/ajax/user_jwt?type=logout'][contains(.,'Thoát')]")
    private WebElementFacade buttonLogout;

	@FindBy(xpath = "//h3[contains(@class,'username-card-info')]")
	private WebElementFacade userNameTitle;

	public void enterEmail(String email) {
		inputEmail.type(email);
		
	}

	public void enterPassword(String password) {
		inputPassword.type(password);
		
	}


	public boolean check_login_sucsess(String result) {
		return buttonLogout.isDisplayed();
		
	}

	public void clickLogoutButton(){
		buttonLogout.click();
	}

	public boolean check_login_fail(String result) {
		return getDriver().findElement(By.xpath("(//div[contains(.,'"+result+"')])[9]")).isDisplayed();
	}

	
	public boolean check_email_fail(String result) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
		String s =(String) jsExecutor.executeScript("return arguments[0].validationMessage;", inputEmail);
		return s.equalsIgnoreCase(result);
	}


	public void clickLoginButton() {
		buttonLogin.click();
		
	}

	public void clickLoginButtonInPopup(){
		buttonLoginInPopup.click();
	}

	public void hoverUserName(){
		Actions builder = new Actions(getDriver());
		builder.moveToElement(userNameTitle).perform();
	}

}