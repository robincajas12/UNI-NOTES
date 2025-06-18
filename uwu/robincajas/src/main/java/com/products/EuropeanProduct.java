package com.products;

import com.cart.Item;
import com.products.typesOfProducts.european.EuropeanTypes;
import lombok.Builder;

@Builder
public record EuropeanProduct(
        String name,
        double price,
        EuropeanTypes type,
        String expireDate,
        int loteNumber,
        String manufacturingDate,
        String countryOfOrigin,
        String fabricationNorm,
        int quantity
        ) implements Product<EuropeanTypes>, Item {
    @Override
    public double getPrice() {
        return this.price();
    }

    @Override
    public String inforForFactura() {
        return String.format("%s - %s -  %.2f", name(),type() ,price());
    }
}
