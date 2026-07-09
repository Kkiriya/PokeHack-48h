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

        Scene scene = new Scene(pokedexView.getRoot(), 800, 600);
        stage.setTitle("Pokedex");
        stage.setScene(scene);
        scene.getStylesheets().add(
                getClass().getResource("/styles/style.css").toExternalForm()
        );
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

