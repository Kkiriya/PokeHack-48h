package controller;

import javafx.scene.image.Image;
import model.PokemonDAO;
import model.PokemonTypesDOA;
import service.PokeApiService;
import view.screens.PokedexView;
import model.Pokemon;

public class PokedexController {
    private final PokemonDAO pokemonDAO = new PokemonDAO();
    private final PokemonTypesDOA pokemonTypesDAO = new PokemonTypesDOA();
    private final PokeApiService service = new PokeApiService();
    private final PokedexView view;


    // Constructor
    public PokedexController(PokedexView view) {
        this.view = view;

        view.searchBox.btnCatch.setOnAction(e -> loadFromApi());

        view.searchBox.searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            previewPokemonFromSearchBox(newValue);
        });

        displayPokedex(null);
    }

    public void loadFromApi() {
        // Load data with ID or name from the API and update the model
        int id = (Integer.parseInt(view.searchBox.searchField.getText()));

        System.out.println("Loading Pokemon with ID: " + id);
        try {
            Pokemon pokemon = service.recupererPokemon(id);
            displayPokedex(pokemon);
            pokemonDAO.sauvegarder(pokemon);
            refreshList();
        } catch (Exception e) {
            // TODO: Display error on screen instead of printing to console
            // Handle exceptions (e.g., show an error message in the view)
            System.err.println("Error loading Pokemon: " + e.getMessage());
        }
    }

    public void refreshList() {
        // Refresh the list view of captured Pokemon
        try {
            view.capturedListView.listView.getItems().clear();
            for (Pokemon pokemon : pokemonDAO.lister()) {
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
            // sprite
            Image sprite = new Image(pokemon.sprites);
            view.filterView.imageView.pokemonImage.setImage(sprite);
            // stats
            view.filterView.statsView.hp.setText(String.valueOf(pokemon.hp));
            view.filterView.statsView.attack.setText(String.valueOf(pokemon.attack));
            view.filterView.statsView.defense.setText(String.valueOf(pokemon.defense));
            view.filterView.statsView.specialAttack.setText(String.valueOf(pokemon.special_attack));
            view.filterView.statsView.specialDefense.setText(String.valueOf(pokemon.special_defense));
            view.filterView.statsView.speed.setText(String.valueOf(pokemon.speed));
            // id
            view.filterView.id = pokemon.id;
            // types
            // TODO: Implement displaying types in the view. Check how to get types from the Pokemon object or the API response.
        }
    }

    public void displayLeftPreview (Pokemon pokemon) {
        view.pokemonImageFrame.pokemonImage.setImage(new Image(pokemon.sprites));
        view.statsGrid.hp.setText(String.valueOf(pokemon.hp));
        view.statsGrid.attack.setText(String.valueOf(pokemon.attack));
        view.statsGrid.defense.setText(String.valueOf(pokemon.defense));
        view.statsGrid.specialAttack.setText(String.valueOf(pokemon.special_attack));
        view.statsGrid.specialDefense.setText(String.valueOf(pokemon.special_defense));
        view.statsGrid.speed.setText(String.valueOf(pokemon.speed));
    }

    private void previewPokemonFromSearchBox(String text) {
        if (text.isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(text);
            Pokemon pokemon = service.recupererPokemon(id);
            displayLeftPreview(pokemon);
        } catch (Exception e) {
            // TODO: Display error on screen instead of printing to console
            System.err.println("Error previewing Pokemon: " + e.getMessage());
        }
    }
}