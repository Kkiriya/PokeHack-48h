package model;

import java.util.ArrayList;
import java.util.Map;

public class Pokemon {
    public int id;
    public int baseExperience;
    public String cries;
    public double height;
    public String name;
    public String species;
    public String sprites;
    public int hp;
    public int attack;
    public int defense;
    public int special_attack;
    public int special_defense;
    public int speed;
    public double weight;

    public Pokemon() {}

    @Override
    public String toString() {
        return String.format("%d, %s", id, name);
    }
}
