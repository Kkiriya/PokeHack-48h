package model;

public class PokemonAbilities {
    public int pokemon_id;
    public int ability_id;
    public boolean is_hidden;

    public PokemonAbilities() {}

    @Override
    public String toString(){
        return String.format("%d-%d", pokemon_id, ability_id);
    }
}


