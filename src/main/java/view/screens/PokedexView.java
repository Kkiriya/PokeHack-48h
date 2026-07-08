package view.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import view.components.*;

public class PokedexView {

    private final BorderPane root;
    public final VBox leftBox;


    public PokedexView() {
        // Root assembly layout
        root = new BorderPane();

        // Left section contains the search box, Pokemon image, and stats
        SearchView searchBox = new SearchView();
        ImageView pokemonImageFrame = new ImageView();
        StatsView statsGrid = new StatsView();

        leftBox = new VBox(searchBox,pokemonImageFrame, statsGrid);
        leftBox.setPadding(new Insets(10));


        root.setLeft(leftBox);
        BorderPane.setMargin(leftBox, new Insets(10, 0, 0, 50)); // Adjust the left margin to move the leftBox down and to the right

        // Center
        CapturedListView capturedListView = new CapturedListView();
        FilterView filterView = new FilterView();
        EvolutionsView evolutionsView = new EvolutionsView();
        VBox centerBox = new VBox(capturedListView, filterView,evolutionsView);
        centerBox.setSpacing(10);

        root.setCenter(centerBox);
        BorderPane.setMargin(centerBox, new Insets(10, 200, 0, 0));
    }

    public Parent getRoot() {
        return root;
    }
}
