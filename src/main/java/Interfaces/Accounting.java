package Interfaces;

import PrintingFactoryEmployees.Employee;
import PrintingFactoryProducts.PaperType;

public interface Accounting {

    double calculatePaperExpenses(int amount, PaperType paperType);

    void addPaperExpense(int PaperAmount, PaperType paperType);

    boolean calculateIndividualSalaryExpenses(Employee employee);

    boolean RemoveIndividualSalaryExpenses(Employee employee);

    double pricePerCopyEstimation(int amountOfCopies, PaperType paperType, int AmountOfCopiesRequiredForDiscount, double discountPercentage);

    double priceOfEntireOrderEstimation(int amountOfCopies, PaperType paperType, int AmountOfCopiesRequiredForDiscount, double discountPercentage);

    void AddToIncome(int amountOfCopies, PaperType paperType, int AmountOfCopiesRequiredForDiscount, double discountPercentage);
}