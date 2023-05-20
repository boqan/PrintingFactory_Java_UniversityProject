package PrintingFactoryDriver;

import Interfaces.Accounting;
import PrintingFactoryEmployees.Employee;
import PrintingFactoryProducts.PaperType;
import PrintingFactoryProducts.PageSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PrintingFactoryAccountingTest {

    private PrintingFactoryAccounting accounting;

    private Accounting accountingMock;
    private Paper paper;

    @BeforeEach
    void setUp(){
        accounting = new PrintingFactoryAccounting(0.1, "EasyConsult");
        paper = Mockito.mock(Paper.class);
        accountingMock = Mockito.mock(Accounting.class);
        when(paper.getPaperType()).thenReturn(PaperType.GLOSSY);
    }


    @Test
    void setPaperPriceTest() {
        accounting.setPaperPrice(paper, 0.1);
        assertEquals(0.1, accounting.getPaperPrice(paper));
    }


    @Test
    void calculatePaperExpensesWorkingTest() {
        Paper paper = new Paper(PageSize.A2, PaperType.GLOSSY);
        assertEquals(25, accounting.calculatePaperExpenses(50, paper));
    }

    @Test
    void calculatePaperExpensesExceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> accounting.calculatePaperExpenses(-10, paper));
    }

    @Test
    void calculateIndividualSalaryExpensesWorkingTest() {
        Employee employee1 = new Employee("employee1", 1000);
        assertTrue(accounting.calculateIndividualSalaryExpenses(employee1));
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
        assertTrue(accounting.RemoveIndividualSalaryExpenses(employee1));
        assertEquals(0, accounting.getSalaryExpenses());
    }

    @Test
    void pricePerCopyEstimation_negativeAmountOfCopies_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accounting.pricePerCopyEstimation(-100, paper, 0, 0.1, 0.5));
    }

    @Test
    void pricePerCopyEstimation_zeroAmountOfCopies_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accounting.pricePerCopyEstimation(0, paper, 50, 0.1, 0.5));
    }

    @Test
    void pricePerCopyEstimation_negativeDiscountRequired_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accounting.pricePerCopyEstimation(100, paper, -50, 0.1, 0.5));
    }

    @Test
    void pricePerCopyEstimation_zeroDiscountRequired_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accounting.pricePerCopyEstimation(100, paper, 0, 0.1, 0.5));
    }

    @Test
    void pricePerCopyEstimation_negativeDiscountPercentage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accounting.pricePerCopyEstimation(100, paper, 50, -0.1, 0.5));
    }

    @Test
    void pricePerCopyEstimation_zeroDiscountPercentage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accounting.pricePerCopyEstimation(100, paper, 50, 0.0, 0.5));
    }
    @Test
    void pricePerCopyEstimation_nullPaperType_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accounting.pricePerCopyEstimation(100, null, 50, 0.1, 0.5));
    }
    @Test
    void pricePerCopyEstimation_negativeMarkupPercentage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accounting.pricePerCopyEstimation(100, paper, 50, 0.1, -0.5));
    }

    @Test
    void pricePerCopyEstimation_worksAsExpected() {
        when(paper.getPaperType()).thenReturn(PaperType.GLOSSY);
        when(paper.getPageSize()).thenReturn(PageSize.A4);
        accounting.setPaperPrice(paper, 0.1);
        assertEquals(13.5, accounting.pricePerCopyEstimation(100, paper, 50, 0.1, 0.5));
    }
}
