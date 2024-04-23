package dataloaders;

import dataclasses.DataContainer;
import exceptions.MatrixLoadingException;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class DataLoader {
    private final int MAX_NUMBER_LENGTH = 10;

    protected double[][] loadMatrix(BufferedReader bufferedReader, final short size) throws IOException, NumberFormatException, MatrixLoadingException {
        double[][] matrix = new double[size][size + 1];
        for (int i = 0; i < size; i++) {
            String[] numStrings = bufferedReader.readLine().replaceAll("\\s+", " ").split(" ");
            if (numStrings.length != size + 1) throw new MatrixLoadingException();
            for (int j = 0; j < size + 1; j++) {
                String line = numStrings[j].replace(",", ".");
                line = line.length() > MAX_NUMBER_LENGTH ? line.substring(0, MAX_NUMBER_LENGTH) : line;
                matrix[i][j] = Double.parseDouble(line);
            }
        }
        return matrix;
    }

    public abstract DataContainer load() throws IOException, NumberFormatException, MatrixLoadingException;
}
