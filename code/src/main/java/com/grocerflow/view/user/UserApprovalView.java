package com.grocerflow.view.user;

import com.grocerflow.controller.UserController;
import com.grocerflow.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class UserApprovalView extends JFrame {

    private final UserController userController;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public UserApprovalView() {
        userController = new UserController();
        setTitle("User Approvals - GrocerFlow");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240)); // light gray

        initUI();
        loadPendingUsers();
        setVisible(true);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Pending User Approvals", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(50, 50, 50));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"User ID", "Username", "Email", "Role"}, 0);
        userTable = new JTable(tableModel);
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userTable.setRowHeight(24);
        userTable.setGridColor(new Color(210, 210, 210));
        userTable.setSelectionBackground(new Color(220, 225, 230));
        userTable.setSelectionForeground(Color.BLACK);

        // Center cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < userTable.getColumnCount(); i++) {
            userTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Header styling
        JTableHeader header = userTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(230, 230, 230));
        header.setForeground(new Color(60, 60, 60));

        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(new Color(240, 240, 240));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton approveBtn = new JButton("Approve");
        styleButton(approveBtn, new Color(100, 100, 100)); // dark gray

        JButton rejectBtn = new JButton("Reject");
        styleButton(rejectBtn, new Color(130, 130, 130)); // medium gray

        approveBtn.addActionListener(e -> handleUserAction("approve"));
        rejectBtn.addActionListener(e -> handleUserAction("reject"));

        actionPanel.add(approveBtn);
        actionPanel.add(rejectBtn);
        add(actionPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void loadPendingUsers() {
        tableModel.setRowCount(0); // clear table
        List<User> pendingUsers = userController.getPendingUsers();
        for (User user : pendingUsers) {
            tableModel.addRow(new Object[]{
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
            });
        }
    }

    private void handleUserAction(String action) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        boolean result = "approve".equals(action)
                ? userController.approveUser(userId)
                : userController.rejectUser(userId);

        if (result) {
            JOptionPane.showMessageDialog(this,
                    action.equals("approve") ? "User approved!" : "User rejected.");
            loadPendingUsers(); // refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Operation failed.");
        }
    }
}
