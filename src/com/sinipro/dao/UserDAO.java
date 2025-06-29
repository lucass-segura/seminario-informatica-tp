package com.sinipro.dao;

import com.sinipro.model.User;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String VALIDATE = "SELECT id,email,rol FROM usuarios WHERE email=? AND password=?";
    private static final String GET_ASESORES = "SELECT id, email, rol FROM usuarios WHERE rol='asesor' ORDER BY email";
    private static final String CREATE_ASESOR = "INSERT INTO usuarios (email, password, rol) VALUES (?, ?, 'asesor')";
    private static final String UPDATE_ASESOR = "UPDATE usuarios SET email = ?, password = ? WHERE id = ?";
    private static final String DELETE_ASESOR = "DELETE FROM usuarios WHERE id = ?";

    public User validar(String email, String pass) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(VALIDATE)) {

            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setRol(rs.getString("rol"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> obtenerTodosLosAsesores() {
        List<User> lista = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ASESORES);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean crearAsesor(User asesor) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(CREATE_ASESOR)) {
            ps.setString(1, asesor.getEmail());
            ps.setString(2, asesor.getPassword());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarAsesor(User asesor) {
        String query = "UPDATE usuarios SET email = ? " +
                       (asesor.getPassword() != null && !asesor.getPassword().isEmpty() ? ", password = ? " : "") +
                       "WHERE id = ?";
        
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, asesor.getEmail());
            if (asesor.getPassword() != null && !asesor.getPassword().isEmpty()) {
                ps.setString(2, asesor.getPassword());
                ps.setInt(3, asesor.getId());
            } else {
                ps.setInt(2, asesor.getId());
            }
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarAsesor(int asesorId) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_ASESOR)) {
            ps.setInt(1, asesorId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public static String getUpdateAsesor() {
		return UPDATE_ASESOR;
	}
}