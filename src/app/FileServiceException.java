package app;

/** A custom exception for problems reading or saving inventory files. */
public class FileServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    /** @param message a description of the file error */
    public FileServiceException(String message) {
        super(message);
    }
}
