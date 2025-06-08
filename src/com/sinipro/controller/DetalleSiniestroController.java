package com.sinipro.controller;

import com.sinipro.dao.SiniestroDAO;
import com.sinipro.dao.SiniestroNotaDAO;
import com.sinipro.model.Siniestro;
import com.sinipro.model.SiniestroNota;
import com.sinipro.model.User;
import com.sinipro.view.DetalleSiniestroView;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class DetalleSiniestroController {
    private final DetalleSiniestroView vista;
    private final SiniestroDAO siniestroDAO = new SiniestroDAO();
    private final SiniestroNotaDAO notaDAO = new SiniestroNotaDAO();
    private final MainController mainController;
    private Siniestro siniestroActual;
    private final User usuarioLogueado;

    public DetalleSiniestroController(DetalleSiniestroView vista, Siniestro siniestro, MainController mainController, User usuario) {
        this.vista = vista;
        this.siniestroActual = siniestro;
        this.mainController = mainController;
        this.usuarioLogueado = usuario;
        initView();
        initListeners();
    }

    private void initView() {
        vista.lblNumeroSiniestro.setText(siniestroActual.getNumeroSiniestro());
        vista.lblAsegurado.setText(siniestroActual.getAsegurado().toString());
        vista.lblCompania.setText(siniestroActual.getCompania().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        vista.lblFecha.setText(sdf.format(siniestroActual.getFecha()));
        vista.txtDescripcion.setText(siniestroActual.getDescripcion());
        vista.cmbEstado.setSelectedItem(siniestroActual.getEstado());
        configurarVistaPorRol();
        refrescarListaNotas();
    }
    
    private void configurarVistaPorRol() {
        boolean esProductor = "productor".equalsIgnoreCase(usuarioLogueado.getRol());
        if (esProductor) {
            vista.btnEliminarSiniestro.setText("Eliminar Permanente");
        } else {
            vista.btnEliminarSiniestro.setText("Enviar a Papelera");
        }
        
        boolean editable = !"En papelera".equals(siniestroActual.getEstado()) && !"Eliminado".equals(siniestroActual.getEstado());
        vista.btnEliminarSiniestro.setEnabled(editable);
        vista.btnGuardar.setEnabled(editable);
        vista.cmbEstado.setEnabled(editable);
        vista.btnAgregarNota.setEnabled(editable);
    }

    private void initListeners() {
        vista.btnCerrar.addActionListener(e -> cerrarVentana());
        vista.btnGuardar.addActionListener(e -> guardarCambios());
        vista.btnEliminarSiniestro.addActionListener(e -> procesarBotonEliminar());

        vista.listaNotas.addListSelectionListener(e -> {
            boolean haySeleccion = vista.listaNotas.getSelectedValue() != null;
            boolean esEditable = !"En papelera".equals(siniestroActual.getEstado()) && !"Eliminado".equals(siniestroActual.getEstado());
            vista.btnEditarNota.setEnabled(haySeleccion && esEditable);
            vista.btnEliminarNota.setEnabled(haySeleccion && esEditable);
        });

        vista.btnAgregarNota.addActionListener(e -> agregarNota());
        vista.btnEditarNota.addActionListener(e -> editarNota());
        vista.btnEliminarNota.addActionListener(e -> eliminarNota());
    }
    
    private void procesarBotonEliminar() {
        boolean esProductor = "productor".equalsIgnoreCase(usuarioLogueado.getRol());
        String mensaje = esProductor 
            ? "El siniestro se marcará como 'Eliminado' y no podrá recuperarse.\n¿Continuar?"
            : "El siniestro se enviará a la papelera para su revisión.\n¿Continuar?";
        int confirm = JOptionPane.showConfirmDialog(vista, mensaje, "Confirmar Acción", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = esProductor 
                ? siniestroDAO.eliminarPermanente(siniestroActual.getId())
                : siniestroDAO.enviarAPapelera(siniestroActual.getId());
            
            if (exito) {
                JOptionPane.showMessageDialog(vista, "Acción realizada con éxito.");
                cerrarVentana();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al realizar la acción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarCambios() {
        String nuevoEstado = (String) vista.cmbEstado.getSelectedItem();
        if (!nuevoEstado.equals(siniestroActual.getEstado())) {
            siniestroDAO.actualizarEstado(siniestroActual.getId(), nuevoEstado);
        }
        cerrarVentana();
    }

    private void cerrarVentana() {
        vista.dispose();
        mainController.refrescarTablaSiniestros();
        mainController.getVista().setVisible(true);
    }
    
    private void refrescarListaNotas() {
        List<SiniestroNota> notas = notaDAO.obtenerPorSiniestroId(siniestroActual.getId());
        DefaultListModel<SiniestroNota> model = new DefaultListModel<>();
        notas.forEach(model::addElement);
        vista.listaNotas.setModel(model);
    }

    private void agregarNota() {
        String textoNota = JOptionPane.showInputDialog(vista, "Escribe la nueva nota de seguimiento:", "Agregar Nota", JOptionPane.PLAIN_MESSAGE);
        if (textoNota != null && !textoNota.trim().isEmpty()) {
            SiniestroNota nuevaNota = new SiniestroNota();
            nuevaNota.setSiniestroId(siniestroActual.getId());
            nuevaNota.setNota(textoNota.trim());
            if (notaDAO.crearNota(nuevaNota)) {
                refrescarListaNotas();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar la nota.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarNota() {
        SiniestroNota notaSeleccionada = vista.listaNotas.getSelectedValue();
        if (notaSeleccionada == null) return;
        
        String textoEditado = (String) JOptionPane.showInputDialog(vista, "Edita la nota:", "Editar Nota", JOptionPane.PLAIN_MESSAGE, null, null, notaSeleccionada.getNota());
        if (textoEditado != null && !textoEditado.trim().isEmpty()) {
            notaSeleccionada.setNota(textoEditado.trim());
            if (notaDAO.actualizarNota(notaSeleccionada)) {
                refrescarListaNotas();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar la nota.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarNota() {
        SiniestroNota notaSeleccionada = vista.listaNotas.getSelectedValue();
        if (notaSeleccionada == null) return;

        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de que quieres eliminar esta nota?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (notaDAO.eliminarNota(notaSeleccionada.getId())) {
                refrescarListaNotas();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la nota.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}