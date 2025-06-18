package com.cart.factory;

import com.cart.AmericanProductsCart;
import com.cart.AsianProductsCart;
import com.cart.EuropeanProductsCart;
import com.cart.Item;
import com.products.EuropeanProduct;

public class CartFactory {
    // por el tiempo vamos a hacer uno simple
    static public Item get(String name)
    {
        switch (name)
        {
            case "american":
                return new AmericanProductsCart();
            case "asian":
                return new AsianProductsCart();
            case "european":
                return new EuropeanProductsCart();
            default: throw new RuntimeException("Unrecognized cart name: " + name);
        }
    }
}
