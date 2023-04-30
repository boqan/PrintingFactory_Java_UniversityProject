package PrintingFactoryEmployees;


import PrintingFactoryMachinery.PrintingMachine;

public class Operator extends Employee{

    private PrintingMachine printingMachine;

    public Operator(String name, double BaseSalary, PrintingMachine printingMachine) {
        super(name, BaseSalary);
        this.printingMachine = printingMachine;
    }

    public PrintingMachine getPrintingMachine() {
        return printingMachine;
    }

    public void setPrintingMachine(PrintingMachine printingMachine) {
        this.printingMachine = printingMachine;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "printingMachine=" + printingMachine +
                "} " + super.toString();
    }
}
