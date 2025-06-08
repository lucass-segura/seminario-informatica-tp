package com.sinipro.view;

import com.sinipro.model.SiniestroNota;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class DetalleSiniestroView extends JFrame {
    public JLabel lblNumeroSiniestro = new JLabel();
    public JLabel lblAsegurado = new JLabel();
    public JLabel lblCompania = new JLabel();
    public JLabel lblFecha = new JLabel();
    public JTextArea txtDescripcion = new JTextArea(5, 30);
    public JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"Abierto", "En gestión", "Cerrado"});

    public JList<SiniestroNota> listaNotas = new JList<>();
    public JButton btnAgregarNota = new JButton("Agregar");
    public JButton btnEditarNota = new JButton("Editar");
    public JButton btnEliminarNota = new JButton("Eliminar");

    public JButton btnGuardar = new JButton("Guardar Cambios");
    public JButton btnEliminarSiniestro = new JButton("Eliminar Siniestro");
    public JButton btnCerrar = new JButton("Cerrar");

    public DetalleSiniestroView() {
        setTitle("SINI PRO - Detalle de Siniestro");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelDetalles = new JPanel();
        panelDetalles.setLayout(new BoxLayout(panelDetalles, BoxLayout.Y_AXIS));
        panelDetalles.add(createDetailRow("N° Siniestro:", lblNumeroSiniestro));
        panelDetalles.add(Box.createRigidArea(new Dimension(0, 5)));
        panelDetalles.add(createDetailRow("Asegurado:", lblAsegurado));
        panelDetalles.add(Box.createRigidArea(new Dimension(0, 5)));
        panelDetalles.add(createDetailRow("Compañía:", lblCompania));
        panelDetalles.add(Box.createRigidArea(new Dimension(0, 5)));
        panelDetalles.add(createDetailRow("Fecha Hecho:", lblFecha));
        panelDetalles.add(Box.createRigidArea(new Dimension(0, 5)));
        panelDetalles.add(createDetailRow("Estado:", cmbEstado));
        panelDetalles.add(Box.createRigidArea(new Dimension(0, 10)));
        
        txtDescripcion.setEditable(false);
        txtDescripcion.setFont(new Font("SansSerif", Font.ITALIC, 12));
        txtDescripcion.setBackground(new Color(240, 240, 240));
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setLineWrap(true);
        JScrollPane descScrollPane = new JScrollPane(txtDescripcion);
        descScrollPane.setBorder(BorderFactory.createTitledBorder("Descripción"));
        panelDetalles.add(descScrollPane);
        
        JPanel panelNotas = new JPanel(new BorderLayout(0, 5));
        panelNotas.setBorder(BorderFactory.createTitledBorder("Notas de Seguimiento"));
        panelNotas.add(new JScrollPane(listaNotas), BorderLayout.CENTER);
        
        JPanel panelBotonesNotas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonesNotas.add(btnAgregarNota);
        panelBotonesNotas.add(btnEditarNota);
        panelBotonesNotas.add(btnEliminarNota);
        panelNotas.add(panelBotonesNotas, BorderLayout.SOUTH);
        
        btnEditarNota.setEnabled(false);
        btnEliminarNota.setEnabled(false);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAcciones.add(btnEliminarSiniestro);
        panelAcciones.add(btnCerrar);
        panelAcciones.add(btnGuardar);
        
        panelPrincipal.add(panelDetalles, BorderLayout.NORTH);
        panelPrincipal.add(panelNotas, BorderLayout.CENTER);
        panelPrincipal.add(panelAcciones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel createDetailRow(String labelText, Component component) {
        JPanel panel = new JPanel(new BorderLayout(10, 2));
        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setPreferredSize(new Dimension(110, 25));
        panel.add(label, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }
}