package model;

import util.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TypeDOA {

    public void sauvegarder(Type t) throws SQLException {
        String sql =
                "INSERT INTO type "
                + "(id, name, double_damage_to, half_damage_from, no_damage_from, no_damage_to, move_damage_class, " +
                        "sprites) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
                + "ON CONFLICT (id) DO UPDATE SET "
                + "name=EXCLUDED.name ";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, t.id);
            ps.setString(2, t.name);
            ps.setString(3, t.double_damage_to);
            ps.setString(4, t.half_damage_from);
            ps.setString(5, t.no_damage_from);
            ps.setString(6, t.no_damage_to);
            ps.setString(7, t.move_damage_class);
            ps.setString(8, t.sprites);
            ps.executeUpdate();
        }
    }

    public List<Type> lister() throws SQLException {
        List<Type> all = new ArrayList<>();
        String sql = "SELECT * FROM type ORDER BY id";

        try (Connection co = Connexion.ouvrir();
             Statement st = co.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Type t = new Type();
                t.id = rs.getInt("id");
                t.name = rs.getString("name");
                t.double_damage_to = rs.getString("double_damage_to");
                t.half_damage_from = rs.getString("half_damage_from");
                t.no_damage_from = rs.getString("no_damage_from");
                t.no_damage_to = rs.getString("no_damage_to");
                t.move_damage_class = rs.getString("move_damage_class");
                t.sprites = rs.getString("sprites");
                all.add(t);
            }
        }
        return all;
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM type WHERE id = ?";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No Type with this id exists " + id);
            }
        }
    }
}
