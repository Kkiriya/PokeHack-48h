package controller;

import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import model.*;
import service.PokeApiService;
import view.screens.PokedexView;

import java.sql.SQLException;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.AudioClip;

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

        view.searchBox.searchField.textProperty().addListener((
                observable,
                oldValue,
                newValue) -> {
            previewPokemonFromSearchBox(newValue);

            if (newValue.isEmpty()) {
                displayLeftPreview(null);
            }
        });

        view.searchBox.randomButton.setOnAction(e -> {
            // Math.Random generate between 0 and 1. +1 to avoid 0.
            int randomId = (int) (Math.random() * 1000) + 1;
            // Set the random ID in the search field
            view.searchBox.searchField.setText(String.valueOf(randomId));
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
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        view.capturedListView.releaseButton.setOnAction(e -> {
            // Get the selected Pokemon name from the list view.
            String selectedPokemon = view.capturedListView.listView.getSelectionModel().getSelectedItem();

            if (selectedPokemon == null) {
                showErrorPopup("Select a Pokemon first !");
                return;
            }

            if (!confirmReleasePopUp(selectedPokemon)) {
                return;
            }

            releasePokemon(selectedPokemon);
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
        } catch (SQLException ex) {
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
    private boolean confirmReleasePopUp(String pokemonName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm release");
        alert.setHeaderText(null);
        alert.setContentText("Release " + pokemonName + "?");

        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private void loadFromApi() {
        String idOrName = view.searchBox.searchField.getText().strip();

        if (idOrName.isBlank()) {
            showErrorPopup("Invalid Name or ID, please enter valid Name or ID");
            return;
        }

        Pokemon pokemon;

        if (idOrName.matches("^[0-9]+$")) {
            try {
                System.out.println("Loading Pokemon with ID " + idOrName);
                // Retrieve Pokemon data from the API
                pokemon = service.recupererPokemon(Integer.parseInt(idOrName));
            } catch (Exception e) {
                showErrorPopup("Invalid ID. Please enter a valid integer.");
                return;
            }
        } else {
            try {
                System.out.println("Loading Pokemon with Name: " + idOrName);
                // Retrieve Pokemon data from the API
                pokemon = service.recupererPokemonParNom(idOrName);
            } catch (Exception e) {
                showErrorPopup("Invalid Name. Please enter a valid Name.");
                return;
            }
        }

        try {
            // retrieves pokemonTypes from API
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
     * @param name The name of the Pokémon to release.
     */
    private void releasePokemon(String name) {
        // TODO: Add confirmation dialog before release
        try {
            //TODO: Try with array method.
            for (Pokemon pokemon : pokemonDAO.lister()) {
                if (pokemon.name.equals(name)) {
                    pokemonDAO.supprimer(pokemon.id);
                    displayCardPokedex(null, null); // Clear
                    break;
                }
            }
        } catch (Exception e) {
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
            // name
            view.selectedPokemonFilterBox.pokemonNameLabel.setText(pokemon.name);
            // image
            view.selectedPokemonFilterBox.imageView.pokemonImage.setImage(sprite);
            // stats
            view.selectedPokemonFilterBox.statsView.hp.setText(String.valueOf(pokemon.hp));
            view.selectedPokemonFilterBox.statsView.attack.setText(String.valueOf(pokemon.attack));
            view.selectedPokemonFilterBox.statsView.defense.setText(String.valueOf(pokemon.defense));
            view.selectedPokemonFilterBox.statsView.specialAttack.setText(String.valueOf(pokemon.special_attack));
            view.selectedPokemonFilterBox.statsView.specialDefense.setText(String.valueOf(pokemon.special_defense));
            view.selectedPokemonFilterBox.statsView.speed.setText(String.valueOf(pokemon.speed));
            // id
            view.selectedPokemonFilterBox.id = pokemon.id;
            view.selectedPokemonFilterBox.pokemonIdLabel.setText("#" + pokemon.id);
            // types
            // TODO: ----
            if (!pokemonTypes.isEmpty()) {
                Type typeOne = service.recupererType(pokemonTypes.getFirst().type_id);
                view.selectedPokemonFilterBox.typesView.typeOne.setText(typeOne.name);
                view.selectedPokemonFilterBox.typesView.typeOne.getStyleClass().clear();
                view.selectedPokemonFilterBox.typesView.typeOne.getStyleClass().add(typeOne.name);
                view.selectedPokemonFilterBox.typesView.typeOne.getStyleClass().add("pokemon-type");
            }
            // Click event to play his cry when clicked
            view.selectedPokemonFilterBox.imageView.pokemonImage.setOnMouseClicked(e ->
                    playPokemonCry());

            if (pokemonTypes.size() >= 2) {
                Type typeTwo = service.recupererType(pokemonTypes.get(1).type_id);
                view.selectedPokemonFilterBox.typesView.typeTwo.setText(typeTwo.name);
                view.selectedPokemonFilterBox.typesView.typeTwo.getStyleClass().clear();
                view.selectedPokemonFilterBox.typesView.typeTwo.getStyleClass().add(typeTwo.name);
                view.selectedPokemonFilterBox.typesView.typeTwo.getStyleClass().add("pokemon-type");
                // Show the second type and adjust the layout to accommodate it
                view.selectedPokemonFilterBox.typesView.typeTwo.setVisible(true);
                view.selectedPokemonFilterBox.typesView.typeTwo.setManaged(true);
                GridPane.setColumnSpan(view.selectedPokemonFilterBox.typesView.typeOne, 1);
                GridPane.setHalignment(view.selectedPokemonFilterBox.typesView.typeOne, HPos.LEFT);
            } else {
                // No second type, put the first into two columns
                view.selectedPokemonFilterBox.typesView.typeTwo.setText("");
                view.selectedPokemonFilterBox.typesView.typeTwo.getStyleClass().clear();
                view.selectedPokemonFilterBox.typesView.typeTwo.setVisible(false);
                view.selectedPokemonFilterBox.typesView.typeTwo.setManaged(false);
                GridPane.setColumnSpan(view.selectedPokemonFilterBox.typesView.typeOne, 2);
                GridPane.setHalignment(view.selectedPokemonFilterBox.typesView.typeOne, HPos.CENTER);
            }

        } else {
            // Clear the view if no Pokemon is provided
            view.selectedPokemonFilterBox.imageView.pokemonImage.setImage(null);
            view.selectedPokemonFilterBox.statsView.hp.setText("");
            view.selectedPokemonFilterBox.statsView.attack.setText("");
            view.selectedPokemonFilterBox.statsView.defense.setText("");
            view.selectedPokemonFilterBox.statsView.specialAttack.setText("");
            view.selectedPokemonFilterBox.statsView.specialDefense.setText("");
            view.selectedPokemonFilterBox.statsView.speed.setText("");
            view.selectedPokemonFilterBox.id = 0;
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

            // Click event for the preview Pokemon image in the left box
            view.pokemonImageFrame.pokemonImage.setOnMouseClicked(e ->
                playPokemonCry());

            view.pokemonNameLabel.setText(pokemon.name);

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
        if (text.isBlank()) {
            return;
        }

        Pokemon pokemon;

        try {
            if (text.strip().matches("^[0-9]+$")) {
                pokemon = service.recupererPokemon(Integer.parseInt(text));
            } else {
                pokemon = service.recupererPokemonParNom(text);
            }
            displayLeftPreview(pokemon);
        } catch (Exception e) {
            displayLeftPreview(null);
            // TODO: Display error on screen instead of printing to console
            System.err.println("Error previewing Pokemon: " + e.getMessage());
        }
    }

    // Plays the cry sound of the given Pokémon. Do not work with url because it do not support .ogg format.
    private void playPokemonCry() {
        String soundUrl = getClass().getResource("/sounds/pokemon.mp3").toExternalForm();
        AudioClip sound = new AudioClip(soundUrl);
        sound.play();
    }
}
