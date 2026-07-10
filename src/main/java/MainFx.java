import controller.PokedexController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.screens.PokedexView;

public class MainFx extends Application {

    @Override
    public void start(Stage stage) {
        PokedexView pokedexView = new PokedexView();

        PokedexController controller = new PokedexController(pokedexView);
        controller.start();

        Scene scene = new Scene(pokedexView.getRoot(), 1440, 810);
        scene.getStylesheets().add(
                getClass().getResource("/styles/style.css").toExternalForm()
        );

        stage.setTitle("Pokedex");
        stage.setScene(scene);
        stage.setMinHeight(760);
        stage.setMinWidth(1300);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

