package view.screens;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import view.components.CardInfoButtons;
import view.components.ImageView;
import view.components.StatsView;
import view.components.TypesView;

import static java.util.Arrays.setAll;


/**
 * FilterView is a JavaFX HBox that contains buttons to switch between different views (ImagePanel, StatsView, TypesView).
 * It uses CardInfoButtons for the buttons and dynamically updates the content pane based on user interaction.
 */
public class FilterView extends HBox {
    public final ImageView imageView;
    public final StatsView statsView;
    public TypesView typesView;
    public int id;

    public FilterView() {
        CardInfoButtons cardInfoButtons = new CardInfoButtons();
        imageView = new ImageView();
        statsView = new StatsView();
        typesView = new TypesView();

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

            contentPane.getChildren().setAll(statsView);
        });

        cardInfoButtons.typesButton.setOnAction(e -> {
            contentPane.getChildren().setAll(typesView);
        });

        cardInfoButtons.idButton.setOnAction(e -> {
            contentPane.getChildren().setAll(new Label("ID: " + id));
        });

        setSpacing(10);
        getChildren().addAll(cardInfoButtons, contentPane);
    }
}

