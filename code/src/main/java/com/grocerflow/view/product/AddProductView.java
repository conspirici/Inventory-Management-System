package com.grocerflow.view.product;

import com.grocerflow.controller.ProductController;
import com.grocerflow.model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddProductView extends JFrame {

    private final JTextField nameField = new JTextField();
    private final JTextField categoryField = new JTextField();
    private final JTextField quantityField = new JTextField();
    private final JTextField priceField = new JTextField();

    private final ProductController productController = new ProductController();
    private final int currentUserId;

    public AddProductView(int currentUserId) {
        this.currentUserId = currentUserId;
        setTitle("Add Product - GrocerFlow");
        setSize(500, 460);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240)); // Soft gray

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel header = new JLabel("Add New Product", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(new Color(50, 50, 50));
        header.setBounds(100, 20, 300, 30);
        add(header);

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(40, 70, 400, 260);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(formPanel);

        String[] labels = {"Product Name", "Category", "Quantity", "Price"};
        JTextField[] fields = {nameField, categoryField, quantityField, priceField};

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lbl.setBounds(20, 20 + i * 55, 120, 25);
            formPanel.add(lbl);

            JTextField field = fields[i];
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setBounds(150, 20 + i * 55, 220, 30);
            formPanel.add(field);
        }

        JButton addButton = new JButton("Add Product");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBounds(80, 350, 140, 35);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(this::handleAddProduct);
        add(addButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clearButton.setBackground(new Color(200, 200, 200));
        clearButton.setForeground(Color.DARK_GRAY);
        clearButton.setFocusPainted(false);
        clearButton.setBounds(260, 350, 140, 35);
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> clearFields());
        add(clearButton);
    }

    private void handleAddProduct(ActionEvent e) {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String quantityStr = quantityField.getText().trim();
        String priceStr = priceField.getText().trim();

        if (name.isEmpty() || category.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);

            Product product = new Product();
            product.setName(name);
            product.setCategory(category);
            product.setQuantity(quantity);
            product.setPrice(price);
            product.setAddedBy(currentUserId);

            boolean success = productController.addProduct(product, currentUserId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        categoryField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        int fakeUserId = 1;
        new AddProductView(fakeUserId);
    }
}
