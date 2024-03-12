package dataloaders;

import dataclasses.DataContainer;
import exceptions.MatrixLoadingException;
import printers.Printer;

import java.io.*;
import java.util.Scanner;

public class FileDataLoader extends DataLoader {
    private Printer printer;

    public FileDataLoader(Printer printer) {
        this.printer = printer;
    }

    protected String getFilePath() {
        Scanner scanner = new Scanner(System.in);
        printer.println("Enter file path:");
        String filePath = "";
        do {
            File file = new File(scanner.nextLine());
            if (!file.exists()) {
                printer.println("File does not exist!");
                continue;
            }
            if (!file.canRead()) {
                printer.println("Can not access to the file!");
                continue;
            }
            filePath = file.getPath();
        } while (filePath.isEmpty());
        return filePath;
    }

    @Override
    public DataContainer load() throws IOException, NumberFormatException, MatrixLoadingException {
        short size;
        double accuracy;
        double[][] matrix;
        String filePath = getFilePath();
        File file = new File(filePath);
        try (FileReader fileReader = new FileReader(file)) {
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String sizeString = bufferedReader.readLine();
                size = Short.parseShort(sizeString);
                String accuracyString = bufferedReader.readLine();
                accuracy = Double.parseDouble(accuracyString);
                matrix = loadMatrix(bufferedReader, size);
            }
        }
        return new DataContainer(size, accuracy, matrix);
    }
}
