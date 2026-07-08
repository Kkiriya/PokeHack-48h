package view.components;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class CapturedListView extends VBox {
    ListView <String> capturedListView;

    public CapturedListView() {
        capturedListView = new ListView<>();
        capturedListView.getItems().add("Bulbasaur");
        capturedListView.getItems().add("Charmander");
        capturedListView.getItems().add("Squirtle");

        VBox listViewBox = new VBox(new Label("Captured Pokemon"), capturedListView);
        listViewBox.setSpacing(0);

        getChildren().add(listViewBox);
    }
}
