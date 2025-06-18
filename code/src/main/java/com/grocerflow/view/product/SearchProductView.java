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
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 255, 250));

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel title = new JLabel("Search Product", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBounds(150, 20, 200, 30);
        add(title);

        JLabel searchLabel = new JLabel("Product ID or Name:");
        searchLabel.setBounds(50, 80, 150, 25);
        add(searchLabel);

        searchField.setBounds(200, 80, 200, 30);
        add(searchField);
        makeDraggable(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(100, 130, 120, 35);
        searchBtn.addActionListener(this::handleSearch);
        add(searchBtn);

        JButton clearBtn = new JButton("Clear");
        clearBtn.setBounds(250, 130, 120, 35);
        clearBtn.addActionListener(e -> {
            searchField.setText("");
            resultArea.setText("");
        });
        add(clearBtn);

        resultArea.setBounds(50, 190, 380, 130);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setBorder(BorderFactory.createTitledBorder("Product Details"));
        add(resultArea);
    }

    private void handleSearch(ActionEvent e) {
    String input = searchField.getText().trim();
    if (input.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter Product ID or Name.");
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

    private void makeDraggable(JComponent comp) {
        final Point click = new Point();
        comp.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                click.setLocation(e.getPoint());
            }
        });

        comp.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = comp.getLocation();
                comp.setLocation(p.x + e.getX() - click.x,
                                 p.y + e.getY() - click.y);
            }
        });
    }

    public static void main(String[] args) {
        new SearchProductView();
    }
}
