package com.example.rozetka.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.rozetka.common.Product;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static java.time.Duration.ofSeconds;

public class MobilePhonesPage {

    private static final String PRODUCT_CARD = "rz-product-tile";

    private static final String PRODUCT_NAME =
            "a.tile-title.black-link.text-base";

    private static final String TOP_SALES_BADGE =
            "rz-promo-label.promo-label_type_popularity";

    // The regular current price, not the crossed-out or discounted price.
    private static final String CURRENT_PRICE =
            "rz-tile-price .price";

    // The old crossed-out price.
    private static final String OLD_PRICE =
            "rz-tile-price .old-price";

    // The current discounted price displayed in red.
    private static final String DISCOUNT_PRICE =
            "rz-tile-red-price [data-testid='red-price-discount']";


    private static final String CATEGORY_TITLE =
            "//h1[contains(normalize-space(.), 'Мобільні телефони')]";


    public MobilePhonesPage waitForMobilePageLoaded() {
        $x(CATEGORY_TITLE).shouldBe(visible, ofSeconds(15));
        return this;
    }

    /**
     * Collects the products displayed in the mobile phones listing.
     *
     */
    public List<Product> collectProductsFromFirstPage() {
        List<Product> products = new ArrayList<>();
        int index = 0;

        while (index < $$(PRODUCT_CARD).size()) {
            SelenideElement card = $$(PRODUCT_CARD).get(index);
            card.scrollIntoView("{block: 'center'}");

            String name = getProductName(card);

            if (!name.isBlank()) {
                products.add(new Product(
                        name,
                        getCurrentPrice(card),
                        getOldPrice(card),
                        card.$(TOP_SALES_BADGE).exists()
                ));
            }

            index++;
        }

        long topSalesCount = products.stream()
                .filter(Product::topSales)
                .count();

        System.out.printf("Collected products: %d%n", products.size());
        System.out.printf("Collected Top Sales products: %d%n", topSalesCount);

        return products;
    }

    private String getProductName(SelenideElement card) {
        SelenideElement nameElement = card.$(PRODUCT_NAME);

        if (!nameElement.exists()) {
            return "";
        }

        String title = nameElement.getAttribute("title");

        return title == null || title.isBlank()
                ? nameElement.getText().trim()
                : title.trim();
    }

    private String getCurrentPrice(SelenideElement card) {
        String discountPrice = getTextIfPresent(card, DISCOUNT_PRICE);

        if (!discountPrice.isBlank()) {
            return discountPrice;
        }

        ElementsCollection prices = card.$$(CURRENT_PRICE);

        for (SelenideElement price : prices) {
            String priceText = price.getText().trim();
            String classes = price.getAttribute("class");

            if (!priceText.isBlank()
                    && (classes == null || !classes.contains("old-price"))) {
                return priceText;
            }
        }

        return "";
    }

    private String getOldPrice(SelenideElement card) {
        return getTextIfPresent(card, OLD_PRICE);
    }

    private String getTextIfPresent(SelenideElement parent, String selector) {
        SelenideElement element = parent.$(selector);

        return element.exists() ? element.getText().trim() : "";
    }
}