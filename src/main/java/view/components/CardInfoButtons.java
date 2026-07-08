package view.components;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CardInfoButtons extends VBox {
    public Button statsButton;
    public Button typesButton;
    public Button spritesButton;
    Button idButton;

    public CardInfoButtons() {
        statsButton = new Button("Stats");
        typesButton = new Button("Types");
        spritesButton = new Button("Sprites");
        idButton = new Button("ID");

        VBox buttonBox = new VBox(statsButton, typesButton, spritesButton, idButton);
        buttonBox.setSpacing(10);

        getChildren().add(buttonBox);
    }
}
