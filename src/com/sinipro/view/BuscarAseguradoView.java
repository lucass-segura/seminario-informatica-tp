package com.sinipro.view;

import com.sinipro.model.Asegurado;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class BuscarAseguradoView extends JDialog {
    public JTextField txtFiltro = new JTextField(20);
    public JButton btnBuscar = new JButton("Buscar");
    public JList<Asegurado> listaResultados = new JList<>();
    public JButton btnSeleccionar = new JButton("Seleccionar");
    public JButton btnNuevo = new JButton("Crear Nuevo");
    public JButton btnCancelar = new JButton("Cancelar");

    public BuscarAseguradoView(JFrame owner) {
        super(owner, "Buscar Asegurado", true);
        setSize(450, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorte.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panelNorte.add(new JLabel("Nombre o DNI:"));
        panelNorte.add(txtFiltro);
        panelNorte.add(btnBuscar);

        JScrollPane scrollPane = new JScrollPane(listaResultados);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panelSur.add(btnNuevo);
        panelSur.add(btnCancelar);
        panelSur.add(btnSeleccionar);
        
        btnSeleccionar.setEnabled(false);

        add(panelNorte, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }
}