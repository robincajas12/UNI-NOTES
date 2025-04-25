package com.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Product {
    private Integer id;
    private String nombre;
    private Integer precio;
    private Integer cantidad;
}

