package com.pages;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;

import static com.config.ConfigurationManager.config;

public final class loginPage extends BasePage{

    @Step("Navigate to the login page")
    public loginPage open(){
        page.navigate(config().baseUrl());
        return this;
    }

    @Step("Type <username> into 'Username' textbox")
    public loginPage typeUsername(final String username){
        page.fill("id=user=name", username);
        return this;
    }

    @Step("Type <password> into 'Password' textbox")
    public loginPage typePassword(final String password){
        page.fill("id=password", password);
        return this;
    }

    @Step("Get error message")
    public Locator getErrorMessage(){
        return page.locator(".error-message-container h3");
    }

}
