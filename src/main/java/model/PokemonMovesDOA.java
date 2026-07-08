package model;

import util.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PokemonMovesDOA {

    public void sauvegarder(PokemonMoves pm) throws SQLException {
        String sql =
                "INSERT INTO pokemon_moves "
                + "(pokemon_id, move_id) "
                + "VALUES (?, ?) "
                + "ON CONFLICT (pokemon_id, move_id) DO UPDATE SET "
                + "pokemon_id=EXCLUDED.pokemon_id, move_id=EXCLUDED.move_id";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(2, pm.pokemonId);
            ps.setInt(3, pm.moveId);
            ps.executeUpdate();
        }
    }

    public List<PokemonMoves> lister() throws SQLException {
        List<PokemonMoves> all = new ArrayList<>();
        String sql = "SELECT * FROM pokemon_moves ORDER BY (pokemon_id, move_id)";

        try (Connection co = Connexion.ouvrir();
             Statement st = co.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()) {
                PokemonMoves pm = new PokemonMoves();
                pm.pokemonId = rs.getInt("pokemonId");
                pm.moveId = rs.getInt("moveId");
                all.add(pm);
            }
        }
        return all;
    }

    public void supprimer(int pokemon_id, int move_id) throws SQLException {
        String sql = "DELETE FROM pokemon_moves WHERE pokemon_id = ? AND move_id = ? ";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, pokemon_id);
            ps.setInt(2, move_id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No pokemonMoves with this id exists " + pokemon_id + "-" + move_id);
            }
        }
    }
}
