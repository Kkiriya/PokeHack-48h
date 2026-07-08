package controller;
import view.screens.PokedexView;

public class PokedexController {
    private PokedexView view;

    // Constructor
    public PokedexController(PokedexView view) {
        this.view = view;
    }

    public void displayPokedex(PokedexView view) {
        // I must wait for the model to be implemented before I can display the Pokedex
        //view.pokemonImage = new Image("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",true);
    }
}
