package app;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** A weapon sold in the arena store. */
public class Weapon extends SalableProduct {

    private int damage;
    private int range; // Added for Milestone 4.

    /** Empty constructor used when loading JSON. */
    public Weapon() { }

    /** Creates a weapon with damage and range. */
    public Weapon(String name, String description, double price,
            int quantity, int damage, int range) {
        super(name, description, price, quantity);
        this.damage = damage;
        this.range = range;
    }

    /** @return weapon damage */
    public int getDamage() { return damage; }

    /** @param damage weapon damage */
    public void setDamage(int damage) { this.damage = damage; }

    /** @return weapon range */
    public int getRange() { return range; }

    /** @param range weapon range */
    public void setRange(int range) { this.range = range; }

    /** @return product type */
    @Override
    @JsonIgnore
    public String getItemType() { return "Weapon"; }

    /** @return weapon information for the inventory */
    @Override
    @JsonIgnore
    public String getItemDetails() {
        return "Damage: " + damage + " | Range: " + range;
    }
}
