package com.products.typesOfProducts.european;

public interface EuropeanTypes
{
    static enum TYPE{
        PERMANENT,
        SEMI_PERMANENT,
    }
    TYPE type();
}
