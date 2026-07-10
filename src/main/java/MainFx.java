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
        PokedexView pokedexView = new PokedexView();
        Group appRoot = new Group(pokedexView.getRoot());
        StackPane scaledRoot = new StackPane(appRoot);
        scaledRoot.getStyleClass().add("app-background");

        PokedexController controller = new PokedexController(pokedexView);
        controller.start();

        Scene scene = new Scene(scaledRoot, BASE_WIDTH, BASE_HEIGHT);
        scene.getStylesheets().add(
                getClass().getResource("/styles/style.css").toExternalForm()
        );

        appRoot.scaleXProperty().bind(
                Bindings.min(
                        scene.widthProperty().divide(BASE_WIDTH),
                        scene.heightProperty().divide(BASE_HEIGHT)
                )
        );
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
