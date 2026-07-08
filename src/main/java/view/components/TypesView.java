package view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class TypesView extends GridPane {

    public enum PokemonElement {
        NORMAL,
        FIRE,
        WATER,
        ELECTRIC,
        GRASS,
        ICE,
        FIGHTING,
        POISON,
        GROUND,
        FLYING,
        PSYCHIC,
        BUG,
        ROCK,
        GHOST,
        DRAGON,
        DARK,
        STEEL,
        FAIRY;

        public String toLowerCase() {
            return name().toLowerCase();
        }
    }

    public TypesView() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(10));
        setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");

        add(new Label("Type:"), 0, 0);
        add(createElementLabel(PokemonElement.WATER), 0, 1);
        add(createElementLabel(PokemonElement.FIRE), 1, 1);

        add(new Label("Weakness:"), 0, 2);
        add(createElementLabel(PokemonElement.GRASS), 0, 3);
        add(createElementLabel(PokemonElement.ELECTRIC), 1, 3);
    }

    /**
     * Creates a label for a Pokemon element type with the appropriate background color.
     *
     * @param type the PokemonElement type to create a label for
     * @return a Label with the type name and background color
     */
    private Label createElementLabel(PokemonElement type) {
        Label element = new Label(type.name());

        element.getStyleClass().add("pokemon-type");
        element.getStyleClass().add(type.toLowerCase());

        return element;
    }
}