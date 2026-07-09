package model;

import util.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PokemonAbilitiesDOA {

    public void sauvegarder(PokemonAbilities pa) throws SQLException {
        String sql =
                "INSERT INTO pokemon_abilities "
                + "(pokemon_id, ability_id, is_hidden) "
                + "VALUES (?, ?, ?) "
                + "ON CONFLICT (pokemon_id, ability_id) DO UPDATE SET "
                + "is_hidden=EXCLUDED.is_hidden";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, pa.pokemon_id);
            ps.setInt(2, pa.ability_id);
            ps.setBoolean(3, pa.is_hidden);
            ps.executeUpdate();
        }
    }

    public List<PokemonAbilities> lister() throws SQLException {
        List<PokemonAbilities> all = new ArrayList<>();
        String sql = "SELECT * FROM pokemon_abilities ORDER BY (pokemon_id, ability_id)";

        try (Connection co = Connexion.ouvrir();
             Statement st = co.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                PokemonAbilities pa = new PokemonAbilities();
                pa.pokemon_id = rs.getInt("pokemon_id");
                pa.ability_id = rs.getInt("ability_id");
                pa.is_hidden = rs.getBoolean("is_hidden");
                all.add(pa);
            }
        }
        return all;
    }

    public void supprimer(int pokemon_id, int ability_id) throws SQLException {
        String sql = "DELETE FROM pokemon_abilities WHERE pokemon_id = ? AND ability_id = ?";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, pokemon_id);
            ps.setInt(2, ability_id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No pokemon_abilities with this id exists" + pokemon_id + "-" + ability_id);
            }
        }
    }
}
