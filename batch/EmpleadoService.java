package org.uce.batch;

import java.sql.SQLException;
import java.util.List;

public class EmpleadoService {
    private final EmpleadoCrud crud;
    private final RedisEmpleadoService redisEmpleadoService;
    public EmpleadoService(EmpleadoCrud crud, RedisEmpleadoService redisEmpleadoService) {
        this.crud = crud;
        this.redisEmpleadoService = redisEmpleadoService;
    }

    // Method to read all employees
    public List<Empleado> getAllEmpleados() throws SQLException {
        return redisEmpleadoService.readAllEmpleados();
    }

    // Method to read X number of employees
    public List<Empleado> getEmpleadosLimit(int limit) throws SQLException {
        return redisEmpleadoService.readEmpleadosConLimite(limit);
    }

    // Method to create an employee
    public void createEmpleado(Empleado empleado) throws SQLException {
        crud.createEmpleado(empleado);
    }

    // Method to read a specific employee by ID
    public Empleado getEmpleadoById(int id) throws SQLException {
        return redisEmpleadoService.readEmpleadoById(id);
    }

    // Method to update an employee
    public void updateEmpleado(Empleado empleado) throws SQLException {
        crud.updateEmpleado(empleado);
    }

    // Method to delete an employee
    public void deleteEmpleado(int id) throws SQLException {
        crud.deleteEmpleado(id);
    }
    public boolean doesKeyExist(int value)
    {
        return redisEmpleadoService.doesKeyExist(value);
    }
    public int createRandomHash()
    {
        return redisEmpleadoService.storeRandomNumberKeyValue();
    }
}
