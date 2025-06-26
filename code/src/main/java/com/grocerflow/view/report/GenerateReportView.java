package com.grocerflow.view.report;

import com.grocerflow.controller.ReportController;
import com.grocerflow.model.Report;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class GenerateReportView extends JFrame {

    private final ReportController reportController = new ReportController();
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JTextArea contentArea;
    private JComboBox<String> reportTypeCombo;

    private final int adminId = 1; // mock admin ID

    public GenerateReportView() {
        setTitle("Generate Report - GrocerFlow");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240)); // Grayscale background

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel titleLabel = new JLabel("Generate Report", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setBounds(250, 10, 300, 30);
        add(titleLabel);

        JLabel typeLabel = new JLabel("Report Type:");
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        typeLabel.setBounds(50, 60, 100, 25);
        add(typeLabel);

        reportTypeCombo = new JComboBox<>(new String[]{"Inventory", "Sales", "Users", "Custom"});
        reportTypeCombo.setBounds(150, 60, 200, 28);
        reportTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(reportTypeCombo);

        JLabel contentLabel = new JLabel("Content:");
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentLabel.setBounds(50, 100, 100, 25);
        add(contentLabel);

        contentArea = new JTextArea();
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        JScrollPane contentScroll = new JScrollPane(contentArea);
        contentScroll.setBounds(150, 100, 580, 100);
        contentScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(contentScroll);

        JButton generateBtn = new JButton("Generate");
        generateBtn.setBounds(150, 220, 120, 32);
        generateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        generateBtn.setBackground(new Color(60, 130, 200));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFocusPainted(false);
        generateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        generateBtn.addActionListener(this::handleGenerateReport);
        add(generateBtn);

        JLabel recentReportsLabel = new JLabel("Recent Reports:");
        recentReportsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        recentReportsLabel.setBounds(50, 270, 150, 25);
        add(recentReportsLabel);

        tableModel = new DefaultTableModel(new String[]{"ID", "Type", "Generated At", "Preview"}, 0);
        reportTable = new JTable(tableModel);
        reportTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        reportTable.setRowHeight(24);
        reportTable.setShowVerticalLines(false);
        reportTable.setGridColor(new Color(210, 210, 210));
        reportTable.setSelectionBackground(new Color(220, 230, 240));
        reportTable.setSelectionForeground(Color.BLACK);

        // Table Header Styling
        reportTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        reportTable.getTableHeader().setBackground(new Color(230, 230, 230));
        reportTable.getTableHeader().setForeground(new Color(50, 50, 50));

        // Center align table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < reportTable.getColumnCount(); i++) {
            reportTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScroll = new JScrollPane(reportTable);
        tableScroll.setBounds(50, 300, 680, 200);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(tableScroll);

        loadReports();
    }

    private void handleGenerateReport(ActionEvent e) {
        String reportType = (String) reportTypeCombo.getSelectedItem();
        String content = contentArea.getText().trim();

        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Report content cannot be empty.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Report report = new Report(adminId, reportType, content);
        boolean success = reportController.generateReport(report);

        if (success) {
            JOptionPane.showMessageDialog(this, "Report generated successfully.");
            contentArea.setText("");
            loadReports();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to generate report.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadReports() {
        List<Report> reports = reportController.getAllReports();
        tableModel.setRowCount(0); // Clear previous data

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
