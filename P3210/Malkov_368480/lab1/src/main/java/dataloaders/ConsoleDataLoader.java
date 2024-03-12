package dataloaders;

import dataclasses.DataContainer;
import exceptions.MatrixLoadingException;
import validators.AccuracyValidator;
import validators.IValidator;
import validators.SizeValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleDataLoader extends DataLoader {

    protected short loadSize(BufferedReader bufferedReader) throws NumberFormatException, IOException {
        IValidator<Short> validator = new SizeValidator();
        short size;
        do {
            System.out.print("Enter the number of lines:\n> ");
            String sizeString = bufferedReader.readLine();
            size = Short.parseShort(sizeString);
        } while (!validator.test(size));
        return size;
    }

    protected double loadAccuracy(BufferedReader bufferedReader) throws NumberFormatException, IOException {
        IValidator<Double> validator = new AccuracyValidator();
        double accuracy;
        do {
            System.out.print("Enter accuracy:\n> ");
            String accuracyString = bufferedReader.readLine();
            accuracy = Double.parseDouble(accuracyString);
        } while (!validator.test(accuracy));
        return accuracy;
    }

    @Override
    public DataContainer load() throws IOException, NumberFormatException, MatrixLoadingException {
        short size;
        double accuracy;
        double[][] matrix;
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        size = loadSize(bufferedReader);
        accuracy = loadAccuracy(bufferedReader);
        System.out.print("Enter the matrix lines:\n> ");
        matrix = loadMatrix(bufferedReader, size);
        return new DataContainer(size, accuracy, matrix);
    }
}
