package view.components;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class ImageView extends StackPane {
    public final javafx.scene.image.ImageView pokemonImage;

    public ImageView() {
        pokemonImage = new javafx.scene.image.ImageView();
        pokemonImage.setPreserveRatio(true);
        pokemonImage.setFitWidth(240);
        pokemonImage.setFitHeight(240);
        pokemonImage.setSmooth(true);

        StackPane pokemonImageFrame = new StackPane(pokemonImage);
        pokemonImageFrame.setPrefSize(240, 240);
        pokemonImageFrame.setAlignment(Pos.CENTER);
        pokemonImageFrame.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");

        setMinSize(240, 240);
        setPrefSize(240, 240);
        setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");

        getChildren().add(pokemonImageFrame);
    }
}