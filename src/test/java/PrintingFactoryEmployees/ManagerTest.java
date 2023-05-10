package PrintingFactoryEmployees;

import PrintingFactoryDriver.PrintingFactory;
import PrintingFactoryDriver.PrintingFactoryAccounting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    Manager manager1;
    PrintingFactory printingFactory1;

    PrintingFactoryAccounting accounting1;
    @BeforeEach
    void setUp() {
        accounting1 = new PrintingFactoryAccounting(0.1, "EasyConsult");
        printingFactory1 = new PrintingFactory("printingFactory", accounting1);
        manager1 = new Manager("manager1", 1000, accounting1);
    }
    @Test
    void checkIfEligibleForBonusTest() {
        accounting1.setIncome(1200);
        assertTrue(manager1.checkIfEligibleForBonus());
    }

    @Test
    void checkIfEligibleForBonusFalseTest() {
        accounting1.setIncome(800);
        assertFalse(manager1.checkIfEligibleForBonus());
    }

    @Test
    void calculateSalaryWithBonusTest() {
        accounting1.setIncome(1200);
        assertEquals(1100, manager1.calculateSalaryWithBonus());
    }

    @Test
    void calculateSalaryWithBonusFalseTest() {
        accounting1.setIncome(800);
        assertEquals(1000, manager1.calculateSalaryWithBonus());
    }
}