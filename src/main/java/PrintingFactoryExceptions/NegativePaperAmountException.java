package PrintingFactoryExceptions;

public class NegativePaperAmountException extends Throwable {
    public NegativePaperAmountException(String message) {
        super(message);
    }
}
