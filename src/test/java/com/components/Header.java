package com.components;

import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Header extends BaseComponent{


    public Header(Page page) {
        super(page);
    }

    public void clickOnHamburgerIcon(){
        page.click("#react-burger-menu-btn");
    }
}
