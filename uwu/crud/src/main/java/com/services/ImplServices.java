package com.services;

import com.db.DbConfig;
import com.models.Producto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplServices implements Services {
    DbConfig dbConfig = new DbConfig();
    @Override
    public void setDbConfig(DbConfig config) {

    }

    @Override
    public void create(Producto p) {
        try(var con = dbConfig.getConnection())
        {
            var pst = con.prepareStatement("INSERT INTO PRODUCTOS (NOMBRE,PRECIO,CANTIDAD) VALUES(?,?,?)");
            pst.setString(1, p.getNombre());
            pst.setInt(2, p.getPrecio());
            pst.setInt(2, p.getCantidad());

            pst.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }finally {

        }
    }

    @Override
    public Producto read(int id) {
        try(var con = dbConfig.getConnection())
        {
            var pst = con.prepareStatement("SELECT * FROM PRODUCTOS WHERE id = ?;");
            pst.setInt(1, id);  // Set the provided ID as the parameter
            var res = pst.executeQuery();
            if (res.next()) {
                var p = new Producto();
                p.setId(res.getInt("ID"));
                p.setNombre(res.getString("NOMBRE"));
                p.setPrecio(res.getInt("PRECIO"));
                p.setCantidad(res.getInt("CANTIDAD"));
                return p;
            } else {
                // No product found with the given ID
                return null;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        try (var con = dbConfig.getConnection()) {
            var pst = con.prepareStatement("SELECT * FROM PRODUCTOS;");
            var res = pst.executeQuery();

            while (res.next()) {
                var p = new Producto();
                p.setId(res.getInt("ID"));
                p.setNombre(res.getString("NOMBRE"));
                p.setPrecio(res.getInt("PRECIO"));
                p.setCantidad(res.getInt("CANTIDAD"));
                productos.add(p);  // Add each product to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }
        return productos;  // Return the list of products
    }


    @Override
    public void update(Producto p) {
        try (var con = dbConfig.getConnection()) {
            var pst = con.prepareStatement("UPDATE PRODUCTOS SET NOMBRE = ?, PRECIO = ?, CANTIDAD = ? WHERE ID = ?;");
            pst.setString(1, p.getNombre());
            pst.setInt(2, p.getPrecio());
            pst.setInt(3, p.getCantidad());
            pst.setInt(4, p.getId()); // Use the ID of the product to identify which one to update

            int rowsAffected = pst.executeUpdate(); // Executes the update query
            if (rowsAffected == 0) {
                System.out.println("No product found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }
    }


    @Override
    public void delete(int id) {
        try (var con = dbConfig.getConnection()) {
            var pst = con.prepareStatement("DELETE FROM PRODUCTOS WHERE ID = ?;");
            pst.setInt(1, id); // Set the ID of the product to be deleted

            int rowsAffected = pst.executeUpdate(); // Executes the delete query
            if (rowsAffected == 0) {
                System.out.println("No product found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }
    }

}
