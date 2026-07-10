package view.components;

import javafx.geometry.Insets;
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
        deleteButton.getStyleClass().add("delete-button");

        Label titleLabel = new Label("Captured Pokemon");
        titleLabel.getStyleClass().add("captured-list-title");

        VBox listViewBox = new VBox((titleLabel), listView, deleteButton);
        VBox.setMargin(deleteButton, new Insets(10, 0, 0, 0));
        listViewBox.setSpacing(0);

        getChildren().add(listViewBox);
    }
}
