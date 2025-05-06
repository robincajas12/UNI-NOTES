package org.uce.batch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import redis.clients.jedis.UnifiedJedis;

public class App {
    static Integer session = null;
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3340/employees"; // Cambia tu base de datos
        String user = "root";
        String password = "root";
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false); 
            crearTablaSiNoExiste(conn);
            createTriggers(conn);
            //test(conn);
            EmpleadoCrud crud = new EmpleadoCrud(conn);
            EmpleadoService service = new EmpleadoService(crud, new RedisEmpleadoService(jedis));
            int hash = service.createRandomHash();
            App.session = hash;
            EmpleadoApp app = new EmpleadoApp(service, new RedisPull(conn, jedis));
            HistoryCrud HCRUD= new HistoryCrud(conn);
           
            app.startApp();  // Start the application
             HCRUD.readAllHistory().forEach(System.out::println);
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        jedis.close();
    }
private static void createTriggers(Connection conn) {
    try (Statement stmt = conn.createStatement()) {

        // INSERT Trigger
        String checkInsertTrigger = "SELECT COUNT(*) FROM information_schema.triggers WHERE trigger_name = 'log_tabla_redis_insert'";
        ResultSet rs = stmt.executeQuery(checkInsertTrigger);
        rs.next();
        if (rs.getInt(1) == 0) {
            String createInsertTrigger =
                "CREATE TRIGGER log_tabla_redis_insert " +
                "AFTER INSERT ON tabla_redis " +
                "FOR EACH ROW " +
                "BEGIN " +
                "  INSERT INTO history (type, id_item) VALUES ('INSERT', NEW.id); " +
                "END;";
            stmt.executeUpdate(createInsertTrigger);
        }

        // UPDATE Trigger
        String checkUpdateTrigger = "SELECT COUNT(*) FROM information_schema.triggers WHERE trigger_name = 'log_tabla_redis_update'";
        rs = stmt.executeQuery(checkUpdateTrigger);
        rs.next();
        if (rs.getInt(1) == 0) {
            String createUpdateTrigger =
                "CREATE TRIGGER log_tabla_redis_update " +
                "AFTER UPDATE ON tabla_redis " +
                "FOR EACH ROW " +
                "BEGIN " +
                "  INSERT INTO history (type, id_item) VALUES ('UPDATE', NEW.id); " +
                "END;";
            stmt.executeUpdate(createUpdateTrigger);
        }

        // DELETE Trigger
        String checkDeleteTrigger = "SELECT COUNT(*) FROM information_schema.triggers WHERE trigger_name = 'log_tabla_redis_delete'";
        rs = stmt.executeQuery(checkDeleteTrigger);
        rs.next();
        if (rs.getInt(1) == 0) {
            String createDeleteTrigger =
                "CREATE TRIGGER log_tabla_redis_delete " +
                "AFTER DELETE ON tabla_redis " +
                "FOR EACH ROW " +
                "BEGIN " +
                "  INSERT INTO history (type, id_item) VALUES ('DELETE', OLD.id); " +
                "END;";
            stmt.executeUpdate(createDeleteTrigger);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private static void crearTablaSiNoExiste(Connection conn) throws SQLException {
            String sql = "CREATE TABLE IF NOT EXISTS tabla_redis (" +
             "id INT PRIMARY KEY AUTO_INCREMENT, " +
             "first_name VARCHAR(100), " +
             "last_name VARCHAR(100), " +
             "dept_name VARCHAR(100), " +
             "salary INT" +
             ")";
             String sql2 = "CREATE TABLE IF NOT EXISTS history("+
              "id INT PRIMARY KEY AUTO_INCREMENT, " +
              "type VARCHAR(100)," +
              "id_item int"+
             ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'tabla1' creada/verificada.");
            stmt.execute(sql2);
            System.out.println("Tabla 'history' creada/verificada.");
            
        }
    }

    private static String test(Connection conn) throws SQLException {
        System.out.println("importing to tabla_redis");
        String sql = "INSERT INTO tabla_redis (first_name, last_name, dept_name, salary) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        int count = 0;
        long startTime = System.currentTimeMillis();
         String query = "SELECT e.first_name, e.last_name, d.dept_name, s.salary " +
                       "FROM employees e " +
                       "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                       "JOIN departments d ON de.dept_no = d.dept_no " +
                       "JOIN salaries s ON e.emp_no = s.emp_no";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            int batchSize = 1000; 
            while (rs.next()) {
                if(count % 10000 == 0)System.gc();
                Empleado emp = new Empleado(
                        0,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("dept_name").toString(),
                        rs.getInt("salary")
                );
                pstmt.setString(1, emp.firstName());
                pstmt.setString(2, emp.lastName());
                pstmt.setString(3, emp.deptName());
                pstmt.setInt(4, (int) emp.salary());

                pstmt.addBatch();
                count++;
    
                if (count % batchSize == 0) {
                    System.out.println("count: "+ count);
                    pstmt.executeBatch(); // Ejecuta el lote
                    conn.commit(); // Guarda cambios
                    pstmt.clearBatch(); // Limpia para nuevo batch
                    System.out.println("Batch de " + batchSize + " insertado.");
                }
            }
                    // Ejecuta el último lote si quedó algo pendiente
            if (count % batchSize != 0) {
                pstmt.executeBatch();
                conn.commit();
                pstmt.clearBatch();
                System.out.println("Último batch insertado.");
            }

            long endTime = System.currentTimeMillis();
            return "Tiempo total: " + (endTime - startTime) + " ms";

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
