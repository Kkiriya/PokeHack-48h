package view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class StatsView extends GridPane {
    // TODO: Could be improved. Maybe a way to populate the columns and rows dynamically using enums or a list of stats.
    public final GridPane statsGrid;
    public Label hp;
    public Label attack;
    public Label defense;
    public Label specialAttack;
    public Label specialDefense;
    public Label speed;


    public StatsView() {
        statsGrid = new GridPane();
        statsGrid.setHgap(10);
        statsGrid.setVgap(10);
        statsGrid.setPadding(new Insets(10));

        hp = new Label("-");
        attack = new Label("-");
        defense = new Label("-");
        specialAttack = new Label("-");
        specialDefense = new Label("-");
        speed = new Label("-");

        statsGrid.add(new Label("HP:"), 0, 0);
        statsGrid.add((hp), 1, 0);

        statsGrid.add(new Label("Attack:"), 0, 1);
        statsGrid.add((attack), 1, 1);

        statsGrid.add(new Label("Defense:"), 0, 2);
        statsGrid.add(defense, 1, 2);

        statsGrid.add(new Label("Special Attack:"), 0, 3);
        statsGrid.add((specialAttack), 1, 3);

        statsGrid.add(new Label("Special Defense:"), 0, 4);
        statsGrid.add((specialDefense), 1, 4);

        statsGrid.add(new Label("Speed:"), 0, 5);
        statsGrid.add((speed), 1, 5);

        statsGrid.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");

        getChildren().add(statsGrid);
    }
}
