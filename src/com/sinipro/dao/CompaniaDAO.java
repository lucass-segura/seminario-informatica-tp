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
}