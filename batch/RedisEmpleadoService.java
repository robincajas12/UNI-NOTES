package org.uce.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.params.ScanParams;

public class RedisEmpleadoService {
    private final UnifiedJedis jedis;
    private Jedis jedisAuth = new Jedis("localhost", 6379);
    public RedisEmpleadoService(UnifiedJedis jedis) {
        this.jedis = jedis;
        
    }
     public List<Empleado> readEmpleadosConLimite(int limite) {
        List<Empleado> empleados = new ArrayList<>();
        String cursor = "0";  // Iniciar el escaneo desde la primera posición
        ScanParams scanParams = new ScanParams().match("*").count(limite);  // Limitar el número de resultados
        int empleadosLeidos = 0;

        try {
            do {
                var scanResult = jedis.scan(cursor, scanParams); // Obtener las claves
                cursor = scanResult.getCursor();  // Actualizar el cursor
                List<String> keys = scanResult.getResult();  // Obtener las claves de Redis

                // Iterar sobre las claves y mostrar los valores
                for (String key : keys) {
                    if (jedis.type(key).equals("hash")) {
                        // Obtener los valores del hash
                        Map<String, String> hashValues = jedis.hgetAll(key);
                        
                        // Verificar si la estructura del hash es válida
                        if (hashValues.containsKey("firstName") &&
                            hashValues.containsKey("lastName") &&
                            hashValues.containsKey("deptName") &&
                            hashValues.containsKey("salary")) {
                            
                            // Convertir el hash a un objeto Empleado
                            Empleado empleado = new Empleado(
                                Integer.parseInt(key),  // El ID es la clave
                                hashValues.get("firstName"),
                                hashValues.get("lastName"),
                                hashValues.get("deptName"),
                                Integer.parseInt(hashValues.get("salary"))
                            );

                            empleados.add(empleado);
                            empleadosLeidos++;

                            // Si hemos alcanzado el límite, salir del bucle
                            if (empleadosLeidos >= limite) {
                                return empleados;
                            }
                        } else {
                            System.out.println("Estructura de hash inválida para la clave: " + key);
                        }
                    }
                }
            } while (!cursor.equals("0")); // Continuar escaneando hasta que el cursor vuelva a 0
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empleados;
    }
    // CREATE
    public void createEmpleado(Empleado empleado) {
        String key = String.valueOf(empleado.id()); // Use the employee's ID as the Redis key

        // Create a hash for the employee's details
        Map<String, String> empleadoMap = Map.of(
                "firstName", empleado.firstName(),
                "lastName", empleado.lastName(),
                "deptName", empleado.deptName(),
                "salary", String.valueOf(empleado.salary())
        );
        jedis.hset(key, empleadoMap);
    }

    // READ all employees
    public List<Empleado> readAllEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String cursor = "0";  // Start scanning from the first position
        ScanParams scanParams = new ScanParams().match("*").count(100);  // Scan all keys, adjust COUNT as needed
        
        try {
            do {
                var scanResult = jedis.scan(cursor, scanParams); // Get the keys
                cursor = scanResult.getCursor();  // Update the cursor
                List<String> keys = scanResult.getResult();  // Get the list of keys
                
                // Iterate over the keys and print their values based on their types
                for (String key : keys) {
                    if (jedis.type(key).equals("hash")) {
                        // Handle hash keys
                        Map<String, String> hashValues = jedis.hgetAll(key);
                        
                        // Check if all required fields are present in the hash
                        if (hashValues.containsKey("firstName") &&
                            hashValues.containsKey("lastName") &&
                            hashValues.containsKey("deptName") &&
                            hashValues.containsKey("salary")) {
                            
                            // Convert hash to Empleado object
                            Empleado empleado = new Empleado(
                                Integer.parseInt(key),  // ID is the key
                                hashValues.get("firstName"),
                                hashValues.get("lastName"),
                                hashValues.get("deptName"),
                                Integer.parseInt(hashValues.get("salary"))
                            );
                            empleados.add(empleado);
                        } else {
                            System.out.println("Invalid hash structure for key: " + key);
                        }
                    }
                }
            } while (!cursor.equals("0")); // Continue scanning until the cursor returns to 0
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empleados;
    }

    // READ a single employee by ID
    public Empleado readEmpleadoById(int id) {
        String key = String.valueOf(id);  // Redis key is the ID (as String)
        
        if (jedis.exists(key)) {
            Map<String, String> hashValues = jedis.hgetAll(key);
            
            // Check if all required fields are present in the hash
            if (hashValues.containsKey("firstName") &&
                hashValues.containsKey("lastName") &&
                hashValues.containsKey("deptName") &&
                hashValues.containsKey("salary")) {
                
                // Convert hash to Empleado object
                return new Empleado(
                    id,
                    hashValues.get("firstName"),
                    hashValues.get("lastName"),
                    hashValues.get("deptName"),
                    Integer.parseInt(hashValues.get("salary"))
                );
            }
        }
        return null;  // Return null if the employee with the given ID is not found
    }

    // UPDATE an employee by ID
    public void updateEmpleado(int id, Empleado empleado) {
        String key = String.valueOf(id);  // Redis key is the ID (as String)
        
        if (jedis.exists(key)) {
            // Update the employee's details in the Redis hash
            Map<String, String> empleadoMap = Map.of(
                    "firstName", empleado.firstName(),
                    "lastName", empleado.lastName(),
                    "deptName", empleado.deptName(),
                    "salary", String.valueOf(empleado.salary())
            );
            jedis.hset(key, empleadoMap);
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    // DELETE an employee by ID
    public void deleteEmpleado(int id) {
        String key = String.valueOf(id);  // Redis key is the ID (as String)
        
        if (jedisAuth.exists(key)) {
            jedisAuth.del(key);  // Delete the employee by their ID
            System.out.println("Employee with ID " + id + " deleted.");
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }
        public int storeRandomNumberKeyValue() {
        jedisAuth.select(1); // Select Redis DB 1

        // Generate a random number to be used as the key
        int randomNumber = new Random().nextInt(1000);  // Random number between 0 and 999

        // Store the random number as the key and set its value
        jedisAuth.set(String.valueOf(randomNumber), String.valueOf(randomNumber)); // Store the random number as both key and value
        jedisAuth.expire(String.valueOf(randomNumber), 60); // Set expiration time of 60 seconds

        System.out.println("Stored random number " + randomNumber + " as the key with the same value " + randomNumber + " (expires in 60 seconds)");

        // Return the random number (which is the key)
        return randomNumber;
    }

    // Check if a key exists in Redis
    public boolean doesKeyExist(int key) {
        jedisAuth.select(1); // Select Redis DB 1
        return jedisAuth.exists(String.valueOf(key)); // Check if the key exists
    }
}
