package com.sinipro.controller;

import com.sinipro.dao.AseguradoDAO;
import com.sinipro.model.Asegurado;
import com.sinipro.view.BuscarAseguradoView;
import com.sinipro.view.NuevoAseguradoView;
import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;

public class BuscarAseguradoController {
    private final BuscarAseguradoView vista;
    private final AseguradoDAO dao = new AseguradoDAO();
    private final Consumer<Asegurado> onAseguradoSeleccionado;

    public BuscarAseguradoController(BuscarAseguradoView vista, Consumer<Asegurado> onAseguradoSeleccionado) {
        this.vista = vista;
        this.onAseguradoSeleccionado = onAseguradoSeleccionado;
        initListeners();
    }

    private void initListeners() {
        vista.btnBuscar.addActionListener(e -> buscar());
        vista.btnCancelar.addActionListener(e -> vista.dispose());

        vista.listaResultados.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                vista.btnSeleccionar.setEnabled(vista.listaResultados.getSelectedValue() != null);
            }
        });

        vista.btnSeleccionar.addActionListener(e -> {
            Asegurado seleccionado = vista.listaResultados.getSelectedValue();
            if (seleccionado != null) {
                onAseguradoSeleccionado.accept(seleccionado);
                vista.dispose();
            }
        });

        vista.btnNuevo.addActionListener(e -> {
            NuevoAseguradoView modalNuevo = new NuevoAseguradoView(null);
            new NuevoAseguradoController(modalNuevo, aseguradoCreado -> {
                onAseguradoSeleccionado.accept(aseguradoCreado);
                vista.dispose();
            });
            modalNuevo.setVisible(true);
        });
    }

    private void buscar() {
        String filtro = vista.txtFiltro.getText();
        List<Asegurado> resultados = dao.buscarAsegurados(filtro, 20); 
        
        DefaultListModel<Asegurado> model = new DefaultListModel<>();
        if (resultados.isEmpty()) {
            vista.listaResultados.setModel(model);
            JOptionPane.showMessageDialog(vista, "No se encontraron asegurados.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        } else {
            resultados.forEach(model::addElement);
            vista.listaResultados.setModel(model);
        }
    }
}