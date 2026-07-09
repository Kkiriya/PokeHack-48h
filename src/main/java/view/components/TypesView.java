package view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class TypesView extends GridPane {
    public Label typeOne;
    public Label typeTwo;

    public Label weaknessOne;
    public Label weaknessTwo;

    public TypesView() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(10));
        setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");

        typeOne = new Label("-");
        typeTwo = new Label("-");
        weaknessOne = new Label("-");
        weaknessTwo = new Label("-");

        add(new Label("Type:"), 0, 0);
        add((typeOne), 0, 1);
        add((typeTwo), 1, 1);

        add(new Label("Weakness:"), 0, 2);
        add((weaknessOne), 0, 3);
        add((weaknessTwo), 1, 3);
    }
}