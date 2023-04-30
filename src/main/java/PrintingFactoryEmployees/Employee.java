package PrintingFactoryEmployees;

import java.util.Random;

public class Employee {
    private String name;
    private final String EmployeeID;

    private double BaseSalary;

    public Employee(String name, double BaseSalary) {
        this.name = name;
        this.EmployeeID = GenerateID();
        this.BaseSalary = BaseSalary;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public double getBaseSalary() {
        return BaseSalary;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String GenerateID(){
        Random random = new Random();
        long randomNumber = (long) (random.nextDouble() * 1_000_000L);
        return "ID" + String.format("%05d", randomNumber);
    }

    public void setBaseSalary(double BaseSalary) {
        this.BaseSalary = BaseSalary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", EmployeeID='" + EmployeeID + '\'' +
                ", BaseSalary=" + BaseSalary +
                '}';
    }
}
