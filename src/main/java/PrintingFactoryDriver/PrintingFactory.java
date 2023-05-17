package PrintingFactoryDriver;

import PrintingFactoryEmployees.Employee;
import PrintingFactoryExceptions.*;
import PrintingFactoryMachinery.PrintingMachine;
import PrintingFactoryProducts.PageSize;
import PrintingFactoryProducts.PaperType;
import PrintingFactoryProducts.Publication;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintingFactory implements Comparable<PrintingFactory>, Serializable {
    private String name;

    private HashSet<Employee> employeeList;

    private HashSet<PrintingMachine> machinesList;

    private Map<Paper, Integer> paperInventory;

    private PrintingFactoryAccounting accounting;



    public PrintingFactory(String name, PrintingFactoryAccounting accounting) {
        this.name = name;
        this.employeeList = new HashSet<>();
        this.machinesList = new HashSet<>();
        this.paperInventory = new HashMap<>();
        this.paperInventory = Arrays.stream(PageSize.values())
                        .flatMap(pageSize -> Arrays.stream(PaperType.values())
                                .map(paperType -> new Paper(pageSize, paperType)))
                        .collect(Collectors.toMap(Function.identity(), paper -> 0));
        this.accounting = accounting;
    }

    //----------------------------add and remove functions for employees, machines and paper inventory----------------------------
    public void addEmployee(Employee employee){
        this.employeeList.add(employee);
        this.accounting.calculateIndividualSalaryExpenses(employee);
    }
    public void addMachine(PrintingMachine machine){
        this.machinesList.add(machine);
    }
    // adds paper to inventory, if successful it also adds the expense for the bought paper to paperexpenses
    public boolean addPaperTypeAmountToInventory(Paper paper, int amount) throws NegativePaperAmountException {
        if(amount < 0){
            throw new NegativePaperAmountException("Amount of paper cannot be negative");
        }
        int currentAmount = this.paperInventory.get(paper);
        this.paperInventory.put(paper, currentAmount + amount);
        this.accounting.addPaperExpense(amount, paper);
        return true;
    }

    public void removeEmployee(Employee employee){
        this.employeeList.remove(employee);
        accounting.RemoveIndividualSalaryExpenses(employee);
    }
    public void removeMachine(PrintingMachine machine){
        this.machinesList.remove(machine);
    }
    // remove paper from inventory, does not do add or remove anything to the expenses field of the accounting class
    public boolean removePaperTypeAmountFromInventory(PaperType paperType, int amount) throws NegativePaperAmountException {
        if(amount < 0 || amount > this.paperInventory.get(paperType)){
            throw new NegativePaperAmountException("Amount of paper cannot be negative");
        }
        int currentAmount = this.paperInventory.get(paperType);
        this.paperInventory.put(paperType, currentAmount - amount);
        return true;
    }

    //----------------------------getters and setters----------------------------

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


    public void setName(String name) {
        this.name = name;
    }

    //----------------------------printing functions----------------------------
    public boolean printingOrder(int amountOfCopies, PaperType paperType, Publication publication, boolean colorPrinting)
            throws InsufficientPaperAmountInStorageException, NoSuitableMachineException
    {
        int currentAmountOfPaper = paperInventory.get(paperType);
        // if there is not enough paper in storage, throw exception
        if(currentAmountOfPaper < amountOfCopies){
            throw new InsufficientPaperAmountInStorageException("Not enough paper for the print in storage");
        }
        // search for suitableMachine in the list in the factory
        PrintingMachine suitableMachine = machinesList.stream()
                .filter(machine -> machine.isPrintsColour() == colorPrinting)
                .findFirst()
                .orElse(null);

        // if no suitable machine is found, throw exception
        if(suitableMachine == null){
            throw new NoSuitableMachineException("No suitable machine for the print");
        }

        // try to load paper into machine and print the publication
        try {

            suitableMachine.loadPaperIntoMachine(amountOfCopies, paperType, this.getPaperInventory());
            suitableMachine.printPublication(publication, paperType);
        } catch (NoPaperInMachineException | NoSpaceForMorePaperException | InsufficientPaperAmountInStorageException e) {
            throw new RuntimeException(e);
        }
        // if everything is successful, try to remove the paper from the inventory
        try {
            this.removePaperTypeAmountFromInventory(paperType, amountOfCopies);
        } catch (NegativePaperAmountException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Printing order successful");
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintingFactory that = (PrintingFactory) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public int compareTo(PrintingFactory object) {
        return this.getName().compareTo(object.getName());
    }

    @Override
    public String toString() {
        return "PrintingFactory{" +
                "name='" + name + '\'' +
                ", employeeList=" + employeeList +
                ", machinesList=" + machinesList +
                ", paperInventory=" + paperInventory +
                ", accounting=" + accounting +
                '}';
    }
}
