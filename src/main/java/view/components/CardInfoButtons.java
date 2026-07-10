package view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class CardInfoButtons extends HBox {
    public Button statsButton;
    public Button typesButton;
    public Button spritesButton;
    public Button idButton;

    public CardInfoButtons() {
        statsButton = new Button("Stats");
        typesButton = new Button("Types");
        spritesButton = new Button("Sprites");
        idButton = new Button("ID");

        statsButton.getStyleClass().addAll("card-info-button", "stats-button");
        typesButton.getStyleClass().addAll("card-info-button", "types-button");
        spritesButton.getStyleClass().addAll("card-info-button", "sprite-button");
        idButton.getStyleClass().addAll("card-info-button", "id-button");

        HBox buttonBox = new HBox(statsButton, typesButton, spritesButton, idButton);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        getChildren().add(buttonBox);
    }
}
