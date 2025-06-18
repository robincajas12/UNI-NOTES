package com;

import com.cart.AmericanProductsCart;
import com.cart.AsianProductsCart;
import com.cart.Cart;
import com.cart.EuropeanProductsCart;
import com.cart.factory.CartFactory;
import com.products.AmericanProduct;
import com.products.AsianProduct;
import com.products.EuropeanProduct;
import com.products.typesOfProducts.asian.AsianType;

public class main {
    public static void main(String[] args) {
        Cart cart = new Cart();
        AmericanProductsCart acart = (AmericanProductsCart) CartFactory.get("american");
        acart.add(AmericanProduct.builder().price(5).name("Americano").build());
        cart.addItem(acart);
        AsianProductsCart asianProductsCart =  (AsianProductsCart) CartFactory.get("asian");
        asianProductsCart.add(AsianProduct.builder().name("asian").price(10).build());
        EuropeanProductsCart europeanProductsCart = (EuropeanProductsCart) CartFactory.get("european");
        europeanProductsCart.add(EuropeanProduct.builder().name("europena").price(25).build());
        cart.addItem(asianProductsCart);
        cart.addItem(europeanProductsCart);
        System.out.println(cart.inforForFactura());

    }
}
