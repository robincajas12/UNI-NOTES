package org.uce.batch;



public record Empleado(
    int id,
    String firstName,
    String lastName,
    String deptName,
    int salary
) {}
