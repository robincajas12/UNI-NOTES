package com.products;

import com.cart.Item;
import com.products.typesOfProducts.asian.AsianType;
import lombok.Builder;

@Builder
public record AsianProduct(
        String name,
        double price,
        String expireDate,
        int loteNumber,
        String manufacturingDate,
        String countryOfOrigin,
        AsianType type,
        int quantity
        ) implements Product<AsianType>, Item {
    @Override
    public double getPrice() {
        return this.price();
    }
    @Override
    public String inforForFactura() {
        return String.format("%s - %s - %.2f", name(), type() ,price());
    }
}
