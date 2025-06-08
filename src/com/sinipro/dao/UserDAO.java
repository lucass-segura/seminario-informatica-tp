package com.sinipro.dao;

import com.sinipro.model.User;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    private static final String VALIDATE =
        "SELECT id,email,rol FROM usuarios WHERE email=? AND password=?";

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
}