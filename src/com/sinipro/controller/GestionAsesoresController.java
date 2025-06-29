package com.sinipro.controller;

import com.sinipro.dao.UserDAO;
import com.sinipro.model.User;
import com.sinipro.view.GestionAsesoresView;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GestionAsesoresController {
    private final GestionAsesoresView vista;
    private final UserDAO dao = new UserDAO();

    public GestionAsesoresController(GestionAsesoresView vista) {
        this.vista = vista;
        initListeners();
        refrescarLista();
    }

    private void initListeners() {
        vista.btnCerrar.addActionListener(e -> vista.dispose());
        vista.btnNuevo.addActionListener(e -> nuevoAsesor());
        vista.btnEditar.addActionListener(e -> editarAsesor());
        vista.btnEliminar.addActionListener(e -> eliminarAsesor());

        vista.listaAsesores.addListSelectionListener(e -> {
            boolean seleccion = vista.listaAsesores.getSelectedValue() != null;
            vista.btnEditar.setEnabled(seleccion);
            vista.btnEliminar.setEnabled(seleccion);
        });
    }

    private void refrescarLista() {
        List<User> asesores = dao.obtenerTodosLosAsesores();
        DefaultListModel<User> model = new DefaultListModel<>();
        asesores.forEach(model::addElement);
        vista.listaAsesores.setModel(model);
    }

    private void nuevoAsesor() {
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(vista, panel, "Nuevo Asesor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (email != null && !email.trim().isEmpty() && password != null && !password.isEmpty()) {
                User asesor = new User();
                asesor.setEmail(email.trim());
                asesor.setPassword(password);
                if (dao.crearAsesor(asesor)) {
                    refrescarLista();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al crear el asesor.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Email y contraseña no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarAsesor() {
        User seleccionado = vista.listaAsesores.getSelectedValue();
        if (seleccionado == null) return;

        JTextField emailField = new JTextField(seleccionado.getEmail());
        JPasswordField passwordField = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Nueva Contraseña (dejar en blanco para no cambiar):"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(vista, panel, "Editar Asesor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (email != null && !email.trim().isEmpty()) {
                seleccionado.setEmail(email.trim());
                if (password != null && !password.isEmpty()) {
                    seleccionado.setPassword(password);
                }
                if (dao.actualizarAsesor(seleccionado)) {
                    refrescarLista();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al actualizar el asesor.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                 JOptionPane.showMessageDialog(vista, "El email no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarAsesor() {
        User seleccionado = vista.listaAsesores.getSelectedValue();
        if (seleccionado == null) return;

        int confirm = JOptionPane.showConfirmDialog(vista, "¿Está seguro de que desea eliminar al asesor " + seleccionado.getEmail() + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminarAsesor(seleccionado.getId())) {
                refrescarLista();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar al asesor. Es posible que esté asociado a un siniestro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}