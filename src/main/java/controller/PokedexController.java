package controller;

import javafx.scene.image.Image;
import model.PokemonDAO;
import service.PokeApiService;
import view.screens.PokedexView;
import model.Pokemon;

public class PokedexController {
    private final PokemonDAO dao = new PokemonDAO();
    private final PokeApiService service = new PokeApiService();
    private final PokedexView view;


    // Constructor
    public PokedexController(PokedexView view) {
        this.view = view;

        view.searchBox.btnLoad.setOnAction(e -> loadFromApi());
    }

    public void loadFromApi() {
        // Load data with ID or name from the API and update the model
        int id = (Integer.parseInt(view.searchBox.searchField.getText()));

        System.out.println("Loading Pokemon with ID: " + id);
        try {
            Pokemon pokemon = service.recupererPokemon(id);
            dao.sauvegarder(pokemon);
            refreshList();
        } catch (Exception e) {
            // TODO: Display error on screen instead of printing to console
            // Handle exceptions (e.g., show an error message in the view)
            System.err.println("Error loading Pokemon: " + e.getMessage());
        }
    }

    public void refreshList() {
        // Refresh the list view of captured Pokemon in the view
        try {
            view.capturedListView.listView.getItems().clear();
            for (Pokemon pokemon : dao.lister()) {
                System.out.println("Pokemon in DAO: " + pokemon.name);
                view.capturedListView.listView.getItems().add(pokemon.name);
            }
        } catch (Exception e) {
            // TODO: Display error on screen instead of printing to console
            System.err.println("Error loading Pokemon: " + e.getMessage());
        }
    }

    public void start() {
        refreshList();
    }

    public void displayPokedex(Pokemon pokemon) {
        // Update the view with the Pokemon data
        if (pokemon != null) {
            Image sprite = new Image(pokemon.sprites);
            view.pokemonImageFrame.pokemonImage.setImage(sprite);
        }
    }
}