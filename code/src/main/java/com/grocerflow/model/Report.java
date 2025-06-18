package com.grocerflow.model;

import java.sql.Timestamp;

public class Report {
    private int reportId;
    private int generatedBy;
    private String reportType;
    private Timestamp generatedAt;
    private String content;

    public Report() {}

    public Report(int reportId, int generatedBy, String reportType, Timestamp generatedAt, String content) {
        this.reportId = reportId;
        this.generatedBy = generatedBy;
        this.reportType = reportType;
        this.generatedAt = generatedAt;
        this.content = content;
    }

    public Report(int generatedBy, String reportType, String content) {
        this.generatedBy = generatedBy;
        this.reportType = reportType;
        this.content = content;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(int generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Timestamp getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Timestamp generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
