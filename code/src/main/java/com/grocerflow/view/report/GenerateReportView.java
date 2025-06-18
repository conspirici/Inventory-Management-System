package com.grocerflow.view.report;

import com.grocerflow.controller.ReportController;
import com.grocerflow.model.Report;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.List;

public class GenerateReportView extends JFrame {

    private final ReportController reportController = new ReportController();
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JTextArea contentArea;
    private JComboBox<String> reportTypeCombo;

    // Mock admin ID for now (should come from session/user context in real app)
    private final int adminId = 1;

    public GenerateReportView() {
        setTitle("Generate Report - GrocerFlow");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel titleLabel = new JLabel("Generate Report", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setBounds(250, 10, 300, 30);
        add(titleLabel);

        JLabel typeLabel = new JLabel("Report Type:");
        typeLabel.setBounds(50, 60, 100, 25);
        add(typeLabel);

        reportTypeCombo = new JComboBox<>(new String[]{"Inventory", "Sales", "Users", "Custom"});
        reportTypeCombo.setBounds(150, 60, 200, 25);
        add(reportTypeCombo);

        JLabel contentLabel = new JLabel("Content:");
        contentLabel.setBounds(50, 100, 100, 25);
        add(contentLabel);

        contentArea = new JTextArea();
        JScrollPane contentScroll = new JScrollPane(contentArea);
        contentScroll.setBounds(150, 100, 580, 100);
        add(contentScroll);

        JButton generateBtn = new JButton("Generate");
        generateBtn.setBounds(150, 220, 120, 30);
        generateBtn.addActionListener(this::handleGenerateReport);
        add(generateBtn);

        JLabel recentReportsLabel = new JLabel("Recent Reports:");
        recentReportsLabel.setBounds(50, 270, 150, 25);
        add(recentReportsLabel);

        tableModel = new DefaultTableModel(new String[]{"ID", "Type", "Generated At", "Preview"}, 0);
        reportTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(reportTable);
        tableScroll.setBounds(50, 300, 680, 200);
        add(tableScroll);

        loadReports();
    }

    private void handleGenerateReport(ActionEvent e) {
        String reportType = (String) reportTypeCombo.getSelectedItem();
        String content = contentArea.getText().trim();

        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Report content cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Report report = new Report(adminId, reportType, content);
        boolean success = reportController.generateReport(report);

        if (success) {
            JOptionPane.showMessageDialog(this, "Report generated successfully.");
            contentArea.setText("");
            loadReports();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to generate report.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadReports() {
        List<Report> reports = reportController.getAllReports();
        tableModel.setRowCount(0); // Clear previous

        for (Report r : reports) {
            tableModel.addRow(new Object[]{
                    r.getReportId(),
                    r.getReportType(),
                    r.getGeneratedAt(),
                    r.getContent().length() > 50 ? r.getContent().substring(0, 50) + "..." : r.getContent()
            });
        }
    }

    public static void main(String[] args) {
        new GenerateReportView();
    }
}
