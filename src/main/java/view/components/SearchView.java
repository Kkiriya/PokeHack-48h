package view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SearchView extends HBox {
    public final TextField searchField;
    public final Button btnLoad;
    public final Label errorMessage;

    public SearchView() {
        setSpacing(8);
        setPadding(new Insets(10));

        searchField = new TextField();
        searchField.setPromptText("Enter Pokemon name or ID");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        btnLoad = new Button("Load");
        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");

        getChildren().addAll(searchField, btnLoad, errorMessage);
    }
}