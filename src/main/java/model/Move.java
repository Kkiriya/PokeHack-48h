package model;

public class Move {
    public int id;
    public int accuracy;
    public String damage_class;
    public String effect_change;
    public String effect_entries;
    public String flavor_text_entries;
    public String name;
    public int power;
    public int pp;
    public int priority;
    public String stat_changes;
    public String target;
    public int type_id;

    public Move() {}

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }

}
