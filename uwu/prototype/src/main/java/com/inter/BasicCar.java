package com.inter;

import java.util.Random;

public abstract class BasicCar implements Cloneable{


    protected String modelName;
    public int price;
    public BasicCar()
    {

    }

    public int getPrice() {
        return price;
    }

    public int setPrice() {
        Random r = new Random();
        return r.nextInt(10000);
    }
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        modelName = modelName;
    }
    public BasicCar clone() throws CloneNotSupportedException
    {
        BasicCar bs = (BasicCar) super.clone();
        bs.price += bs.setPrice();
        return bs;
    }

    @Override
    public String toString() {
        return "BasicCar{" +
                "modelName='" + modelName + '\'' +
                ", price=" + price +
                '}';
    }
}
