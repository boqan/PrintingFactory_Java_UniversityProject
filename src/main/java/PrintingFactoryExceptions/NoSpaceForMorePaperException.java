package PrintingFactoryExceptions;

public class NoSpaceForMorePaperException extends Exception {

    public NoSpaceForMorePaperException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "NoSpaceForMorePaperException{} " + super.toString();
    }
}
