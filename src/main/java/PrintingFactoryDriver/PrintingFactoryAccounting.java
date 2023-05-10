package PrintingFactoryDriver;

import PrintingFactoryEmployees.Employee;
import PrintingFactoryProducts.PaperType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintingFactoryAccounting implements Comparable<PrintingFactoryAccounting>, Serializable {

    private String name;
    private Map<PaperType, Double> paperPrices;
    private double SalaryExpenses;
    private double PaperExpenses;
    private double income;
    private double bonusIncomeTarget;

    public PrintingFactoryAccounting(double bonusIncomeTarget, String name) {
        this.bonusIncomeTarget = bonusIncomeTarget;
        this.paperPrices = new HashMap<>();
        this.paperPrices =
                Stream.of(PaperType.values())
                        .collect(Collectors.toMap(paperType -> paperType, paperType -> 0.0));
        initializePaperPrices();
        this.SalaryExpenses = 0;
        this.PaperExpenses = 0;
        this.income = 0;
        this.name = name;
    }

    //----------------------------Paper prices helper functions, getters and setters----------------------------
    public void initializePaperPrices(){
        this.paperPrices.put(PaperType.GLOSSY, 0.5);
        this.paperPrices.put(PaperType.NORMAL, 0.25);
        this.paperPrices.put(PaperType.NEWSPAPER, 0.1);
    }

    public void setPaperPrice(PaperType paperType, double price){
        this.paperPrices.put(paperType, price);
    }

    public double getPaperPrice(PaperType paperType){
        return this.paperPrices.get(paperType);
    }


    //----------------------------calculate expenses and income----------------------------

    // simply calculates the actual expense, does not add or remove anything to the expenses field of the class
    public double calculatePaperExpenses(int amount, PaperType paperType){
        if(amount <= 0)
            throw new IllegalArgumentException("Amount cannot be below or equal to zero");

        return amount * this.paperPrices.get(paperType);
    }
    // uses the above method and adds the paperexpense to the field
    public void addPaperExpense(int PaperAmount, PaperType paperType){
        this.PaperExpenses += calculatePaperExpenses(PaperAmount, paperType);
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

    public double pricePerCopyEstimation(int amountOfCopies, PaperType paperType, int AmountOfCopiesRequiredForDiscount, double discountPercentage){
        if (amountOfCopies <= 0) {
            throw new IllegalArgumentException("Amount of copies cannot be below or equal to zero");
        }
        if (AmountOfCopiesRequiredForDiscount <= 0) {
            throw new IllegalArgumentException("Amount of copies required for discount must be above zero");
        }
        if (discountPercentage <= 0) {
            throw new IllegalArgumentException("Discount percentage must be above zero");
        }
        if (paperType == null) {
            throw new IllegalArgumentException("Paper type cannot be null");
        }
        double pricePerCopy = this.paperPrices.get(paperType);

        if (amountOfCopies >= AmountOfCopiesRequiredForDiscount) {
            pricePerCopy *= (1 - discountPercentage);
        }

        double discountDecimal = discountPercentage / 100.0;
        return amountOfCopies * pricePerCopy;
    }

    public double priceOfEntireOrderEstimation(int amountOfCopies, PaperType paperType, int AmountOfCopiesRequiredForDiscount, double discountPercentage){
        return pricePerCopyEstimation(amountOfCopies, paperType, AmountOfCopiesRequiredForDiscount, discountPercentage) * amountOfCopies;
    }

    // calculates and adds the income of an entire printing order to the income field, using the above methods for calculations
    public void AddToIncome(int amountOfCopies, PaperType paperType, int AmountOfCopiesRequiredForDiscount, double discountPercentage)
    {
        this.income += priceOfEntireOrderEstimation(amountOfCopies, paperType, AmountOfCopiesRequiredForDiscount, discountPercentage);
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
    public Map<PaperType, Double> getPaperPrices() {
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