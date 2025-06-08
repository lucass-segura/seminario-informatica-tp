package com.sinipro.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
@SuppressWarnings("serial")
public class MainView extends JFrame {
    public JTextField txtBuscar = new JTextField(30);
    public JButton btnBuscar = new JButton("Buscar");
    public JButton btnAbrirSiniestro = new JButton("Abrir Siniestro");
    public JButton btnPapelera = new JButton();
    public JButton btnNuevoSiniestro = new JButton("Nuevo Siniestro");
    public JTable tablaSiniestros;
    public DefaultTableModel modeloTabla;

    public MainView() {
        setTitle("SINI PRO - Gestión Profesional de Siniestros");
        setSize(950, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelNorte.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelNorte.add(new JLabel("Buscar:"));
        panelNorte.add(txtBuscar);

        JPanel panelBotonesDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotonesDerecha.add(btnAbrirSiniestro);
        panelBotonesDerecha.add(btnPapelera);
        panelBotonesDerecha.add(btnNuevoSiniestro);
        btnAbrirSiniestro.setEnabled(false);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(panelNorte, BorderLayout.WEST);
        topContainer.add(panelBotonesDerecha, BorderLayout.EAST);

        String[] columnas = {"N° Siniestro", "Asegurado", "Fecha", "Estado", "Compañía", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaSiniestros = new JTable(modeloTabla);
        tablaSiniestros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaSiniestros.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(tablaSiniestros);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(topContainer, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}