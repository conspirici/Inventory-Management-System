package com.grocerflow.model.dao;

import com.grocerflow.model.Report;
import com.grocerflow.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public boolean saveReport(Report report) {
        String sql = "INSERT INTO Reports (generated_by, report_type, content) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, report.getGeneratedBy());
            stmt.setString(2, report.getReportType());
            stmt.setString(3, report.getContent());

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Report> getAllReports() {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM Reports ORDER BY generated_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reports.add(mapResultSetToReport(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }

    public List<Report> getReportsByUser(int userId) {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM Reports WHERE generated_by = ? ORDER BY generated_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reports.add(mapResultSetToReport(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }

    public Report getReportById(int reportId) {
        String sql = "SELECT * FROM Reports WHERE report_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reportId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToReport(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Report mapResultSetToReport(ResultSet rs) throws SQLException {
        Report report = new Report();
        report.setReportId(rs.getInt("report_id"));
        report.setGeneratedBy(rs.getInt("generated_by"));
        report.setReportType(rs.getString("report_type"));
        report.setGeneratedAt(rs.getTimestamp("generated_at"));
        report.setContent(rs.getString("content"));
        return report;
    }
}
