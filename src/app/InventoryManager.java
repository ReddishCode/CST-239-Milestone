package app;

/**
 * Manages the arena store's stock using an array of SalableProduct objects.
 * The array holds different subclasses through Java inheritance.
 */
public class InventoryManager {
    private SalableProduct[] products;

    /** Creates and populates the initial store inventory. */
    public InventoryManager() {
        initializeInventory();
    }

    /**
     * Restocks the store with two weapons, two armor items, and one health item.
     * The initial items are hard-coded for Milestone 2.
     */
    public void initializeInventory() {
        products = new SalableProduct[] {
            new Weapon("Iron Sword", "A basic sword for arena battles", 25.00, 3, 18),
            new Weapon("Battle Axe", "A heavy axe with powerful strikes", 42.00, 2, 30),
            new Armor("Wooden Shield", "A shield for blocking attacks", 15.00, 2, 10),
            new Armor("Steel Chestplate", "Strong chest armor for close combat", 55.00, 2, 30),
            new Health("Health Potion", "Restores health during a battle", 10.00, 5, 40)
        };
    }

    /** Returns the number of different products in the store.
     * 
     * @return number of distinct products carried by the store
     */
    public int getProductCount() {
        return products.length;
    }

    /**
     * Returns a copy of the product-reference array for initializing the cart.
     * Changes to returned array positions will not change inventory positions.
     *
     * @return shallow copy of available product references
     */
    public SalableProduct[] getProducts() {
        return products.clone();
    }

    /**
     * Gets one product by its zero-based inventory index.
     *
     * @param index zero-based item position
     * @return matching product or null if the position is invalid
     */
    public SalableProduct getProduct(int index) {
        if (index < 0 || index >= products.length) {
            return null;
        }
        return products[index];
    }

    /**
     * Removes one available unit of a product from store inventory.
     *
     * @param index zero-based product position
     * @return true if an in-stock product was decremented
     */
    public boolean removeProduct(int index) {
        SalableProduct product = getProduct(index);
        if (product == null || product.getQuantity() <= 0) {
            return false;
        }
        product.setQuantity(product.getQuantity() - 1);
        return true;
    }

    /**
     * Adds one unit back to inventory after the shopper cancels a purchase.
     *
     * @param index zero-based product position
     * @return true if the requested product exists and was restocked
     */
    public boolean addProduct(int index) {
        SalableProduct product = getProduct(index);
        if (product == null || product.getQuantity() == Integer.MAX_VALUE) {
            return false;
        }
        product.setQuantity(product.getQuantity() + 1);
        return true;
    }

    /** Displays each product, price, available quantity, and class-specific detail. */
    public void displayInventory() {
        System.out.println("\n=== AVAILABLE INVENTORY ===");
        for (int i = 0; i < products.length; i++) {
            SalableProduct product = products[i];
            System.out.printf("%d. [%s] %s | $%.2f | Stock: %d%n",
                    i + 1, product.getItemType(), product.getName(),
                    product.getPrice(), product.getQuantity());
            System.out.println("   " + product.getDescription());
            if (!product.getItemDetails().isEmpty()) {
                System.out.println("   " + product.getItemDetails());
            }
        }
    }
}
