package PrintingFactoryDriver;

import Interfaces.Accounting;
import PrintingFactoryEmployees.Employee;
import PrintingFactoryProducts.PageSize;
import PrintingFactoryProducts.PaperType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintingFactoryAccounting implements Comparable<PrintingFactoryAccounting>, Serializable, Accounting {

    private String name;
    private Map<Paper, Double> paperPrices;
    private double SalaryExpenses;
    private double PaperExpenses;
    private double income;
    private double bonusIncomeTarget;

    public PrintingFactoryAccounting(double bonusIncomeTarget, String name) {
        this.bonusIncomeTarget = bonusIncomeTarget;
        this.paperPrices = new HashMap<>();
        this.paperPrices =
                Arrays.stream(PageSize.values())
                        .flatMap(pageSize -> Arrays.stream(PaperType.values())
                                .map(paperType -> new Paper(pageSize, paperType)))
                        .collect(Collectors.toMap(Function.identity(), paper -> 0.0));
        initializePaperPrices();
        this.SalaryExpenses = 0;
        this.PaperExpenses = 0;
        this.income = 0;
        this.name = name;
    }

    //----------------------------Paper prices helper functions, getters and setters----------------------------
    public void initializePaperPrices(){
        // Initialize all paper types with their respective prices
        this.paperPrices.replaceAll((paper, price) -> {
            switch (paper.getPaperType()) {
                case GLOSSY:
                    return 0.5;
                case NORMAL:
                    return 0.25;
                case NEWSPAPER:
                    return 0.1;
                default:
                    return 0.0;
            }
        });
    }

    public void setPaperPrice(Paper paper, double price){
        this.paperPrices.put(paper, price);
    }

    public double getPaperPrice(Paper paper){
        return this.paperPrices.get(paper);
    }


    //----------------------------calculate expenses and income----------------------------

    // simply calculates the actual expense, does not add or remove anything to the expenses field of the class
    public double calculatePaperExpenses(int amount, Paper paper){
        if(amount <= 0)
            throw new IllegalArgumentException("Amount cannot be below or equal to zero");

        return amount * this.paperPrices.get(paper);
    }
    // uses the above method and adds the paperexpense to the field
    public void addPaperExpense(int PaperAmount, Paper paper){
        this.PaperExpenses += calculatePaperExpenses(PaperAmount, paper);
    }


    // calculates and adds the income expense of a single employee - its boolean for testing purposes
    public boolean calculateIndividualSalaryExpenses(Employee employee){
        if(employee == null){
            throw new IllegalArgumentException("Employee cannot be null");
        }
        this.SalaryExpenses += employee.getBaseSalary();
        return true;
    }

    public boolean RemoveIndividualSalaryExpenses(Employee employee){
        if(employee == null){
            throw new IllegalArgumentException("Employee cannot be null");
        }
        this.SalaryExpenses -= employee.getBaseSalary();
        return true;
    }

    public double pricePerCopyEstimation(int amountOfCopies, Paper paper, int AmountOfCopiesRequiredForDiscount, double discountPercentage){
        if (amountOfCopies <= 0) {
            throw new IllegalArgumentException("Amount of copies cannot be below or equal to zero");
        }
        if (AmountOfCopiesRequiredForDiscount <= 0) {
            throw new IllegalArgumentException("Amount of copies required for discount must be above zero");
        }
        if (discountPercentage <= 0) {
            throw new IllegalArgumentException("Discount percentage must be above zero");
        }
        if (paper == null) {
            throw new IllegalArgumentException("Paper type cannot be null");
        }
        double pricePerCopy = this.paperPrices.get(paper);

        if (amountOfCopies >= AmountOfCopiesRequiredForDiscount) {
            pricePerCopy *= (1 - discountPercentage);
        }

        double discountDecimal = discountPercentage / 100.0;
        return amountOfCopies * pricePerCopy;
    }

    public double priceOfEntireOrderEstimation(int amountOfCopies, Paper paper, int AmountOfCopiesRequiredForDiscount, double discountPercentage){
        return pricePerCopyEstimation(amountOfCopies, paper, AmountOfCopiesRequiredForDiscount, discountPercentage) * amountOfCopies;
    }

    // calculates and adds the income of an entire printing order to the income field, using the above methods for calculations
    public void AddToIncome(int amountOfCopies, Paper paper, int AmountOfCopiesRequiredForDiscount, double discountPercentage)
    {
        this.income += priceOfEntireOrderEstimation(amountOfCopies, paper, AmountOfCopiesRequiredForDiscount, discountPercentage);
    }

    // NOT NEEDED RIGHT NOW - SALARY EXPENSES IS UPDATED IN calculateIndividualSalaryExpenses and the add methods of employees in PrintingFactory
//    // calculates and adds the income expense of all the employees in the company
//    public double calculateMonthlySalaryExpensesTotal(){
//        return this.employeeList.stream()
//        .mapToDouble(Employee::getBaseSalary)
//        .sum();
//    }

//----------------------------Getters and setters----------------------------

    public String getName() {
        return name;
    }
    public Map<Paper, Double> getPaperPrices() {
        return paperPrices;
    }

    public double getSalaryExpenses() {
        return SalaryExpenses;
    }

    public double getPaperExpenses() {
        return PaperExpenses;
    }

    public double getIncome() {
        return income;
    }

    public double getBonusIncomeTarget() {
        return bonusIncomeTarget;
    }

    public void setBonusIncomeTarget(double bonusIncomeTarget) {
        this.bonusIncomeTarget = bonusIncomeTarget;
    }
    //for testing purposes we have to be able to set the income
    public void setIncome(double income) {
        this.income = income;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintingFactoryAccounting that = (PrintingFactoryAccounting) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }


    @Override
    public int compareTo(PrintingFactoryAccounting object) {
        return this.getName().compareTo(object.getName());
    }

    @Override
    public String toString() {
        return "PrintingFactoryAccounting{" +
                "name='" + name + '\'' +
                ", paperPrices=" + paperPrices +
                ", SalaryExpenses=" + SalaryExpenses +
                ", PaperExpenses=" + PaperExpenses +
                ", income=" + income +
                ", bonusIncomeTarget=" + bonusIncomeTarget +
                '}';
    }
}