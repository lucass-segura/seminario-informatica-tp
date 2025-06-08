package com.sinipro.dao;

import com.sinipro.model.Asegurado;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AseguradoDAO {

    private static final String SEARCH_BY_FILTER = "SELECT asegurado_id, nombre, dni, contacto FROM asegurados WHERE nombre LIKE ? OR dni LIKE ? ORDER BY nombre ASC LIMIT ?";
    private static final String CREATE_ASEGURADO = "INSERT INTO asegurados (productor_id, dni, nombre, contacto) VALUES (?, ?, ?, ?)";

    public List<Asegurado> buscarAsegurados(String filtro, int limite) {
        List<Asegurado> lista = new ArrayList<>();
        String filtroLike = "%" + filtro + "%";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SEARCH_BY_FILTER)) {
            
            ps.setString(1, filtroLike);
            ps.setString(2, filtroLike);
            ps.setInt(3, limite);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Asegurado a = new Asegurado();
                a.setId(rs.getInt("asegurado_id"));
                a.setNombre(rs.getString("nombre"));
                a.setDni(rs.getString("dni"));
                a.setContacto(rs.getString("contacto"));
                lista.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Asegurado crearAsegurado(Asegurado asegurado) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(CREATE_ASEGURADO, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, 1); 
            ps.setString(2, asegurado.getDni());
            ps.setString(3, asegurado.getNombre());
            ps.setString(4, asegurado.getContacto());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        asegurado.setId(generatedKeys.getInt(1));
                        return asegurado;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}