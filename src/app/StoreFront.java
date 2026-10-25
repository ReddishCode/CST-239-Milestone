package app;

import java.util.Scanner;

/**
 * Runs the console-based Arena Supply Store for the game user.
 * Coordinates purchases and cancellations between inventory and the cart.
 */
public class StoreFront {
    private InventoryManager inventory;
    private ShoppingCart cart;

    /** Creates a new store with starter inventory and an empty shopping cart. */
    public StoreFront() {
        initializeStore();
    }

    /** Resets the stock and cart at the beginning of a store session. */
    public void initializeStore() {
        inventory = new InventoryManager();
        cart = new ShoppingCart(inventory.getProducts());
    }

    /** Returns the active inventory manager.
     * 
     * @return inventory manager used by this store
     */
    public InventoryManager getInventory() {
        return inventory;
    }

    /** Returns the active shopping cart.
     * 
     * @return shopping cart used by this store
     */
    public ShoppingCart getCart() {
        return cart;
    }

    /**
     * Purchases one unit of a product and adds it to the cart if in stock.
     * In the unlikely case that cart insertion fails, the stock is restored.
     *
     * @param index zero-based product position
     * @return true when the item is successfully moved to the cart
     */
    public boolean purchaseProduct(int index) {
        if (!inventory.removeProduct(index)) {
            return false;
        }
        if (!cart.addProduct(index)) {
            inventory.addProduct(index);
            return false;
        }
        return true;
    }

    /**
     * Cancels a selected purchase and returns one unit to store inventory.
     * In the unlikely case that restocking fails, the cart is restored.
     *
     * @param index zero-based product position
     * @return true when an item in the cart has been returned to stock
     */
    public boolean cancelPurchase(int index) {
        if (!cart.removeProduct(index)) {
            return false;
        }
        if (!inventory.addProduct(index)) {
            cart.addProduct(index);
            return false;
        }
        return true;
    }
    
    /**
     * Empties the shopping cart and returns all selected products
     * to the store inventory.
     *
     * @return true if every cart item was successfully returned
     */
    public boolean emptyShoppingCart() {

        // Go through every product in the inventory.
        for (int i = 0; i < inventory.getProductCount(); i++) {

            // Keep removing the product until none remain in the cart.
            while (cart.getQuantity(i) > 0) {

                // Cancel the purchase to restore one unit to inventory.
                if (!cancelPurchase(i)) {

                    // Stop if a product cannot be returned.
                    return false;
                }
            }
        }

        // Make sure all shopping cart quantities are reset.
        cart.emptyCart();

        // Every selected item was returned successfully.
        return true;
    }

    /**
     * Reads a complete input line as an integer without causing input mismatch errors.
     *
     * @param input scanner reading from the console
     * @return integer entered or -1 for invalid input/end of input
     */
    private int readNumber(Scanner input) {
        if (!input.hasNextLine()) {
            return -1;
        }
        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    /**
     * Prompts for an inventory item, purchases it, and prints detailed feedback.
     *
     * @param input scanner reading keyboard input
     */
    private void handlePurchase(Scanner input) {
        inventory.displayInventory();
        System.out.print("Enter the number of the product to purchase: ");
        int index = readNumber(input) - 1;
        SalableProduct product = inventory.getProduct(index);
        if (product == null) {
            System.out.println("Invalid product number. Choose a number shown in the inventory.");
        } else if (product.getQuantity() == 0) {
            System.out.println("Sorry, " + product.getName() + " is out of stock.");
        } else if (purchaseProduct(index)) {
            System.out.printf("Purchased 1 %s for $%.2f. Remaining stock: %d.%n",
                    product.getName(), product.getPrice(), product.getQuantity());
            System.out.printf("Updated cart total: $%.2f%n", cart.getTotal());
        } else {
            System.out.println("Unable to complete the purchase. Please try again.");
        }
    }

    /**
     * Prompts for an item already purchased, returns it to stock, and prints feedback.
     *
     * @param input scanner reading keyboard input
     */
    private void handleCancellation(Scanner input) {
        cart.displayCart();
        if (!cart.hasItems()) {
            System.out.println("There are no purchases to cancel.");
            return;
        }
        System.out.print("Enter the number of the product to return: ");
        int index = readNumber(input) - 1;
        SalableProduct product = inventory.getProduct(index);
        if (product == null) {
            System.out.println("Invalid product number. Choose a number shown in your cart.");
        } else if (cart.getQuantity(index) == 0) {
            System.out.println("You have not purchased " + product.getName() + ".");
        } else if (cancelPurchase(index)) {
            System.out.printf("Canceled 1 %s. Available stock is now %d.%n",
                    product.getName(), product.getQuantity());
            System.out.printf("Updated cart total: $%.2f%n", cart.getTotal());
        } else {
            System.out.println("Unable to cancel this purchase. Please try again.");
        }
    }

    /** Displays the menu until the gamer chooses to leave the store. */
    public void run() {
        Scanner input = new Scanner(System.in);
        boolean running = true;
        System.out.println("===================================");
        System.out.println("  Welcome to the Arena Supply Store!");
        System.out.println("===================================");
        while (running) {
        	System.out.println("\n1. View inventory"
        	        + "\n2. Purchase a product"
        	        + "\n3. Cancel a purchase"
        	        + "\n4. View cart"
        	        + "\n5. Empty cart"
        	        + "\n6. Exit");
            System.out.print("Choose an option (1-6): ");
            if (!input.hasNextLine()) {
                break;
            }
            int choice = readNumber(input);
            switch (choice) {
                case 1:
                    inventory.displayInventory();
                    break;
                case 2:
                    handlePurchase(input);
                    break;
                case 3:
                    handleCancellation(input);
                    break;
                case 4:
                    cart.displayCart();
                    break;
                case 5:

                    // Check whether there are products to remove.
                    if (!cart.hasItems()) {
                        System.out.println("Your cart is already empty.");
                    }

                    // Return every cart product to the store inventory.
                    else if (emptyShoppingCart()) {
                        System.out.println("Cart emptied. All items returned to inventory.");
                    }

                    // Display an error if the process cannot be completed.
                    else {
                        System.out.println("Could not empty the cart completely.");
                    }

                    break;

                case 6:

                    // End the shopping session.
                    running = false;
                    break;

                default:

                    // Handle menu selections that are not recognized.
                    System.out.println("Invalid menu selection. Enter a number from 1 to 6.");
            }
        }
        System.out.println("Thank you for visiting the Arena Supply Store!");
    }

    /**
     * Entry point for the game-user application.
     *
     * @param args unused command-line parameters
     */
    public static void main(String[] args) {
        new StoreFront().run();
    }
}
