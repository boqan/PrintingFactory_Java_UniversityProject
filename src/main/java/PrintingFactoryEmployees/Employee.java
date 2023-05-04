package PrintingFactoryEmployees;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class Employee implements Comparable<Employee>, Serializable {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(getEmployeeID(), employee.getEmployeeID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployeeID());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", EmployeeID='" + EmployeeID + '\'' +
                ", BaseSalary=" + BaseSalary +
                '}';
    }

    @Override
    public int compareTo(Employee other) {
        return this.EmployeeID.compareTo(other.EmployeeID);
    }
}
