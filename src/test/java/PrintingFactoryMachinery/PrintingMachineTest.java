package PrintingFactoryMachinery;

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

    @BeforeEach
    public void setUp() {
        printingMachine1 = new PrintingMachine(500, true, 2);
        book1 = new Book("Harry Potter", 300, PageSize.A2, "JK Rowling");
    }

    @Test
    public void LoadPaperIntoMachineNegativePaperTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            printingMachine1.loadPaperIntoMachine(-1);
        });
    }
    @Test
    public void LoadPaperIntoMachinePositivePaperTest(){
        assertDoesNotThrow(() -> {
            printingMachine1.loadPaperIntoMachine(1);
        });
    }
    @Test
    public void LoadPaperIntoMachineOverloadTest(){
        assertThrows(NoSpaceForMorePaperException.class, () -> {
            printingMachine1.loadPaperIntoMachine(501);
        });
    }
    @Test
    public void LoadPaperIntoMachineNoOverloadTest(){
        assertDoesNotThrow(() -> {
            printingMachine1.loadPaperIntoMachine(500);
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
            printingMachine1.loadPaperIntoMachine(500);
        } catch (NoSpaceForMorePaperException e) {
            throw new RuntimeException(e);
        }
        assertDoesNotThrow(() ->{
            printingMachine1.printPublication(book1, PaperType.NORMAL);
        });
    }

    @Test
    public void PrintPublicationStopToRefillDuringPrintTest(){
        try {
            printingMachine1.loadPaperIntoMachine(500);
        } catch (NoSpaceForMorePaperException e) {
            throw new RuntimeException(e);
        }
        book1.setNumberOfPages(1000);

        try {
            assertTrue(printingMachine1.printPublication(book1, PaperType.NORMAL));
        } catch (NoPaperInMachineException e) {
            throw new RuntimeException(e);
        }
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