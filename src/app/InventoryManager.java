package app;

import java.util.ArrayList;
import java.util.Collections;

/** Manages store stock and sorts products by name, then by price. */
public class InventoryManager {

    private ArrayList<SalableProduct> products;
    private FileService fileService;

    /**
     * Creates the inventory and reads products from inventory.json.
     * @throws FileServiceException if the JSON file cannot be loaded
     */
    public InventoryManager() throws FileServiceException {
        fileService = new JsonFileService("inventory.json");
        initializeInventory();
    }

    /**
     * Loads the saved product list from JSON.
     * @throws FileServiceException if the file cannot be loaded
     */
    public void initializeInventory() throws FileServiceException {
        products = fileService.readProducts();
        if (products == null) {
            throw new FileServiceException("The inventory file is empty.");
        }
    }

    /**
     * Writes inventory quantities to JSON.
     * @throws FileServiceException if saving fails
     */
    public void saveInventory() throws FileServiceException {
        fileService.writeProducts(products);
    }

    /** @return number of different products */
    public int getProductCount() {
        return products.size();
    }

    /**
     * Returns a new ArrayList, so callers cannot change the inventory's order.
     * The products inside the list are the same product objects.
     * @return a copy of the inventory list
     */
    public ArrayList<SalableProduct> getProducts() {
        return new ArrayList<SalableProduct>(products);
    }

    /**
     * Finds a product by its current position in the inventory.
     * @param index zero-based product index
     * @return product or null if the index is invalid
     */
    public SalableProduct getProduct(int index) {
        if (index < 0 || index >= products.size()) {
            return null;
        }
        return products.get(index);
    }

    /**
     * Reduces a product's quantity and saves the change.
     * @param index zero-based product index
     * @return true if a product was in stock
     * @throws FileServiceException if saving fails
     */
    public boolean removeProduct(int index) throws FileServiceException {
        SalableProduct product = getProduct(index);
        if (product == null || product.getQuantity() <= 0) {
            return false;
        }
        product.setQuantity(product.getQuantity() - 1);
        try {
            saveInventory();
        } catch (FileServiceException e) {
            product.setQuantity(product.getQuantity() + 1);
            throw e;
        }
        return true;
    }

    /**
     * Returns a product to inventory and saves the change.
     * @param index zero-based product index
     * @return true if the product exists
     * @throws FileServiceException if saving fails
     */
    public boolean addProduct(int index) throws FileServiceException {
        SalableProduct product = getProduct(index);
        if (product == null) {
            return false;
        }
        product.setQuantity(product.getQuantity() + 1);
        try {
            saveInventory();
        } catch (FileServiceException e) {
            product.setQuantity(product.getQuantity() - 1);
            throw e;
        }
        return true;
    }

    /** Sorts alphabetically by name, and by price if names match. */
    public void sortProductsAscending() {
        Collections.sort(products);
    }

    /** Sorts by name and then price, both in descending order. */
    public void sortProductsDescending() {
        Collections.sort(products);
        Collections.reverse(products);
    }

    /** Shows the inventory in its current sort order. */
    public void displayInventory() {
        System.out.println("\n=== AVAILABLE INVENTORY ===");
        for (int i = 0; i < products.size(); i++) {
            SalableProduct product = products.get(i);
            System.out.printf("%d. [%s] %s | $%.2f | Stock: %d%n",
                    i + 1, product.getItemType(), product.getName(),
                    product.getPrice(), product.getQuantity());
            System.out.println("   " + product.getDescription());
            System.out.println("   " + product.getItemDetails());
        }
    }
}
