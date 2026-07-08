package model;

import util.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PokemonTypesDOA {

    public void sauvegarder(PokemonTypes pt) throws SQLException {
        String sql =
                "INSERT INTO pokemon_types "
                + "(pokemon_id, type_id) "
                + "VALUES (?, ?) "
                + "ON CONFLICT (pokemon_id, type_id) DO UPDATE SET "
                + "pokemon_id=EXCLUDED.pokemon_id, type_id=EXCLUDED.type_id ";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(2, pt.pokemon_id);
            ps.setInt(3, pt.type_id);
            ps.executeUpdate();
        }
    }

    public List<PokemonTypes> lister() throws SQLException {
        List<PokemonTypes> all = new ArrayList<>();
        String sql = "SELECT * FROM pokemon_types ORDER BY (pokemon_id, type_id)";

        try (Connection co = Connexion.ouvrir();
             Statement st = co.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                PokemonTypes pt = new PokemonTypes();
                pt.pokemon_id = rs.getInt("pokemonId");
                pt.type_id = rs.getInt("typeId");
                all.add(pt);
            }
        }
        return all;
    }

    public void supprimer(int pokemon_id, int type_id) throws SQLException {
        String sql = "DELETE FROM pokemon_types WHERE pokemon_id = ? AND type_id = ? ";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, pokemon_id);
            ps.setInt(2, type_id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No pokemonTypes with this id exists " + pokemon_id + "-" + type_id);
            }
        }
    }
}
