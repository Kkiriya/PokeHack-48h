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
                "INSERT INTO pokemonMoves "
                + "(id, pokemonId, moveId) "
                + "VALUES (?, ?, ?) "
                + "ON CONFLICT (id) DO UPDATE SET "
                + "pokemonId=EXCLUDED.pokemonId, moveId=EXCLUDED.moveId";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, pm.id);
            ps.setInt(2, pm.pokemonId);
            ps.setInt(3, pm.moveId);
            ps.executeUpdate();
        }
    }

    public List<PokemonMoves> lister() throws SQLException {
        List<PokemonMoves> all = new ArrayList<>();
        String sql = "SELECT * FROM pokemonMoves ORDER BY id";

        try (Connection co = Connexion.ouvrir();
             Statement st = co.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()) {
                PokemonMoves pm = new PokemonMoves();
                pm.id = rs.getInt("id");
                pm.pokemonId = rs.getInt("pokemonId");
                pm.moveId = rs.getInt("moveId");
                all.add(pm);
            }
        }
        return all;
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM pokemonMoves WHERE id = ?";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No pokemonMoves with this id exists " + id);
            }
        }
    }
}
