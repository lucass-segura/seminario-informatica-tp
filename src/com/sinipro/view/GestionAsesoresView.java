package com.sinipro.view;

import com.sinipro.model.User;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GestionAsesoresView extends JDialog {
    public JList<User> listaAsesores = new JList<>();
    public JButton btnNuevo = new JButton("Nuevo");
    public JButton btnEditar = new JButton("Editar");
    public JButton btnEliminar = new JButton("Eliminar");
    public JButton btnCerrar = new JButton("Cerrar");

    public GestionAsesoresView(JFrame owner) {
        super(owner, "Gestionar Asesores", true);
        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JScrollPane scrollPane = new JScrollPane(listaAsesores);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);

        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
}