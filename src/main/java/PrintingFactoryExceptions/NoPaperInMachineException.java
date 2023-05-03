package PrintingFactoryExceptions;

public class NoPaperInMachineException extends Exception {
    public NoPaperInMachineException(String message) {
        super(message);
    }
}
