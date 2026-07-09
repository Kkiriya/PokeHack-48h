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
                + "(id, name, sprites) "
                + "VALUES (?, ?, ?) "
                + "ON CONFLICT (id) DO UPDATE SET "
                + "name=EXCLUDED.name, sprites=COALESCE(EXCLUDED.sprites, type.sprites) ";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, t.id);
            ps.setString(2, t.name);
            ps.setString(3, t.sprites);
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
