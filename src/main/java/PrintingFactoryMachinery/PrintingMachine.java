package PrintingFactoryMachinery;

import Interfaces.Generatable;
import PrintingFactoryDriver.PrintingFactory;
import PrintingFactoryExceptions.InsufficientPaperAmountInStorageException;
import PrintingFactoryExceptions.NoPaperInMachineException;
import PrintingFactoryExceptions.NoSpaceForMorePaperException;
import PrintingFactoryProducts.PaperType;
import PrintingFactoryProducts.Publication;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class PrintingMachine implements Comparable<PrintingMachine>, Serializable, Generatable {
    private String id;

    private final int maximumPaperCapacity;

    private int currentPaperCapacity;

    private final int printingSpeed;

    private boolean printsColour;

    private PrintingFactory factory;

    public PrintingMachine(int maximumPaperCapacity, boolean printsColour, int printingSpeed, PrintingFactory factory){
        this.id = generateID();
        this.maximumPaperCapacity = maximumPaperCapacity;
        this.printsColour = printsColour;
        this.printingSpeed = printingSpeed;
        this.currentPaperCapacity = 0;
        this.factory = factory;
    }

    public String generateID(){
        Random random = new Random();
        long randomNumber = (long) (random.nextDouble() * 1_000_000L);
        return "MachineID" + String.format("%04d", randomNumber);
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

    public void loadPaperIntoMachine(int numberOfPages, PaperType paperType, Map<PaperType,Integer> paperInventory)
            throws NoSpaceForMorePaperException, InsufficientPaperAmountInStorageException {
        if(numberOfPages < 0){
            throw new IllegalArgumentException("Number of pages cannot be negative");
        }
        if(this.currentPaperCapacity + numberOfPages > this.maximumPaperCapacity){
            throw new NoSpaceForMorePaperException("No space for more paper");
        }

        int currentInventory = paperInventory.get(paperType);
        if(currentInventory < numberOfPages){
            throw new InsufficientPaperAmountInStorageException("Not enough paper in inventory. Current inventory: " + currentInventory + " pages.");
        }

        this.currentPaperCapacity += numberOfPages;
    }


//    public boolean printPublication(Publication publication, PaperType paperType) throws NoPaperInMachineException {
//        System.out.println("Printing " + publication.getTitle() + " on " + paperType + " paper."
//                + " Estimated time for printing: " + printingTimeEstimation(publication) + " hours.");
//        System.out.println(this.printsColour ? "Printing in colour." : "Printing in black and white.");
//
//        if(this.currentPaperCapacity == 0) {
//            throw new NoPaperInMachineException("No paper in the machine. Maximum paper capacity: " + this.maximumPaperCapacity + " pages.");
//        }
//        int remainingPagesToPrint = publication.getNumberOfPages();
//
//        while(remainingPagesToPrint > 0)
//        {
//            if (this.currentPaperCapacity >= remainingPagesToPrint)
//            {
//                System.out.println("Printing " + remainingPagesToPrint + " pages");
//                this.currentPaperCapacity -= publication.getNumberOfPages();
//                return true;
//            }
//            else if (this.currentPaperCapacity > 0)
//            {
//                System.out.println("Printing " + this.currentPaperCapacity + " pages...");
//                remainingPagesToPrint -= this.currentPaperCapacity;
//                this.currentPaperCapacity = 0;
//            }
//            if(remainingPagesToPrint > 0)
//            {
//                System.out.println("The machine ran out of paper. Please refill the machine.");
//                System.out.println("Remaining pages to print: " + remainingPagesToPrint);
//                System.out.println("Refilling machine...");
//
//                try {
//                    // math.min insures that we do not go over the maximum paper capacity.
//                    int refillAmount = Math.min(remainingPagesToPrint, this.maximumPaperCapacity - this.currentPaperCapacity);
//                    loadPaperIntoMachine(refillAmount, paperType, factory.getPaperInventory());
//                } catch (NoSpaceForMorePaperException | InsufficientPaperAmountInStorageException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//        System.out.println("Printing process completed!");
//        return true;
//    }

    // big method broken down into three separate methods for better readability and SOLID adherence
    public boolean printPublication(Publication publication, PaperType paperType) throws NoPaperInMachineException {
        System.out.println("Printing " + publication.getTitle() + " on " + paperType + " paper."
                + " Estimated time for printing: " + printingTimeEstimation(publication) + " hours.");
        System.out.println(this.printsColour ? "Printing in colour." : "Printing in black and white.");

        if (this.currentPaperCapacity == 0) {
            throw new NoPaperInMachineException("No paper in the machine. Maximum paper capacity: " + this.maximumPaperCapacity + " pages.");
        }
        int remainingPagesToPrint = publication.getNumberOfPages();

        while (remainingPagesToPrint > 0) {
            remainingPagesToPrint = printPages(remainingPagesToPrint, paperType, publication);
            if (remainingPagesToPrint > 0) {
                refillMachine(remainingPagesToPrint, paperType);
            }
        }
        System.out.println("Printing process completed!");
        return true;
    }

    public int printPages(int remainingPagesToPrint, PaperType paperType, Publication publication) {
        if (this.currentPaperCapacity >= remainingPagesToPrint) {
            System.out.println("Printing " + remainingPagesToPrint + " pages");
            this.currentPaperCapacity -= publication.getNumberOfPages();
            return 0;
        } else if (this.currentPaperCapacity > 0) {
            System.out.println("Printing " + this.currentPaperCapacity + " pages...");
            remainingPagesToPrint -= this.currentPaperCapacity;
            this.currentPaperCapacity = 0;
        }
        return remainingPagesToPrint;
    }

    public void refillMachine(int remainingPagesToPrint, PaperType paperType) {
        System.out.println("The machine ran out of paper. Please refill the machine.");
        System.out.println("Remaining pages to print: " + remainingPagesToPrint);
        System.out.println("Refilling machine...");

        try {
            // math.min insures that we do not go over the maximum paper capacity.
            int refillAmount = Math.min(remainingPagesToPrint, this.maximumPaperCapacity - this.currentPaperCapacity);
            //this.maximumPaperCapacity - this.currentPaperCapacity);
            loadPaperIntoMachine(refillAmount, paperType, factory.getPaperInventory());
        } catch (NoSpaceForMorePaperException | InsufficientPaperAmountInStorageException e) {
            throw new RuntimeException(e);
        }
    }


    public double printingTimeEstimation(Publication publication){
        double est = ((double)publication.getNumberOfPages() / (double)this.printingSpeed) / 60;
        // creates a new bigdecimal object with the value of est, rounds it to 1 decimal place and converts it back to a double.
        double roundedEst = BigDecimal.valueOf(est).setScale(1, RoundingMode.HALF_UP).doubleValue();
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
    public int compareTo(PrintingMachine other) {
        return this.id.compareTo(other.id);
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
