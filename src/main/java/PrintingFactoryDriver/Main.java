package PrintingFactoryDriver;

import PrintingFactoryEmployees.*;
import PrintingFactoryProducts.*;
import PrintingFactoryMachinery.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        PrintingFactory printingFactory1 = new PrintingFactory("printingFactory1", 1000);
        printingFactory1.setIncome(1200);
        Manager manager1 = new Manager("manager1", 1000, printingFactory1);
        printingFactory1.addEmployee(manager1);

        System.out.println(manager1.getBaseSalary());
    }
}