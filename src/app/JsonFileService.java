package app;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Reads and writes products using Jackson, like the Topic 4-2 example. */
public class JsonFileService implements FileService {

    private String filename;
    private ObjectMapper mapper = new ObjectMapper();

    /** @param filename the JSON inventory filename */
    public JsonFileService(String filename) {
        this.filename = filename;
    }

    /** Reads the array of products from inventory.json. */
    @Override
    public SalableProduct[] readProducts() throws FileServiceException {
        try {
            return mapper.readValue(new File(filename), SalableProduct[].class);
        } catch (IOException e) {
            throw new FileServiceException("Could not read " + filename + ": " + e.getMessage());
        }
    }

    /** Writes the array of products into inventory.json. */
    @Override
    public void writeProducts(SalableProduct[] products) throws FileServiceException {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(filename), products);
        } catch (IOException e) {
            throw new FileServiceException("Could not save " + filename + ": " + e.getMessage());
        }
    }
}
