package app;

import java.util.ArrayList;

/** Defines the file operations for the store inventory. */
public interface FileService {

    /**
     * Reads the product inventory from a JSON file.
     * @return all products in the inventory
     * @throws FileServiceException when reading the file fails
     */
    ArrayList<SalableProduct> readProducts() throws FileServiceException;

    /**
     * Saves the product inventory to a JSON file.
     * @param products products to save
     * @throws FileServiceException when saving the file fails
     */
    void writeProducts(ArrayList<SalableProduct> products) throws FileServiceException;
}
