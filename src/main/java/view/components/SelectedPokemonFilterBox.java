package view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FilterView is a JavaFX VBox that contains buttons to switch between different views (ImagePanel, StatsView, TypesView).
 * It uses CardInfoButtons for the buttons and dynamically updates the content pane based on user interaction.
 */
public class SelectedPokemonFilterBox extends VBox {
    public final ImageView imageView;
    public final StatsView statsView;
    public TypesView typesView;
    public int id;
    public Label pokemonNameLabel;

    public SelectedPokemonFilterBox() {
        CardInfoButtons cardInfoButtons = new CardInfoButtons();
        imageView = new ImageView();
        statsView = new StatsView();
        typesView = new TypesView();

        Label selectedPokemonLabel = new Label("Selected Pokemon");
        selectedPokemonLabel.getStyleClass().add("main-font");

        pokemonNameLabel = new Label("");
        pokemonNameLabel.getStyleClass().add("pokemon-name-label");

        VBox capturedFilterBox = new VBox();

        capturedFilterBox.setSpacing(10);
        capturedFilterBox.setPadding(new Insets(10));
        capturedFilterBox.setMinSize(260, 260);
        capturedFilterBox.setPrefSize(260, 260);

        capturedFilterBox.getChildren().add(imageView);

        cardInfoButtons.spritesButton.setOnAction(e ->
            capturedFilterBox.getChildren().setAll(imageView));

        cardInfoButtons.statsButton.setOnAction(e ->
            capturedFilterBox.getChildren().setAll(statsView));

        cardInfoButtons.typesButton.setOnAction(e ->
            capturedFilterBox.getChildren().setAll(typesView));

        cardInfoButtons.idButton.setOnAction(e ->
            capturedFilterBox.getChildren().setAll(new Label("ID: " + id)));

        getChildren().addAll(selectedPokemonLabel,pokemonNameLabel, cardInfoButtons, capturedFilterBox);
    }
}

