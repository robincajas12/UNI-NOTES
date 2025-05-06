package org.uce.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoCrud {
    private final Connection conn;

    public EmpleadoCrud(Connection conn) {
        this.conn = conn;
    }

      // CREATE
    public void createEmpleado(Empleado empleado) throws SQLException {
        String sql = "INSERT INTO tabla_redis (first_name, last_name, dept_name, salary) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empleado.firstName());
            stmt.setString(2, empleado.lastName());
            stmt.setString(3, empleado.deptName());
            stmt.setInt(4, empleado.salary());
            stmt.executeUpdate();
            conn.commit();
        }
    }

    // READ all employees
    public List<Empleado> readAllEmpleados() throws SQLException {
        String sql = "SELECT * FROM tabla_redis";
        List<Empleado> empleados = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                empleados.add(new Empleado(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("dept_name"),
                        rs.getInt("salary")
                ));
            }
        }
        return empleados;
    }

    // READ X number of employees
    public List<Empleado> readEmpleadosLimit(int limit) throws SQLException {
        String sql = "SELECT * FROM tabla_redis LIMIT ?";
        List<Empleado> empleados = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    empleados.add(new Empleado(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("dept_name"),
                            rs.getInt("salary")
                    ));
                }
            }
        }
        return empleados;
    }

    // READ single employee by ID
    public Empleado readEmpleado(int id) throws SQLException {
        String sql = "SELECT * FROM tabla_redis WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("dept_name"),
                            rs.getInt("salary")
                    );
                }
            }
        }
        return null;  // If no employee found with the given ID
    }

    // UPDATE
    public void updateEmpleado(Empleado empleado) throws SQLException {
        String sql = "UPDATE tabla_redis SET first_name = ?, last_name = ?, dept_name = ?, salary = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empleado.firstName());
            stmt.setString(2, empleado.lastName());
            stmt.setString(3, empleado.deptName());
            stmt.setInt(4, empleado.salary());
            stmt.setInt(5, empleado.id());
            stmt.executeUpdate();
            conn.commit();
        }
    }

    // DELETE
    public void deleteEmpleado(int id) throws SQLException {
        String sql = "DELETE FROM tabla_redis WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.commit();
        }
    }
}
