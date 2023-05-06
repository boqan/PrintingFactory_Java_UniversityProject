package PrintingFactoryDriver;

import PrintingFactoryEmployees.Employee;
import PrintingFactoryExceptions.NegativePaperAmountException;
import PrintingFactoryMachinery.PrintingMachine;
import PrintingFactoryProducts.PaperType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintingFactory {
    private String name;

    private HashSet<Employee> employeeList;

    private HashSet<PrintingMachine> machinesList;

    private Map<PaperType, Integer> paperInventory;

    private Map<PaperType, Double> paperPrices;

    private double SalaryExpenses;

    private double PaperExpenses;

    private double income;

    private double bonusIncomeTarget;

    public PrintingFactory(String name, double bonusIncomeTarget) {
        this.name = name;
        this.bonusIncomeTarget = bonusIncomeTarget;
        this.employeeList = new HashSet<>();
        this.machinesList = new HashSet<>();
        this.paperInventory = new HashMap<>();
        this.paperPrices = new HashMap<>();
        initializePaperPrices();
        this.paperInventory =
                Stream.of(PaperType.values())
                        .collect(Collectors.toMap(paperType -> paperType, paperType -> 0));
        this.SalaryExpenses = 0;
        this.PaperExpenses = 0;
        this.income = 0;
    }

    //////////////////////////Paper prices helper functions, getters and setters//////////////////////////////////////////
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
    ////////////////////////// add and remove functions for employees, machines and paper inventory//////////////////////////////////////////
    public void addEmployee(Employee employee){
        this.employeeList.add(employee);
        this.SalaryExpenses += employee.getBaseSalary();
    }
    public void addMachine(PrintingMachine machine){
        this.machinesList.add(machine);
    }
    // adds paper to inventory, if successful it also adds the expense for the bought paper to paperexpenses
    public boolean addPaperTypeAmountToInventory(PaperType paperType, int amount) throws NegativePaperAmountException {
        if(amount < 0){
            throw new NegativePaperAmountException("Amount of paper cannot be negative");
        }
        int currentAmount = this.paperInventory.get(paperType);
        this.paperInventory.put(paperType, currentAmount + amount);
        this.PaperExpenses = calculatePaperExpenses(amount, paperType);
        return true;
    }

    public void removeEmployee(Employee employee){
        this.employeeList.remove(employee);
        this.SalaryExpenses -= employee.getBaseSalary();
    }
    public void removeMachine(PrintingMachine machine){
        this.machinesList.remove(machine);
    }
    // remove paper from inventory, does not do add or remove anything to the expenses field of the class
    public boolean removePaperTypeAmountFromInventory(PaperType paperType, int amount) throws NegativePaperAmountException {
        if(amount < 0){
            throw new NegativePaperAmountException("Amount of paper cannot be negative");
        }
        int currentAmount = this.paperInventory.get(paperType);
        this.paperInventory.put(paperType, currentAmount - amount);
        return true;
    }
    ///////////////////////////calculate expenses and income//////////////////////////////////////////

    // simply calculates the actual expense, does not add or remove anything to the expenses field of the class
    public double calculatePaperExpenses(int amount, PaperType paperType){
        return amount * this.paperPrices.get(paperType);
    }

    // calculates and adds the income expense of a single employee
    public boolean calculateIndividualSalaryExpenses(Employee employee){
        this.SalaryExpenses += employee.getBaseSalary();
        return true;
    }
    // calculates and adds the income expense of all the employees in the company
    public double calculateMonthlySalaryExpensesTotal(){
        return this.employeeList.stream()
        .mapToDouble(Employee::getBaseSalary)
        .sum();
    }
    ///////////////////////////getters and setters//////////////////////////////////////////

    public String getName() {
        return name;
    }

    public HashSet<Employee> getEmployeeList() {
        return employeeList;
    }

    public HashSet<PrintingMachine> getMachinesList() {
        return machinesList;
    }

    public Map<PaperType, Integer> getPaperInventory() {
        return paperInventory;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setBonusIncomeTarget(double bonusIncomeTarget) {
        this.bonusIncomeTarget = bonusIncomeTarget;
    }



    //implement priting function with expenses and everything
    // implement otstupka function
    // create full pojo class, getters setters equals hashcode comparable tostring etc
}
