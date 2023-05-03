package PrintingFactoryDriver;

import PrintingFactoryEmployees.Employee;
import PrintingFactoryMachinery.PrintingMachine;
import PrintingFactoryProducts.PaperType;

import java.util.HashSet;
import java.util.List;

public class PrintingFactory {
    private String name;

    private HashSet<Employee> employeeList;

    private List<PrintingMachine> machinesList;

    private List<PaperType> paperTypesList;

    private double SalaryExpenses;

    private double PaperExpenses;

    private double income;

    private double bonusIncomeTarget;


}
