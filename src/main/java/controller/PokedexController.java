package controller;

import javafx.scene.image.Image;
import model.*;
import service.PokeApiService;
import view.screens.PokedexView;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class PokedexController {
    // Data Base
    private final PokemonDAO pokemonDAO = new PokemonDAO();
    private final PokemonTypesDOA pokemonTypesDAO = new PokemonTypesDOA();
    // API Service
    private final PokeApiService service = new PokeApiService();
    // View
    private final PokedexView view;


    // Constructor
    public PokedexController(PokedexView view) {
        this.view = view;

        view.searchBox.searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            previewPokemonFromSearchBox(newValue);

            if (newValue.isEmpty()) {
                displayLeftPreview(null);
            }
        });

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

                                List<PokemonTypes> pokemonTypes = service.recupererPokemonTypes(pokemon.id);

                                displayCardPokedex(pokemon, pokemonTypes);
                                break;
                            }
                        }
                    }  catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        view.capturedListView.deleteButton.setOnAction(e -> {
            // Get the selected Pokemon name from the list view.
            String selectedPokemon = view.capturedListView.listView.getSelectionModel().getSelectedItem();

            if (selectedPokemon == null) {
                showErrorPopup("Select a Pokemon first !");
                return;
            }

            if (!confirmDeletePopUp(selectedPokemon)) {
                return;
            }

            deletePokemon(selectedPokemon);
            try {
                displayCardPokedex(null, null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Captured count label update
        try {
            int capturedCount = pokemonDAO.lister().size();
            view.capturedCountLabel.setText("Captured: " + capturedCount);
        }
        catch (SQLException ex) {
            showErrorPopup("Not able to load Pokemon list.");
        }
    }

    /**
     * Displays an error popup with the specified message.
     *
     * @param message The error message to display.
     */
    private void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * Displays a confirmation dialog before deleting a Pokémon.
     *
     * @param pokemonName The name of the Pokémon to delete.
     * @return true if the user confirms the deletion, false otherwise.
     */
    private boolean confirmDeletePopUp(String pokemonName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete");
        alert.setHeaderText(null);
        alert.setContentText("Delete " + pokemonName + "?");

        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private void loadFromApi() {
        int id;
        try {
            id = Integer.parseInt(view.searchBox.searchField.getText());
        } catch (NumberFormatException e) {
            showErrorPopup("Invalid ID. Please enter a valid integer.");
            return;
        }

        System.out.println("Loading Pokemon with ID: " + id);
        try {
            // Retrieve Pokemon data from the API
            Pokemon pokemon = service.recupererPokemon(id);
            List<PokemonTypes> pokemonTypes = service.recupererPokemonTypes(pokemon.id);

            // Save the Pokemon data to the database
            pokemonDAO.sauvegarder(pokemon);
            for (PokemonTypes pokemonType : pokemonTypes) {
                // Save the Pokemon type data to the database
                pokemonTypesDAO.sauvegarder(pokemonType);
            }

            displayCardPokedex(pokemon, pokemonTypes);
            refreshList();

        } catch (Exception e) {
            // TODO: Display error on screen instead of printing to console
            // Handle exceptions (e.g., show an error message in the view)
            System.err.println("Error loading Pokemon: " + e.getMessage());
        }
    }

    /**
     * Deletes a Pokémon from the database by its name.
     *
     * @param name The name of the Pokémon to delete.
     */
    private void deletePokemon(String name) {
        // TODO: Add confirmation dialog before deletion
        try {
            //TODO: Try with array method.
            for (Pokemon pokemon : pokemonDAO.lister()) {
                if (pokemon.name.equals(name)) {
                    pokemonDAO.supprimer(pokemon.id);
                    displayCardPokedex(null, null); // Clear
                    break;
                }
            }
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
        refreshList();
    }

    /**
     * Refreshes the list view of captured Pokémon in the PokedexView.
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
        refreshCapturedCount();
    }

    private void refreshCapturedCount() {
        try {
            int capturedCount = pokemonDAO.lister().size();
            view.capturedCountLabel.setText("Captured: " + capturedCount);
        } catch (SQLException ex) {
            showErrorPopup("Not able to load Pokemon list.");
        }
    }

    public void start() {
        refreshList();
    }

    /**
     * Displays the details of a Pokémon in the PokedexView.
     *
     * @param pokemon The Pokémon object containing the details to display.
     */
    private void displayCardPokedex(Pokemon pokemon, List<PokemonTypes> pokemonTypes) throws Exception {
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
            // TODO: ----
            if (!pokemonTypes.isEmpty()) {
                Type typeOne = service.recupererType(pokemonTypes.getFirst().type_id);
                view.filterView.typesView.typeOne.setText(typeOne.name);
                view.filterView.typesView.typeOne.getStyleClass().clear();
                view.filterView.typesView.typeOne.getStyleClass().add(typeOne.name);
                view.filterView.typesView.typeOne.getStyleClass().add("pokemon-type");
            }

            if (pokemonTypes.size() >= 2) {
                Type typeTwo = service.recupererType(pokemonTypes.get(1).type_id);
                view.filterView.typesView.typeTwo.setText(typeTwo.name);
                view.filterView.typesView.typeTwo.getStyleClass().clear();
                view.filterView.typesView.typeTwo.getStyleClass().add(typeTwo.name);
                view.filterView.typesView.typeTwo.getStyleClass().add("pokemon-type");
            } else {
                view.filterView.typesView.typeTwo.setText("-");
                view.filterView.typesView.typeTwo.getStyleClass().clear();
            }

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
     * Displays a preview of the Pokémon before catch on the left side of the PokedexView.
     *
     * @param pokemon The Pokémon object containing the details to preview.
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
     * Previews a Pokémon based on the text entered in the search box.
     * If the text is empty, it does nothing. If the text can be parsed as an integer,
     * it attempts to retrieve the Pokémon with that ID from the API and display its preview.
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