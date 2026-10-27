# Rozetka Mobile Phones — Test Task

A UI test automation project built with Java 17, Maven, Selenide, and TestNG using the Page Object Model (POM).

The test opens Rozetka, navigates to the Mobile Phones category, collects product names and prices from the first listing page, identifies products with the **Top Sales** badge, and exports the results to CSV files.

## Tech Stack

- Java 17
- Maven 3.9+
- Selenide 7.19.1
- TestNG 7.12.0
- Selenium WebDriver (managed through Selenide)

## Project Structure

```text
src/
├── main/
│   └── java/com/example/rozetka/
│       ├── Product.java
│       ├── page/
│       │   ├── MainPage.java
│       │   ├── CategoryPage.java
│       │   └── MobilePhonesPage.java
│       └── utils/
│           └── CsvExporter.java
└── test/
    ├── java/com/example/rozetka/
    │   ├── BaseTest.java
    │   └── RozetkaMobilePhonesTest.java
    └── resources/
        ├── all-mobile-phones.csv
        └── top-sales-mobile-phones.csv
```

## Prerequisites

- JDK 17 or later
- Maven 3.9 or later
- Google Chrome

Verify your Java and Maven installations:

```bash
java -version
mvn -version
```

## How to Run

Clone the repository and navigate to the project root directory.

Run the test suite:

```bash
mvn clean test
```

Run in headless mode:

```bash
mvn clean test -Dheadless=true
```

The test suite is configured through `TestNG.xml` and executed by the Maven Surefire plugin.

## Output

The test generates two CSV files in `target/test-results/`. Each run overwrites the previous contents instead of appending to them.

| File | Description |
|---|---|
| `all-mobile-phones.csv` | Product names, current prices, and old prices for all collected products |
| `top-sales-mobile-phones.csv` | Product names and prices for products marked with the Top Sales badge |

CSV format:
- Encoding: UTF-8
- Delimiter: semicolon (`;`)
- Columns: `Name`, `Current Price`, `Old Price`

The old price is empty when no previous price is displayed. Products without a visible current price are retained with an empty price field.

## Implementation Details

- Uses the Page Object Model to separate page interactions from test logic.
- Represents product data with a Java `Product` record.
- Uses a dedicated `CsvExporter` utility to write the results.
- Scrolls product cards into view before extracting their data.
- Filters Top Sales products from the same collected product list.
- Does not click **Show more** or navigate to another listing page.
- Uses browser interaction and element-based synchronization rather than fixed sleep delays.

## Assumptions and Limitations

- “First page” means the initial Mobile Phones listing, including product cards loaded as the page is scrolled.
- The test does not intentionally navigate to subsequent listing pages.
- Rozetka is a live third-party website, so product data and page markup may change. Selectors may require maintenance.
- The CSV files represent the website state at the time the test runs.
