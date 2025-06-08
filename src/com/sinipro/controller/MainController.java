package com.sinipro.controller;

import com.sinipro.dao.SiniestroDAO;
import com.sinipro.model.Siniestro;
import com.sinipro.model.User;
import com.sinipro.view.DetalleSiniestroView;
import com.sinipro.view.MainView;
import com.sinipro.view.NuevoSiniestroView;
import com.sinipro.view.SolicitudesView;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainController {
    private final MainView vista;
    private final SiniestroDAO dao = new SiniestroDAO();
    private List<Siniestro> siniestrosEnTabla;
    private final User usuarioLogueado;

    public MainController(MainView vista, User usuario) {
        this.vista = vista;
        this.usuarioLogueado = usuario;
        initListeners();
        configurarVistaPorRol();
        refrescarTablaSiniestros();
    }

    private void configurarVistaPorRol() {
        vista.btnPapelera.setText("Solicitudes");
        vista.btnPapelera.setVisible(true);
    }

    private void initListeners() {
        vista.btnBuscar.addActionListener(e -> refrescarTablaSiniestros());
        vista.txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { refrescarTablaSiniestros(); }
            public void removeUpdate(DocumentEvent e) { refrescarTablaSiniestros(); }
            public void changedUpdate(DocumentEvent e) { refrescarTablaSiniestros(); }
        });

        vista.btnNuevoSiniestro.addActionListener(e -> {
            vista.setVisible(false);
            NuevoSiniestroView nuevoSiniestroView = new NuevoSiniestroView();
            new NuevoSiniestroController(nuevoSiniestroView, this, usuarioLogueado);
            nuevoSiniestroView.setVisible(true);
        });

        vista.tablaSiniestros.getSelectionModel().addListSelectionListener(e -> {
            boolean filaSeleccionada = vista.tablaSiniestros.getSelectedRow() != -1;
            vista.btnAbrirSiniestro.setEnabled(filaSeleccionada);
        });

        vista.btnAbrirSiniestro.addActionListener(e -> {
            int filaSeleccionada = vista.tablaSiniestros.getSelectedRow();
            if (filaSeleccionada != -1) {
                abrirDetalleSiniestro(siniestrosEnTabla.get(filaSeleccionada));
            }
        });

        vista.btnPapelera.addActionListener(e -> abrirVentanaSolicitudes());
    }
    
    private void abrirDetalleSiniestro(Siniestro siniestro) {
        vista.setVisible(false);
        DetalleSiniestroView detalleView = new DetalleSiniestroView();
        new DetalleSiniestroController(detalleView, siniestro, this, usuarioLogueado);
        detalleView.setVisible(true);
    }
    
    private void abrirVentanaSolicitudes() {
        vista.setVisible(false);
        SolicitudesView solicitudesView = new SolicitudesView();
        new SolicitudesController(solicitudesView, this, usuarioLogueado);
        solicitudesView.setVisible(true);
    }

    public void refrescarTablaSiniestros() {
        String filtro = vista.txtBuscar.getText();
        this.siniestrosEnTabla = dao.buscarSiniestros(filtro);
        actualizarTabla(this.siniestrosEnTabla);
    }

    private void actualizarTabla(List<Siniestro> siniestros) {
        vista.modeloTabla.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Siniestro s : siniestros) {
            Object[] fila = {s.getNumeroSiniestro(), s.getAsegurado().getNombre(), sdf.format(s.getFecha()), s.getEstado(), s.getCompania().getNombre(), s.getDescripcion()};
            vista.modeloTabla.addRow(fila);
        }
    }

    public MainView getVista() {
        return this.vista;
    }
}