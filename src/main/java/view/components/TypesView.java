package view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TypesView extends GridPane {

    public enum PokemonElement {
        NORMAL("#A8A77A"),
        FIRE("#EE8130"),
        WATER("#6390F0"),
        ELECTRIC("#F7D02C"),
        GRASS("#7AC74C"),
        ICE("#96D9D6"),
        FIGHTING("#C22E28"),
        POISON("#A33EA1"),
        GROUND("#E2BF65"),
        FLYING("#A98FF3"),
        PSYCHIC("#F95587"),
        BUG("#A6B91A"),
        ROCK("#B6A136"),
        GHOST("#735797"),
        DRAGON("#6F35FC"),
        DARK("#705746"),
        STEEL("#B7B7CE"),
        FAIRY("#D685AD");

        private final Color color;

        PokemonElement(String hex) {
            this.color = Color.web(hex);
        }

        /**
         * Returns the background color for the Pokemon element type.
         *
         * @return a Background object with the appropriate color and corner radius
        */
        // It's a little like implementing in Rust.
        public Background getBackground() {
            return new Background(
                    new BackgroundFill(
                            color,
                            new CornerRadii(8),
                            Insets.EMPTY
                    )
            );
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

        element.setBackground(type.getBackground());
        element.setTextFill(Color.WHITE);
        element.setPadding(new Insets(5, 10, 5, 10));

        return element;
    }
}