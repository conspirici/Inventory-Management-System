package com.grocerflow.view.dashboard;

import com.grocerflow.view.auth.LoginView;
import com.grocerflow.view.product.*;
import com.grocerflow.view.report.GenerateReportView;
import com.grocerflow.view.user.UserApprovalView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

public class AdminDashboardView extends JFrame {

    private final int currentUserId;

    public AdminDashboardView(int currentUserId) {
        this.currentUserId = currentUserId;
        setTitle("Admin Dashboard - GrocerFlow");
        setSize(820, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JLabel header = new JLabel("Welcome, Admin", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(Color.DARK_GRAY);
        header.setBounds(250, 20, 300, 40);
        add(header);

        JPanel container = new JPanel(null);
        container.setBounds(50, 80, 700, 420);
        container.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        container.setBackground(Color.WHITE);
        add(container);

        String[] labels = {
                "Add Product", "View Products",
                "Remove Product", "Search Product",
                "User Approvals", "Generate Report"
        };

        Supplier<JFrame>[] views = new Supplier[]{
                () -> new AddProductView(currentUserId),
                ViewInventoryView::new,
                RemoveProductView::new,
                SearchProductView::new,
                UserApprovalView::new,
                GenerateReportView::new
        };

        for (int i = 0; i < labels.length; i++) {
            JPanel card = createDraggableCard(labels[i], views[i]);
            card.setBounds((i % 2 == 0 ? 50 : 370), (i / 2) * 130 + 20, 280, 100);
            container.add(card);
        }

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(650, 520, 100, 35);
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutBtn.setBackground(new Color(70, 70, 70));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                logoutBtn.setBackground(new Color(45, 45, 45));
            }

            public void mouseExited(MouseEvent e) {
                logoutBtn.setBackground(new Color(70, 70, 70));
            }
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginView();
        });

        add(logoutBtn);
    }

    private JPanel createDraggableCard(String title, Supplier<JFrame> viewSupplier) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(230, 230, 240));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(Color.DARK_GRAY);
        card.add(label, BorderLayout.CENTER);

        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    JFrame nextView = viewSupplier.get();
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
                Container parent = card.getParent();
                if (parent != null) {
                    parent.setComponentZOrder(card, 0);
                    parent.repaint();
                }
                card.repaint();
            }
        });

        return card;
    }

    public static void main(String[] args) {
        new AdminDashboardView(1);
    }
}
