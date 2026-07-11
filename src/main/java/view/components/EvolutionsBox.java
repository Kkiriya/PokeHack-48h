package view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class EvolutionsBox extends VBox {
    private final List<Pokemon> evolutions;
    private final HBox evolutionsRow;

    // Constructor
    public EvolutionsBox() {
        Label evolutionsLabel = new Label("Evolutions");
        evolutionsLabel.getStyleClass().add("main-font");

        evolutions = new ArrayList<>();
        evolutionsRow = new HBox(10);

        evolutionsRow.setAlignment(Pos.CENTER);

        getChildren().addAll(evolutionsLabel, evolutionsRow);
    }

    public void setEvolutions(List<Pokemon> newEvolutions) {
        evolutions.clear();
        evolutions.addAll(newEvolutions);
        evolutionsRow.getChildren().clear();

        for (int i = 0; i < evolutions.size(); i++) {
            Pokemon evolution = evolutions.get(i);
            evolutionsRow.getChildren().add(createEvolutionBox(evolution.name, evolution.sprites));
            if (i < evolutions.size() - 1){

                StackPane evolutionArrow = new StackPane();
                evolutionArrow.getStyleClass().add("evolution-image-arrow");
                // Set margin so the arrow are centered with the evolution images.
                HBox.setMargin(evolutionArrow, new Insets(0, 0, 22, 0));

                evolutionsRow.getChildren().add(evolutionArrow);
            }
        }
    }

    /**
     * Creates one evolution item with the Pokémon image on top and its name below.
     *
     * @param name     the Pokémon name to display under the image
     * @param imageUrl the URL of the Pokémon sprite image
     * @return a centered VBox containing the image and name
     */
    private VBox createEvolutionBox(String name, String imageUrl) {
        ImageView evolutionImage = new ImageView(new Image(imageUrl, true));
        StackPane evolutionImageFrame = new StackPane(evolutionImage);
        evolutionImageFrame.getStyleClass().add("evolution-image");

        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("evolution-name");

        VBox evolutionBox = new VBox(5, evolutionImageFrame, nameLabel);
        evolutionBox.setAlignment(Pos.CENTER);
        evolutionBox.getStyleClass().add("evolution-box");
        return evolutionBox;
    }
}
