package app;

/** Manages product stock and loads/saves it with a file service. */
public class InventoryManager {
    private SalableProduct[] products;
    private FileService fileService;

    /** Creates the inventory manager and reads inventory.json. */
    public InventoryManager() throws FileServiceException {
        fileService = new JsonFileService("inventory.json");
        initializeInventory();
    }

    /** Loads the store's products from the JSON file. */
    public void initializeInventory() throws FileServiceException {
        products = fileService.readProducts();
        if (products == null) {
            throw new FileServiceException("The inventory file is empty.");
        }
    }

    /** Saves the current quantities and product information as JSON. */
    public void saveInventory() throws FileServiceException {
        fileService.writeProducts(products);
    }

    /** @return number of different products in the store */
    public int getProductCount() { return products.length; }

    /** @return a copy of the product list for the cart */
    public SalableProduct[] getProducts() { return products.clone(); }

    /** @param index the product's position in the inventory
     *  @return product at the position, or null if invalid */
    public SalableProduct getProduct(int index) {
        if (index < 0 || index >= products.length) {
            return null;
        }
        return products[index];
    }

    /** Takes one item out of stock and saves the new quantity.
     *  @return true if the item was in stock */
    public boolean removeProduct(int index) throws FileServiceException {
        SalableProduct product = getProduct(index);
        if (product == null || product.getQuantity() <= 0) {
            return false;
        }
        product.setQuantity(product.getQuantity() - 1);
        try {
            saveInventory();
        } catch (FileServiceException e) {
            // Put the quantity back if saving did not work.
            product.setQuantity(product.getQuantity() + 1);
            throw e;
        }
        return true;
    }

    /** Returns one item to stock and saves the new quantity.
     *  @return true if the item was returned to inventory */
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

    /** Shows all products and their details in the console. */
    public void displayInventory() {
        System.out.println("\n=== AVAILABLE INVENTORY ===");
        for (int i = 0; i < products.length; i++) {
            SalableProduct product = products[i];
            System.out.printf("%d. [%s] %s | $%.2f | Stock: %d%n",
                    i + 1, product.getItemType(), product.getName(),
                    product.getPrice(), product.getQuantity());
            System.out.println("   " + product.getDescription());
            System.out.println("   " + product.getItemDetails());
        }
    }
}
