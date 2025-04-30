package com.inter;

public class Toyota extends BasicCar {
    public Toyota(String m)
    {
        modelName = m;
    }
    @Override
    public BasicCar clone() throws CloneNotSupportedException {
        BasicCar basicCar = super.clone();
        return (Toyota) basicCar;

    }
}
