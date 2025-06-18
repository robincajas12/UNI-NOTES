package com.cart;

import com.products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cart implements Item {
    List<Item> carts = new ArrayList<>();
    public void addItem(Item item) {
        var res = carts.stream().filter(e -> e.equals(item)).findFirst().orElse(null);
        if (res == null) {
            carts.add(item);
        }
    }
    @Override
    public double getPrice() {
        double total = carts.stream().map(Item::getPrice).reduce(0.0, Double::sum);
        // paquete de regalo
        if(quantity() == 3)
        {
            return total - total * 0.10;
        }
        return total;
    }

    @Override
    public int quantity() {
        return carts.size();
    }
    @Override
    public String inforForFactura() {
        StringBuilder fact = new StringBuilder();
        fact.append("Factura: \n");
        for (var product : carts) {
            fact.append(String.format("%s \n", product.inforForFactura()));
        }
        return fact.toString();

    }

}
