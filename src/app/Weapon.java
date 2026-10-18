package app;

/** A salable weapon with an attack damage rating. */
public class Weapon extends SalableProduct {
    private int damage;

    /**
     * Creates a weapon for arena combat.
     *
     * @param name weapon name
     * @param description weapon description
     * @param price selling price in dollars
     * @param quantity number available
     * @param damage damage dealt by the weapon; must be positive
     */
    public Weapon(String name, String description, double price, int quantity, int damage) {
        super(name, description, price, quantity);
        if (damage <= 0) {
            throw new IllegalArgumentException("Weapon damage must be positive.");
        }
        this.damage = damage;
    }

    /** Returns the weapon attack rating.
     * 
     * @return weapon attack damage
     */
    public int getDamage() {
        return damage;
    }

    /** @return Weapon as this item's category */
    @Override
    public String getItemType() {
        return "Weapon";
    }

    /** @return damage rating for inventory display */
    @Override
    public String getItemDetails() {
        return "Damage: " + damage;
    }
}
