package view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class StatsView extends GridPane {
    public final GridPane statsGrid;

    public StatsView() {
        statsGrid = new GridPane();
        statsGrid.setHgap(10);
        statsGrid.setVgap(10);
        statsGrid.setPadding(new Insets(10));

        statsGrid.add(new Label("HP:"), 0, 0);
        statsGrid.add(new Label("45"), 1, 0);

        statsGrid.add(new Label("Attack:"), 0, 1);
        statsGrid.add(new Label("49"), 1, 1);

        statsGrid.add(new Label("Defense:"), 0, 2);
        statsGrid.add(new Label("49"), 1, 2);

        statsGrid.add(new Label("Special Attack:"), 0, 3);
        statsGrid.add(new Label("65"), 1, 3);

        statsGrid.add(new Label("Special Defense:"), 0, 4);
        statsGrid.add(new Label("65"), 1, 4);

        statsGrid.add(new Label("Speed:"), 0, 5);
        statsGrid.add(new Label("45"), 1, 5);
        statsGrid.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");

        getChildren().add(statsGrid);
    }
}
