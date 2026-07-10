package view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FilterView is a JavaFX HBox that contains buttons to switch between different views (ImagePanel, StatsView, TypesView).
 * It uses CardInfoButtons for the buttons and dynamically updates the content pane based on user interaction.
 */
public class CapturedFilterBox extends HBox {
    public final ImageView imageView;
    public final StatsView statsView;
    public TypesView typesView;
    public int id;

    public CapturedFilterBox() {
        CardInfoButtons cardInfoButtons = new CardInfoButtons();
        imageView = new ImageView();
        statsView = new StatsView();
        typesView = new TypesView();

        HBox capturedFilterBox = new HBox();

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

        setSpacing(10);
        getChildren().addAll(cardInfoButtons, capturedFilterBox);
    }
}

