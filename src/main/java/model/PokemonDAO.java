package model;

import util.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAO {

    public void sauvegarder(Pokemon p) throws SQLException {
        String sql =
                "INSERT INTO pokemon"
                + "(id, base_experience, cries, height, name, species, sprites, hp, attack, defense, " +
                        "special_attack, special_defense, speed, weight)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                + "ON CONFLICT (id) DO UPDATE SET "
                + "name=EXCLUDED.name";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, p.id);
            ps.setInt(3, p.baseExperience);
            ps.setString(4, p.cries);
            ps.setDouble(5, p.height);
            ps.setString(6, p.name);
            ps.setString(7, p.species);
            ps.setString(8, p.sprites);
            ps.setInt(9, p.hp);
            ps.setInt(10, p.attack);
            ps.setInt(11, p.defense);
            ps.setInt(12, p.special_attack);
            ps.setInt(13, p.special_defense);
            ps.setInt(14, p.special_defense);
            ps.setDouble(15, p.weight);
            ps.executeUpdate();
        }
    }

    public List<Pokemon> lister() throws SQLException {
        List<Pokemon> all = new ArrayList<>();
        String sql = "SELECT * FROM pokemon ORDER BY id";

        try (Connection co = Connexion.ouvrir();
             Statement st = co.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Pokemon p = new Pokemon();
                p.id = rs.getInt("id");
                p.baseExperience = rs.getInt("base_experience");
                p.cries = rs.getString("cries");
                p.height = rs.getDouble("height");
                p.name = rs.getString("name");
                p.species = rs.getString("species");
                p.sprites = rs.getString("sprites");
                p.hp = rs.getInt("hp");
                p.attack = rs.getInt("attack");
                p.defense = rs.getInt("defense");
                p.special_attack = rs.getInt("special_attack");
                p.special_defense = rs.getInt("special_defense");
                p.speed = rs.getInt("speed");
                p.weight = rs.getDouble("weight");
                all.add(p);
            }
        }
        return all;
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM pokemon WHERE id = ?";

        try (Connection co = Connexion.ouvrir();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No pokemon with this id exists " + id);
            }
        }
    }
}
