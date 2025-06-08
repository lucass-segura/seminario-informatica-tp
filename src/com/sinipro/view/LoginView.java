package com.sinipro.view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;
	public JTextField txtEmail = new JTextField(20);
    public JPasswordField txtPass = new JPasswordField(20);
    public JButton btnLogin = new JButton("Ingresar");
    public JLabel lblMsg = new JLabel(" ", SwingConstants.CENTER);

    public LoginView() {
        setTitle("SINI PRO - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 220);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(0,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,25,20,25));

        panel.add(new JLabel("E-mail:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPass);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        buttonPanel.add(btnLogin);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}