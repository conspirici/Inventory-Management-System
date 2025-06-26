package com.grocerflow.view.product;

import com.grocerflow.controller.ProductController;
import com.grocerflow.model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class SearchProductView extends JFrame {

    private final JTextField searchField = new JTextField();
    private final JTextArea resultArea = new JTextArea();
    private final ProductController productController = new ProductController();

    public SearchProductView() {
        setTitle("Search Product - GrocerFlow");
        setSize(520, 420);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel title = new JLabel("Search Product", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(50, 50, 50));
        title.setBounds(130, 20, 260, 30);
        add(title);

        JPanel inputPanel = new JPanel(null);
        inputPanel.setBounds(40, 70, 420, 90);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(inputPanel);

        JLabel searchLabel = new JLabel("Product ID or Name:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchLabel.setBounds(20, 20, 150, 25);
        inputPanel.add(searchLabel);

        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBounds(170, 20, 220, 30);
        inputPanel.add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchBtn.setBackground(new Color(60, 130, 200));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setBounds(70, 60, 120, 30);
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.addActionListener(this::handleSearch);
        inputPanel.add(searchBtn);

        JButton clearBtn = new JButton("Clear");
        clearBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        clearBtn.setBackground(new Color(200, 200, 200));
        clearBtn.setForeground(Color.DARK_GRAY);
        clearBtn.setBounds(210, 60, 120, 30);
        clearBtn.setFocusPainted(false);
        clearBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearBtn.addActionListener(e -> {
            searchField.setText("");
            resultArea.setText("");
        });
        inputPanel.add(clearBtn);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(40, 180, 420, 160);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Product Details"));
        add(scrollPane);

        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(250, 250, 250));
        resultArea.setForeground(new Color(40, 40, 40));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
    }

    private void handleSearch(ActionEvent e) {
        String input = searchField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Product ID or Name.", "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Product> products;
        try {
            int id = Integer.parseInt(input);
            Product product = productController.getProductById(id);
            products = new ArrayList<>();
            if (product != null) {
                products.add(product);
            }
        } catch (NumberFormatException ex) {
            products = productController.searchProducts(input);
        }

        if (!products.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Product p : products) {
                sb.append(String.format(
                    "ID: %d\nName: %s\nCategory: %s\nQuantity: %d\nPrice: $%.2f\nAdded: %s\n\n",
                    p.getProductId(),
                    p.getName(),
                    p.getCategory(),
                    p.getQuantity(),
                    p.getPrice(),
                    p.getAddedAt() != null ? p.getAddedAt().toString() : "N/A"
                ));
            }
            resultArea.setText(sb.toString());
        } else {
            resultArea.setText("No matching products found.");
        }
    }

    public static void main(String[] args) {
        new SearchProductView();
    }
}
