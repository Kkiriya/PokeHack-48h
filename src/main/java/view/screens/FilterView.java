package view.screens;

import javafx.scene.layout.HBox;
import view.components.CardInfoButtons;
import view.components.ImageView;
import view.components.StatsView;
import view.components.TypesView;

/**
 * FilterView is a JavaFX HBox that contains buttons to switch between different views (ImagePanel, StatsView, TypesView).
 * It uses CardInfoButtons for the buttons and dynamically updates the content pane based on user interaction.
 */
public class FilterView extends HBox {
    public final ImageView imageView;

    public FilterView() {
        CardInfoButtons cardInfoButtons = new CardInfoButtons();
        imageView = new ImageView();
        HBox contentPane = new HBox();

        contentPane.getChildren().add(imageView);

        cardInfoButtons.spritesButton.setOnAction(e -> {;
            contentPane.getChildren().setAll(imageView);
        });

        cardInfoButtons.statsButton.setOnAction(e -> {;
            StatsView statsView = new StatsView();
            contentPane.getChildren().setAll(statsView);
        });

        cardInfoButtons.typesButton.setOnAction(e -> {;
            TypesView typesView = new TypesView();
            contentPane.getChildren().setAll(typesView);
        });

        getChildren().addAll(cardInfoButtons, contentPane);
    }
}

