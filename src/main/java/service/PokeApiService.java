package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class PokeApiService {
    // helper method for getting pokemon stats
    private int getStats(JsonNode pokemon, String statName) {
        for (JsonNode stat: pokemon.get("stats")) {
            if (stat.get("stat").get("name").asText().equals(statName)) {
                return stat.get("base_stat").asInt();
            }
        }
        throw new RuntimeException("Stat not found " + statName);
    }

    // helper method to extract the id out of a URL, works for all major tables
    private int getIdFromURL(String url) {
        String cleanUrl = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
        String[] sections = cleanUrl.split("/");
        return Integer.parseInt(sections[sections.length - 1]);
    }

    private static final String URL =
            "https://pokeapi.co/api/v2/";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Pokemon recupererPokemon(int id) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(URL + "pokemon/" + id)).GET().build();
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
        p.special_attack = getStats(pokemon, "special-attack");
        p.special_defense = getStats(pokemon, "special-defense");
        p.speed = getStats(pokemon, "speed");
        p.weight = pokemon.get("weight").asInt() / 10.0;
        return p;
    }

    public Pokemon recupererPokemonParNom(String name) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(URL + "pokemon/" + name)).GET().build();
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
        p.special_attack = getStats(pokemon, "special-attack");
        p.special_defense = getStats(pokemon, "special-defense");
        p.speed = getStats(pokemon, "speed");
        p.weight = pokemon.get("weight").asInt() / 10.0;
        return p;
    }

    // helper fonction for recupererEvolutionParID
    private JsonNode getSpeciesInfo(int id) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(URL + "pokemon-species/" + id)).GET().build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            throw new RuntimeException("API erreur: " + res.statusCode());
        }

        return mapper.readTree(res.body());
    }

    private JsonNode getJsonFromUrl(String url) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            throw new RuntimeException("API erreur: " + res.statusCode());
        }

        return mapper.readTree(res.body());
    }

    public List<Pokemon> recupererEvolutionParId(int id) throws Exception {
        JsonNode speciesInfo = getSpeciesInfo(id);
        String evolutionChainUrl = speciesInfo.get("evolution_chain").get("url").asText();
        JsonNode evolutionChain = getJsonFromUrl(evolutionChainUrl);

        List<Pokemon> evolutions = new ArrayList<>();
        addEvolutionChainPokemon(evolutionChain.get("chain"), evolutions);
        return evolutions;
    }

    private void addEvolutionChainPokemon(JsonNode evolutionNode, List<Pokemon> evolutions) throws Exception {
        String speciesUrl = evolutionNode.get("species").get("url").asText();
        int pokemonId = getIdFromURL(speciesUrl);
        evolutions.add(recupererPokemon(pokemonId));

        for (JsonNode nextEvolution : evolutionNode.get("evolves_to")) {
            addEvolutionChainPokemon(nextEvolution, evolutions);
        }
    }

    public Type recupererType(int id) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(URL + "type/" + id)).GET().build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            throw new RuntimeException("API erreur: " + res.statusCode());
        }

        JsonNode type = mapper.readTree(res.body());
        Type t = new Type();
        t.id = type.get("id").asInt();
        t.name = type.get("name").asText();
        t.sprites = type.get("sprites").get("generation-vi").get("x-y").get("name_icon").asText();
        return t;
    }

    public  Ability recupererAbility(int id) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(URL + "ability/" + id)).GET().build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            throw new RuntimeException("API erreur: " + res.statusCode());
        }

        JsonNode ability = mapper.readTree(res.body());
        Ability a = new Ability();
        a.id = ability.get("id").asInt();

        // getting effect_entries
        a.effect_entries = null;
        for (JsonNode effect_entries: ability.get("effect_entries")) {
            if (effect_entries.get("language").get("name").asText().equals("en")) {
                a.effect_entries = effect_entries.get("short_effect").asText();
            }
        }

        // getting flavor_text_entries
        a.flavor_text_entries = null;
        for (JsonNode flavor_text_entries: ability.get("flavor_text_entries")) {
            if (flavor_text_entries.get("language").get("name").asText().equals("en")
                    && flavor_text_entries.get("version_group").get("name").asText().equals("x-y")) {
                a.flavor_text_entries = flavor_text_entries.get("flavor_text").asText();
            }
        }

        a.name = ability.get("name").asText();
        return a;
    }

    public Move recupererMove(int id) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(URL + "move/" + id)).GET().build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            throw new RuntimeException("API erreur: " + res.statusCode());
        }

        JsonNode move = mapper.readTree(res.body());
        Move m = new Move();
        m.id = move.get("id").asInt();
        m.accuracy = move.get("accuracy").asInt();
        m.damage_class = move.get("damage_class").get("name").asText();
        m.effect_change = move.get("effect_changes").asText(); // needs to be reviewed I have no idea what data is in
        // here

        // getting effect_entries
        m.effect_entries = null;
        for (JsonNode effect_entries: move.get("effect_entries")) {
            if (effect_entries.get("language").get("name").asText().equals("en")) {
                m.effect_entries = effect_entries.get("effect").asText();
            }
        }

        // getting flavor_text_entries
        m.flavor_text_entries = null;
        for (JsonNode flavor_text_entries: move.get("flavor_text_entries")) {
            if (flavor_text_entries.get("language").get("name").asText().equals("en")
                    && flavor_text_entries.get("version_group").get("name").asText().equals("x-y")) {
                m.flavor_text_entries = flavor_text_entries.get("flavor_text").asText();
            }
        }

        m.name = move.get("name").asText();
        m.power = move.get("power").asInt();
        m.pp = move.get("pp").asInt();
        m.priority = move.get("priority").asInt();
        m.stat_changes = move.get("stat_changes").asText(); // needs tp be reviewed I have no idea how the data is
        // returned here and im getting tired
        m.target = move.get("target").get("name").asText();
        m.type_id = getIdFromURL(move.get("type").get("url").asText());
        return m;
    }

    // Parse all the abilities a pokemon can have then returns a list of PokemonAbilities objects (junction table)
    public List<PokemonAbilities> recupererPokemonAbilities(int pokemon_id) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(URL + "pokemon/" + pokemon_id)).GET().build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            throw new RuntimeException("API erreur: " + res.statusCode());
        }

        JsonNode pokemon = mapper.readTree(res.body());
        List<PokemonAbilities> pokemonAbilities = new ArrayList<>();

        for (JsonNode ability : pokemon.get("abilities")) {
            PokemonAbilities pa = new PokemonAbilities();
            pa.pokemon_id = pokemon.get("id").asInt();
            pa.ability_id = getIdFromURL(ability.get("ability").get("url").asText());
            pokemonAbilities.add(pa);
        }
        return pokemonAbilities;
    }

    // Parse all the moves a pokemon can have then returns a list of pokemonMoves objects (junction table)
    public List<PokemonMoves> recupererPokemonMoves(int pokemon_id) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(URL + "pokemon/" + pokemon_id)).GET().build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            throw new RuntimeException("API erreur: " + res.statusCode());
        }

        JsonNode pokemon = mapper.readTree(res.body());
        List<PokemonMoves> pokemonMoves = new ArrayList<>();

        for (JsonNode move: pokemon.get("moves")) {
            PokemonMoves pm = new PokemonMoves();
            pm.pokemonId = pokemon.get("id").asInt();
            pm.moveId = getIdFromURL(move.get("move").get("url").asText());
            pokemonMoves.add(pm);
        }
        return pokemonMoves;
    }

    // Parse all the types a pokemon can have then returns a list of pokemonTypes objects (junction table)
    public List<PokemonTypes> recupererPokemonTypes(int pokemon_id) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(URL + "pokemon/" + pokemon_id)).GET().build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            throw new RuntimeException("API erreur: " + res.statusCode());
        }

        JsonNode pokemon = mapper.readTree(res.body());
        List<PokemonTypes> pokemonTypes = new ArrayList<>();

        for (JsonNode type: pokemon.get("types")) {
            PokemonTypes pt = new PokemonTypes();
            pt.pokemon_id = pokemon.get("id").asInt();
            pt.type_id = getIdFromURL(type.get("type").get("url").asText());
            pokemonTypes.add(pt);
        }
        return pokemonTypes;
    }
}
