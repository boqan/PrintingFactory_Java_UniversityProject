package Interfaces;

import PrintingFactoryDriver.Paper;
import PrintingFactoryEmployees.Employee;
import PrintingFactoryProducts.PaperType;

public interface Accounting {

    double calculatePaperExpenses(int amount, Paper paper);

    void addPaperExpense(int PaperAmount, Paper paper);

    boolean calculateIndividualSalaryExpenses(Employee employee);

    boolean RemoveIndividualSalaryExpenses(Employee employee);

    double pricePerCopyEstimation(int amountOfCopies, Paper paper, int AmountOfCopiesRequiredForDiscount, double discountPercentage);

    double priceOfEntireOrderEstimation(int amountOfCopies, Paper paper, int AmountOfCopiesRequiredForDiscount, double discountPercentage);

    void AddToIncome(int amountOfCopies, Paper paper, int AmountOfCopiesRequiredForDiscount, double discountPercentage);
}