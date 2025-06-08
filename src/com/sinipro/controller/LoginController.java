package com.sinipro.controller;

import com.sinipro.dao.UserDAO;
import com.sinipro.model.User;
import com.sinipro.view.LoginView;
import com.sinipro.view.MainView;
import javax.swing.*;

public class LoginController {

    private final LoginView vista;
    private final UserDAO dao = new UserDAO();

    public LoginController(LoginView vista) {
        this.vista = vista;
        initListeners();
    }

    private void initListeners() {
        vista.btnLogin.addActionListener(e -> autenticar());
    }

    private void autenticar() {
        String email = vista.txtEmail.getText();
        String pass  = String.valueOf(vista.txtPass.getPassword());

        User u = dao.validar(email, pass);
        if (u != null) {
            vista.dispose();
            MainView mainView = new MainView();
            new MainController(mainView, u);
            mainView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vista,
                    "Credenciales incorrectas",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}