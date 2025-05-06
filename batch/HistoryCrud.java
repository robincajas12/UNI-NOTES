package org.uce.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryCrud {
    private final Connection conn;

    public HistoryCrud(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public void createHistory(History history) throws SQLException {
        String sql = "INSERT INTO history (type, id_item) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, history.type());
            stmt.setInt(2, history.idItem());
            stmt.executeUpdate();
            conn.commit();
        }
    }

    // READ
    public History readHistory(int id) throws SQLException {
        String sql = "SELECT * FROM history WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new History(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getInt("id_item")
                    );
                } else {
                    return null;
                }
            }
        }
    }
     public List<History> readAllHistory() throws SQLException {
        String sql = "SELECT * FROM history";
        List<History> historyList = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                History history = new History(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getInt("id_item")
                );
                historyList.add(history);
            }
        }
        return historyList;
    }
    // UPDATE
    public void updateHistory(History history) throws SQLException {
        String sql = "UPDATE history SET type = ?, id_item = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, history.type());
            stmt.setInt(2, history.idItem());
            stmt.setInt(3, history.id());
            stmt.executeUpdate();
            conn.commit();
        }
    }

    // DELETE
    public void deleteHistory(int id) throws SQLException {
        String sql = "DELETE FROM history WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.commit();
        }
    }
    public void deleteAllHistory() throws SQLException {
        String sql = "DELETE FROM history";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            conn.commit();
        }
    }
}
