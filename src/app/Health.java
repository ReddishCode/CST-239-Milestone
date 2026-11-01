package app;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** A health item sold in the arena store. */
public class Health extends SalableProduct {

    private int healingPoints;
    private int cooldownSeconds; // Added for Milestone 4.

    /** Empty constructor used when loading JSON. */
    public Health() { }

    /** Creates an item with healing points and cooldown time. */
    public Health(String name, String description, double price,
            int quantity, int healingPoints, int cooldownSeconds) {
        super(name, description, price, quantity);
        this.healingPoints = healingPoints;
        this.cooldownSeconds = cooldownSeconds;
    }

    /** @return number of health points restored */
    public int getHealingPoints() { return healingPoints; }

    /** @param healingPoints number of points restored */
    public void setHealingPoints(int healingPoints) { this.healingPoints = healingPoints; }

    /** @return seconds between uses */
    public int getCooldownSeconds() { return cooldownSeconds; }

    /** @param cooldownSeconds seconds between uses */
    public void setCooldownSeconds(int cooldownSeconds) { this.cooldownSeconds = cooldownSeconds; }

    /** @return product type */
    @Override
    @JsonIgnore
    public String getItemType() { return "Health"; }

    /** @return health item information for the inventory */
    @Override
    @JsonIgnore
    public String getItemDetails() {
        return "Heals: " + healingPoints + " | Cooldown: " + cooldownSeconds;
    }
}
