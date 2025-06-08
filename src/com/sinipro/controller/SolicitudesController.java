package com.sinipro.controller;

import com.sinipro.dao.SiniestroDAO;
import com.sinipro.model.Siniestro;
import com.sinipro.model.User;
import com.sinipro.view.SolicitudesView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class SolicitudesController {
    private final SolicitudesView vista;
    private final MainController mainController;
    private final User usuario;
    private final SiniestroDAO dao = new SiniestroDAO();
    private List<Siniestro> solicitudesEnTabla;

    public SolicitudesController(SolicitudesView vista, MainController mainController, User usuario) {
        this.vista = vista;
        this.mainController = mainController;
        this.usuario = usuario;
        configurarVistaPorRol();
        initListeners();
        refrescarTabla();
    }

    private void configurarVistaPorRol() {
        boolean esProductor = "productor".equalsIgnoreCase(usuario.getRol());
        vista.btnEliminarPermanente.setVisible(esProductor);
    }

    private void initListeners() {
        vista.btnVolver.addActionListener(e -> cerrarVentana());
        vista.btnEliminarPermanente.addActionListener(e -> eliminarPermanente());
        vista.btnRestaurar.addActionListener(e -> restaurarSiniestro());
    }
    
    private Siniestro getSiniestroSeleccionado() {
        int fila = vista.tablaSolicitudes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Debes seleccionar un siniestro de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return solicitudesEnTabla.get(fila);
    }

    private void eliminarPermanente() {
        Siniestro s = getSiniestroSeleccionado();
        if (s == null) return;
        
        int confirm = JOptionPane.showConfirmDialog(vista, "Se eliminará el siniestro N°" + s.getNumeroSiniestro() + " permanentemente.\nEsta acción no se puede deshacer. ¿Continuar?", "Eliminación Permanente", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminarPermanente(s.getId())) {
                refrescarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el siniestro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void restaurarSiniestro() {
        Siniestro s = getSiniestroSeleccionado();
        if (s == null) return;
        
        int confirm = JOptionPane.showConfirmDialog(vista, "El siniestro N°" + s.getNumeroSiniestro() + " saldrá de la papelera y volverá a estar activo.\n¿Continuar?", "Restaurar Siniestro", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.restaurarDesdePapelera(s.getId())) {
                refrescarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al restaurar el siniestro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @SuppressWarnings("serial")
	private void refrescarTabla() {
        Integer asesorId = "productor".equalsIgnoreCase(usuario.getRol()) ? null : usuario.getId();
        solicitudesEnTabla = dao.obtenerSiniestrosEnPapelera(asesorId);
        
        DefaultTableModel model = new DefaultTableModel(new String[]{"N° Siniestro", "Asegurado", "Fecha Hecho", "Estado Actual"}, 0){
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Siniestro s : solicitudesEnTabla) {
            model.addRow(new Object[]{s.getNumeroSiniestro(), s.getAsegurado().getNombre(), sdf.format(s.getFecha()), s.getEstado()});
        }
        vista.tablaSolicitudes.setModel(model);
    }

    private void cerrarVentana() {
        vista.dispose();
        mainController.refrescarTablaSiniestros();
        mainController.getVista().setVisible(true);
    }
}