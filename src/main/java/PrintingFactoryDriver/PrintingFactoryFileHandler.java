package PrintingFactoryDriver;
import PrintingFactoryDriver.PrintingFactory;
import PrintingFactoryDriver.PrintingFactoryAccounting;

import java.io.*;

public class PrintingFactoryFileHandler {

    private String fileNamePrintingFactory;
    private String fileNamePrintingFactoryAccounting;

    public PrintingFactoryFileHandler(String fileName, String fileNameAccounting){
        this.fileNamePrintingFactory = fileName;
        this.fileNamePrintingFactoryAccounting = fileNameAccounting;
    }

    // Write data to a file
    public void saveFactoryState(PrintingFactory factory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileNamePrintingFactory))) {
            oos.writeObject(factory);
        } catch (IOException e) {
            System.err.println("Error writing to file " + fileNamePrintingFactory + ". " + e.getMessage());
        }
    }

    // Read data from a file
    public PrintingFactory loadFactoryState() {
        PrintingFactory factory = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileNamePrintingFactory))) {
            factory = (PrintingFactory) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file " + fileNamePrintingFactory + ". " + e.getMessage());
        }
        return factory;
    }

    // Write data to a file
    public void saveAccountingState(PrintingFactoryAccounting accounting) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileNamePrintingFactoryAccounting))) {
            oos.writeObject(accounting);
        } catch (IOException e) {
            System.err.println("Error writing to file " + fileNamePrintingFactoryAccounting + ". " + e.getMessage());
        }
    }

    // Read data from a file
    public PrintingFactoryAccounting loadAccountingState() {
        PrintingFactoryAccounting accounting = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileNamePrintingFactoryAccounting))) {
            accounting = (PrintingFactoryAccounting) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file " + fileNamePrintingFactoryAccounting + ". " + e.getMessage());
        }
        return accounting;
    }
}

