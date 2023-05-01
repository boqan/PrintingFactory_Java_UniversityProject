package PrintingFactoryMachinery;

import PrintingFactoryExceptions.NoSpaceForMorePaperException;
import PrintingFactoryProducts.PaperType;
import PrintingFactoryProducts.Publication;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PrintingMachine {
    private String id;

    private final int maximumPaperCapacity;

    private int currentPaperCapacity;

    private final int printingSpeed;

    private boolean printsColour;

    public PrintingMachine(String id, int maximumPaperCapacity, boolean printsColour, int printingSpeed) {
        this.id = id;
        this.maximumPaperCapacity = maximumPaperCapacity;
        this.printsColour = printsColour;
        this.printingSpeed = printingSpeed;
        this.currentPaperCapacity = 0;
    }

    public void loadPaperIntoMachine(int numberOfPages) throws NoSpaceForMorePaperException {
        if(currentPaperCapacity + numberOfPages <= maximumPaperCapacity){
            currentPaperCapacity += numberOfPages;
        }
        else{
            throw new NoSpaceForMorePaperException("No space for more paper");
        }
    }

    // to be finished with file writing
    public boolean printPublication(Publication publication, PaperType paperType){
        System.out.println("Printing " + publication.getTitle() + " on " + paperType + " paper." +
                " Estimated time for printing: " + printingTimeEstimation(publication));
        System.out.println(this.printsColour ? "Printing in colour" : "Printing in black and white");
        if(currentPaperCapacity >= publication.getNumberOfPages()){
            currentPaperCapacity -= publication.getNumberOfPages();
            return true;
        }
        else{
            return false;
        }
    }

    public double printingTimeEstimation(Publication publication){
        double est = (publication.getNumberOfPages() / this.printingSpeed) / 60;

        BigDecimal value = BigDecimal.valueOf(est);
        value.setScale(1, RoundingMode.HALF_UP);
        double roundedEst = value.doubleValue();
        return roundedEst;
    }
}
