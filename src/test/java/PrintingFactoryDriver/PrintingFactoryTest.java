package PrintingFactoryDriver;

import PrintingFactoryEmployees.Employee;
import PrintingFactoryEmployees.Operator;
import PrintingFactoryExceptions.InsufficientPaperAmountInStorageException;
import PrintingFactoryExceptions.NegativePaperAmountException;
import PrintingFactoryExceptions.NoSuitableMachineException;
import PrintingFactoryExceptions.PaperMismatchException;
import PrintingFactoryMachinery.PrintingMachine;
import PrintingFactoryProducts.PageSize;
import PrintingFactoryProducts.PaperType;
import PrintingFactoryProducts.Publication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintingFactoryTest {

    PrintingFactoryAccounting accounting1;

    PrintingFactory printingFactory1;

    @BeforeEach
    void setup(){
        accounting1 = new PrintingFactoryAccounting(0.1, "EasyConsult");
        printingFactory1 = new PrintingFactory("printingFactory", accounting1);
    }

    @Test
    void addEmployee_EmployeeListTest() {
        PrintingMachine printingMachine1 = new PrintingMachine(500, true,2, printingFactory1);
        Operator operator1 = new Operator("operator1", 1000, printingMachine1);
        printingFactory1.addEmployee(operator1);
        assertTrue(printingFactory1.getEmployeeList().contains(operator1));
    }

    @Test
    void addEmployee_EmployeeExpensesTest() {
        PrintingMachine printingMachine1 = new PrintingMachine(500, true,2, printingFactory1);
        Operator operator1 = new Operator("operator1", 1000, printingMachine1);
        printingFactory1.addEmployee(operator1);
        assertEquals(1000, accounting1.getSalaryExpenses());
    }

    @Test
    void addPaperTypeAmountToInventory_TestingPaperInInventory() {
        try {
            Paper glossyA4 = new Paper(PageSize.A4, PaperType.GLOSSY);
            printingFactory1.addPaperAmountToInventory(glossyA4, 100);
        } catch (NegativePaperAmountException e) {
            throw new RuntimeException(e);
        }
        assertEquals(100, printingFactory1.getPaperInventory().get(PaperType.GLOSSY));
    }

    @Test
    void addPaperTypeAmountToInventory_TestingNegativePaperAmountException() {
        Paper glossyA4 = new Paper(PageSize.A4, PaperType.GLOSSY);
        assertThrows(NegativePaperAmountException.class, () -> printingFactory1.addPaperAmountToInventory(glossyA4, 100));
    }

    @Test
    void addPaperTypeAmountToInventory_TestingExpenses() {
        try {
            Paper glossyA4 = new Paper(PageSize.A4, PaperType.GLOSSY);
            printingFactory1.addPaperAmountToInventory(glossyA4, 100);
        } catch (NegativePaperAmountException e) {
            throw new RuntimeException(e);
        }
        assertEquals(50, accounting1.getPaperExpenses());
    }

    @Test
    void removeEmployee_EmployeeListTest() {
        PrintingMachine printingMachine1 = new PrintingMachine(500, true,2, printingFactory1);
        Operator operator1 = new Operator("operator1", 1000, printingMachine1);
        printingFactory1.addEmployee(operator1);
        printingFactory1.removeEmployee(operator1);
        assertFalse(printingFactory1.getEmployeeList().contains(operator1));
    }

    @Test
    void removeEmployee_EmployeeExpensesRemovalTest() {
        PrintingMachine printingMachine1 = new PrintingMachine(500, true,2, printingFactory1);
        Operator operator1 = new Operator("operator1", 1000, printingMachine1);
        printingFactory1.addEmployee(operator1);
        printingFactory1.removeEmployee(operator1);
        assertEquals(0, accounting1.getSalaryExpenses());
    }


    @Test
    void removePaperTypeAmountFromInventory_RemovalTest() {
        Paper glossyA4 = new Paper(PageSize.A4, PaperType.GLOSSY);
        try {
            printingFactory1.addPaperAmountToInventory(glossyA4, 100);
        } catch (NegativePaperAmountException e) {
            throw new RuntimeException(e);
        }
        try {
            printingFactory1.removePaperAmountFromInventory(glossyA4, 50);
        } catch (NegativePaperAmountException e) {
            throw new RuntimeException(e);
        }
        assertEquals(50, printingFactory1.getPaperInventory().get(glossyA4));
    }

    @Test
    void removePaperTypeAmountFromInventory_ExceptionTest() {
        Paper glossyA4 = new Paper(PageSize.A4, PaperType.GLOSSY);
        try {
            printingFactory1.addPaperAmountToInventory(glossyA4, 100);
        } catch (NegativePaperAmountException e) {
            throw new RuntimeException(e);
        }

        assertThrows(NegativePaperAmountException.class,
                () -> printingFactory1.removePaperAmountFromInventory(glossyA4, 150));

        assertThrows(NegativePaperAmountException.class,
                () -> printingFactory1.removePaperAmountFromInventory(glossyA4, -20));
    }


    @Test
    void printingOrder_notEnoughPaper_throwsInsufficientPaperException() {
        int amountOfCopies = 500;
        Paper paper = new Paper(PageSize.A4,PaperType.GLOSSY);
        Publication publication = new Publication("title", 100, PageSize.A2);
        boolean colorPrinting = true;

        assertThrows(InsufficientPaperAmountInStorageException.class, () -> {
            printingFactory1.printingOrder(amountOfCopies, paper, publication, colorPrinting);
        });
    }

    @Test
    void printingOrder_noSuitableMachine_throwsException() {
        int amountOfCopies = 100;
        Paper paper = new Paper(PageSize.A4,PaperType.GLOSSY);
        Publication publication = new Publication("title", 100, PageSize.A2);
        boolean colorPrinting = true;

        try {
            printingFactory1.addPaperAmountToInventory(paper, 1000);
        } catch (NegativePaperAmountException e) {
            throw new RuntimeException(e);
        }
        // Assuming that there are no color printing machines in the list
        assertThrows(NoSuitableMachineException.class, () -> {
            printingFactory1.printingOrder(amountOfCopies, paper, publication, colorPrinting);
        });
    }

    @Test
    void printingOrder_orderSuccessful_returnsTrue() {
        int amountOfCopies = 100;
        Paper paper = new Paper(PageSize.A4,PaperType.GLOSSY);
        Publication publication = new Publication("title", 100, PageSize.A2);
        boolean colorPrinting = true;

        try {
            printingFactory1.addPaperAmountToInventory(paper, 1000);
        } catch (NegativePaperAmountException e) {
            throw new RuntimeException(e);
        }

        printingFactory1.addMachine(new PrintingMachine(500, true,2, printingFactory1));

        // Assuming that there is a suitable machine and enough paper
        try {
            assertTrue(printingFactory1.printingOrder(amountOfCopies, paper, publication, colorPrinting));
        } catch (InsufficientPaperAmountInStorageException | NoSuitableMachineException | PaperMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void printingOrder_orderSuccessful_paperRemoved() {
        int amountOfCopies = 100;
        Paper paper = new Paper(PageSize.A4,PaperType.GLOSSY);
        Publication publication = new Publication("title", 100, PageSize.A2);
        boolean colorPrinting = true;

        try {
            printingFactory1.addPaperAmountToInventory(paper, 1000);
        } catch (NegativePaperAmountException e) {
            throw new RuntimeException(e);
        }

        printingFactory1.addMachine(new PrintingMachine(500, true,2, printingFactory1));

        int initialAmount = printingFactory1.getPaperInventory().get(paper);

        try {
            printingFactory1.printingOrder(amountOfCopies, paper, publication, colorPrinting);
        } catch (InsufficientPaperAmountInStorageException | NoSuitableMachineException | PaperMismatchException e) {
            throw new RuntimeException(e);
        }

        int finalAmount = printingFactory1.getPaperInventory().get(paper);

        assertEquals(initialAmount - amountOfCopies, finalAmount);
    }




}