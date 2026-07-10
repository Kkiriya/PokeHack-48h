package view.components;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class ImageView extends StackPane {
    public final javafx.scene.image.ImageView pokemonImage;

    public ImageView() {
        pokemonImage = new javafx.scene.image.ImageView();
        pokemonImage.setFitWidth(240);
        pokemonImage.setFitHeight(240);
        pokemonImage.setSmooth(true);
        pokemonImage.setPreserveRatio(true);

        StackPane pokemonImageFrame = new StackPane(pokemonImage);
        pokemonImageFrame.setPrefSize(240, 240);
        pokemonImageFrame.setAlignment(Pos.CENTER);
        // Css styling
        pokemonImage.getStyleClass().add("pokemon-image");
        pokemonImageFrame.getStyleClass().add("pokemon-image-frame");

        getChildren().add(pokemonImageFrame);
    }
}