package app;

import java.util.Scanner;

/**
 * Runs the console-based Arena Supply Store for the game user.
 * Coordinates purchases and cancellations between inventory and the cart.
 */
public class StoreFront {
    private InventoryManager inventory;
    private ShoppingCart cart;

    /**
     * Creates a store by loading the current inventory from JSON.
     *
     * @throws FileServiceException if the JSON file cannot be read
     */
    public StoreFront() throws FileServiceException {
        initializeStore();
    }

    /**
     * Reloads stock from the saved file and creates an empty cart.
     *
     * @throws FileServiceException if inventory loading fails
     */
    public void initializeStore() throws FileServiceException {
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
     * Purchases are saved through the inventory manager.
     *
     * @param index zero-based product position
     * @return true when the item is successfully moved to the cart
     * @throws FileServiceException if the stock change cannot be saved
     */
    public boolean purchaseProduct(int index) throws FileServiceException {
        if (!inventory.removeProduct(index)) {
            return false;
        }
        if (!cart.addProduct(inventory.getProduct(index))) {
            inventory.addProduct(index);
            return false;
        }
        return true;
    }

    /**
     * Cancels a selected purchase and returns one unit to store inventory.
     * Returned products are saved through the inventory manager.
     *
     * @param index zero-based product position
     * @return true when an item in the cart has been returned to stock
     * @throws FileServiceException if the stock change cannot be saved
     */
    public boolean cancelPurchase(int index) throws FileServiceException {
        if (!cart.removeProduct(inventory.getProduct(index))) {
            return false;
        }
        try {
            if (!inventory.addProduct(index)) {
                cart.addProduct(inventory.getProduct(index));
                return false;
            }
        } catch (FileServiceException ex) {
            // Put the item back in the cart when writing inventory.json fails.
            cart.addProduct(inventory.getProduct(index));
            throw ex;
        }
        return true;
    }
    
    /**
     * Empties the shopping cart and returns all selected products
     * to the store inventory.
     *
     * @return true if every cart item was successfully returned
     * @throws FileServiceException if the stock change cannot be saved
     */
    public boolean emptyShoppingCart() throws FileServiceException {

        // Go through every product in the inventory.
        for (int i = 0; i < inventory.getProductCount(); i++) {

            // Keep removing the product until none remain in the cart.
            while (cart.getQuantity(inventory.getProduct(i)) > 0) {

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
     * Reads a menu number and returns -1 if it is not a number.
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
     * @throws FileServiceException if inventory cannot be saved
     */
    private void handlePurchase(Scanner input) throws FileServiceException {
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
     * @throws FileServiceException if inventory cannot be saved
     */
    private void handleCancellation(Scanner input) throws FileServiceException {
        cart.displayCart(inventory.getProducts());
        if (!cart.hasItems()) {
            System.out.println("There are no purchases to cancel.");
            return;
        }
        System.out.print("Enter the number of the product to return: ");
        int index = readNumber(input) - 1;
        SalableProduct product = inventory.getProduct(index);
        if (product == null) {
            System.out.println("Invalid product number. Choose a number shown in your cart.");
        } else if (cart.getQuantity(product) == 0) {
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
        	        + "\n6. Exit"
                    + "\n7. Sort inventory A-Z (name, then price)"
                    + "\n8. Sort inventory Z-A (name, then price)");
            System.out.print("Choose an option (1-8): ");
            if (!input.hasNextLine()) {
                break;
            }
            int choice = readNumber(input);
            try {
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
                    cart.displayCart(inventory.getProducts());
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

                case 7:
                    // Sort products using their compareTo method.
                    inventory.sortProductsAscending();
                    System.out.println("Inventory sorted by name and price (A-Z).");
                    inventory.displayInventory();
                    break;

                case 8:
                    inventory.sortProductsDescending();
                    System.out.println("Inventory sorted by name and price (Z-A).");
                    inventory.displayInventory();
                    break;

                case 6:

                    // End the shopping session.
                    running = false;
                    break;

                default:

                    // Handle menu selections that are not recognized.
                    System.out.println("Invalid menu selection. Enter a number from 1 to 8.");
            }
            } catch (FileServiceException ex) {
                // A file/JSON failure is reported without crashing the menu.
                System.err.println("Inventory file error: " + ex.getMessage());
                System.err.println("Your last stock change was not saved. Check inventory.json and permissions.");
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
        try {
            new StoreFront().run();
        } catch (FileServiceException ex) {
            System.err.println("Unable to start the Arena Supply Store: " + ex.getMessage());
            System.err.println("Please restore a valid inventory.json in the project directory.");
        }
    }
}
