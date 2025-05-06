package org.uce.batch;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import redis.clients.jedis.UnifiedJedis;

public class RedisPull {

    private final UnifiedJedis unifiedJedis;
    private final Connection conn;

    public RedisPull(Connection conn, UnifiedJedis unifiedJedis) {
        this.conn = conn;
        this.unifiedJedis = unifiedJedis;
    }

    public void pullTablaRedisToRedis() throws SQLException {
        HistoryCrud historyCrud = new HistoryCrud(conn);
        historyCrud.deleteAllHistory();
        String sql = "SELECT id, first_name, last_name, dept_name, salary FROM tabla_redis";
        try (var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                String key = String.valueOf(rs.getInt("id"));

                Map<String, String> empleadoMap = Map.of(
                        "firstName", rs.getString("first_name"),
                        "lastName", rs.getString("last_name"),
                        "deptName", rs.getString("dept_name"),
                        "salary", String.valueOf(rs.getInt("salary"))
                );

                unifiedJedis.hset(key, empleadoMap);
            }
            historyCrud.deleteAllHistory();
        }
    }

    public void sync() throws SQLException {
        EmpleadoCrud empleadoCrud = new EmpleadoCrud(conn);
        HistoryCrud historyCrud = new HistoryCrud(conn);

        List<History> historyList = historyCrud.readAllHistory();

        for (History h : historyList) {
            String key = String.valueOf(h.idItem());

            switch (h.type().toUpperCase()) {
                case "INSERT":
                case "UPDATE":
                    Empleado empleado = empleadoCrud.readEmpleado(h.idItem());
                    if (empleado != null) {
                        Map<String, String> empleadoMap = Map.of(
                                "firstName", empleado.firstName(),
                                "lastName", empleado.lastName(),
                                "deptName", empleado.deptName(),
                                "salary", String.valueOf(empleado.salary())
                        );
                        unifiedJedis.hset(key, empleadoMap);
                        historyCrud.deleteHistory(h.id());
                    }
                    break;
                case "DELETE":
                    unifiedJedis.del(key);
                    historyCrud.deleteHistory(h.id());
                    break;
                default:
                    System.out.println("Unknown type: " + h.type());
            }
        }

        historyCrud.deleteAllHistory();
    }

}
