package model;

public class Move {
    public int id;
    public int accuracy;
    public int damage_class;
    public String effect_chance;
    public String effect_change;
    public String effect_entries;
    public String flavor_text_entries;
    public String learned_by_pokemon;
    public String name;
    public int power;
    public int pp;
    public int priority;
    public String stat_changes;
    public MoveTarget target;
    public int typeId;

    public Move() {}

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }

}
