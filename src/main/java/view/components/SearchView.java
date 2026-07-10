package view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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

        getChildren().addAll(searchBox, catchButton);
    }
}