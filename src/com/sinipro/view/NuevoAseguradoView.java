package com.sinipro.view;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class NuevoAseguradoView extends JDialog {
    public JTextField txtDni = new JTextField(15);
    public JTextField txtNombre = new JTextField(15);
    public JTextField txtContacto = new JTextField(15);
    public JButton btnConfirmar = new JButton("Confirmar");
    public JButton btnCancelar = new JButton("Cancelar");

    public NuevoAseguradoView(JFrame owner) {
        super(owner, "Nuevo Asegurado", true);
        setSize(380, 220);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        formPanel.add(new JLabel("DNI:"));
        formPanel.add(txtDni);
        formPanel.add(new JLabel("Nombre y Apellido:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Contacto (Tel/Email):"));
        formPanel.add(txtContacto);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,5,5));
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnConfirmar);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}