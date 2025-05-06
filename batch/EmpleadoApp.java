package org.uce.batch;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmpleadoApp {
    private final EmpleadoService service;
    private final RedisPull redisPull;

    public EmpleadoApp(EmpleadoService service, RedisPull redisPull) {
        this.service = service;
        this.redisPull = redisPull;
    }

    public void startApp() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            // Show menu options
            showMenu();
            option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    createEmpleado(scanner);
                    break;
                case 2:
                    if (service.doesKeyExist(App.session))  readEmpleado(scanner);
                    else System.out.println("redis session has caducated");
                   
                    break;
                case 3:
                    updateEmpleado(scanner);
                    break;
                case 4:
                    deleteEmpleado(scanner);
                    break;
                case 5:
                    if (service.doesKeyExist(App.session)) readAllEmpleados();
                    else System.out.println("redis session has caducated");
                    break;
                case 6:
                    if (service.doesKeyExist(App.session)) readXEmpleados(scanner);
                    else System.out.println("redis session has caducated");
                    ;
                    break;
                case 7:
                    try {
                        redisPull.pullTablaRedisToRedis();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }

        } while (option != 8);
        scanner.close();
    }

    private void showMenu() {
        System.out.println("\n----- Employee Management System -----");
        System.out.println("1. Create Employee");
        System.out.println("2. Read Employee by ID");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Read All Employees");
        System.out.println("6. Read X Number of Employees");
        System.out.println("7. Pull from mysql to redis all data");
        System.out.println("8. Exit");
        System.out.print("Select an option: ");
    }

    private void createEmpleado(Scanner scanner) {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Department Name: ");
        String deptName = scanner.nextLine();
        System.out.print("Enter Salary: ");
        int salary = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Empleado newEmpleado = new Empleado(0, firstName, lastName, deptName, salary);
        try {
            service.createEmpleado(newEmpleado);
            System.out.println("Employee created successfully!");
            redisPull.sync();
        } catch (SQLException e) {
            System.out.println("Error creating employee: " + e.getMessage());
        }
    }

    private void readEmpleado(Scanner scanner) {
        System.out.print("Enter Employee ID to fetch: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Empleado emp = service.getEmpleadoById(id);
            if (emp != null) {
                System.out.println("Employee Details: ");
                System.out.println("ID: " + emp.id());
                System.out.println("Name: " + emp.firstName() + " " + emp.lastName());
                System.out.println("Department: " + emp.deptName());
                System.out.println("Salary: " + emp.salary());
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error reading employee: " + e.getMessage());
        }
    }

    private void updateEmpleado(Scanner scanner) {
        System.out.print("Enter Employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter new First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter new Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter new Department Name: ");
        String deptName = scanner.nextLine();
        System.out.print("Enter new Salary: ");
        int salary = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Empleado updatedEmpleado = new Empleado(id, firstName, lastName, deptName, salary);
        try {
            service.updateEmpleado(updatedEmpleado);
            System.out.println("Employee updated successfully!");
            redisPull.sync();
        } catch (SQLException e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }

    private void deleteEmpleado(Scanner scanner) {
        System.out.print("Enter Employee ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            service.deleteEmpleado(id);
            System.out.println("Employee deleted successfully!");
            redisPull.sync();
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }

    private void readAllEmpleados() {
        try {
            List<Empleado> empleados = service.getAllEmpleados();
            if (empleados.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                System.out.println("All Employees:");
                for (Empleado emp : empleados) {
                    System.out.println(emp.id() + ": " + emp.firstName() + " " + emp.lastName() + ", " +
                            emp.deptName() + ", " + emp.salary());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading all employees: " + e.getMessage());
        }
    }

    private void readXEmpleados(Scanner scanner) {
        System.out.print("Enter number of employees to fetch: ");
        int limit = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            List<Empleado> empleados = service.getEmpleadosLimit(limit);
            if (empleados.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                System.out.println("Employees:");
                for (Empleado emp : empleados) {
                    System.out.println(emp.id() + ": " + emp.firstName() + " " + emp.lastName() + ", " +
                            emp.deptName() + ", " + emp.salary());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading employees: " + e.getMessage());
        }
    }
}
