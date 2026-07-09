package view.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class CapturedListView extends VBox {
    public final ListView <String> listView;
    public final Button deleteButton;

    public CapturedListView() {
        listView = new ListView<>();

        deleteButton = new Button("Delete");

        VBox listViewBox = new VBox(new Label("Captured Pokemon"), listView, deleteButton);
        listViewBox.setSpacing(0);

        getChildren().add(listViewBox);
    }
}
