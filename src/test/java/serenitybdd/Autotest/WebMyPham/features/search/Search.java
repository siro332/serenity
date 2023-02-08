package serenitybdd.Autotest.WebMyPham.features.search;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.ClearCookiesPolicy;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import serenitybdd.Autotest.WebMyPham.steps.serenity.LoginSteps;
import serenitybdd.Autotest.WebMyPham.steps.serenity.SearchSteps;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("data/search.csv")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Search {

	@Managed(uniqueSession = true, clearCookies = ClearCookiesPolicy.Never)
	public WebDriver webdriver;

	String testcase, song,result;
	
	@Qualifier
	public String qualifier() {
		return this.testcase;
	}

	@Steps
	public LoginSteps login;
	
	@Steps
	public SearchSteps search;

	@Test
    public void SearchTestcase() {
		searchSong(song);
		search.check_search_success();
    }

	public void searchSong(String productName) {
		search.openPage();
		search.enter_search_texbox(productName);
		search.click_search_button();
	
	}
}