package com.products.typesOfProducts.european;

public record SemiPermanent(String usefulLife) implements EuropeanTypes {
    @Override
    public TYPE type() {
        return TYPE.SEMI_PERMANENT;
    }
}
