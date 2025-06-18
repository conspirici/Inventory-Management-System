package com.grocerflow.main;


import com.grocerflow.view.auth.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set UI look and feel (optional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Failed to set look and feel.");
        }

        // Launch the login view
        SwingUtilities.invokeLater(() -> {
            new LoginView();
        });
        
    }
}
