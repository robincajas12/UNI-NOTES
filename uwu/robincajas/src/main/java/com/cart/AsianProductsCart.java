package com.cart;

import java.util.ArrayList;
import java.util.List;
@cartType("asian")
public class AsianProductsCart implements Item
{
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
    public void add(Item item) {
        products.add(item);
    }
    @Override
    public int quantity() {
        return products.stream().mapToInt(Item::quantity).sum();
    }
    @Override
    public String inforForFactura() {
        StringBuilder fact = new StringBuilder();
        fact.append("Asian products: \n");
        for (var product : products) {
            fact.append(String.format("%s \n", product.inforForFactura()));
        }
        return fact.toString();

    }

}
