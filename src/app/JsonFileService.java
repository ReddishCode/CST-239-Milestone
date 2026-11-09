package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Reads and writes an ArrayList of products using Jackson JSON. */
public class JsonFileService implements FileService {

    private String filename;
    private ObjectMapper mapper = new ObjectMapper();

    /** @param filename the JSON inventory filename */
    public JsonFileService(String filename) {
        this.filename = filename;
    }

    /**
     * Loads the JSON inventory as an ArrayList of products.
     * TypeReference tells Jackson that each item is a SalableProduct.
     * @return loaded inventory
     * @throws FileServiceException when the file cannot be read
     */
    @Override
    public ArrayList<SalableProduct> readProducts() throws FileServiceException {
        try {
            return mapper.readValue(new File(filename),
                    new TypeReference<ArrayList<SalableProduct>>() { });
        } catch (IOException e) {
            throw new FileServiceException("Could not read " + filename + ": " + e.getMessage());
        }
    }

    /**
     * Saves the products to the JSON inventory file.
     * @param products the products with their current stock quantities
     * @throws FileServiceException when the file cannot be saved
     */
    @Override
    public void writeProducts(ArrayList<SalableProduct> products) throws FileServiceException {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(filename), products);
        } catch (IOException e) {
            throw new FileServiceException("Could not save " + filename + ": " + e.getMessage());
        }
    }
}
