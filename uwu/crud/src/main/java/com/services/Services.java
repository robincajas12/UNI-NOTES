package com.services;

import com.db.DbConfig;
import com.models.Producto;

import java.util.List;

public interface Services {
    void setDbConfig(DbConfig config);
    void create(Producto p);
    Producto read(int id);
    List<Producto> listar();
    void update(Producto p);
    void delete(int id);
}
