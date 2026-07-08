package model;

public class Type {
    public int id;
    public String name;
    public String sprites;

    public Type() {}

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }
}
