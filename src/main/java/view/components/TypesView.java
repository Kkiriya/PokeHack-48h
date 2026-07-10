package view.components;

import javafx.geometry.HPos;
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
        getStyleClass().add("types-view");

        typeOne = new Label("");
        typeTwo = new Label("");
        weaknessOne = new Label("-");
        weaknessTwo = new Label("-");
        Label title = new Label("Types");
        title.getStyleClass().add("types-title");

        // Add a span so title is centerd. Like merge cells in excel.
        add(title, 0, 0,2,1);
        GridPane.setHalignment(title, HPos.CENTER);

        add((typeOne), 0, 1);
        add((typeTwo), 1, 1);

        //add(new Label("Weakness:"), 0, 2);
        //add((weaknessOne), 0, 3);
        //add((weaknessTwo), 1, 3);
    }
}