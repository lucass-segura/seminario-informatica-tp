package com.sinipro.view;

import com.sinipro.model.Compania;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GestionCompaniaView extends JDialog {
    public JList<Compania> listaCompanias = new JList<>();
    public JButton btnNueva = new JButton("Nueva");
    public JButton btnEditar = new JButton("Editar");
    public JButton btnEliminar = new JButton("Eliminar");
    public JButton btnCerrar = new JButton("Cerrar");

    public GestionCompaniaView(JFrame owner) {
        super(owner, "Gestionar Compañías", true);
        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JScrollPane scrollPane = new JScrollPane(listaCompanias);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panelBotones.add(btnNueva);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);

        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
}