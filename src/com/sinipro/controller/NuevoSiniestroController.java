package com.sinipro.controller;

import com.sinipro.dao.CompaniaDAO;
import com.sinipro.dao.SiniestroDAO;
import com.sinipro.model.Asegurado;
import com.sinipro.model.Compania;
import com.sinipro.model.Siniestro;
import com.sinipro.model.User;
import com.sinipro.view.BuscarAseguradoView;
import com.sinipro.view.NuevoSiniestroView;
import javax.swing.*;
import java.util.Date;
import java.util.List;

public class NuevoSiniestroController {

    private final NuevoSiniestroView vista;
    private final MainController mainController;
    private final CompaniaDAO companiaDAO = new CompaniaDAO();
    private final SiniestroDAO siniestroDAO = new SiniestroDAO();
    private Asegurado aseguradoSeleccionado;
    private final User usuarioLogueado;

    public NuevoSiniestroController(NuevoSiniestroView vista, MainController mainController, User usuario) {
        this.vista = vista;
        this.mainController = mainController;
        this.usuarioLogueado = usuario;
        initListeners();
        cargarCompanias();
    }

    private void initListeners() {
        vista.btnBuscarAsegurado.addActionListener(e -> abrirDialogoBusqueda());
        vista.btnConfirmar.addActionListener(e -> crearSiniestro());
        vista.btnCancelar.addActionListener(e -> {
            vista.dispose();
            mainController.getVista().setVisible(true);
        });
    }
    
    private void abrirDialogoBusqueda() {
        BuscarAseguradoView dialogo = new BuscarAseguradoView(vista);
        new BuscarAseguradoController(dialogo, asegurado -> {
            this.aseguradoSeleccionado = asegurado;
            vista.txtAsegurado.setText(asegurado.toString());
        });
        dialogo.setVisible(true);
    }

    private void cargarCompanias() {
        List<Compania> companias = companiaDAO.obtenerTodas();
        DefaultComboBoxModel<Compania> model = new DefaultComboBoxModel<>();
        companias.forEach(model::addElement);
        vista.cmbCompania.setModel(model);
    }

    private void crearSiniestro() {
        if (aseguradoSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un asegurado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Compania compSeleccionada = (Compania) vista.cmbCompania.getSelectedItem();
        Date fecha;
        try {
            String fechaTexto = vista.txtFecha.getText();
            if (fechaTexto.contains("_")) {
                JOptionPane.showMessageDialog(vista, "La fecha está incompleta.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            fecha = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(fechaTexto);
             if (fecha.after(new Date())) {
                JOptionPane.showMessageDialog(vista, "La fecha del siniestro no puede ser futura.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(vista, "El formato de la fecha es inválido. Use dd/mm/aaaa.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String descripcion = vista.txtDescripcion.getText();

        Siniestro nuevoSiniestro = new Siniestro();
        nuevoSiniestro.setAsegurado(aseguradoSeleccionado);
        nuevoSiniestro.setCompania(compSeleccionada);
        nuevoSiniestro.setFecha(fecha);
        nuevoSiniestro.setDescripcion(descripcion);

        Siniestro siniestroCreado = siniestroDAO.crearSiniestro(nuevoSiniestro, usuarioLogueado.getId());
        if (siniestroCreado != null) {
            JOptionPane.showMessageDialog(vista, "Siniestro creado con N°: " + siniestroCreado.getNumeroSiniestro(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.dispose();
            mainController.refrescarTablaSiniestros();
            mainController.getVista().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vista, "Error al guardar el siniestro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}