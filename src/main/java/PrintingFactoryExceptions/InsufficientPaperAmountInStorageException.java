package PrintingFactoryExceptions;

public class InsufficientPaperAmountInStorageException extends Exception {
    public InsufficientPaperAmountInStorageException(String message) {
        super(message);
    }
}
