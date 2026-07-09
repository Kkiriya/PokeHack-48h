package view.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class CapturedListView extends VBox {
    public final ListView <String> listView;

    public CapturedListView() {
        listView = new ListView<>();

        // TODO: Populate the ListView with captured Pokemon names.
        listView.getItems().add("Bulbasaur");
        listView.getItems().add("Charmander");
        listView.getItems().add("Squirtle");

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            // TODO: Implement delete functionality
        });

        VBox listViewBox = new VBox(new Label("Captured Pokemon"), listView, deleteButton);
        listViewBox.setSpacing(0);

        getChildren().add(listViewBox);
    }
}
