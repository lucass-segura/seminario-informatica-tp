package com.sinipro.controller;

import com.sinipro.dao.CompaniaDAO;
import com.sinipro.model.Compania;
import com.sinipro.view.GestionCompaniaView;
import javax.swing.*;
import java.util.List;

public class GestionCompaniaController {
    private final GestionCompaniaView vista;
    private final CompaniaDAO dao = new CompaniaDAO();

    public GestionCompaniaController(GestionCompaniaView vista) {
        this.vista = vista;
        initListeners();
        refrescarLista();
    }

    private void initListeners() {
        vista.btnCerrar.addActionListener(e -> vista.dispose());
        vista.btnNueva.addActionListener(e -> nuevaCompania());
        vista.btnEditar.addActionListener(e -> editarCompania());
        vista.btnEliminar.addActionListener(e -> eliminarCompania());

        vista.listaCompanias.addListSelectionListener(e -> {
            boolean seleccion = vista.listaCompanias.getSelectedValue() != null;
            vista.btnEditar.setEnabled(seleccion);
            vista.btnEliminar.setEnabled(seleccion);
        });
    }

    private void refrescarLista() {
        List<Compania> companias = dao.obtenerTodas();
        DefaultListModel<Compania> model = new DefaultListModel<>();
        companias.forEach(model::addElement);
        vista.listaCompanias.setModel(model);
    }

    private void nuevaCompania() {
        String nombre = JOptionPane.showInputDialog(vista, "Nombre de la nueva compañía:", "Nueva Compañía", JOptionPane.PLAIN_MESSAGE);
        if (nombre != null && !nombre.trim().isEmpty()) {
            if (dao.crearCompania(nombre.trim())) {
                refrescarLista();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al crear la compañía.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarCompania() {
        Compania seleccionada = vista.listaCompanias.getSelectedValue();
        if (seleccionada == null) return;

        String nuevoNombre = (String) JOptionPane.showInputDialog(vista, "Nuevo nombre para " + seleccionada.getNombre() + ":", "Editar Compañía", JOptionPane.PLAIN_MESSAGE, null, null, seleccionada.getNombre());
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            seleccionada = new Compania(seleccionada.getId(), nuevoNombre.trim());
            if (dao.actualizarCompania(seleccionada)) {
                refrescarLista();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar la compañía.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarCompania() {
        Compania seleccionada = vista.listaCompanias.getSelectedValue();
        if (seleccionada == null) return;

        int confirm = JOptionPane.showConfirmDialog(vista, "¿Está seguro de que desea eliminar la compañía " + seleccionada.getNombre() + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminarCompania(seleccionada.getId())) {
                refrescarLista();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la compañía. Es posible que esté asociada a un siniestro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}