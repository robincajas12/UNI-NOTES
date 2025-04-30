package com.inter;

public class Ford extends BasicCar{
    public Ford(String m)
    {
        modelName = m;
    }
    @Override
    public BasicCar clone() throws CloneNotSupportedException {
        BasicCar basicCar = super.clone();
        return (Ford) basicCar;

    }
}
