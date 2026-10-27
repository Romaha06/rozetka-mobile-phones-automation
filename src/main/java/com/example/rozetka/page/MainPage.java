package com.example.rozetka.page;

import com.codeborne.selenide.ElementsCollection;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;
import static java.time.Duration.*;

public class MainPage {

    private static final String URL = "https://rozetka.com.ua/";

    private static final String SIDEBAR_CATEGORY_LINK =
            "a[data-testid='fat_menu_category_link']";

    public MainPage openPage() {
        open(URL);
        return this;
    }

    public MainPage clickSidebarCategory(String categoryName) {
        ElementsCollection categories = $$(SIDEBAR_CATEGORY_LINK);

        categories
                .findBy(exactText(categoryName))
                .shouldBe(visible, ofSeconds(15))
                .click();

        return this;
    }
}
