package PrintingFactoryDriver;

import PrintingFactoryEmployees.*;
import PrintingFactoryProducts.*;
import PrintingFactoryMachinery.*;


public class Main {
    public static void main(String[] args) {
        PrintingFactoryAccounting accounting1 = new PrintingFactoryAccounting(0.1, "EasyConsult");
        PrintingFactory printingFactory1 = new PrintingFactory("printingFactory1", accounting1);
        PrintingMachine printingMachine1 = new PrintingMachine(500, true,2, printingFactory1);


        accounting1.setIncome(1200);
        Manager manager1 = new Manager("manager1", 1000, accounting1);
        printingFactory1.addEmployee(manager1);
        printingFactory1.addMachine(printingMachine1);


        System.out.println();
    }
}