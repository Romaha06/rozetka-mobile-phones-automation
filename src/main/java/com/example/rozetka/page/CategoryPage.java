package com.example.rozetka.page;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class CategoryPage {
    private static final String CATEGORY_LINK =
            "a:has(img[alt='%s'])";

    private static final String CATEGORY_TITLE =
            "//h1[contains(normalize-space(.), 'Смартфони, ТВ і Електроніка')]";


    public CategoryPage waitForCategoryPageLoaded() {
        $x(CATEGORY_TITLE)
                .shouldBe(visible, ofSeconds(15));

        return this;
    }

    public CategoryPage clickCategory(String categoryName) {
        $(CATEGORY_LINK.formatted(categoryName))
                .scrollIntoView("{block: 'center'}")
                .shouldBe(visible, ofSeconds(15))
                .click();

        return this;
    }
}
