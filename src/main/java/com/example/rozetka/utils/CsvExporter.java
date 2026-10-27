
package com.example.rozetka.utils;

import com.example.rozetka.common.Product;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class CsvExporter {

    private CsvExporter() {
    }

    public static void writeProducts(
            Path output,
            List<Product> products
    ) throws IOException {

        Path parent = output.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        StringBuilder csv = new StringBuilder(
                "Name;   Current Price;   Old Price\n"
        );

        for (Product product : products) {
            csv.append(escape(product.name()))
                    .append("; ")
                    .append(escape(product.currentPrice()))
                    .append("; ")
                    .append(escape(product.oldPrice()))
                    .append('\n');
        }

        // Overwrite the file on every run.
        Files.writeString(
                output,
                csv.toString(),
                StandardCharsets.UTF_8
        );
    }

    private static String escape(String value) {
        String safeValue = value == null ? "" : value;
        return "\"" + safeValue.replace("\"", "\"\"") + "\"";
    }
}
