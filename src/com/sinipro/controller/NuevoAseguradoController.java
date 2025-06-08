package com.sinipro.controller;

import com.sinipro.dao.AseguradoDAO;
import com.sinipro.model.Asegurado;
import com.sinipro.view.NuevoAseguradoView;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.util.function.Consumer;

public class NuevoAseguradoController {
    private final NuevoAseguradoView vista;
    private final AseguradoDAO dao = new AseguradoDAO();
    private final Consumer<Asegurado> callback;

    public NuevoAseguradoController(NuevoAseguradoView vista, Consumer<Asegurado> callback) {
        this.vista = vista;
        this.callback = callback;
        initListeners();
        initValidators();
    }

    private void initListeners() {
        vista.btnConfirmar.addActionListener(e -> crearAsegurado());
        vista.btnCancelar.addActionListener(e -> vista.dispose());
    }
    
    private void initValidators() {
        ((AbstractDocument) vista.txtDni.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9]+") && (fb.getDocument().getLength() + string.length()) <= 10) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]+") && (fb.getDocument().getLength() - length + text.length()) <= 10) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    private void crearAsegurado() {
        String dni = vista.txtDni.getText();
        String nombre = vista.txtNombre.getText();
        String contacto = vista.txtContacto.getText();

        if (dni.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "DNI y Nombre son campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Asegurado nuevoAsegurado = new Asegurado();
        nuevoAsegurado.setDni(dni);
        nuevoAsegurado.setNombre(nombre);
        nuevoAsegurado.setContacto(contacto);

        Asegurado aseguradoCreado = dao.crearAsegurado(nuevoAsegurado);
        if (aseguradoCreado != null) {
            JOptionPane.showMessageDialog(vista, "Asegurado agregado");
            callback.accept(aseguradoCreado);
            vista.dispose();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al crear el asegurado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}