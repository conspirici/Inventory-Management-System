package com.grocerflow.model.dao;

import com.grocerflow.model.InventoryLog;
import com.grocerflow.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryLogDAO {

    public boolean addLog(InventoryLog log) {
        String sql = "INSERT INTO InventoryLogs (product_id, action_type, performed_by) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, log.getProductId());
            stmt.setString(2, log.getActionType());
            stmt.setInt(3, log.getPerformedBy());

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<InventoryLog> getAllLogs() {
        List<InventoryLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM InventoryLogs ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                logs.add(mapResultSetToLog(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    public List<InventoryLog> getLogsByUser(int userId) {
        List<InventoryLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM InventoryLogs WHERE performed_by = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(mapResultSetToLog(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    private InventoryLog mapResultSetToLog(ResultSet rs) throws SQLException {
        InventoryLog log = new InventoryLog();
        log.setLogId(rs.getInt("log_id"));
        log.setProductId(rs.getInt("product_id"));
        log.setActionType(rs.getString("action_type"));
        log.setPerformedBy(rs.getInt("performed_by"));
        log.setTimestamp(rs.getTimestamp("timestamp"));
        return log;
    }
}
