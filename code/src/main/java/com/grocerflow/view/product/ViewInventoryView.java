package com.grocerflow.view.product;

import com.grocerflow.controller.ProductController;
import com.grocerflow.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        getContentPane().setBackground(new Color(245, 250, 255));

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel header = new JLabel("Inventory Overview", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Category", "Qty", "Price", "Added"});

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadInventory());
        bottomPanel.add(refreshBtn);
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
