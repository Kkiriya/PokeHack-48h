package controller;

import javafx.scene.image.Image;
import model.PokemonDAO;
import model.PokemonTypesDOA;
import service.PokeApiService;
import view.screens.PokedexView;
import model.Pokemon;

import java.sql.SQLException;

public class PokedexController {
    private final PokemonDAO pokemonDAO = new PokemonDAO();
    private final PokemonTypesDOA pokemonTypesDAO = new PokemonTypesDOA();
    private final PokeApiService service = new PokeApiService();
    private final PokedexView view;


    // Constructor
    public PokedexController(PokedexView view) {
        this.view = view;

        view.searchBox.catchButton.setOnAction(e -> loadFromApi());

        view.capturedListView.listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    try {
                        //TODO: Try with array method.
                        for (Pokemon pokemon : pokemonDAO.lister()) {
                            if (pokemon.name.equals(newValue)) {
                                displayCardPokedex(pokemon);
                                break;
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

        view.capturedListView.deleteButton.setOnAction(e -> {
            // Get the selected Pokemon name from the list view.
            String selectedPokemon = view.capturedListView.listView.getSelectionModel().getSelectedItem();
            if (selectedPokemon == null) {
                // TODO: Display on screen.
                System.out.println("No Pokemon selected");
                return;
            }
            deletePokemon(selectedPokemon);
            displayCardPokedex(null);
        });

        // To see the preview of the pokemon before catching it.
        view.searchBox.searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            previewPokemonFromSearchBox(newValue);
            if(newValue == null || newValue.isEmpty()) {
                displayLeftPreview(null);
            }
        });
    }


    private void loadFromApi() {
        // Load data with ID or name from the API and update the model
        int id = (Integer.parseInt(view.searchBox.searchField.getText()));

        System.out.println("Loading Pokemon with ID: " + id);
        try {
            Pokemon pokemon = service.recupererPokemon(id);
            displayCardPokedex(pokemon);
            pokemonDAO.sauvegarder(pokemon);
            refreshList();
        } catch (Exception e) {
            // TODO: Display error on screen instead of printing to console
            // Handle exceptions (e.g., show an error message in the view)
            System.err.println("Error loading Pokemon: " + e.getMessage());
        }
    }

    /**
     * Deletes a Pokemon from the database by its name.
     *
     * @param name The name of the Pokemon to delete.
     */
    private void deletePokemon(String name) {
        try {
            //TODO: Try with array method.
            for (Pokemon pokemon : pokemonDAO.lister()) {
                if (pokemon.name.equals(name)) {
                    pokemonDAO.supprimer(pokemon.id);
                    displayCardPokedex(null); // Clear
                    break;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        refreshList();
    }

    /**
     * Refreshes the list view of captured Pokemon in the PokedexView.
     * Clears the current items in the list view and repopulates it with the latest data from the database.
     */
    private void refreshList() {
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

    /**
     * Displays the details of a Pokemon in the PokedexView.
     *
     * @param pokemon The Pokemon object containing the details to display.
     */
    private void displayCardPokedex(Pokemon pokemon) {
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
        } else {
            // Clear the view if no Pokemon is provided
            view.filterView.imageView.pokemonImage.setImage(null);
            view.filterView.statsView.hp.setText("");
            view.filterView.statsView.attack.setText("");
            view.filterView.statsView.defense.setText("");
            view.filterView.statsView.specialAttack.setText("");
            view.filterView.statsView.specialDefense.setText("");
            view.filterView.statsView.speed.setText("");
            view.filterView.id = 0;
        }
    }

    /**
     * Displays a preview of the Pokemon before catch on the left side of the PokedexView.
     *
     * @param pokemon The Pokemon object containing the details to preview.
     */
    private void displayLeftPreview(Pokemon pokemon) {
        if (pokemon != null) {
            view.pokemonImageFrame.pokemonImage.setImage(new Image(pokemon.sprites));
            view.statsGrid.hp.setText(String.valueOf(pokemon.hp));
            view.statsGrid.attack.setText(String.valueOf(pokemon.attack));
            view.statsGrid.defense.setText(String.valueOf(pokemon.defense));
            view.statsGrid.specialAttack.setText(String.valueOf(pokemon.special_attack));
            view.statsGrid.specialDefense.setText(String.valueOf(pokemon.special_defense));
            view.statsGrid.speed.setText(String.valueOf(pokemon.speed));
        } else {
            // Clear the preview if no Pokemon is provided
            view.pokemonImageFrame.pokemonImage.setImage(null);
            view.statsGrid.hp.setText("");
            view.statsGrid.attack.setText("");
            view.statsGrid.defense.setText("");
            view.statsGrid.specialAttack.setText("");
            view.statsGrid.specialDefense.setText("");
            view.statsGrid.speed.setText("");
        }
    }

    /**
     * Previews a Pokemon based on the text entered in the search box.
     * If the text is empty, it does nothing. If the text can be parsed as an integer,
     * it attempts to retrieve the Pokemon with that ID from the API and display its preview.
     *
     * @param text The text entered in the search box.
     */
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