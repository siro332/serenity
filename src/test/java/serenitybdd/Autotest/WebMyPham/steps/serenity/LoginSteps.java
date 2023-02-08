package serenitybdd.Autotest.WebMyPham.steps.serenity;

import serenitybdd.Autotest.WebMyPham.pages.LoginPage;
import net.thucydides.core.annotations.Step;
import static org.junit.Assert.assertTrue;

public class LoginSteps {

    LoginPage page;

    public void login(String username, String password) {
    	openPage();
    	enterEmail(username);
    	enterPassword(password);
    	clickLoginButton();
    }
    
    @Step
	public void openPage() {
		page.open();
	}
	@Step
	public void clickLoginButtonInPopup(){
		page.clickLoginButtonInPopup();
	}

	@Step
	public void hoverOnUserNameTitle() {
		page.hoverUserName();
	}

    @Step
	public void enterEmail(String email) {
		page.enterEmail(email);
		
	}

    @Step
	public void clickLoginButton() {
		page.clickLoginButton();
		
	}

	@Step
	public void clickLogoutButton() {
		page.clickLogoutButton();

	}
    @Step
	public void check_login_sucsess(String result) {
		assertTrue(page.check_login_sucsess(result));
		
	}

    @Step
	public void enterPassword(String password) {
		page.enterPassword(password);
		
	}

    @Step
	public void check_login_fail(String result) {
		assertTrue(page.check_login_fail(result));
		
	}


    @Step
	public void check_email_fail(String result) {
		assertTrue(page.check_email_fail(result));
		
	}
}