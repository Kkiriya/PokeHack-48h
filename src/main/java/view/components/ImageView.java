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

        // TODO - Delete this part when you implement the API call to get the image URL
        Image sprite = new Image("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png");
        // That's the part that will be in controller
        pokemonImage.setImage(sprite);

        StackPane pokemonImageFrame = new StackPane(pokemonImage);
        pokemonImageFrame.setPrefSize(240, 240);
        pokemonImageFrame.setAlignment(Pos.CENTER);
        pokemonImageFrame.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");

        getChildren().add(pokemonImageFrame);
    }
}
