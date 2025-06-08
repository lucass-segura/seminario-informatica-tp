package com.sinipro.main;

import com.sinipro.controller.LoginController;
import com.sinipro.view.LoginView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginView v = new LoginView();
            new LoginController(v);
            v.setVisible(true);
        });
    }
}