package app;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** Armor sold in the arena store. */
public class Armor extends SalableProduct {

    private int defense;
    private int weight; // Added for Milestone 4.

    /** Empty constructor used when loading JSON. */
    public Armor() { }

    /** Creates armor with defense and weight. */
    public Armor(String name, String description, double price,
            int quantity, int defense, int weight) {
        super(name, description, price, quantity);
        this.defense = defense;
        this.weight = weight;
    }

    /** @return armor defense */
    public int getDefense() { return defense; }

    /** @param defense armor defense */
    public void setDefense(int defense) { this.defense = defense; }

    /** @return armor weight */
    public int getWeight() { return weight; }

    /** @param weight armor weight */
    public void setWeight(int weight) { this.weight = weight; }

    /** @return product type */
    @Override
    @JsonIgnore
    public String getItemType() { return "Armor"; }

    /** @return armor information for the inventory */
    @Override
    @JsonIgnore
    public String getItemDetails() {
        return "Defense: " + defense + " | Weight: " + weight;
    }
}
