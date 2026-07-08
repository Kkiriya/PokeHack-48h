package model;

public class PokemonMoves {
    public int id;
    public int pokemonId;
    public int moveId;

    public PokemonMoves() {}

    @Override
    public String toString() {
        return String.format("%d. %d-%d", id, pokemonId, moveId);
    }
}
