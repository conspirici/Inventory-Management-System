import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import javax.swing.*;

public class AccountManagementSystem extends JFrame {
    private static final String DATA_FILE = Paths.get("Lab_Assignment_1", "users.txt").toString();
    private JTabbedPane tabbedPane;
    private JPanel registerPanel, updatePanel, deletePanel, rolePanel;
    private JTextField regNameField, regEmailField, updateIdField, updateNameField, updateEmailField, deleteIdField, roleIdField;
    private JPasswordField regPasswordField;
    private JComboBox<String> regRoleCombo, roleCombo;
    private JButton regSubmitBtn, updateSubmitBtn, deleteSubmitBtn, roleSubmitBtn;
    private Map<String, User> users = new HashMap<>();

    public AccountManagementSystem() {
        setTitle("Admin Account Management");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        

        loadUserData();

        tabbedPane = new JTabbedPane();
        
        // Initialize all panels
        registerPanel = createRegisterPanel();
        updatePanel = createUpdatePanel();
        deletePanel = createDeletePanel();
        rolePanel = createRolePanel();

        tabbedPane.addTab("Register", registerPanel);
        tabbedPane.addTab("Update", updatePanel);
        tabbedPane.addTab("Delete", deletePanel);
        tabbedPane.addTab("Assign Role", rolePanel);

        add(tabbedPane, BorderLayout.CENTER);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveUserData();
            }
        });
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        regNameField = new JTextField();
        regEmailField = new JTextField();
        regPasswordField = new JPasswordField();
        String[] roles = {"Admin", "Manager", "Staff"};
        regRoleCombo = new JComboBox<>(roles);
        regSubmitBtn = new JButton("Register");

        panel.add(new JLabel("Name:"));
        panel.add(regNameField);
        panel.add(new JLabel("Email:"));
        panel.add(regEmailField);
        panel.add(new JLabel("Password:"));
        panel.add(regPasswordField);
        panel.add(new JLabel("Role:"));
        panel.add(regRoleCombo);
        panel.add(new JLabel());
        panel.add(regSubmitBtn);

        regSubmitBtn.addActionListener(e -> registerUser());
        
        return panel;
    }

    private JPanel createUpdatePanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        updateIdField = new JTextField();
        updateNameField = new JTextField();
        updateEmailField = new JTextField();
        updateSubmitBtn = new JButton("Update");

        panel.add(new JLabel("User Email:"));
        panel.add(updateIdField);
        panel.add(new JLabel("New Name:"));
        panel.add(updateNameField);
        panel.add(new JLabel("New Email:"));
        panel.add(updateEmailField);
        panel.add(new JLabel());
        panel.add(updateSubmitBtn);

        updateSubmitBtn.addActionListener(e -> updateUser());
        
        return panel;
    }

    private JPanel createDeletePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        deleteIdField = new JTextField();
        deleteSubmitBtn = new JButton("Delete");

        panel.add(new JLabel("User Email:"));
        panel.add(deleteIdField);
        panel.add(new JLabel());
        panel.add(deleteSubmitBtn);

        deleteSubmitBtn.addActionListener(e -> deleteUser());
        
        return panel;
    }

    private JPanel createRolePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        roleIdField = new JTextField();
        String[] roles = {"Admin", "Manager", "Staff"};
        roleCombo = new JComboBox<>(roles);
        roleSubmitBtn = new JButton("Assign Role");

        panel.add(new JLabel("User Email:"));
        panel.add(roleIdField);
        panel.add(new JLabel("New Role:"));
        panel.add(roleCombo);
        panel.add(new JLabel());
        panel.add(roleSubmitBtn);

        roleSubmitBtn.addActionListener(e -> assignRole());
        
        return panel;
    }

    private void registerUser() {
        String name = regNameField.getText();
        String email = regEmailField.getText();
        String password = new String(regPasswordField.getPassword());
        String role = (String) regRoleCombo.getSelectedItem();
        
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }
        
        if (!users.containsKey(email)) {
            users.put(email, new User(name, email, password, role));
            JOptionPane.showMessageDialog(this, "User registered successfully");
            clearRegisterFields();
            saveUserData();
        } else {
            JOptionPane.showMessageDialog(this, "Email already exists");
        }
    }

    private void updateUser() {
        String oldEmail = updateIdField.getText();
        String newName = updateNameField.getText();
        String newEmail = updateEmailField.getText();
        
        if (users.containsKey(oldEmail)) {
            User user = users.get(oldEmail);
            user.setName(newName);
            if (!oldEmail.equals(newEmail)) {
                users.remove(oldEmail);
                user.setEmail(newEmail);
                users.put(newEmail, user);
            }
            JOptionPane.showMessageDialog(this, "User updated successfully");
            clearUpdateFields();
            saveUserData();
        } else {
            JOptionPane.showMessageDialog(this, "User not found");
        }
    }

    private void deleteUser() {
        String email = deleteIdField.getText();
        if (users.containsKey(email)) {
            users.remove(email);
            JOptionPane.showMessageDialog(this, "User deleted successfully");
            deleteIdField.setText("");
            saveUserData();
        } else {
            JOptionPane.showMessageDialog(this, "User not found");
        }
    }

    private void assignRole() {
        String email = roleIdField.getText();
        String newRole = (String) roleCombo.getSelectedItem();
        
        if (users.containsKey(email)) {
            users.get(email).setRole(newRole);
            JOptionPane.showMessageDialog(this, "Role assigned successfully");
            roleIdField.setText("");
            saveUserData();
        } else {
            JOptionPane.showMessageDialog(this, "User not found");
        }
    }

    private void clearRegisterFields() {
        regNameField.setText("");
        regEmailField.setText("");
        regPasswordField.setText("");
    }

    private void clearUpdateFields() {
        updateIdField.setText("");
        updateNameField.setText("");
        updateEmailField.setText("");
    }

    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                users.put(user.getEmail(), user);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing user data found. Starting fresh.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading user data: " + e.getMessage());
        }
    }

    private void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (User user : users.values()) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving user data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AccountManagementSystem system = new AccountManagementSystem();
            system.setVisible(true);
        });
    }
}

class User {
    private String name;
    private String email;
    private String password;
    private String role;

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("|");
        joiner.add(name).add(email).add(password).add(role);
        return joiner.toString();
    }

    public static User fromString(String str) {
        String[] parts = str.split("\\|");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid user data format");
        }
        return new User(parts[0], parts[1], parts[2], parts[3]);
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}