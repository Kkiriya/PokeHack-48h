package view.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class CapturedListView extends VBox {
    ListView <String> capturedListView;

    public CapturedListView() {
        capturedListView = new ListView<>();

        // TODO: Populate the ListView with captured Pokemon names.
        capturedListView.getItems().add("Bulbasaur");
        capturedListView.getItems().add("Charmander");
        capturedListView.getItems().add("Squirtle");

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            // TODO: Implement delete functionality
        });

        VBox listViewBox = new VBox(new Label("Captured Pokemon"), capturedListView, deleteButton);
        listViewBox.setSpacing(0);

        getChildren().add(listViewBox);
    }
}
