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
    private final int currentUserId; // ✅ dynamic user ID

    public AddProductView(int currentUserId) {
        this.currentUserId = currentUserId; // ✅ assign passed user ID
        setTitle("Add Product - GrocerFlow");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 250, 255));

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel header = new JLabel("Add New Product", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setBounds(100, 20, 300, 30);
        add(header);

        String[] labels = {"Product Name", "Category", "Quantity", "Price"};
        JTextField[] fields = {nameField, categoryField, quantityField, priceField};

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setBounds(50, 70 + i * 50, 150, 30);
            add(lbl);

            JTextField field = fields[i];
            field.setBounds(200, 70 + i * 50, 220, 30);
            add(field);
        }

        JButton addButton = new JButton("Add Product");
        addButton.setBounds(100, 350, 130, 35);
        addButton.setBackground(new Color(60, 179, 113));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(this::handleAddProduct);
        add(addButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(250, 350, 130, 35);
        clearButton.addActionListener(e -> clearFields());
        add(clearButton);
    }

    private void handleAddProduct(ActionEvent e) {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String quantityStr = quantityField.getText().trim();
        String priceStr = priceField.getText().trim();

        if (name.isEmpty() || category.isEmpty() || quantityStr.isEmpty() ||
            priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
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


            boolean success = productController.addProduct(product, currentUserId); // ✅ use dynamic user ID
            if (success) {
                JOptionPane.showMessageDialog(this, "Product added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        categoryField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        // ✅ Replace with actual user info in real app
        int fakeUserId = 1;
        new AddProductView(fakeUserId);
    }
}
