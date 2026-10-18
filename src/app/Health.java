package app;

/** A salable healing item that restores health points during combat. */
public class Health extends SalableProduct {
    private int healingPoints;

    /**
     * Creates a healing supply for arena combat.
     *
     * @param name healing item's name
     * @param description healing item's description
     * @param price selling price in dollars
     * @param quantity number available
     * @param healingPoints health points restored; must be positive
     */
    public Health(String name, String description, double price, int quantity, int healingPoints) {
        super(name, description, price, quantity);
        if (healingPoints <= 0) {
            throw new IllegalArgumentException("Healing points must be positive.");
        }
        this.healingPoints = healingPoints;
    }

    /** Returns how many health points the item restores.
     * 
     * @return number of health points restored
     */
    public int getHealingPoints() {
        return healingPoints;
    }

    /** @return Health as this item's category */
    @Override
    public String getItemType() {
        return "Health";
    }

    /** @return healing points for inventory display */
    @Override
    public String getItemDetails() {
        return "Restores: " + healingPoints + " HP";
    }
}
