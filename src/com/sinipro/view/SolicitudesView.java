package com.sinipro.view;

import javax.swing.*;
import java.awt.*;

public class SolicitudesView extends JFrame {
    private static final long serialVersionUID = 1L;
	public JTable tablaSolicitudes;
    public JButton btnRestaurar = new JButton("Restaurar Siniestro");
    public JButton btnEliminarPermanente = new JButton("Eliminar Permanente");
    public JButton btnVolver = new JButton("Volver");

    public SolicitudesView() {
        setTitle("SINI PRO - Solicitudes de Eliminación");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        tablaSolicitudes = new JTable();
        add(new JScrollPane(tablaSolicitudes), BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        panelSur.add(btnRestaurar);
        panelSur.add(btnEliminarPermanente);

        panelSur.add(btnVolver);
        add(panelSur, BorderLayout.SOUTH);
    }
}