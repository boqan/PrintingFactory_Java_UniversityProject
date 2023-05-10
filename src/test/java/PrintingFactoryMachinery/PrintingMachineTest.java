package PrintingFactoryMachinery;

import PrintingFactoryDriver.PrintingFactory;
import PrintingFactoryDriver.PrintingFactoryAccounting;
import PrintingFactoryExceptions.InsufficientPaperAmountInStorageException;
import PrintingFactoryExceptions.NegativePaperAmountException;
import PrintingFactoryExceptions.NoPaperInMachineException;
import PrintingFactoryExceptions.NoSpaceForMorePaperException;
import PrintingFactoryProducts.Book;
import PrintingFactoryProducts.PageSize;
import PrintingFactoryProducts.PaperType;
import PrintingFactoryProducts.Publication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintingMachineTest {

    PrintingMachine printingMachine1;
    Book book1;

    PrintingFactory printingFactory1;

    PrintingFactoryAccounting accounting1;

    @BeforeEach
    public void setUp() {
        accounting1 = new PrintingFactoryAccounting(0.1, "EasyConsult");
        printingFactory1 = new PrintingFactory("printingFactory1", accounting1);
        try {
            printingFactory1.addPaperTypeAmountToInventory(PaperType.GLOSSY, 1000);
        } catch (NegativePaperAmountException e) {
            throw new RuntimeException(e);
        }
        printingMachine1 = new PrintingMachine(2000, true, 2, printingFactory1);
        book1 = new Book("Harry Potter", 300, PageSize.A2, "JK Rowling");

    }

    @Test
    public void LoadPaperIntoMachineNegativePaperTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            printingMachine1.loadPaperIntoMachine(-1, PaperType.GLOSSY, printingFactory1.getPaperInventory());
        });
    }
    @Test
    public void LoadPaperIntoMachinePositivePaperTest(){
        assertDoesNotThrow(() -> {
            printingMachine1.loadPaperIntoMachine(1, PaperType.GLOSSY, printingFactory1.getPaperInventory());
        });
    }
    @Test
    public void LoadPaperIntoMachineOverloadTest(){
        assertThrows(NoSpaceForMorePaperException.class, () -> {
            printingMachine1.loadPaperIntoMachine(2001, PaperType.GLOSSY, printingFactory1.getPaperInventory());
        });
    }
    @Test
    public void LoadPaperIntoMachineNoOverloadTest(){
        assertDoesNotThrow(() -> {
            printingMachine1.loadPaperIntoMachine(500, PaperType.GLOSSY, printingFactory1.getPaperInventory());
        });
    }

    @Test
    public void LoadPaperIntoMachineInsufficientPaperAmountInStorageExceptionTest(){
        assertThrows(InsufficientPaperAmountInStorageException.class, () -> {
            printingMachine1.loadPaperIntoMachine(1001, PaperType.GLOSSY, printingFactory1.getPaperInventory());
        });
    }

    @Test
    public void PrintPublicationNoPaperTest(){
        Book book2 = new Book("Harry Potter", 300, PageSize.A2, "JK Rowling");
        assertThrows(NoPaperInMachineException.class, () -> {
            printingMachine1.printPublication(book2, PaperType.NORMAL);
        });
    }
    @Test
    public void PrintPublicationEnoughPaperTest(){
        try {
            printingMachine1.loadPaperIntoMachine(500, PaperType.GLOSSY, printingFactory1.getPaperInventory());
        } catch (NoSpaceForMorePaperException | InsufficientPaperAmountInStorageException e) {
            throw new RuntimeException(e);
        }
        assertDoesNotThrow(() ->{
            printingMachine1.printPublication(book1, PaperType.GLOSSY);
        });
    }

    @Test
    public void PrintPublicationStopToRefillDuringPrintTest(){
        try {
            printingMachine1.loadPaperIntoMachine(250, PaperType.GLOSSY, printingFactory1.getPaperInventory());
        } catch (NoSpaceForMorePaperException | InsufficientPaperAmountInStorageException e) {
            throw new RuntimeException(e);
        }
        book1.setNumberOfPages(1000);

        try {
            assertTrue(printingMachine1.printPublication(book1, PaperType.GLOSSY));
        } catch (NoPaperInMachineException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void PrintPublicationNotEnoughPaperInInventoryTest() {
        printingFactory1.getPaperInventory().put(PaperType.GLOSSY, 500); // Reduce the inventory to 500 pages
        book1.setNumberOfPages(600);

        try {
            printingMachine1.loadPaperIntoMachine(500, PaperType.GLOSSY, printingFactory1.getPaperInventory());
        } catch (NoSpaceForMorePaperException | InsufficientPaperAmountInStorageException e) {
            throw new RuntimeException(e);
        }

        // Expecting a NegativePaperAmountException due to not enough paper in the inventory for the inbuilt refill
        // but it is handled in the method and is throwing a runtimexception, so we catch that
        Exception exception = assertThrows(RuntimeException.class, () -> printingMachine1.printPublication(book1, PaperType.GLOSSY));
        assertTrue(exception.getCause() instanceof InsufficientPaperAmountInStorageException);
    }


    @Test
    public void PrintingTimeEstimationTest(){
        double expected = 2.5;
        assertEquals(expected, printingMachine1.printingTimeEstimation(book1));
    }

    @Test
    public void PrintingTimeEstimationTestHighNumberOfPages(){
        Book book2 = new Book("Harry Potter", 5215, PageSize.A2, "JK Rowling");
        double expected = 43.5;
        assertEquals(expected, printingMachine1.printingTimeEstimation(book2));
    }

}