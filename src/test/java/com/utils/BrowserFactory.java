package com.utils;

import com.microsoft.playwright.*;

import static com.config.ConfigurationManager.config;

public enum BrowserFactory {

    CHROMIUM {
        @Override
        public Browser createInstance(final Playwright playwright) {
            return playwright.chromium().launch(options());
        }
    },
    FIREFOX{
         @Override
         public Browser createInstance(final Playwright playwright) {
            return playwright.firefox().launch(options());
         }
    },
    WEBKIT{
        @Override
        public Browser createInstance(final Playwright playwright) {
            return playwright.webkit().launch(options());
        }
    };

    public BrowserType.LaunchOptions options(){
        return new BrowserType.LaunchOptions()
                .setHeadless(config().headless())
                .setSlowMo(config().slowMotion());
    }


    public abstract Browser createInstance(final Playwright playwright);
}
