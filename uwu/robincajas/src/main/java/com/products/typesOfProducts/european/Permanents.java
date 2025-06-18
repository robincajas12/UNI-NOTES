package com.products.typesOfProducts.european;

public record Permanents(String composition, String durability, String risk) implements EuropeanTypes {
    @Override
    public TYPE type() {
        return TYPE.PERMANENT;
    }

}
