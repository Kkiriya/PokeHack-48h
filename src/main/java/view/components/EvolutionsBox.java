package view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EvolutionsBox extends VBox {

    // Constructor
    public EvolutionsBox() {
        Label evolutionsLabel = new Label("Evolutions:");

        HBox evolutionsRow = new HBox(10,
                createEvolutionBox("Bulbasaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
                createEvolutionBox("Ivysaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"),
                createEvolutionBox("Venusaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png")
        );
        evolutionsRow.setAlignment(Pos.CENTER);
        evolutionsRow.getStyleClass().add("evolutions-row");

        getChildren().addAll(evolutionsLabel, evolutionsRow);
    }

    /**
     * Creates one evolution item with the Pokemon image on top and its name below.
     *
     * @param name the Pokemon name to display under the image
     * @param imageUrl the URL of the Pokemon sprite image
     * @return a centered VBox containing the image and name
     */
    private VBox createEvolutionBox(String name, String imageUrl) {
        ImageView evolutionImage = new ImageView(new Image(imageUrl, true));
        StackPane evolutionImageFrame = new StackPane(evolutionImage);
        evolutionImageFrame.getStyleClass().add("evolution-image");

        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("evolution-name");

        VBox evolutionBox = new VBox(5, evolutionImageFrame,nameLabel);
        evolutionBox.setAlignment(Pos.CENTER);
        evolutionBox.getStyleClass().add("evolution-box");
        return evolutionBox;
    }
}
