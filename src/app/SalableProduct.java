package app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/** A product that can be purchased in the arena store. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Weapon.class, name = "weapon"),
    @JsonSubTypes.Type(value = Armor.class, name = "armor"),
    @JsonSubTypes.Type(value = Health.class, name = "health")
})
public class SalableProduct implements Comparable<SalableProduct> {

    private String name;
    private String description;
    private double price;
    private int quantity;

    /** Empty constructor needed when Jackson reads JSON. */
    public SalableProduct() { }

    /** Creates a product with its shared information. */
    public SalableProduct(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    /** @return product name */
    public String getName() { return name; }

    /** @param name product name */
    public void setName(String name) { this.name = name; }

    /** @return product description */
    public String getDescription() { return description; }

    /** @param description product description */
    public void setDescription(String description) { this.description = description; }

    /** @return product price */
    public double getPrice() { return price; }

    /** @param price product price */
    public void setPrice(double price) { this.price = price; }

    /** @return quantity in stock */
    public int getQuantity() { return quantity; }

    /** @param quantity new stock quantity */
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /** @return product category for display */
    @JsonIgnore
    public String getItemType() { return "Product"; }

    /** @return special item information for display */
    @JsonIgnore
    public String getItemDetails() { return ""; }

    /** Compares product names, then prices when names match. */
    @Override
    public int compareTo(SalableProduct other) {
        int nameResult = name.compareToIgnoreCase(other.getName());
        if (nameResult == 0) {
            return Double.compare(price, other.getPrice());
        }
        return nameResult;
    }
}
