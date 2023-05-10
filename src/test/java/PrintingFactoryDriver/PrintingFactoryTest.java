package PrintingFactoryDriver;

import PrintingFactoryEmployees.Employee;
import PrintingFactoryEmployees.Operator;
import PrintingFactoryMachinery.PrintingMachine;
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
    void addMachine() {
    }

    @Test
    void addPaperTypeAmountToInventory() {
    }

    @Test
    void removeEmployee() {
    }

    @Test
    void removeMachine() {
    }

    @Test
    void removePaperTypeAmountFromInventory() {
    }

    @Test
    void printingOrder() {
    }
}