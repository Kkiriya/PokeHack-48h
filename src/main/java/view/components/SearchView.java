package view.components;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class SearchView extends HBox {
    public final TextField searchField;
    public final Button catchButton;
    public final Button randomButton;

    public SearchView() {
        setSpacing(8);
        setPadding(new Insets(10));

        searchField = new TextField();
        searchField.setPromptText("Enter Pokemon name or ID");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        randomButton = new Button("Random Id");
        randomButton.getStyleClass().add("random-button");

        VBox searchBox = new VBox(searchField,randomButton);
        VBox.setMargin(randomButton, new Insets(10, 0, 0, 0));

        catchButton = new Button("Catch");
        catchButton.getStyleClass().add("catch-button");

        // Pokemon ball over catch button
        Image pokeballCursorImage = new Image(
                Objects.requireNonNull(getClass().getResource("/images/pokeball.png")).toExternalForm()
        );

        ImageCursor pokeballCursor = new ImageCursor(pokeballCursorImage);

        catchButton.setOnMouseEntered(e -> catchButton.setCursor(pokeballCursor));
        catchButton.setOnMouseExited(e -> catchButton.setCursor(Cursor.DEFAULT));

        getChildren().addAll(searchBox, catchButton);
    }
}