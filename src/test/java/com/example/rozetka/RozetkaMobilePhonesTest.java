package com.example.rozetka;

import com.example.rozetka.common.Product;
import com.example.rozetka.page.CategoryPage;
import com.example.rozetka.page.MainPage;
import com.example.rozetka.page.MobilePhonesPage;
import com.example.rozetka.utils.CsvExporter;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class RozetkaMobilePhonesTest extends BaseTest {

    private final MainPage mainPage = new MainPage();
    private final CategoryPage categoryPage = new CategoryPage();
    private final MobilePhonesPage mobilePhonesPage = new MobilePhonesPage();
    private static final Path RESULTS_DIRECTORY =
            Path.of("target", "test-results");

    @Test
    public void shouldExportAllMobilePhonesAndTopSales() throws IOException {

        mainPage.openPage()
                .clickSidebarCategory("Смартфони, ТВ і електроніка");
        categoryPage.waitForCategoryPageLoaded()
                .clickCategory("Мобільні телефони");
        mobilePhonesPage.waitForMobilePageLoaded();

        List<Product> allProducts = mobilePhonesPage.collectProductsFromFirstPage();
        List<Product> topSalesProducts = allProducts.stream().filter(Product::topSales).toList();

        Assert.assertFalse(allProducts.isEmpty(),
                "No products were collected. The category page or its selectors may have changed.");

        CsvExporter.writeProducts(RESULTS_DIRECTORY.resolve("all-mobile-phones.csv"), allProducts);
        CsvExporter.writeProducts(RESULTS_DIRECTORY.resolve("top-sales-mobile-phones.csv"), topSalesProducts);

        System.out.printf("Collected %d products from the first listing page.%n", allProducts.size());
        System.out.printf("Found %d Top Sales products.%n", topSalesProducts.size());
    }

}
