package com.sinipro.dao;

import com.sinipro.model.SiniestroNota;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SiniestroNotaDAO {

    private static final String GET_BY_SINIESTRO_ID = "SELECT nota_id, siniestro_id, fecha_nota, nota FROM siniestro_notas WHERE siniestro_id = ? ORDER BY fecha_nota DESC";
    private static final String CREATE_NOTA = "INSERT INTO siniestro_notas (siniestro_id, fecha_nota, nota) VALUES (?, NOW(), ?)";
    private static final String UPDATE_NOTA = "UPDATE siniestro_notas SET nota = ? WHERE nota_id = ?";
    private static final String DELETE_NOTA = "DELETE FROM siniestro_notas WHERE nota_id = ?";

    public List<SiniestroNota> obtenerPorSiniestroId(int siniestroId) {
        List<SiniestroNota> lista = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_SINIESTRO_ID)) {
            ps.setInt(1, siniestroId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SiniestroNota nota = new SiniestroNota();
                nota.setId(rs.getInt("nota_id"));
                nota.setSiniestroId(rs.getInt("siniestro_id"));
                nota.setFechaNota(rs.getTimestamp("fecha_nota"));
                nota.setNota(rs.getString("nota"));
                lista.add(nota);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean crearNota(SiniestroNota nota) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(CREATE_NOTA)) {
            ps.setInt(1, nota.getSiniestroId());
            ps.setString(2, nota.getNota());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarNota(SiniestroNota nota) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_NOTA)) {
            ps.setString(1, nota.getNota());
            ps.setInt(2, nota.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarNota(int notaId) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_NOTA)) {
            ps.setInt(1, notaId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}