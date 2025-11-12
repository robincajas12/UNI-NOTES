package com.progAvanzada.db;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
@Builder
@ToString
@Getter
@Setter
public class Book {
    private String isbn;
    private String title;
    private BigDecimal price;
    private Integer version;
}
