package PrintingFactoryDriver;

import PrintingFactoryEmployees.*;
import PrintingFactoryExceptions.InsufficientPaperAmountInStorageException;
import PrintingFactoryExceptions.NegativePaperAmountException;
import PrintingFactoryExceptions.NoSuitableMachineException;
import PrintingFactoryProducts.*;
import PrintingFactoryMachinery.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static PrintingFactoryProducts.PageSize.A2;


public class Main {
    public static void main(String[] args) {
        PrintingFactoryAccounting accounting = new PrintingFactoryAccounting(5000, "My Accounting");
        PrintingFactory factory = new PrintingFactory("MyFactory", accounting);
        PrintingFactoryFileHandler fileHandler = new PrintingFactoryFileHandler("printingFactory.ser", "printingFactoryAccounting.ser");

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("********** Printing Factory Menu **********");
            System.out.println("1. Add an employee");
            System.out.println("2. Remove an employee");
            System.out.println("3. Add a machine");
            System.out.println("4. Remove a machine");
            System.out.println("5. Print order");
            System.out.println("6. Save current state");
            System.out.println("7. Load previous state");
            System.out.println("8. Print factory state");
            System.out.println("9. Exit");
            System.out.println("******************************************");
            System.out.print("Please choose an option: ");

            option = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

            switch (option) {
                case 1:
                    int case1_option;
                    do {
                        System.out.println("Please choose an employee type:");
                        System.out.println("1. Manager");
                        System.out.println("2. Operator");
                        case1_option = scanner.nextInt();
                        scanner.nextLine();

                        if (case1_option != 1 && case1_option != 2)
                            System.out.println("Invalid option. Please choose a valid option from the menu.");
                    }while(case1_option != 1 && case1_option != 2);

                    System.out.println("Choose the base salary (double value) : ");
                    double baseSalary = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Choose the name of the employee : ");
                    String name = scanner.nextLine();

                    if(case1_option == 1) {
                        factory.addEmployee(new Manager(name, baseSalary, accounting));
                        System.out.println("Manager by the name of: " + name + " added successfully");
                    }
                    if(case1_option == 2){
                        if(factory.getMachinesList().isEmpty()){
                            System.out.println("There are no machines in the factory. Please add a machine first.");
                            break;
                        }
                        factory.addEmployee(new Operator(name, baseSalary, factory.getMachinesList()
                                .stream()
                                .findFirst()
                                .get()
                        ));
                        System.out.println("Operator by the name of: " + name + " added successfully");
                    }

                    break;
                case 2:
                    if(factory.getEmployeeList().isEmpty()){
                        System.out.println("There are no employees in the factory. Please add an employee first.");
                        break;
                    }
                    System.out.println("Please choose an employee to remove by providing the valid " +
                            "employee name :");
                    String nameOfEmployeeToRemove = scanner.nextLine();
                    factory.getEmployeeList()
                            .removeIf(employee -> employee
                                    .getName()
                                    .equals(nameOfEmployeeToRemove)
                            );
                    System.out.println("Employee by the name of: " + nameOfEmployeeToRemove + " removed successfully");

                    break;
                case 3:
                    int case3_option;
                    do {
                        System.out.println("Please choose a printing machine type:");
                        System.out.println("1. Color printing");
                        System.out.println("2. Black and white printing");
                        case3_option = scanner.nextInt();

                        if (case3_option != 1 && case3_option != 2)
                            System.out.println("Invalid option. Please choose a valid option from the menu.");
                    }while(case3_option != 1 && case3_option != 2);

                    System.out.println("Choose the maximum paper capacity of the machine (int value) : ");
                    int maxPaperCapacity = scanner.nextInt();
                    System.out.println("Choose the maximum printing speed of the machine(int value) : ");
                    int maxPrintingSpeed = scanner.nextInt();

                    if(case3_option == 1) {
                        factory.addMachine(new PrintingMachine(maxPaperCapacity, true ,maxPrintingSpeed, factory));
                        System.out.println("Color printing machine added successfully");
                    }
                    if(case3_option == 2){
                        factory.addMachine(new PrintingMachine(maxPaperCapacity, false ,maxPrintingSpeed, factory));
                        System.out.println("Black and white printing machine added successfully");
                    }


                    System.out.println("Printing machines in the factory: ");
                    factory.getMachinesList()
                            .stream()
                            .forEach(System.out::println);
                    break;
                case 4:
                    if(factory.getMachinesList().isEmpty()){
                        System.out.println("There are no machines in the factory. Please add a machine first.");
                        break;
                    }
                    System.out.println("Please choose a machine to remove by providing the valid " +
                            "machine id in the format (MachineIDXXXX):");

                    System.out.println("Printing machines in the factory: ");
                    factory.getMachinesList()
                            .stream()
                            .forEach(machine -> System.out.println(machine.getId()));

                    String idOfMachineToRemove = scanner.nextLine();

                    if(factory.getMachinesList()
                            .stream()
                            .noneMatch(machine -> machine
                                    .getId()
                                    .equals(idOfMachineToRemove)
                            )){
                        System.out.println("There is no machine with the provided id. Please try again.");
                        break;
                    }

                    factory.getMachinesList()
                            .removeIf(machine -> machine
                                    .getId()
                                    .equals(idOfMachineToRemove)
                            );

                    System.out.println("Machine by the id of: " + idOfMachineToRemove + " removed successfully");

                    break;
                case 5:
                    if(factory.getMachinesList().isEmpty()){
                        System.out.println("There are no machines in the factory. Please add a machine first.");
                        break;
                    }

                    System.out.println("Please provide the information of the publication to be printed:");

                    System.out.println("Choose the number of pages (int value) : ");
                    int numberOfPages = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Choose the title of the publication : ");
                    String title = scanner.nextLine();

                    System.out.println("Choose the colour type of the print (boolean true or false) : ");
                    boolean isColor = scanner.nextBoolean();
                    scanner.nextLine();

                    int case5_option_papertype;
                    do {
                        System.out.println("Choose the paper type for the print :");
                        System.out.println("1. Glossy");
                        System.out.println("2. Newspaper");
                        System.out.println("3. Normal");
                        case5_option_papertype = scanner.nextInt();
                        scanner.nextLine();

                        if (case5_option_papertype != 1 && case5_option_papertype != 2 && case5_option_papertype != 3)
                            System.out.println("Invalid option. Please choose a valid option from the menu.");
                    }while (case5_option_papertype != 1 && case5_option_papertype != 2 && case5_option_papertype != 3);

                    PaperType paperType = switch (case5_option_papertype) {
                        case 1 -> PaperType.GLOSSY;
                        case 2 -> PaperType.NEWSPAPER;
                        case 3 -> PaperType.NORMAL;
                        default -> PaperType.NORMAL;
                    };

                    int case5_option_pagesize;
                    do {
                        System.out.println("Please provide the page size of the publication:");
                        System.out.println("1. A1");
                        System.out.println("2. A2");
                        System.out.println("3. A3");
                        System.out.println("4. A4");
                        System.out.println("5. A5");
                        case5_option_pagesize = scanner.nextInt();
                        scanner.nextLine();

                        if(case5_option_pagesize != 1 && case5_option_pagesize != 2 && case5_option_pagesize != 3 && case5_option_pagesize != 4 && case5_option_pagesize != 5)
                            System.out.println("Invalid option. Please choose a valid option from the menu.");

                    } while(case5_option_pagesize != 1 && case5_option_pagesize != 2 && case5_option_pagesize != 3 && case5_option_pagesize != 4 && case5_option_pagesize != 5);

                    PageSize pageSize = switch (case5_option_pagesize) {
                        case 1 -> pageSize = PageSize.A1;
                        case 2 -> pageSize = PageSize.A2;
                        case 3 -> pageSize = PageSize.A3;
                        case 4 -> pageSize = PageSize.A4;
                        case 5 -> pageSize = PageSize.A5;
                        default -> PageSize.A2;
                    };

                    int case5_option;
                    do {
                        System.out.println("Choose the publication type: ");
                        System.out.println("1. Book");
                        System.out.println("2. Poster");
                        System.out.println("3. Newspaper");
                        case5_option = scanner.nextInt();
                        scanner.nextLine();

                        if(case5_option != 1 && case5_option != 2 && case5_option != 3)
                            System.out.println("Invalid option. Please choose a valid option from the menu.");
                    } while(case5_option != 1 && case5_option != 2 && case5_option != 3);

                    if(case5_option == 1) {
                        System.out.println("Please provide the author of the book (string) :");
                        String author = scanner.nextLine();

                        Book book = new Book(title, numberOfPages, pageSize, author);

                        try {
                            factory.addPaperTypeAmountToInventory(paperType, numberOfPages + 100);
                        } catch (NegativePaperAmountException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            factory.printingOrder(numberOfPages, paperType, book, isColor);
                        } catch (InsufficientPaperAmountInStorageException | NoSuitableMachineException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if(case5_option == 2) {
                        System.out.println("Please provide the category of the poster (string) :");
                        String category = scanner.nextLine();
                        Poster poster = new Poster(title, numberOfPages, pageSize, category);
                        try {
                            factory.addPaperTypeAmountToInventory(paperType, numberOfPages + 100);
                        } catch (NegativePaperAmountException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            factory.printingOrder(numberOfPages, paperType, poster, isColor);
                        } catch (InsufficientPaperAmountInStorageException | NoSuitableMachineException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if(case5_option == 3) {
                        System.out.println("Please provide the redactor of the newspaper :");
                        String redactor = scanner.nextLine();

                        System.out.println("Please provide the date of issue of the newspaper in the format (dd-MM-yyyy):");

                        LocalDate localDate;
                        try {
                            String dateString = scanner.nextLine();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            localDate = LocalDate.parse(dateString, formatter);
                        } catch (DateTimeParseException e) {
                            throw new RuntimeException(e);
                        }

                        Newspaper newspaper = new Newspaper(title, numberOfPages, pageSize, redactor, localDate);
                        try {
                            factory.addPaperTypeAmountToInventory(paperType, numberOfPages + 100);
                        } catch (NegativePaperAmountException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            factory.printingOrder(numberOfPages, paperType, newspaper, isColor);
                        } catch (InsufficientPaperAmountInStorageException | NoSuitableMachineException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    System.out.println("The order has been successfully printed.");

                    break;
                case 6:
                    System.out.println("Saving the current state...");
                    // Save current state
                    fileHandler.saveFactoryState(factory);
                    fileHandler.saveAccountingState(accounting);
                    break;
                case 7:
                    System.out.println("Loading the previous state...");
                    // Load previous state
                    factory = fileHandler.loadFactoryState();
                    accounting = fileHandler.loadAccountingState();

                    break;
                case 8:
                    System.out.println("Printing the factory state...");
                    System.out.println(factory);
                    System.out.println("Printing the accounting state...");
                    System.out.println(accounting);
                case 9:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option from the menu.");
                    break;
            }
        } while (option != 9);

        scanner.close();
    }
}