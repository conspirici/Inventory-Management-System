package com.grocerflow.main;


import com.grocerflow.view.auth.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView();
        });
        
    }
}
