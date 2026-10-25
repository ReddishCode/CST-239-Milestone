package app;

/**
 * Maintains a store shopper's selected quantities for each salable product.
 * Products are tracked using a stable array of product references, while
 * cart quantities remain separate from the available inventory quantities.
 */
public class ShoppingCart {
    private final SalableProduct[] products;
    private final int[] quantities;

    /**
     * Starts an empty cart whose indexes correspond to inventory product indexes.
     *
     * @param products the store's initial catalog of salable products
     * @throws IllegalArgumentException if the product array or an item is null
     */
    public ShoppingCart(SalableProduct[] products) {
        if (products == null) {
            throw new IllegalArgumentException("Product catalog cannot be null.");
        }
        this.products = products.clone();
        for (SalableProduct product : this.products) {
            if (product == null) {
                throw new IllegalArgumentException("Product catalog cannot contain null.");
            }
        }
        this.quantities = new int[products.length];
    }

    /**
     * Adds one unit of a product to the cart (without adjusting store stock).
     * Only StoreFront coordinates both the inventory and cart changes.
     *
     * @param index zero-based product position
     * @return true if a cart quantity was increased
     */
    public boolean addProduct(int index) {
        if (index < 0 || index >= quantities.length || quantities[index] == Integer.MAX_VALUE) {
            return false;
        }
        quantities[index]++;
        return true;
    }

    /**
     * Removes one unit of a product from the cart.
     *
     * @param index zero-based product position
     * @return true if an item was actually in the cart and removed
     */
    public boolean removeProduct(int index) {
        if (index < 0 || index >= quantities.length || quantities[index] == 0) {
            return false;
        }
        quantities[index]--;
        return true;
    }

    /**
     * Gets the quantity of a product currently in the shopper's cart.
     *
     * @param index zero-based product position
     * @return selected units, or zero for an invalid position
     */
    public int getQuantity(int index) {
        if (index < 0 || index >= quantities.length) {
            return 0;
        }
        return quantities[index];
    }
    
    /**
     * Returns the products tracked by the shopping cart.
     * The array is copied to protect the original cart structure.
     *
     * @return an array containing the cart product references
     */
    public SalableProduct[] getCartProducts() {

        // Return a copy so the original array cannot be rearranged.
        return products.clone();
    }

    /**
     * Returns the quantity selected for each product.
     * Each quantity matches the product at the same array index.
     * A quantity of zero means the product is not in the cart.
     *
     * @return an array containing all cart quantities
     */
    public int[] getCartQuantities() {

        // Return a copy of the current quantities.
    	return quantities.clone();
    }

    /** Checks whether any selected product is in the cart.
     * 
     * @return true when the cart contains at least one product
     */
    public boolean hasItems() {
        for (int quantity : quantities) {
            if (quantity > 0) {
                return true;
            }
        }
        return false;
    }

    /** Computes the price of all selected quantities.
     * 
     * @return total price of the products currently in the cart
     */
    public double getTotal() {
        double total = 0;
        for (int i = 0; i < quantities.length; i++) {
            total += quantities[i] * products[i].getPrice();
        }
        return total;
    }

    /** Displays cart contents using the same item numbers as the inventory. */
    public void displayCart() {
        System.out.println("\n=== YOUR SHOPPING CART ===");
        if (!hasItems()) {
            System.out.println("Your cart is empty.");
        } else {
            for (int i = 0; i < quantities.length; i++) {
                if (quantities[i] > 0) {
                    System.out.printf("%d. %s | Quantity: %d | Subtotal: $%.2f%n",
                            i + 1, products[i].getName(), quantities[i],
                            quantities[i] * products[i].getPrice());
                }
            }
        }
        System.out.printf("Cart total: $%.2f%n", getTotal());
    }
    
    /**
     * Empties the shopping cart by resetting all selected quantities.
     * Inventory restoration is handled by the StoreFront class.
     */
    public void emptyCart() {

        // Visit every product quantity in the shopping cart.
        for (int i = 0; i < quantities.length; i++) {

            // Reset the selected quantity to zero.
            quantities[i] = 0;
        }
    }
}
