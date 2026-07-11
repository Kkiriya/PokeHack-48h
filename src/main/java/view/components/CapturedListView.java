package view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class CapturedListView extends VBox {
    public final ListView <String> listView;
    public final Button releaseButton;

    public CapturedListView() {
        listView = new ListView<>();
        listView.getStyleClass().add("captured-list");

        releaseButton = new Button("Release");
        releaseButton.getStyleClass().add("release-button");

        Label titleLabel = new Label("Captured Pokemon");
        titleLabel.getStyleClass().add("captured-list-label");

        VBox listViewBox = new VBox((titleLabel), listView, releaseButton);
        VBox.setMargin(releaseButton, new Insets(10, 0, 0, 0));
        listViewBox.setSpacing(0);

        listViewBox.getStyleClass().add("captured-list-view-box");

        getChildren().add(listViewBox);
    }
}
