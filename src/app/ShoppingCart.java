package app;

import java.util.ArrayList;

/** Keeps track of a customer's purchases using type-safe ArrayLists. */
public class ShoppingCart {

    private ArrayList<SalableProduct> products;
    private ArrayList<Integer> quantities;

    /**
     * Creates an empty cart from the inventory product list.
     * @param products product catalog from InventoryManager
     */
    public ShoppingCart(ArrayList<SalableProduct> products) {
        if (products == null) {
            throw new IllegalArgumentException("Product catalog cannot be null.");
        }
        this.products = new ArrayList<SalableProduct>(products);
        quantities = new ArrayList<Integer>();
        for (SalableProduct product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Product catalog cannot contain null.");
            }
            quantities.add(0);
        }
    }

    /**
     * Adds one purchased product to the cart.
     * @param product the actual product object, not its inventory index
     * @return true when the product is in the catalog
     */
    public boolean addProduct(SalableProduct product) {
        int index = products.indexOf(product);
        if (index < 0 || quantities.get(index) == Integer.MAX_VALUE) {
            return false;
        }
        quantities.set(index, quantities.get(index) + 1);
        return true;
    }

    /**
     * Removes one purchased product from the cart.
     * @param product the product to return
     * @return true if a purchased item was removed
     */
    public boolean removeProduct(SalableProduct product) {
        int index = products.indexOf(product);
        if (index < 0 || quantities.get(index) <= 0) {
            return false;
        }
        quantities.set(index, quantities.get(index) - 1);
        return true;
    }

    /**
     * Gets the number of purchased units for one product.
     * @param product the product being checked
     * @return number in the cart, or zero if not found
     */
    public int getQuantity(SalableProduct product) {
        int index = products.indexOf(product);
        if (index < 0) {
            return 0;
        }
        return quantities.get(index);
    }

    /** @return a copy of the product catalog */
    public ArrayList<SalableProduct> getCartProducts() {
        return new ArrayList<SalableProduct>(products);
    }

    /** @return a copy of the selected quantities */
    public ArrayList<Integer> getCartQuantities() {
        return new ArrayList<Integer>(quantities);
    }

    /** @return true if any items are in the cart */
    public boolean hasItems() {
        for (int quantity : quantities) {
            if (quantity > 0) {
                return true;
            }
        }
        return false;
    }

    /** @return total cost of all products in the cart */
    public double getTotal() {
        double total = 0;
        for (int i = 0; i < products.size(); i++) {
            total += products.get(i).getPrice() * quantities.get(i);
        }
        return total;
    }

    /**
     * Shows items using the CURRENT inventory numbers, even after sorting.
     * @param inventoryOrder inventory list in its current order
     */
    public void displayCart(ArrayList<SalableProduct> inventoryOrder) {
        System.out.println("\n=== YOUR SHOPPING CART ===");
        if (!hasItems()) {
            System.out.println("Your cart is empty.");
        } else {
            for (int i = 0; i < inventoryOrder.size(); i++) {
                SalableProduct product = inventoryOrder.get(i);
                int quantity = getQuantity(product);
                if (quantity > 0) {
                    System.out.printf("%d. %s | Quantity: %d | Subtotal: $%.2f%n",
                            i + 1, product.getName(), quantity, quantity * product.getPrice());
                }
            }
        }
        System.out.printf("Cart total: $%.2f%n", getTotal());
    }

    /** Removes all products from the cart after they are returned to stock. */
    public void emptyCart() {
        for (int i = 0; i < quantities.size(); i++) {
            quantities.set(i, 0);
        }
    }
}
