package com;

import com.adapter.Adapter;
import com.anotation.Adaptable;
import com.impl.Auto;
import com.impl.Grua;
import com.inter.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Vehicle miAdapter = new Adapter<Auto>(new Auto());
        List<Object> vehicles = Arrays.asList(new Auto(), new Grua());
        vehicles.stream()
                .filter(element -> element.getClass().isAnnotationPresent(Adaptable.class))
                .map(Adapter::new)
                .forEach(Vehicle::drive);
    }
}
