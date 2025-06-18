package com.grocerflow.view.user;

import com.grocerflow.controller.UserController;
import com.grocerflow.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

        initUI();
        loadPendingUsers();
        setVisible(true);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Pending User Approvals", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"User ID", "Username", "Email", "Role"}, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();

        JButton approveBtn = new JButton("Approve");
        approveBtn.addActionListener(e -> handleUserAction("approve"));
        actionPanel.add(approveBtn);

        JButton rejectBtn = new JButton("Reject");
        rejectBtn.addActionListener(e -> handleUserAction("reject"));
        actionPanel.add(rejectBtn);

        add(actionPanel, BorderLayout.SOUTH);
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
        boolean result;
        if (action.equals("approve")) {
            result = userController.approveUser(userId);
        } else {
            result = userController.rejectUser(userId);
        }

        if (result) {
            JOptionPane.showMessageDialog(this,
                    action.equals("approve") ? "User approved!" : "User rejected.");
            loadPendingUsers(); // refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Operation failed.");
        }
    }
}
