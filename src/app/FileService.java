package app;

/** Defines the two file operations used by the inventory manager. */
public interface FileService {

    /** @return products loaded from the JSON file */
    SalableProduct[] readProducts() throws FileServiceException;

    /** @param products products to save to the JSON file */
    void writeProducts(SalableProduct[] products) throws FileServiceException;
}
