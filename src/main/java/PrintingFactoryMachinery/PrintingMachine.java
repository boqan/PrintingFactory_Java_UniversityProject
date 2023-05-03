package PrintingFactoryMachinery;

import PrintingFactoryExceptions.NoPaperInMachineException;
import PrintingFactoryExceptions.NoSpaceForMorePaperException;
import PrintingFactoryProducts.PaperType;
import PrintingFactoryProducts.Publication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaximumPaperCapacity() {
        return maximumPaperCapacity;
    }

    public int getCurrentPaperCapacity() {
        return currentPaperCapacity;
    }

    public void setCurrentPaperCapacity(int currentPaperCapacity) {
        this.currentPaperCapacity = currentPaperCapacity;
    }

    public int getPrintingSpeed() {
        return printingSpeed;
    }

    public boolean isPrintsColour() {
        return printsColour;
    }

    public void setPrintsColour(boolean printsColour) {
        this.printsColour = printsColour;
    }

    public void loadPaperIntoMachine(int numberOfPages) throws NoSpaceForMorePaperException {
        if(this.currentPaperCapacity + numberOfPages <= this.maximumPaperCapacity){
            this.currentPaperCapacity += numberOfPages;
        }
        else{
            throw new NoSpaceForMorePaperException("No space for more paper");
        }
    }

    // method finished, file writing needs to be implemented externally.
    public boolean printPublication(Publication publication, PaperType paperType) throws NoPaperInMachineException {
        System.out.println("Printing " + publication.getTitle() + " on " + paperType + " paper."
                + " Estimated time for printing: " + printingTimeEstimation(publication));
        System.out.println(this.printsColour ? "Printing in colour" : "Printing in black and white");

        if(this.currentPaperCapacity == 0) {
            throw new NoPaperInMachineException("No paper in the machine. Maximum paper capacity: " + this.maximumPaperCapacity + " pages.");
        }
        int remainingPagesToPrint = publication.getNumberOfPages();

        while(remainingPagesToPrint > 0)
        {
            if (this.currentPaperCapacity >= remainingPagesToPrint)
            {
                System.out.println("Printing " + remainingPagesToPrint + " pages");
                this.currentPaperCapacity -= publication.getNumberOfPages();
                return true;
            }
            else if (this.currentPaperCapacity > 0)
            {
                System.out.println("Printing " + this.currentPaperCapacity + " pages...");
                remainingPagesToPrint -= this.currentPaperCapacity;
                this.currentPaperCapacity = 0;
            }
            if(remainingPagesToPrint > 0)
            {
                System.out.println("The machine ran out of paper. Please refill the machine.");
                System.out.println("Remaining pages to print: " + remainingPagesToPrint);
                System.out.println("Refilling machine...");

                try {
                    loadPaperIntoMachine(this.maximumPaperCapacity - this.currentPaperCapacity);
                } catch (NoSpaceForMorePaperException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("Printing process completed!");
        return true;
    }

    public double printingTimeEstimation(Publication publication){
        double est = ((double)publication.getNumberOfPages() / (double)this.printingSpeed) / 60;

        BigDecimal value = BigDecimal.valueOf(est);
        value.setScale(1, RoundingMode.HALF_UP);
        double roundedEst = value.doubleValue();
        return roundedEst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintingMachine that = (PrintingMachine) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PrintingMachine{" +
                "id='" + id + '\'' +
                ", maximumPaperCapacity=" + maximumPaperCapacity +
                ", currentPaperCapacity=" + currentPaperCapacity +
                ", printingSpeed=" + printingSpeed +
                ", printsColour=" + printsColour +
                '}';
    }


}
