package serenitybdd.Autotest.WebMyPham.steps.serenity;


import serenitybdd.Autotest.WebMyPham.pages.SearchPage;
import net.thucydides.core.annotations.Step;
import static org.junit.Assert.assertTrue;

public class SearchSteps {

    SearchPage page;

    @Step
	public void enter_search_texbox(String product) {
		page.enter_search_texbox(product);
		
	}
    @Step
	public void click_search_button() {
		page.click_search_button();
		
	}
    @Step
	public void check_search_success() {
		assertTrue(page.check_search_success());
		
	}

	@Step
	public void openPage() {
		page.open();
	}
   
   
}