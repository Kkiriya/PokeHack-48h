package model;

public class PokemonTypes {
    public int pokemon_id;
    public int type_id;

    public PokemonTypes() {}

    @Override
    public String toString() {
        return String.format("%d-%d", pokemon_id, type_id);
    }
}
