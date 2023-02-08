package serenitybdd.Autotest.WebMyPham.pages;

import net.thucydides.core.annotations.DefaultUrl;

import java.util.List;


import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("/")
public class SearchPage extends PageObject {

	@FindBy(xpath = "//input[contains(@id,'txtSearch')]")
    private WebElementFacade inputSearch;

	public void enter_search_texbox(String product) {
		inputSearch.type(product);
		
	}

	@FindBy(xpath = "//input[contains(@id,'btnSearch')]")
	private WebElementFacade buttonSearch;
	public void click_search_button() {
		buttonSearch.click();
		
	}

	@FindBy(xpath = "//div[contains(@class,'sn_search_returns_frame')]")
	private List<WebElementFacade> listResult;
	public boolean check_search_success() {		
		return listResult.size()>=1;
	}

	
	

    
}