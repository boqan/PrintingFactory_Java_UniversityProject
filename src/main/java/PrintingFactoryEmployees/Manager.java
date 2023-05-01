package PrintingFactoryEmployees;
public class Manager extends Employee{

    public Manager(String name, double BaseSalary) {
        super(name, BaseSalary);
    }

    // TO DO: Implement bonusPercentage() method
    double bonusPercentage(){
        return 0;
    }

    @Override
    public String toString() {
        return "Manager{} " + super.toString();
    }
}
