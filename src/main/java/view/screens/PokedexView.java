package view.screens;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import view.components.*;

public class PokedexView {

    private final GridPane root;
    public final VBox wildPokemonBox;
    public final SearchView searchBox;
    public final SelectedPokemonFilterBox selectedPokemonFilterBox;
    public final ImageView pokemonImageFrame;
    public final CapturedListView capturedListView;
    public final StatsView statsGrid;
    public final Label capturedCountLabel;
    public final Label pokemonNameLabel;
    public final EvolutionsBox evolutionsBox;


    public PokedexView() {
        // Root assembly layout.
        root = new GridPane();

        // Set column constraints so it's more responsive and look better than gridpane with no constraints.
        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(25);
        leftColumn.setMinWidth(300);

        ColumnConstraints centerColumn = new ColumnConstraints();
        centerColumn.setPercentWidth(45);
        centerColumn.setMinWidth(520);

        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(30);
        rightColumn.setMinWidth(360);

        root.getColumnConstraints().addAll(leftColumn, centerColumn, rightColumn);
        root.setHgap(45);
        root.setPadding(new Insets(10, 60, 0, 40));

        // Left
        searchBox = new SearchView();
        pokemonImageFrame = new ImageView();
        statsGrid = new StatsView();

        pokemonNameLabel = new Label("");
        pokemonNameLabel.getStyleClass().add("pokemon-name-label");

        Label wildLabel = new Label("Wild Pokemon");
        wildLabel.getStyleClass().add("main-font");

        wildPokemonBox = new VBox(wildLabel,searchBox,pokemonNameLabel,pokemonImageFrame, statsGrid);
        VBox.setMargin(pokemonImageFrame, new Insets(0, 0, 20, 0));
        wildPokemonBox.getStyleClass().add("wild-pokemon-box");

        // Center
        capturedListView = new CapturedListView();
        capturedListView.getStyleClass().add("captured-list-view");

        evolutionsBox = new EvolutionsBox();
        evolutionsBox.getStyleClass().add("evolutions-box");

        capturedCountLabel = new Label("Captured: 0");
        capturedCountLabel.getStyleClass().add("main-font");

        VBox centerBox = new VBox(capturedListView, evolutionsBox,capturedCountLabel);
        centerBox.getStyleClass().add("center-box");

        // Right
        selectedPokemonFilterBox = new SelectedPokemonFilterBox();
        selectedPokemonFilterBox.getStyleClass().add("filter-view");

        // Add all the boxes to the root gridpane.
        root.add(wildPokemonBox, 0, 0);
        root.add(centerBox, 1, 0);
        root.add(selectedPokemonFilterBox, 2, 0);

        GridPane.setHalignment(wildPokemonBox, HPos.LEFT);
        GridPane.setHalignment(centerBox, HPos.CENTER);
        GridPane.setHalignment(selectedPokemonFilterBox, HPos.LEFT);

        GridPane.setValignment(wildPokemonBox, VPos.TOP);
        GridPane.setValignment(centerBox, VPos.TOP);
        GridPane.setValignment(selectedPokemonFilterBox, VPos.TOP);
    }

    public Parent getRoot() {
        return root;
    }
}
