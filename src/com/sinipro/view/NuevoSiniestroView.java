package com.sinipro.view;

import com.sinipro.model.Compania;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class NuevoSiniestroView extends JFrame {

    public JTextField txtAsegurado = new JTextField();
    public JButton btnBuscarAsegurado = new JButton("Buscar...");
    public JComboBox<Compania> cmbCompania = new JComboBox<>();
    public JFormattedTextField txtFecha;
    public JTextArea txtDescripcion = new JTextArea(5, 30);
    public JButton btnConfirmar = new JButton("Confirmar");
    public JButton btnCancelar = new JButton("Cancelar");

    public NuevoSiniestroView() {
        setTitle("SINI PRO - Cargar Nuevo Siniestro");
        setSize(550, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtAsegurado.setEditable(false);
        txtAsegurado.setBackground(Color.WHITE);
        JPanel panelAseguradoCompuesto = new JPanel(new BorderLayout(5, 0));
        panelAseguradoCompuesto.add(txtAsegurado, BorderLayout.CENTER);
        panelAseguradoCompuesto.add(btnBuscarAsegurado, BorderLayout.EAST);
        JPanel panelAsegurado = createFieldPanel("Asegurado:", panelAseguradoCompuesto);
        
        formPanel.add(panelAsegurado);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        try {
            javax.swing.text.MaskFormatter dateMask = new javax.swing.text.MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            txtFecha = new JFormattedTextField(dateMask);
        } catch (java.text.ParseException e) {
            txtFecha = new JFormattedTextField();
        }

        JPanel panelCompania = createFieldPanel("Compañía:", cmbCompania);
        formPanel.add(panelCompania);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel panelFecha = createFieldPanel("Fecha del Hecho:", txtFecha);
        formPanel.add(panelFecha);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        JPanel panelDescripcion = createFieldPanel("Descripción:", scrollDescripcion);
        formPanel.add(panelDescripcion);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnConfirmar);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createFieldPanel(String labelText, Component component) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(120, 25));
        panel.add(label, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }
}