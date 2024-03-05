package dataloaders;

import dataclasses.DataContainer;
import exceptions.MatrixLoadingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class RandomDataLoader extends ConsoleDataLoader {

    protected double[][] createRandomMatrix(short size) {
        double[][] matrix = new double[size][size + 1];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                matrix[i][j] = random.nextDouble();
            }
        }
        for (int i = 0; i < size; i++) {
            matrix[i][i] = Arrays.stream(matrix[i]).sum() - matrix[i][i] + 1;
        }
        return matrix;
    }

    @Override
    public DataContainer load() throws IOException, NumberFormatException {
        short size;
        double accuracy;
        double[][] matrix;
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        size = loadSize(bufferedReader);
        accuracy = loadAccuracy(bufferedReader);
        matrix = createRandomMatrix(size);
        return new DataContainer(size, accuracy, matrix);
    }
}
