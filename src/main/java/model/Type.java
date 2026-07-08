package model;

public class Type {
    public int id;
    public String name;
    public String double_damage_to;
    public String half_damage_from;
    public String no_damage_from;
    public String no_damage_to;
    public String move_damage_class;
    public String sprites;

    public Type() {}

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }
}
