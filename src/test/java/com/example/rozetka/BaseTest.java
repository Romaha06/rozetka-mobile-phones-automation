package com.example.rozetka;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.codeborne.selenide.Selenide.*;

public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1728x1117";
        Configuration.timeout = 5000;
        Configuration.pageLoadTimeout = 60000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        closeWebDriver();
    }
}
