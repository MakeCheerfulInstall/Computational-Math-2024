package lab5;


import lab5.methods.*;
import lab5.util.Drawer;
import lab5.util.Printer;
import lab5.util.Reader;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Reader reader = new Reader();
        reader.readInput();
        var values = Polynomial.getValues();
        var hOld = values.get(1)[0] - values.get(0)[0];
        boolean distanceIsEqual = true;
        double hNew;
        for (int i = 2; i < values.size(); i++) {
            hNew = values.get(i)[0] - values.get(i - 1)[0];
            distanceIsEqual = distanceIsEqual && (Math.abs(hNew - hOld) < 0.000001);
            hOld = hNew;
        }

        double lagrangeResult = new Lagrange().execute();
        Double gaussResult;
        Double newtonResult;
        Double stirlingResult;
        Double besselResult;

        if (distanceIsEqual) {
            System.out.println("\n" + "\u001B[31m" + "Узлы равноотстоящие, метод Ньютона для разделенных разностей неприменим" + "\u001B[0m");
            newtonResult = null;
        } else {
            newtonResult = new Newton().execute();
        }

        if (values.size() % 2 == 0) {
            System.out.println("\n" + "\u001B[31m" +"Количество узлов четное, метод Гаусса и Стирлинга неприменимы" + "\u001B[0m");
            gaussResult = null;
            stirlingResult = null;
        } else {
            gaussResult = new Gauss().execute();
            stirlingResult = new Stirling().execute();
        }

        if (values.size() % 2 == 1) {
            System.out.println("\n" + "\u001B[31m" +"Количество узлов нечетное, метод Бесселя неприменим" + "\u001B[0m");
            besselResult = null;
        } else {
            besselResult = new Bessel().execute();
        }

        Printer.printResult(lagrangeResult, newtonResult, gaussResult, stirlingResult, besselResult);
        Printer.printFiniteDifferenceTable();
        Drawer.drawLagrange();
        Drawer.drawNewton();
    }
}