package app;

/** A salable armor item with a defense rating. */
public class Armor extends SalableProduct {
    private int defense;

    /**
     * Creates armor that can protect an arena fighter.
     *
     * @param name armor name
     * @param description armor description
     * @param price selling price in dollars
     * @param quantity number available
     * @param defense amount of defense provided; must be positive
     */
    public Armor(String name, String description, double price, int quantity, int defense) {
        super(name, description, price, quantity);
        if (defense <= 0) {
            throw new IllegalArgumentException("Armor defense must be positive.");
        }
        this.defense = defense;
    }

    /** Returns the armor defense rating.
     * 
     * @return defense rating
     */
    public int getDefense() {
        return defense;
    }

    /** @return Armor as this item's category */
    @Override
    public String getItemType() {
        return "Armor";
    }

    /** @return defense rating for inventory display */
    @Override
    public String getItemDetails() {
        return "Defense: " + defense;
    }
}
