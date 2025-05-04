package controller;

import auth.UserAuthenticator;
import dao.UserDAO;
import javax.swing.JOptionPane;
import model.User;
import ui.DashboardForm;
import ui.LoginForm;

public class LoginController {
    private LoginForm loginForm;
    private UserAuthenticator authenticator;

    public LoginController(LoginForm loginForm) {
        this.loginForm = loginForm;
        this.authenticator = new UserAuthenticator();
    }

    public void login(String email, String password) {
    UserDAO userDAO = new UserDAO();
    User user = userDAO.getUserByEmail(email);

    if (user != null) {
        if (!user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(loginForm, "Incorrect password.");
        } else {
            DashboardForm dashboard = new DashboardForm();
            dashboard.setVisible(true);
            loginForm.dispose();
        }
    } else {
        String status = userDAO.getUserStatusByEmail(email);
        if ("Pending".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(loginForm, "Your account is pending approval.");
        } else {
            JOptionPane.showMessageDialog(loginForm, "Invalid email or password.");
        }
    }
}
}
