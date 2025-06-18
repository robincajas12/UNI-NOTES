package com.products.typesOfProducts.asian;

public interface AsianType {
    static enum TYPE {
        FACIAL_ROUTINE,
        COSMETICS
    }
    TYPE type();
}
