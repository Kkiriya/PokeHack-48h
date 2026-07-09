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
                "INSERT INTO pokemonTypes "
                + "(id, pokemonId, typeId) "
                + "VALUES (?, ?, ?) "
                + "ON CONFLICT (id) DO UPDATE SET "
                + "pokemonId=EXCLUDED.pokemonId, typeId=EXCLUDED.typeId ";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, pt.id);
            ps.setInt(2, pt.pokemonId);
            ps.setInt(3, pt.typeId);
            ps.executeUpdate();
        }
    }

    public List<PokemonTypes> lister() throws SQLException {
        List<PokemonTypes> all = new ArrayList<>();
        String sql = "SELECT * FROM pokemonTypes ORDER BY id";

        try (Connection co = Connexion.ouvrir();
             Statement st = co.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                PokemonTypes pt = new PokemonTypes();
                pt.id = rs.getInt("id");
                pt.pokemonId = rs.getInt("pokemonId");
                pt.typeId = rs.getInt("typeId");
                all.add(pt);
            }
        }
        return all;
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM pokemonTypes WHERE id = ?";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No pokemonTypes with this id exists " + id);
            }
        }
    }
}
