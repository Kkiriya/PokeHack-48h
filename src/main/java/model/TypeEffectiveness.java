package model;

public class TypeEffectiveness {
    public int attacking_type_id;
    public int defending_type_id;
    public double multiplier;

    public TypeEffectiveness() {}

    @Override
    public String toString(){
        return String.format("%d-%d", attacking_type_id, defending_type_id);
    }
}
