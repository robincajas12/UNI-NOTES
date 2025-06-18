package com.cart;

import java.util.ArrayList;
import java.util.List;
@cartType("european")
public class EuropeanProductsCart implements Item{
    List<Item> products = new ArrayList<>();
    @Override
    public double getPrice() {
        double total = products.stream().map(Item::getPrice).reduce(0.0, Double::sum);
        if(quantity() > 12)
        {
            return total - total * 0.25;
        }
        return total - total * 0.03;
    }

    @Override
    public int quantity() {
        return products.stream().mapToInt(Item::quantity).sum();
    }
    @Override
    public String inforForFactura() {
        StringBuilder fact = new StringBuilder();
        fact.append("European products: \n");
        for (var product : products) {
            fact.append(String.format("%s \n", product.inforForFactura()));
        }
        return fact.toString();

    }
    public void add(Item item) {
        products.add(item);
    }

}
