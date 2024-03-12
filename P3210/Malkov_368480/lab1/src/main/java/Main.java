import core.SLAESolver;
import dataclasses.DataContainer;
import dataloaders.ConsoleDataLoader;
import dataloaders.DataLoader;
import dataloaders.FileDataLoader;
import dataloaders.RandomDataLoader;
import exceptions.MatrixLoadingException;
import exceptions.InvalidDiagonalException;
import printers.ConsolePrinter;
import printers.Printer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static Printer printer;

    private static Map<String, DataLoader> loaders = new HashMap<>();

    static {
        printer = new ConsolePrinter();
        loaders.put("file", new FileDataLoader(printer));
        loaders.put("console", new ConsoleDataLoader());
        loaders.put("random", new RandomDataLoader());
    }

    private static DataLoader selectLoader() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            printer.println("Select data source (file/console/random)");
            choice = scanner.nextLine();
        } while (!loaders.containsKey(choice));

        return loaders.get(choice);
    }

    private static String getContinueChoice(Printer printer){
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            printer.println("Do you want to continue?(y/n)");
            choice = scanner.nextLine();
        } while (!choice.equals("n") && !choice.equals("y"));
        return choice;
    }

    public static void main(String... args) {
        try {
            do {
                DataLoader dataLoader = selectLoader();
                DataContainer dataContainer = dataLoader.load();
                SLAESolver slaeSolver = new SLAESolver(4);
                SLAESolver.Solution solution = slaeSolver.solve(dataContainer.matrix(), dataContainer.accuracy());
                printer.println(solution);
            } while (!getContinueChoice(printer).equals("n"));
        } catch (MatrixLoadingException | InvalidDiagonalException e) {
            printer.printf("Error while executing: %s", e.getMessage());
        } catch (IOException e) {
            printer.println("Can not parse input file!");
        } catch (NumberFormatException e) {
            printer.println("Can not parse input number!");
        }
    }
}
