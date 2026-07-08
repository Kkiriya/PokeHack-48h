package model;

import util.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class moveDAO {

    public void sauvegarder(Move m) throws SQLException {
        String sql =
                "INSERT INTO move"
                + "(id, accuracy, damage_class, effect_change, effect_entries, flavor_text_entries, " +
                        "name, power, pp, priority, stat_changes, target, type_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                + "ON CONFLICT (id) DO UPDATE SET "
                + "accuracy=EXCLUDED.accuracy, damage_class=EXCLUDED.damage_class";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, m.id);
            ps.setInt(2, m.accuracy);
            ps.setString(3, m.damage_class);
            ps.setString(4, m.effect_change);
            ps.setString(5, m.effect_entries);
            ps.setString(6, m.flavor_text_entries);
            ps.setString(7, m.name);
            ps.setInt(8, m.power);
            ps.setInt(9, m.pp);
            ps.setInt(10, m.priority);
            ps.setString(11, m.stat_changes);
            ps.setString(12, m.target);
            ps.setInt(13, m.type_id);
            ps.executeUpdate();
        }
    }

    public List<Move> lister() throws SQLException {
        List<Move> all = new ArrayList<>();
        String sql = "SELECT * FROM move ORDER BY id";

        try (Connection co = Connexion.ouvrir();
             Statement st = co.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Move m = new Move();
                m.id = rs.getInt("id");
                m.accuracy = rs.getInt("accuracy");
                m.damage_class = rs.getString("damage_class");
                m.effect_change = rs.getString("effect_change");
                m.effect_entries = rs.getString("effect_entries");
                m.flavor_text_entries = rs.getString("flavor_text_entries");
                m.name = rs.getString("name");
                m.power = rs.getInt("power");
                m.pp = rs.getInt("pp");
                m.priority = rs.getInt("priority");
                m.stat_changes = rs.getString("stat_changes");
                m.target = rs.getString("target");
                m.type_id = rs.getInt("typeId");
                all.add(m);
            }
        }
        return all;
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM move WHERE id = ?";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No move with this id exists " + id);
            }
        }
    }
}
