package lab1.commands;



import lab1.IContext;
import lab1.algebra.Jacobi;
import lab1.algebra.SystemSolver;
import lab1.exceptions.NotFileFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class SolveMatrix implements ICommand {
    private int size = -1;
    private double[][] matrix;
    private final SystemSolver solver = new SystemSolver();

    @Override
    public void execute(IContext context, String[] args) {
        if (args.length == 1) {
            readFromConsole(context, context.getReader());
        } else if(args[1].equals("-f")) {
            context.print("Чтение матрицы с файла "+ args[2] +"...\n");
            readFromFile(context, args[2]);
        } else if (args[1].equals("-s")) {
            readFromConsole(context, context.getReader());
        } else if (args[1].equals("-r")) {
            size = Integer.parseInt(args[2]);
            generateRandomMatrix(size);
        }
        if ((size < 1 || size > 20) || matrix == null) {
            throw new NumberFormatException();
        }

        Jacobi jacobi = new Jacobi(matrix, size);
        context.print(jacobi.printSystem() + "\n");
        context.print("Проверка матрицы на диагональное преобладание...\n");
        if (!jacobi.makeDominant()) {
            context.print("Нельзя привести матрицу к диагональному преобладанию. Итерационный метод может не сходиться\n");
            context.print("Попытка решить систему...\n");
        } else {
            context.print("Диагональное преобладание было достигнуто!\n");
        }
        if (solver.solveWithJacoby(jacobi, context.getAccuracy())) {
            context.print(jacobi.toString());
        } else {
            context.print("Не удалось решить систему за максимальное количество итераций.\n");
        }
    }


    public void readFromConsole(IContext context, Scanner in) {
        int rows = 0;
        context.print("Введите точность:\n");
        if (in.hasNextLine()) {
            var line = in.nextLine().trim().replace(",", ".");
            if (line.length() > 10) {
                line = line.substring(0, 9);
            }
            double accuracy = Double.parseDouble(line.substring(0, 9));
            context.print("Введенная точность: " + accuracy + "\n");
            context.setAccuracy(accuracy);
        }
        context.print("Введите размер:\n");
        if (in.hasNextLine()) {
            size = Integer.parseInt(in.nextLine().trim().replace(",", "."));
            context.print("Введенный размер: " + size + "\n");
        }
        matrix = new double[size][size+1];
        context.print("Введите матрицу\n");
        reader:
        while(in.hasNextLine() ) {
            for (int i=0; i < matrix.length; i++) {
                String[] line = in.nextLine().trim().split(" ");
                for (int j=0; j < line.length; j++) {
                    var linej = line[j].replace(",",".");
                            if(linej.length() > 10) {
                                linej = linej.substring(0, 9);
                            }
                    matrix[i][j] = Double.parseDouble(linej);
                }
                rows++;
                if (rows == size) break reader;
            }
        }
    }

    public void generateRandomMatrix(int size) {
        double[][] matrix = new double[size][size + 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length + 1; j++) {
                matrix[i][j] = Math.random() * 50 - 25;
            }
        }
        this.matrix = matrix;
    }

    public void readFromFile(IContext context, String filePath) {
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(filePath)));
            readFromConsole(context, sc);
        } catch (FileNotFoundException e) {
            throw new NotFileFoundException();
        }
    }
}
