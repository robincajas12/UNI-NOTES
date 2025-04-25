package com.db.services;

import com.db.DbConfig;
import com.models.Product;


import java.util.List;

public interface Services {
    void setDbConfig(DbConfig config);
    void create(Product p);
    Product read(int id);
    List<Product> listar();
    void update(Product p);
    void delete(int id);
}
