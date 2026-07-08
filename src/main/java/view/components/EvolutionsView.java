package view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EvolutionsView extends VBox {
    private static final int EVOLUTION_IMAGE_WIDTH = 180;
    private static final int EVOLUTION_IMAGE_HEIGHT = 180;

    // Constructor
    public EvolutionsView() {
        Label evolutionsLabel = new Label("Evolutions:");

        HBox evolutionsRow = new HBox(10,
                createEvolutionBox("Bulbasaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
                createEvolutionBox("Ivysaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"),
                createEvolutionBox("Venusaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png")
        );
        evolutionsRow.setAlignment(Pos.CENTER);

        getChildren().addAll(evolutionsLabel, evolutionsRow);
        setSpacing(8);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
    }

    /**
     * Creates one evolution item with the Pokemon image on top and its name below.
     *
     * @param name the Pokemon name to display under the image
     * @param imageUrl the URL of the Pokemon sprite image
     * @return a centered VBox containing the image and name
     */
    private VBox createEvolutionBox(String name, String imageUrl) {
        ImageView image = new ImageView(new Image(imageUrl, true));
        image.setFitWidth(EVOLUTION_IMAGE_WIDTH);
        image.setFitHeight(EVOLUTION_IMAGE_HEIGHT);
        image.setPreserveRatio(true);
        image.setSmooth(true);

        VBox box = new VBox(5, image, new Label(name));
        box.setAlignment(Pos.CENTER);
        box.setMinWidth(EVOLUTION_IMAGE_WIDTH);
        return box;
    }
}
