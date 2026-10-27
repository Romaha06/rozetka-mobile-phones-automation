package com.example.rozetka.common;

public record Product(String name,
                      String currentPrice,
                      String oldPrice,
                      boolean topSales) {
}
