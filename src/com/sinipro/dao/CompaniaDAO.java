package com.sinipro.dao;

import com.sinipro.model.Compania;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompaniaDAO {
    private static final String GET_ALL = "SELECT compania_id, nombre FROM companias ORDER BY nombre";
    private static final String CREATE = "INSERT INTO companias (nombre) VALUES (?)";
    private static final String UPDATE = "UPDATE companias SET nombre = ? WHERE compania_id = ?";
    private static final String DELETE = "DELETE FROM companias WHERE compania_id = ?";

    public List<Compania> obtenerTodas() {
        List<Compania> lista = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Compania(rs.getInt("compania_id"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean crearCompania(String nombre) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(CREATE)) {
            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCompania(Compania compania) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setString(1, compania.getNombre());
            ps.setInt(2, compania.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCompania(int companiaId) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setInt(1, companiaId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}