package model;

public class Ability {
    public int id;
    public String effect_entries;
    public String flavor_text_entries;
    public String name;

    public Ability() {}

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }
}
