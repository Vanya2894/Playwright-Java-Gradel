package com.utils;

import com.microsoft.playwright.Page;
import com.pages.BasePage;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public final class BasePageFactory {
    public static <T extends BasePage> T createInstance(final Page page, final Class<T> clazz) {
        try {
            System.out.println("Creating instance of: " + clazz.getName());
            BasePage instance = clazz.getDeclaredConstructor().newInstance();
            System.out.println("Instance created: " + instance);

            if (page == null) {
                throw new RuntimeException("Page is null in BasePageFactory!");
            }
            instance.setAndConfigurePage(page);
            System.out.println("Page set and configured");

            instance.initComponents();
            System.out.println("Components initialized");

            return clazz.cast(instance);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Page class instantiation failed: " + e.getMessage(), e);
        }
    }
}