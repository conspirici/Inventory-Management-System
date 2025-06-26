package com.grocerflow.view.product;

import com.grocerflow.controller.ProductController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RemoveProductView extends JFrame {

    private final JTextField productIdField = new JTextField();
    private final JTextField productNameField = new JTextField();

    private final ProductController productController = new ProductController();

    public RemoveProductView() {
        setTitle("Remove Product - GrocerFlow");
        setSize(480, 360);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240)); // light gray background

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel title = new JLabel("Remove Product", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(50, 50, 50));
        title.setBounds(90, 20, 300, 30);
        add(title);

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(40, 70, 390, 180);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(formPanel);

        JLabel idLabel = new JLabel("Product ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idLabel.setBounds(30, 20, 120, 25);
        formPanel.add(idLabel);

        productIdField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        productIdField.setBounds(150, 20, 200, 30);
        formPanel.add(productIdField);

        JLabel nameLabel = new JLabel("OR Product Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setBounds(30, 70, 130, 25);
        formPanel.add(nameLabel);

        productNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        productNameField.setBounds(150, 70, 200, 30);
        formPanel.add(productNameField);

        JButton removeButton = new JButton("Remove");
        removeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        removeButton.setBackground(new Color(220, 53, 69)); // soft red
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.setBounds(50, 120, 130, 35);
        removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        removeButton.addActionListener(this::handleRemoveProduct);
        formPanel.add(removeButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clearButton.setBackground(new Color(200, 200, 200));
        clearButton.setForeground(Color.DARK_GRAY);
        clearButton.setFocusPainted(false);
        clearButton.setBounds(200, 120, 130, 35);
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> clearFields());
        formPanel.add(clearButton);
    }

    private void handleRemoveProduct(ActionEvent e) {
        String idText = productIdField.getText().trim();
        String nameText = productNameField.getText().trim();

        if (idText.isEmpty() && nameText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter either Product ID or Name.", "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = false;

        if (!idText.isEmpty()) {
            try {
                int id = Integer.parseInt(idText);
                success = productController.removeProductById(id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Product ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            success = productController.removeProductByName(nameText);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Product removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to remove product.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        productIdField.setText("");
        productNameField.setText("");
    }

    public static void main(String[] args) {
        new RemoveProductView();
    }
}
