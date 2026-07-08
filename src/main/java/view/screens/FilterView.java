package view.screens;

import javafx.geometry.Insets;
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

    public FilterView() {
        CardInfoButtons cardInfoButtons = new CardInfoButtons();
        ImageView imageView = new ImageView();
        HBox contentPane = new HBox();

        contentPane.setSpacing(10);
        contentPane.setPadding(new Insets(10));
        contentPane.setMinSize(260, 260);
        contentPane.setPrefSize(260, 260);

        contentPane.getChildren().add(imageView);

        cardInfoButtons.spritesButton.setOnAction(e -> {
            contentPane.getChildren().setAll(imageView);
        });

        cardInfoButtons.statsButton.setOnAction(e -> {
            StatsView statsView = new StatsView();
            contentPane.getChildren().setAll(statsView);
        });

        cardInfoButtons.typesButton.setOnAction(e -> {
            TypesView typesView = new TypesView();
            contentPane.getChildren().setAll(typesView);
        });

        setSpacing(10);
        getChildren().addAll(cardInfoButtons, contentPane);
    }
}

