package PrintingFactoryMachinery;

import PrintingFactoryDriver.Paper;
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
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PrintingMachineTest {

    PrintingMachine printingMachine1;
    Book book1;
    Paper paper1;
    PrintingFactory printingFactory1;
    PrintingFactory printingFactoryMock;

    PrintingFactoryAccounting accounting1;

    @BeforeEach
    public void setUp() {
        accounting1 = new PrintingFactoryAccounting(0.1, "EasyConsult");
        printingFactory1 = new PrintingFactory("printingFactory1", accounting1);
        paper1 = new Paper(PageSize.A2, PaperType.GLOSSY);
        try {
            printingFactory1.addPaperAmountToInventory(paper1, 1000);
        } catch (NegativePaperAmountException e) {
            throw new RuntimeException(e);
        }
        printingMachine1 = new PrintingMachine(2000, true, 2, printingFactory1);
        book1 = new Book("Harry Potter", 300, PageSize.A2, "JK Rowling");
        // Mock the PrintingFactory
        printingFactoryMock = Mockito.mock(PrintingFactory.class);

        //to be fixed along with the mock tests
//        when(printingFactoryMock.getPaperInventory()).thenReturn(new HashMap<PaperType, Integer>() {{
//            put(PaperType.GLOSSY, 1000);
//        }});

    }

    @Test
    public void LoadPaperIntoMachineNegativePaperTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            printingMachine1.loadPaperIntoMachine(-1, paper1, printingFactory1.getPaperInventory());
        });
    }
    @Test
    public void LoadPaperIntoMachinePositivePaperTest(){
        assertDoesNotThrow(() -> {
            printingMachine1.loadPaperIntoMachine(1, paper1, printingFactory1.getPaperInventory());
        });
    }
    @Test
    public void LoadPaperIntoMachineOverloadTest(){
        assertThrows(NoSpaceForMorePaperException.class, () -> {
            printingMachine1.loadPaperIntoMachine(2001, paper1, printingFactory1.getPaperInventory());
        });
    }
    @Test
    public void LoadPaperIntoMachineNoOverloadTest(){
        assertDoesNotThrow(() -> {
            printingMachine1.loadPaperIntoMachine(500, paper1, printingFactory1.getPaperInventory());
        });
    }

    @Test
    public void LoadPaperIntoMachineInsufficientPaperAmountInStorageExceptionTest(){
        assertThrows(InsufficientPaperAmountInStorageException.class, () -> {
            printingMachine1.loadPaperIntoMachine(1001, paper1, printingFactory1.getPaperInventory());
        });
    }

    @Test
    public void printPagesNoPaperTest() {
        Book book2 = new Book("Harry Potter", 300, PageSize.A2, "JK Rowling");
        assertThrows(NoPaperInMachineException.class, () -> {
            printingMachine1.printPages(book2.getNumberOfPages(), paper1, book2);
        });
    }

    @Test
    public void printPagesEnoughPaperTest() {
        try {
            printingMachine1.loadPaperIntoMachine(500, paper1, printingFactory1.getPaperInventory());
        } catch (NoSpaceForMorePaperException | InsufficientPaperAmountInStorageException e) {
            throw new RuntimeException(e);
        }
        assertDoesNotThrow(() -> {
            printingMachine1.printPages(book1.getNumberOfPages(), paper1, book1);
        });
    }

    @Test
    public void refillMachineDuringPrintTest() {
        try {
            printingMachine1.loadPaperIntoMachine(250, paper1, printingFactory1.getPaperInventory());
        } catch (NoSpaceForMorePaperException | InsufficientPaperAmountInStorageException e) {
            throw new RuntimeException(e);
        }
        book1.setNumberOfPages(1000);
        printingMachine1.refillMachine(book1.getNumberOfPages(), paper1);

        assertEquals(printingMachine1.getCurrentPaperCapacity(), printingMachine1.getMaximumPaperCapacity());
    }

    @Test
    public void refillMachineNotEnoughPaperInInventoryTest() {
        printingFactory1.getPaperInventory().put(paper1, 500); // Reduce the inventory to 500 pages
        book1.setNumberOfPages(600);

        try {
            printingMachine1.loadPaperIntoMachine(500, paper1, printingFactory1.getPaperInventory());
        } catch (NoSpaceForMorePaperException | InsufficientPaperAmountInStorageException e) {
            throw new RuntimeException(e);
        }

        // Expecting a NegativePaperAmountException due to not enough paper in the inventory for the inbuilt refill
        // but it is handled in the method and is throwing a runtimexception, so we catch that
        Exception exception = assertThrows(RuntimeException.class, () -> printingMachine1.refillMachine(book1.getNumberOfPages(), paper1));
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

//    @Test
//    public void LoadPaperIntoMachinePositivePaperTest(){
//        doNothing().when(printingFactory1).removePaperFromInventory(PaperType.GLOSSY, 1);
//        assertDoesNotThrow(() -> {
//            printingMachine1.loadPaperIntoMachine(1, PaperType.GLOSSY, printingFactory1.getPaperInventory());
//        });
//        verify(printingFactory1, times(1)).removePaperFromInventory(PaperType.GLOSSY, 1);
//    }
//
//    @Test
//    public void LoadPaperIntoMachineInsufficientPaperAmountInStorageExceptionTest(){
//        doThrow(new InsufficientPaperAmountInStorageException()).when(printingFactory1).removePaperFromInventory(PaperType.GLOSSY, 1001);
//        assertThrows(InsufficientPaperAmountInStorageException.class, () -> {
//            printingMachine1.loadPaperIntoMachine(1001, PaperType.GLOSSY, printingFactory1.getPaperInventory());
//        });
//    }
//
//    @Test
//    public void printPagesEnoughPaperTestMock() {
//        doNothing().when(printingFactory1).removePaperFromInventory(PaperType.GLOSSY, book1.getNumberOfPages());
//        assertDoesNotThrow(() -> {
//            printingMachine1.printPages(book1.getNumberOfPages(), PaperType.GLOSSY, book1);
//        });
//        verify(printingFactory1, times(1)).removePaperFromInventory(PaperType.GLOSSY, book1.getNumberOfPages());
//    }
//
//    @Test
//    public void refillMachineDuringPrintTestMock() {
//        doNothing().when(printingFactory1).removePaperFromInventory(PaperType.GLOSSY, book1.getNumberOfPages());
//        printingMachine1.refillMachine(book1.getNumberOfPages(), PaperType.GLOSSY);
//
//        assertEquals(printingMachine1.getCurrentPaperCapacity(), printingMachine1.getMaximumPaperCapacity());
//        verify(printingFactory1, times(1)).removePaperFromInventory(PaperType.GLOSSY, book1.getNumberOfPages());
//    }
//
//    @Test
//    public void refillMachineNotEnoughPaperInInventoryTestMock() {
//        doThrow(new InsufficientPaperAmountInStorageException()).when(printingFactory1).removePaperFromInventory(PaperType.GLOSSY, book1.getNumberOfPages());
//        Exception exception = assertThrows(RuntimeException.class, () -> printingMachine1.refillMachine(book1.getNumberOfPages(), PaperType.GLOSSY));
//        assertTrue(exception.getCause() instanceof InsufficientPaperAmountInStorageException);
//        verify(printingFactory1, times(1)).removePaperFromInventory(PaperType.GLOSSY, book1.getNumberOfPages());
//    }


}