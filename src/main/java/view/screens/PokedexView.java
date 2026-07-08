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

        // Right section contains the Pokemon types and weaknesses
        TypesView typesGrid = new TypesView();

        root.setRight(typesGrid);
        // Change where the typesGrid is positioned by adjusting the Insets values
        BorderPane.setMargin(typesGrid, new Insets(120, 120, 0, 0));

        // Center
        CapturedListView capturedListView = new CapturedListView();
        FilterView filterView = new FilterView();
        VBox centerBox = new VBox(capturedListView, filterView);
        centerBox.setSpacing(10);

        root.setCenter(centerBox);
        BorderPane.setMargin(centerBox, new Insets(10, 0, 0, 0));

        // Bottom section contains the Pokemon evolutions
        EvolutionsView evolutionsView = new EvolutionsView();
        evolutionsView.prefWidthProperty().bind(root.widthProperty().multiply(0.70));
        evolutionsView.maxWidthProperty().bind(root.widthProperty().multiply(0.70));
        evolutionsView.prefHeightProperty().bind(root.heightProperty().multiply(0.20));
        evolutionsView.maxHeightProperty().bind(root.heightProperty().multiply(0.20));

        root.setBottom(evolutionsView);
        BorderPane.setAlignment(evolutionsView, Pos.CENTER);
        BorderPane.setMargin(evolutionsView, new Insets(0, 0, 20, 0)); // Adjust the bottom margin to move the evolutionsView up
    }

    public Parent getRoot() {
        return root;
    }
}
