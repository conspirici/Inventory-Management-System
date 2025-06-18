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
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 245, 245));

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel title = new JLabel("Remove Product", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBounds(100, 20, 250, 30);
        add(title);

        JLabel idLabel = new JLabel("Product ID:");
        idLabel.setBounds(50, 80, 100, 25);
        add(idLabel);

        productIdField.setBounds(160, 80, 200, 30);
        add(productIdField);
        makeDraggable(productIdField);

        JLabel nameLabel = new JLabel("OR Product Name:");
        nameLabel.setBounds(50, 130, 120, 25);
        add(nameLabel);

        productNameField.setBounds(160, 130, 200, 30);
        add(productNameField);
        makeDraggable(productNameField);

        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(90, 200, 120, 35);
        removeButton.setBackground(new Color(220, 20, 60));
        removeButton.setForeground(Color.WHITE);
        removeButton.addActionListener(this::handleRemoveProduct);
        add(removeButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(230, 200, 120, 35);
        clearButton.addActionListener(e -> clearFields());
        add(clearButton);
    }

    private void handleRemoveProduct(ActionEvent e) {
        String idText = productIdField.getText().trim();
        String nameText = productNameField.getText().trim();

        if (idText.isEmpty() && nameText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter either Product ID or Name.");
            return;
        }

        boolean success = false;

        if (!idText.isEmpty()) {
            try {
                int id = Integer.parseInt(idText);
                success = productController.removeProductById(id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Product ID.");
                return;
            }
        } else {
            success = productController.removeProductByName(nameText);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Product removed successfully.");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to remove product.");
        }
    }

    private void clearFields() {
        productIdField.setText("");
        productNameField.setText("");
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
        new RemoveProductView();
    }
}
