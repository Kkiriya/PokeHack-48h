package model;

import util.Connexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbilityDAO {

    public  void sauvegarder(Ability a) throws SQLException {
        String sql =
                "INSERT INTO ability "
                + "(id, effect_entries, flavor_text_entries, name) "
                + "VALUES (?, ?, ?, ?) "
                + "ON CONFLICT (id) DO UPDATE SET "
                + "name=EXCLUDED.name";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, a.id);
            ps.setString(2, a.effect_entries);
            ps.setString(3, a.flavor_text_entries);
            ps.setString(4, a.name);
            ps.executeUpdate();
        }
    }

    public List<Ability> lister() throws SQLException {
        List<Ability> all = new ArrayList<>();
        String sql = "SELECT * FROM ability ORDER BY id";

        try (Connection co = Connexion.ouvrir();
             Statement st = co.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Ability a = new Ability();
                a.id = rs.getInt("id");
                a.effect_entries = rs.getString("effect_entries");
                a.flavor_text_entries = rs.getString("flavor_text_entries");
                a.name = rs.getString("name");
                all.add(a);
            }
        }
        return all;
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM ability WHERE id = ?";
        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No ability with this id exists " + id);
            }
        }
    }
}
