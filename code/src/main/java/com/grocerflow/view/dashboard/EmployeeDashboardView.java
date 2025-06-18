package com.grocerflow.view.dashboard;

import com.grocerflow.view.auth.LoginView;
import com.grocerflow.view.product.AddProductView;
import com.grocerflow.view.product.ViewInventoryView;
import com.grocerflow.view.product.SearchProductView;
import com.grocerflow.view.report.GenerateReportView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmployeeDashboardView extends JFrame {

    private final int userId;

    // NEW constructor accepting userId
    public EmployeeDashboardView(int userId) {
        this.userId = userId;
        setTitle("Employee Dashboard - GrocerFlow");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(250, 250, 255));

        initUI();
        setVisible(true);
    }

    // Optional no-arg constructor for testing
    public EmployeeDashboardView() {
        this(0); // Default userId (not recommended for production)
    }

    private void initUI() {
        // Use userId in the header if available
        String headerText = "Welcome Employee" + (userId != 0 ? " #" + userId : "");
        JLabel header = new JLabel(headerText, SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setBounds(250, 20, 300, 40);
        add(header);

        JPanel container = new JPanel(null);
        container.setBounds(50, 80, 700, 400);
        container.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        container.setBackground(Color.WHITE);
        add(container);

        String[] labels = {
                "Add Product",
                "View Products",
                "Search Products",
                "Generate Report"
        };

        Class<?>[] views = {
                AddProductView.class,
                ViewInventoryView.class,
                SearchProductView.class,
                GenerateReportView.class
        };

        for (int i = 0; i < labels.length; i++) {
            JPanel card = createDraggableCard(labels[i], views[i]);
            // Position cards in two columns
            card.setBounds((i % 2 == 0 ? 50 : 370), (i / 2) * 120 + 20, 250, 100);
            container.add(card);
        }

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(650, 500, 100, 30);
        logoutBtn.setBackground(Color.RED);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginView();
        });
        add(logoutBtn);
    }

    private JPanel createDraggableCard(String title, Class<?> viewClass) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(220, 240, 255));
        card.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        card.add(label, BorderLayout.CENTER);

        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    // For simplicity, we use the no-arg constructor of each view.
                    // If any of these views also require a userId, you'll need to adjust similarly.
                    JFrame nextView = (JFrame) viewClass.getDeclaredConstructor().newInstance();
                    nextView.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(card, "Error loading view: " + title);
                }
            }
        });

        final Point clickPoint = new Point();
        card.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = card.getLocation();
                card.setLocation(p.x + e.getX() - clickPoint.x, p.y + e.getY() - clickPoint.y);
            }
        });
        card.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                clickPoint.setLocation(e.getPoint());
                card.setComponentZOrder(card, 0);
                card.repaint();
            }
        });

        return card;
    }

    public static void main(String[] args) {
        new EmployeeDashboardView(); // For testing, calls the no-arg constructor (userId=0)
    }
}
