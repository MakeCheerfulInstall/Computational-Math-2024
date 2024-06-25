package lab4;

import lab4.entity.Dot;
import lab4.entity.DotCollection;
import lab4.util.Reader;
import lab4.graph.Graphic;
import lab4.util.Printer;
import lab4.work.Approximation;

import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
        Dot[] dots = Reader.readDots();
        DotCollection.setDots(dots);
        Approximation.run();
        Printer.printResult(Approximation.getFinalNumberApprox());
        new Graphic().run();
        } catch (NumberFormatException | NoSuchElementException e) {
            System.out.println("Введены неверные данные");
        }
    }
}