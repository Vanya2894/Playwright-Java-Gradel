package com.pages;

import com.components.Header;
import com.components.SideNavMenu;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;

public final class ProductsPage extends BasePage{

    private Header header;
    private SideNavMenu sideNavMenu;

    @Override
    public void initComponents() {
        header = new Header(page);
        sideNavMenu = new SideNavMenu(page);

    }

    @Step("Get title of the 'Products' page")
    public Locator getTitle(){
        return page.locator(".title");
    }

    @Step("Get all products names")
    public Locator getProductNames(){
        return page.locator(".//div[@data-test='inventory-item-name']");
    }

    @Step("Set <sortName> into 'Sort Filter'")
    public ProductsPage setSortFilter(String sortName){
        page.locator("select[@data-test='product-sort-container']").selectOption(sortName);
        return this;
    }

    @Step("Add to cart item with name <itemName>")
    public ProductsPage addItemToCart(String itemName){
        page.locator(String.format("//div[text()=%s]//following::button[1]",itemName)).click();
        return this;
    }
}
