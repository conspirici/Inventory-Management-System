package com.grocerflow.controller;

import com.grocerflow.model.Report;
import com.grocerflow.model.dao.ReportDAO;

import java.util.List;

public class ReportController {

    private final ReportDAO reportDAO;

    public ReportController() {
        this.reportDAO = new ReportDAO();
    }

    public boolean generateReport(Report report) {
        return reportDAO.saveReport(report);
    }

    public List<Report> getAllReports() {
        return reportDAO.getAllReports();
    }

    public List<Report> getReportsByUser(int userId) {
        return reportDAO.getReportsByUser(userId);
    }

    public Report getReportById(int reportId) {
        return reportDAO.getReportById(reportId);
    }
}
