package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.screens.PokedexView;

public class PokedexDemo extends Application {

    @Override
    public void start(Stage stage) {
        PokedexView view = new PokedexView();

        Scene scene = new Scene(view.getRoot(), 900, 600);
        stage.setTitle("Pokedex Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}