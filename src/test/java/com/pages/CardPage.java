package com.pages;

import com.components.Header;
import com.microsoft.playwright.Locator;
import com.models.ShipInfo;
import io.qameta.allure.Step;

public final class CardPage extends BasePage {
    private Header header;

    @Override
    public void initComponents() {
        header = new Header(page);
    }

    @Step("Get item name")
    public Locator getItems(){
        return page.locator("//div[@class='cart_list']//div[@class='inventory-item_name']");
    }

    @Step("Click on checkout button")
    public CardPage clickOnCheckout(){
        page.locator("//button[@data-test='checkout']").click();
        return this;
    }

    @Step("Click continue button")
    public CardPage fillInfo(ShipInfo shipInfo){
        page.fill("//input[@data-test='firstName']", shipInfo.getFirstname());
        page.fill("//input[@data-test='Lastname']", shipInfo.getLastname());
        page.fill("//input[@data-test='postalCode']", shipInfo.getZip());
        return this;
    }

    @Step("Click finish button")
    public CardPage clackOnFinish(){
        page.locator("//button[@data-test='finish']").click();
        return this;
    }

    @Step("Get complete header")
    public Locator getCompleteHeader(){
        return page.locator("//h2[@data-test='complete-header']");
    }
}
