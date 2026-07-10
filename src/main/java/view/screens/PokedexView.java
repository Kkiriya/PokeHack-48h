package view.screens;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import view.components.*;

public class PokedexView {

    private final BorderPane root;
    public final VBox wildPokemonBox;
    public final SearchView searchBox;
    public final CapturedFilterBox capturedFilterBox;
    public final ImageView pokemonImageFrame;
    public final CapturedListView capturedListView;
    public final StatsView statsGrid;
    public final Label capturedCountLabel;
    public final Label pokemonNameLabel;

    public PokedexView() {
        // Root assembly layout
        root = new BorderPane();

        // Left section contains the search box, Pokemon image, and stats
        searchBox = new SearchView();
        pokemonImageFrame = new ImageView();
        statsGrid = new StatsView();

        pokemonNameLabel = new Label("");
        pokemonNameLabel.getStyleClass().add("pokemon-name-label");

        Label wildLabel = new Label("Wild Pokemon");
        wildLabel.getStyleClass().add("wild-label");

        wildPokemonBox = new VBox(wildLabel,searchBox,pokemonNameLabel,pokemonImageFrame, statsGrid);
        VBox.setMargin(pokemonImageFrame, new Insets(0, 0, 20, 0));
        wildPokemonBox.getStyleClass().add("wild-pokemon-box");

        root.setLeft(wildPokemonBox);
        BorderPane.setMargin(wildPokemonBox, new Insets(10, 0, 0, 50)); // Adjust the left margin to move the leftBox down and to the right

        // Center
        capturedListView = new CapturedListView();

        EvolutionsBox evolutionsBox = new EvolutionsBox();

        capturedCountLabel = new Label("Captured: 0");
        capturedCountLabel.getStyleClass().add("captured-count-label");

        VBox centerBox = new VBox(capturedListView, evolutionsBox,capturedCountLabel);
        centerBox.getStyleClass().add("center-box");
        root.setCenter(centerBox);

        // Right section contains the filter view
        capturedFilterBox = new CapturedFilterBox();
        capturedFilterBox.getStyleClass().add("filter-view");
        root.setRight(capturedFilterBox);
    }

    public Parent getRoot() {
        return root;
    }
}
