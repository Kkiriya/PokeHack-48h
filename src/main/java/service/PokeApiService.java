package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Ability;
import model.Move;
import model.Pokemon;
import model.PokemonMoves;
import model.PokemonTypes;
import model.Type;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PokeApiService {
    // helper method for getting pokemon stats
    private int getStats(JsonNode pokemon, String statName) {
        for (JsonNode stat: pokemon.get("stats")) {
            if (stat.get("stat").get("name").asText().equals(statName)) {
                return stat.get("base_stat").asInt();
            }
        }
        throw new RuntimeException("Stat no found " + statName);
    }

    // helper method to extract the id out of a URL, works for all major tables
    private int getIdFromURL(String url) {
        String[] sections = url.split("/");
        return Integer.parseInt(sections[sections.length - 1]);
    }

    private static final String URL =
            "https://pokeapi.co/api/v2/";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Pokemon recupererPokemon(String id) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(URL + "pokemon" + id)).GET().build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            throw new RuntimeException("API erreur: " + res.statusCode());
        }

        JsonNode pokemon = mapper.readTree(res.body());
        Pokemon p = new Pokemon();
        p.id = pokemon.get("id").asInt();
        p.baseExperience = pokemon.get("base_experience").asInt();
        p.cries = pokemon.get("cries").get("latest").asText();
        p.height = pokemon.get("height").asInt() / 10.0;
        p.name = pokemon.get("name").asText();
        p.species = pokemon.get("species").get("name").asText();
        p.sprites = pokemon.get("sprites").get("front_default").asText();
        p.hp = getStats(pokemon, "hp");
        p.attack = getStats(pokemon, "attack");
        p.defense = getStats(pokemon, "defense");
        p.special_attack = getStats(pokemon, "special_attack");
        p.special_defense = getStats(pokemon, "special_defense");
        p.speed = getStats(pokemon, "speed");
        p.weight = pokemon.get("weight").asInt() / 10.0;
        return p;
    }
}
