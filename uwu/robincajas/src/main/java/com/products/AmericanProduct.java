package com.products;

import com.cart.Item;
import lombok.Builder;
//American oversight body code = AOBC
@Builder
public record AmericanProduct(String name, double price,int quantity, String expireDate, int loteNumber, String countryOfOrigin, String manufacturingDate, String Aobc, String warnings) implements Product<String>, Item {

    @Override
    public String type() {
        return "Tipo americano??"; // en el documento no se especifica que tipo, si o si el tipo se lo define al crear el objeto
    }

    @Override
    public double getPrice() {
        return this.price();
    }
    @Override
    public String inforForFactura() {
        return String.format("%s - %s - %.2f", name(),type(), price());
    }
}
