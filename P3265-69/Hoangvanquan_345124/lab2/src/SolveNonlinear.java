import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SolveNonlinear {
    private class Answers{
        double solution;
        double value;
        double iteration;
        void printAnswer(){
            System.out.println("Корень уравнения: " + solution);
            System.out.println("Значение функции в корне: " + value);
            System.out.println("Число итераций: " + iteration);
        }
        public void file(){
            try {
                FileWriter fw = new FileWriter("C:\\Users\\asgat\\OneDrive\\Máy tính\\WorkSpace\\IdeaProjects\\Math\\Lab2\\src\\FileInput\\Nonlinear.txt");
                fw.write("Корень уравнения: " + solution +"\n");
                fw.write("Значение функции в корне: " + value +"\n");
                fw.write("Число итераций: " + iteration +"\n");
                fw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("Success...");
        }
    }
    private Answers ans;
    static Scanner sc = new Scanner(System.in);
    private double epsilon;
    private final String FILE_INPUT = "C:\\Users\\asgat\\OneDrive\\Máy tính\\WorkSpace\\IdeaProjects\\Math\\Lab2\\src\\FileInput\\input.txt";
    private int method;
    private NonlinearEquation f;
    private double a;
    private double b;
    private double x0;
    private void getMethod(){
        System.out.println("Выберите метод решения.");
        System.out.println(" 1 - Метод хорд");
        System.out.println(" 2 - Метод секущих");
        System.out.println(" 3 - Метод простой итерации");
        method = sc.nextInt();
        while(method != 1 && method != 2 && method != 3){
            System.out.println("Выберите метод решения из списка.");
            method = sc.nextInt();
        }
    }
    public void getdata_input(NonlinearEquation f) throws Exception {
        this.f = f;
        getMethod();
        System.out.println("Выберите границы интервала.");
        while (true) {
            try {
                System.out.print("Границы интервала: ");
                Scanner sc = new Scanner(System.in);
                double a0 = sc.nextDouble();
                double b0 = sc.nextDouble();
                if (a0 > b0) {
                    double temp = a0;
                    a0 = b0;
                    b0 = temp;
                } else if (a0 == b0) {
                    throw new ArithmeticException();
                } else if (f.apply(a0) * f.apply(b0) > 0) {
                    throw new IllegalAccessException();
                }
                a = a0;
                b = b0;
                break;
            } catch (InputMismatchException e) {
                System.out.println("Границы интервала должны быть числами, введенными через пробел.");
            } catch (ArithmeticException e) {
                System.out.println("Границы интервала не могут быть равны.");
            } catch (IllegalAccessException e) {
                System.out.println("Интервал содержит ноль или несколько корней.");
            }
        }
        if(method == 3){
            double x;
            System.out.println("Выберите начальное приближение.");
            while (true) {
                try {
                    System.out.print("Начальное приближение: ");
                    x = sc.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Начальное приближение должно быть числом.");
                } catch (Exception exception){
                    System.out.println(exception);
                }
            } x0 = x;
        }

        System.out.println("Выберите погрешность вычисления.");
        double e;
        while(true){
            try{
                System.out.print("Погрешность вычисления: ");
                e = sc.nextDouble();
                if(e < 0) throw new ArithmeticException();
                break;
            } catch (ArithmeticException exc){
                System.out.println("Погрешность вычисления должна быть положительным числом.");
            }
            catch (Exception exception){
                System.out.println(exception);
            }
        } epsilon = e;
    }
    public void getdata_File() {
        try{
            Scanner sc = new Scanner(new File(FILE_INPUT));
            int functionNumber = sc.nextInt();
            f = new NonlinearEquation(functionNumber);
            method = sc.nextInt();
            a = sc.nextDouble();
            b = sc.nextDouble();
            epsilon = sc.nextDouble();
            if(method == 3) x0 = sc.nextDouble();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл не доступен");
        }
    }
    public void Result(){
        char choice = choice();
        switch (method){
            case 1:
                hordMethod();
                break;
            case 2:
                secanMethod();
                break;
            case 3:
                simpleIterationMethod();
                break;
        }
        if(choice == '+') ans.file();
        else ans.printAnswer();
        drawGraph();
    }

    // Метод хорд
    private void hordMethod() {
        ans = new Answers();
        double x = a - ((b-a) * f.apply(a) )/(f.apply(b) - f.apply(a));
        if(f.apply(a) * f.apply(x) < 0) b = x;
        if(f.apply(x) * f.apply(b) < 0) a = x;

        while(true) {
            double x1 = a - ((b - a) * f.apply(a)) / (f.apply(b) - f.apply(a));
            if (Math.abs(x1 - x) <= epsilon || Math.abs(a - b) <= epsilon || Math.abs(f.apply(x1)) <= epsilon) {
                ans.solution = x1;
                ans.value = f.apply(x1);
                ans.iteration = Math.abs(x1 - x);
                break;
            }
            x = x1;
            if (f.apply(a) * f.apply(x) < 0) b = x;
            if (f.apply(x) * f.apply(b) < 0) a = x;
        }
    }
    private void secanMethod() {
        ans = new Answers();
        double x = a;
        if(f.apply(a) * f.d(2, a) > 0) x = a;
        if(f.apply(b) * f.d(2, b) > 0) x = b;
        double x1 = x - f.apply(x)/f.d(1,x);
        while(true){
            if(Math.abs(x1 - x) <= epsilon || Math.abs(f.apply(x1)) <= epsilon){
                ans.solution = x1;
                ans.value = f.apply(x1);
                ans.iteration = Math.abs(x1 - x);
                break;
            }
            double temp = x1;
            x1 -= (x1 - x)*f.apply(x1)/(f.apply(x1)-f.apply(x));
            x = temp;
        }
    }
    private void simpleIterationMethod() {
        ans = new Answers();
        Phi phi = new Phi(a,b,f);
        if(Math.max(Math.abs(f.d(1,a)), Math.abs(f.d(1,b))) < 1) System.out.println("Условие сходимости ВЫПОЛНЯЕТСЯ");
        else System.out.println("Условие сходимости НЕ ВЫПОЛНЯЕТСЯ");
        while(true){
            double x1 = phi.apply(x0);
            if(Math.abs(x1 - x0) <= epsilon && Math.abs(f.apply(x1)) <= epsilon){
                ans.solution = x1;
                ans.value = f.apply(x1);
                ans.iteration = Math.abs(x1 - x0);
                break;
            }
            x0 = x1;
        }
    }
    public char choice(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Вывести таблицу трассировки? (+ / -)");
        char logChoice = sc.next().charAt(0);
        while (logChoice != '+' && logChoice != '-') {
            System.out.println("Введите '+' или '-' для выбора, выводить ли таблицу трассировки.");
            logChoice = sc.next().charAt(0);
        }
        return logChoice;
    }
    public void drawGraph() {
        double[] xValues = linspace(a,b, 2000);
        double[] yValues = new double[xValues.length];
        for(int i = 0; i < xValues.length; i++)
            yValues[i] = f.apply(xValues[i]);

        JFrame frame = new JFrame();
        frame.setTitle("График функции");
        frame.setSize(500, 500);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, width, height);
                g2.setColor(Color.BLACK);
                g2.draw(new Line2D.Double(0, height / 2, width, height / 2));
                g2.draw(new Line2D.Double(width / 2, 0, width / 2, height));
                g2.setColor(Color.BLUE);

                int xOffset = width / 2;
                int yOffset = height / 2;

                for (int i = 1; i < xValues.length; i++) {
                    double x1 = xValues[i - 1] * 50 + xOffset;
                    double y1 = yOffset - yValues[i - 1] * 50;
                    double x2 = xValues[i] * 50 + xOffset;
                    double y2 = yOffset - yValues[i] * 50;
                    g2.draw(new Line2D.Double(x1, y1, x2, y2));
                }

                g2.setColor(Color.BLACK);
                for(int i = (int) a - 1; i <= (int) b; i++){
                    double x1 = i * 50 + xOffset;
                    double y1 = yOffset - 0.1 * 50;
                    double x2 = i * 50 + xOffset;
                    double y2 =yOffset + 0.1 * 50;
                    g2.draw(new Line2D.Double(x1, y1, x2, y2));
                }
                for(int i = (int) a - 1; i <= (int) b; i++){
                    double x1 = 0.1 * 50 + xOffset;
                    double y1 = yOffset - i * 50;
                    double x2 = -0.1 * 50 + xOffset;
                    double y2 =yOffset - i * 50;
                    g2.draw(new Line2D.Double(x1, y1, x2, y2));
                }
                g2.draw(new Rectangle((int) (ans.solution * 50 + xOffset - 5), (int) (yOffset - ans.value * 50 - 5), 10,10));
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }
    private double[] linspace(double start, double end, int numPoints) {
        double[] result = new double[numPoints];
        double step = (end - start) / (numPoints - 1);
        for (int i = 0; i < numPoints; i++) {
            result[i] = start + i * step;
        }
        return result;
    }

}