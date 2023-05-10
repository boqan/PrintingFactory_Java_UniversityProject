package PrintingFactoryExceptions;

public class NoSuitableMachineException extends Exception {
    public NoSuitableMachineException(String message) {
        super(message);
    }
}
