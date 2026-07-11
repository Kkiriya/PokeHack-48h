import controller.PokedexController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.screens.PokedexView;

public class MainFx extends Application {
    private static final double BASE_WIDTH = 1440;
    private static final double BASE_HEIGHT = 810;

    @Override
    public void start(Stage stage) {
        // Create the main view. Its root node contains the whole UI.
        PokedexView pokedexView = new PokedexView();
        // Wrap the root in a Group so the whole UI scales as one visual block.
        Group appRoot = new Group(pokedexView.getRoot());
        // Wrap the Group in a StackPane to center it and apply background styling.
        StackPane scaledRoot = new StackPane(appRoot);
        scaledRoot.getStyleClass().add("app-background");

        PokedexController controller = new PokedexController(pokedexView);
        controller.start();

        // Create the scene with the scaled root and set the base dimensions
        Scene scene = new Scene(scaledRoot, BASE_WIDTH, BASE_HEIGHT);
        scene.getStylesheets().add(
                getClass().getResource("/styles/style.css").toExternalForm()
        );

        // Scale the UI based on the current scene size.
        // Bindings.min keeps the aspect ratio and avoids stretching.
        appRoot.scaleXProperty().bind(
                Bindings.min(
                        scene.widthProperty().divide(BASE_WIDTH),
                        scene.heightProperty().divide(BASE_HEIGHT)
                )
        );
        // Use the same scale for Y so the UI keeps its proportions.
        appRoot.scaleYProperty().bind(appRoot.scaleXProperty());

        stage.setTitle("Pokedex");
        stage.setScene(scene);
        stage.setMinWidth(960);
        stage.setMinHeight(540);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
