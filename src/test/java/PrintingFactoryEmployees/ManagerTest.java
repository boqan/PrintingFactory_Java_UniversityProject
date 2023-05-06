package PrintingFactoryEmployees;

import PrintingFactoryDriver.PrintingFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    Manager manager1;
    PrintingFactory printingFactory1;
    @BeforeEach
    void setUp() {
        printingFactory1 = new PrintingFactory("printingFactory1", 1000);
        manager1 = new Manager("manager1", 1000, printingFactory1);
    }
    @Test
    void checkIfEligibleForBonusTest() {
        printingFactory1.setIncome(1200);
        assertTrue(manager1.checkIfEligibleForBonus());
    }

    @Test
    void checkIfEligibleForBonusFalseTest() {
        printingFactory1.setIncome(800);
        assertFalse(manager1.checkIfEligibleForBonus());
    }

    @Test
    void calculateSalaryWithBonusTest() {
        printingFactory1.setIncome(1200);
        assertEquals(1100, manager1.calculateSalaryWithBonus());
    }

    @Test
    void calculateSalaryWithBonusFalseTest() {
        printingFactory1.setIncome(800);
        assertEquals(1000, manager1.calculateSalaryWithBonus());
    }
}