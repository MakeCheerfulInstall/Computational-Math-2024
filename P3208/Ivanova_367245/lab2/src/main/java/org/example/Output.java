package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class Output {
    public void consoleOutput(double root, int iterations, double functionValue){
        System.out.println("Корень уравнения: " + root);
        System.out.println("Число итераций: " + iterations);
        System.out.println("Значение функции в корне: " + functionValue);
    }

    public void fileOutput(double root, int iterations, double functionValue){
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/output.txt", false);
            fileWriter.write("Корень уравнения: " + root);
            fileWriter.write('\n');
            fileWriter.write("Число итераций: " + iterations);
            fileWriter.write('\n');
            fileWriter.write("Значение функции в корне: " + functionValue);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
