package model;

public class PokemonTypes {
    public int id;
    public int pokemonId;
    public int typeId;

    public PokemonTypes() {}

    @Override
    public String toString() {
        return String.format("%d. %d-%d", id, pokemonId, typeId);
    }
}
