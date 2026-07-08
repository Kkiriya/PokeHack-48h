package model;

import util.Connexion;

import javax.swing.plaf.nimbus.State;
import java.awt.image.PackedColorModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TypeEffectivenessDOA {

    public void sauvegarder(TypeEffectiveness te) throws SQLException {
        String sql =
                "INSERT INTO type_effectiveness "
                + "(attacking_type_id, defending_type_id, multiplier) "
                + "VALUES (?, ?, ?) "
                + "ON CONFLICT(attacking_type_id, defending_type_id) DO UPDATE SET "
                + "multiplier=EXCLUDED.multiplier";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, te.attacking_type_id);
            ps.setInt(2, te.defending_type_id);
            ps.setDouble(3, te.multiplier);
            ps.executeUpdate();
        }
    }

    public List<TypeEffectiveness> lister() throws SQLException {
        List<TypeEffectiveness> all = new ArrayList<>();
        String sql = "SELECT * FROM type_effectiveness ORDER BY (attacking_type_id, defending_type_id)";

        try (Connection co = Connexion.ouvrir();
             Statement st = co.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                TypeEffectiveness te = new TypeEffectiveness();
                te.attacking_type_id = rs.getInt("attacking_type_id");
                te.attacking_type_id = rs.getInt("defending_type_id");
                te.multiplier = rs.getDouble("multiplier");
                all.add(te);
            }
        }
        return all;
    }

    public void supprimer(int attacking_type_id, int defending_type_id) throws SQLException {
        String sql = "DELETE FROM type_effectiveness WHERE attacking_type_id = ? AND defending_type_id = ?";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, attacking_type_id);
            ps.setInt(2, defending_type_id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No type_effectiveness with this id exists " + attacking_type_id + "-" + defending_type_id);
            }
        }
    }
}
