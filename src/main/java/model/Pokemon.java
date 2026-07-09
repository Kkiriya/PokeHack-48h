package model;

import java.util.ArrayList;
import java.util.Map;

public class Pokemon {
    public int id;
    public int abilityId;
    public int baseExperience;
    public String cries;
    public float height;
    public String name;
    public String species;
    public String sprites;
    public String stats;
    public float weight;
    public String types;

    public Pokemon() {}

    @Override
    public String toString() {
        return String.format("%d, %s", id, name);
    }
}
