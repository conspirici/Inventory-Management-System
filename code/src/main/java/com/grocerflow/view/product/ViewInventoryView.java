package com.grocerflow.view.product;

import com.grocerflow.controller.ProductController;
import com.grocerflow.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ViewInventoryView extends JFrame {

    private final ProductController productController = new ProductController();
    private JTable table;
    private DefaultTableModel tableModel;

    public ViewInventoryView() {
        setTitle("View Inventory - GrocerFlow");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240)); // Grayscale background

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel header = new JLabel("Inventory Overview", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(new Color(40, 40, 40));
        header.setBorder(BorderFactory.createEmptyBorder(12, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Category", "Qty", "Price", "Added"});

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setGridColor(new Color(210, 210, 210));
        table.setSelectionBackground(new Color(200, 220, 240));
        table.setSelectionForeground(Color.BLACK);
        table.setShowVerticalLines(false);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHeader.setBackground(new Color(230, 230, 230));
        tableHeader.setForeground(new Color(50, 50, 50));
        tableHeader.setReorderingAllowed(false);

        // Center alignment for cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(240, 240, 240));

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshBtn.setBackground(new Color(60, 130, 200));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        refreshBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bottomPanel.add(refreshBtn);

        refreshBtn.addActionListener(e -> loadInventory());

        add(bottomPanel, BorderLayout.SOUTH);

        loadInventory();
    }

    private void loadInventory() {
        tableModel.setRowCount(0); // clear existing data
        List<Product> products = productController.getAllProducts();
        for (Product p : products) {
            tableModel.addRow(new Object[]{
                p.getProductId(),
                p.getName(),
                p.getCategory(),
                p.getQuantity(),
                String.format("$%.2f", p.getPrice()),
                p.getAddedAt() != null ? p.getAddedAt().toString() : "N/A"
            });
        }
    }

    public static void main(String[] args) {
        new ViewInventoryView();
    }
}
