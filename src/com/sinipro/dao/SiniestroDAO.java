package com.sinipro.dao;

import com.sinipro.model.Asegurado;
import com.sinipro.model.Compania;
import com.sinipro.model.Siniestro;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SiniestroDAO {

    private static final String SEARCH_SINIESTROS = "SELECT s.siniestro_id, s.numero_siniestro, s.fecha, s.estado, s.descripcion, a.asegurado_id, a.nombre as nombre_asegurado, a.dni, c.compania_id, c.nombre as nombre_compania FROM siniestros s JOIN asegurados a ON s.asegurado_id = a.asegurado_id JOIN companias c ON s.compania_id = c.compania_id WHERE (s.numero_siniestro LIKE ? OR a.nombre LIKE ?) AND s.papelera = FALSE AND s.estado <> 'Eliminado' ORDER BY s.fecha DESC";
    private static final String GET_EN_PAPELERA = "SELECT s.siniestro_id, s.numero_siniestro, s.fecha, s.estado, s.descripcion, a.asegurado_id, a.nombre as nombre_asegurado, a.dni, c.compania_id, c.nombre as nombre_compania FROM siniestros s JOIN asegurados a ON s.asegurado_id = a.asegurado_id JOIN companias c ON s.compania_id = c.compania_id WHERE s.papelera = TRUE";
    private static final String ENVIAR_A_PAPELERA = "UPDATE siniestros SET papelera = TRUE WHERE siniestro_id = ?";
    private static final String RESTAURAR_DESDE_PAPELERA = "UPDATE siniestros SET papelera = FALSE WHERE siniestro_id = ?";
    private static final String ELIMINAR_PERMANENTE = "UPDATE siniestros SET estado = 'Eliminado', papelera = FALSE WHERE siniestro_id = ?";
    private static final String ACTUALIZAR_ESTADO = "UPDATE siniestros SET estado = ? WHERE siniestro_id = ?";
    private static final String CREATE_SINIESTRO = "INSERT INTO siniestros (numero_siniestro, asegurado_id, asesor_id, compania_id, fecha, estado, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_LAST_NUMERO = "SELECT numero_siniestro FROM siniestros ORDER BY siniestro_id DESC LIMIT 1";

    public Siniestro crearSiniestro(Siniestro siniestro, int asesorId) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(CREATE_SINIESTRO, Statement.RETURN_GENERATED_KEYS)) {
            String nuevoNumero = getSiguienteNumeroSiniestro(con);
            ps.setString(1, nuevoNumero);
            ps.setInt(2, siniestro.getAsegurado().getId());
            ps.setInt(3, asesorId);
            ps.setInt(4, siniestro.getCompania().getId());
            ps.setTimestamp(5, new java.sql.Timestamp(siniestro.getFecha().getTime()));
            ps.setString(6, "Abierto");
            ps.setString(7, siniestro.getDescripcion());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                siniestro.setNumeroSiniestro(nuevoNumero);
                siniestro.setEstado("Abierto");
                return siniestro;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<Siniestro> buscarSiniestros(String filtro) {
        return ejecutarQuery(SEARCH_SINIESTROS, "%" + filtro + "%", "%" + filtro + "%");
    }

    public List<Siniestro> obtenerSiniestrosEnPapelera(Integer asesorId) {
        String query = (asesorId == null) ? GET_EN_PAPELERA : GET_EN_PAPELERA + " AND s.asesor_id = ?";
        return (asesorId == null) ? ejecutarQuery(query) : ejecutarQuery(query, asesorId);
    }

    public boolean enviarAPapelera(int siniestroId) {
        return ejecutarUpdate(ENVIAR_A_PAPELERA, siniestroId);
    }
    public boolean restaurarDesdePapelera(int siniestroId) {
        return ejecutarUpdate(RESTAURAR_DESDE_PAPELERA, siniestroId);
    }
    public boolean eliminarPermanente(int siniestroId) {
        return ejecutarUpdate(ELIMINAR_PERMANENTE, siniestroId);
    }
    public boolean actualizarEstado(int siniestroId, String nuevoEstado) {
        return ejecutarUpdate(ACTUALIZAR_ESTADO, nuevoEstado, siniestroId);
    }

    private String getSiguienteNumeroSiniestro(Connection con) throws Exception {
        try (PreparedStatement ps = con.prepareStatement(GET_LAST_NUMERO);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return String.format("%04d", Integer.parseInt(rs.getString("numero_siniestro")) + 1);
            }
        }
        return "0001";
    }
    
    private boolean ejecutarUpdate(String query, Object... params) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    private List<Siniestro> ejecutarQuery(String query, Object... params) {
        List<Siniestro> lista = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Siniestro s = new Siniestro();
                s.setId(rs.getInt("siniestro_id"));
                s.setNumeroSiniestro(rs.getString("numero_siniestro"));
                s.setFecha(rs.getTimestamp("fecha"));
                s.setEstado(rs.getString("estado"));
                s.setDescripcion(rs.getString("descripcion"));
                Asegurado a = new Asegurado();
                a.setId(rs.getInt("asegurado_id"));
                a.setNombre(rs.getString("nombre_asegurado"));
                a.setDni(rs.getString("dni"));
                s.setAsegurado(a);
                Compania c = new Compania(rs.getInt("compania_id"), rs.getString("nombre_compania"));
                s.setCompania(c);
                lista.add(s);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
}