package PrintingFactoryDriver;

import PrintingFactoryEmployees.Employee;
import PrintingFactoryProducts.PaperType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintingFactoryAccountingTest {

    PrintingFactoryAccounting accounting;

    @BeforeEach
    void setUp(){
        accounting = new PrintingFactoryAccounting(0.1, "EasyConsult");
    }


    @Test
    void setPaperPriceTest() {
        accounting.setPaperPrice(PaperType.GLOSSY, 0.1);
        assertEquals(0.1, accounting.getPaperPrices().get(PaperType.GLOSSY));
    }


    @Test
    void calculatePaperExpensesWorkingTest() {
        assertEquals(25, accounting.calculatePaperExpenses(50, PaperType.GLOSSY));
    }

    @Test
    void calculatePaperExpensesExceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> accounting.calculatePaperExpenses(-10, PaperType.GLOSSY));
    }

    @Test
    void calculateIndividualSalaryExpensesWorkingTest() {
        Employee employee1 = new Employee("employee1", 1000);
        accounting.calculateIndividualSalaryExpenses(employee1);
        assertEquals(1000, accounting.getSalaryExpenses());
    }

    @Test
    void calculateIndividualSalaryExpensesExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> accounting.calculateIndividualSalaryExpenses(null));
    }

    @Test
    void removeIndividualSalaryExpenses() {
        Employee employee1 = new Employee("employee1", 1000);
        accounting.calculateIndividualSalaryExpenses(employee1);
        accounting.RemoveIndividualSalaryExpenses(employee1);
        assertEquals(0, accounting.getSalaryExpenses());
    }

    @Test
    void pricePerCopyEstimation_negativeAmountOfCopies_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accounting.pricePerCopyEstimation(-10, PaperType.GLOSSY, 50, 0.1);
        });
    }

    @Test
    void pricePerCopyEstimation_zeroAmountOfCopies_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accounting.pricePerCopyEstimation(0, PaperType.GLOSSY, 50, 0.1);
        });
    }

    @Test
    void pricePerCopyEstimation_negativeDiscountRequired_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accounting.pricePerCopyEstimation(100, PaperType.GLOSSY, -50, 0.1);
        });
    }

    @Test
    void pricePerCopyEstimation_zeroDiscountRequired_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accounting.pricePerCopyEstimation(100, PaperType.GLOSSY, 0, 0.1);
        });
    }

    @Test
    void pricePerCopyEstimation_negativeDiscountPercentage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accounting.pricePerCopyEstimation(100, PaperType.GLOSSY, 50, -0.1);
        });
    }

    @Test
    void pricePerCopyEstimation_zeroDiscountPercentage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accounting.pricePerCopyEstimation(100, PaperType.GLOSSY, 50, 0.0);
        });
    }

    @Test
    void pricePerCopyEstimation_nullPaperType_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accounting.pricePerCopyEstimation(100, null, 50, 0.1);
        });
    }


    @Test
    void pricePerCopyEstimation_validInput_returnsExpectedResult() {
        int amountOfCopies = 100;
        PaperType paperType = PaperType.GLOSSY;
        int AmountOfCopiesRequiredForDiscount = 50;
        double discountPercentage = 0.1; // 10% discount

        // Expected price per copy with discount is $0.5 * (1 - 0.1) = $0.45
        // Therefore, for 100 copies, expected cost would be 100 * $0.45 = $45.0
        double expected = 45.0;

        assertEquals(expected, accounting.pricePerCopyEstimation(amountOfCopies, paperType, AmountOfCopiesRequiredForDiscount, discountPercentage));
    }

    @Test
    void priceOfEntireOrderEstimation_validInput_returnsExpectedResult() {
        int amountOfCopies = 100;
        PaperType paperType = PaperType.GLOSSY;
        int AmountOfCopiesRequiredForDiscount = 50;
        double discountPercentage = 0.1; // 10% discount

        // Expected price per copy with discount is $0.5 * (1 - 0.1) = $0.45
        // Therefore, for 100 copies, expected cost would be 100 * $0.45 = $45.0
        // For priceOfEntireOrderEstimation, the result should be $45.0 * 100 = $4500.0
        double expected = 4500.0;

        assertEquals(expected, accounting.priceOfEntireOrderEstimation(amountOfCopies, paperType, AmountOfCopiesRequiredForDiscount, discountPercentage));
    }

}