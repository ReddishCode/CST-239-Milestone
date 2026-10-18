package app;

/**
 * Represents an item sold at the Arena Supply Store.
 * Common product information is stored here and inherited by all product types.
 */
public class SalableProduct {
    private String name;
    private String description;
    private double price;
    private int quantity;

    /**
     * Creates a product with the supplied information and available stock.
     *
     * @param name product name; cannot be blank
     * @param description product description; cannot be blank
     * @param price item price in dollars; must be finite and nonnegative
     * @param quantity starting inventory quantity; must be nonnegative
     * @throws IllegalArgumentException if a supplied value is not valid
     */
    public SalableProduct(String name, String description, double price, int quantity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be blank.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be blank.");
        }
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Product price must be a nonnegative number.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative.");
        }
        this.name = name.trim();
        this.description = description.trim();
        this.price = price;
        this.quantity = quantity;
    }

    /** Returns the product name.
     * 
     * @return product name
     */
    public String getName() {
        return name;
    }

    /** Returns the product description.
     * 
     * @return product description
     */
    public String getDescription() {
        return description;
    }

    /** Returns the price of one item.
     * 
     * @return product price in dollars
     */
    public double getPrice() {
        return price;
    }

    /** Returns the inventory stock level.
     * 
     * @return available stock quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Changes available stock to a nonnegative quantity.
     *
     * @param quantity updated stock quantity
     * @throws IllegalArgumentException if quantity is negative
     */
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    /** Returns a category name, specialized by subclasses.
     * 
     * @return display name of this product category
     */
    public String getItemType() {
        return "General";
    }

    /**
     * Allows derived classes to describe their specialized game attributes.
     *
     * @return an empty string for a general product
     */
    public String getItemDetails() {
        return "";
    }
}
