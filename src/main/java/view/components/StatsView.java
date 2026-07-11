package view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        statsGrid.setPadding(new Insets(10));

        hp = new Label("-");
        hp.getStyleClass().add("stats-value");

        attack = new Label("-");
        attack.getStyleClass().add("stats-value");

        defense = new Label("-");
        defense.getStyleClass().add("stats-value");

        specialAttack = new Label("-");
        specialAttack.getStyleClass().add("stats-value");

        specialDefense = new Label("-");
        specialDefense.getStyleClass().add("stats-value");

        Label hpLabel = new Label("HP:");
        hpLabel.getStyleClass().add("stats-label");

        Label attackLabel = new Label("Attack:");
        attackLabel.getStyleClass().add("stats-label");

        Label defenseLabel = new Label("Defense:");
        defenseLabel.getStyleClass().add("stats-label");

        Label specialAttackLabel = new Label("Special Attack:");
        specialAttackLabel.getStyleClass().add("stats-label");

        Label specialDefenseLabel = new Label("Special Defense:");
        specialDefenseLabel.getStyleClass().add("stats-label");

        Label speedLabel = new Label("Speed:");
        speedLabel.getStyleClass().add("stats-label");

        speed = new Label("-");
        speed.getStyleClass().add("stats-value");

        statsGrid.add((hpLabel), 0, 0);
        statsGrid.add((hp), 1, 0);

        statsGrid.add((attackLabel), 0, 1);
        statsGrid.add((attack), 1, 1);

        statsGrid.add((defenseLabel), 0, 2);
        statsGrid.add(defense, 1, 2);

        statsGrid.add((specialAttackLabel), 0, 3);
        statsGrid.add((specialAttack), 1, 3);

        statsGrid.add((specialDefenseLabel), 0, 4);
        statsGrid.add((specialDefense), 1, 4);

        statsGrid.add((speedLabel), 0, 5);
        statsGrid.add((speed), 1, 5);

        // Css styling
        statsGrid.getStyleClass().add("stats-grid");
        statsGrid.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        getChildren().add(statsGrid);
    }
}
