package PrintingFactoryEmployees;

import PrintingFactoryDriver.PrintingFactory;
import PrintingFactoryDriver.PrintingFactoryAccounting;

public class Manager extends Employee{

    private PrintingFactoryAccounting accounting;

    //static e poneje e company wide bonus
    private static double BONUS_PERCENTAGE = 0.1;
    public Manager(String name, double BaseSalary, PrintingFactoryAccounting accounting) {
        super(name, BaseSalary);
        this.accounting = accounting;
        this.setBaseSalary(this.calculateSalaryWithBonus());
    }

    public static double getBonusPercentage() {
        return BONUS_PERCENTAGE;
    }

    public static void setBonusPercentage(double bonusPercentage) {
        BONUS_PERCENTAGE = bonusPercentage;
    }


    // takes the income of the factory, compares to the bonus target and if met, returns true
    public boolean checkIfEligibleForBonus(){
        return this.accounting.getIncome() > this.accounting.getBonusIncomeTarget();
    }
    // calculates the salary of the manager, if eligible for bonus, adds the bonus
    public double calculateSalaryWithBonus() {
        if (this.checkIfEligibleForBonus()) {
            return this.getBaseSalary() * (1 + BONUS_PERCENTAGE);
        } else {
            return this.getBaseSalary();
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Manager{" +
                "accounting=" + accounting +
                "} " + super.toString();
    }
}
