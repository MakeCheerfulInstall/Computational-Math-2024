import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.FileWriter;
import java.util.Scanner;

public class SolveSystemNonlinear {
    private class Result{
        double solutionX;
        double solutionY;
        double value1;
        double value2;
        double itr1;
        double itr2;
        int num;
        public void print(){
            System.out.println("x = "+ solutionX + " | y = " + solutionY);
            System.out.println("f1: " + value1);
            System.out.println("f2: " + value2);
            System.out.println("Kоличества итераций, за которое было найдено решение: " + num);
            System.out.println("Вектора погрешностей: (" +itr1 + " ; " + itr2 + ")");
        }
        public void file(){
            try {
                FileWriter fw = new FileWriter("C:\\Users\\asgat\\OneDrive\\Máy tính\\WorkSpace\\IdeaProjects\\Math\\Lab2\\src\\FileInput\\SystemNonlinear.txt");
                fw.write("x = "+ solutionX + " | y = " + solutionY +"\n");
                fw.write("f1: " + value1 +"\n");
                fw.write("f2: " + value2 +"\n");
                fw.write("Kоличества итераций, за которое было найдено решение: " + num +"\n");
                fw.write("Вектора погрешностей: (" +itr1 + " ; " + itr2 + ")" + "\n");
                fw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("Success...");
        }
    }
    SystemNonlinearEquation f;
    public SolveSystemNonlinear(){ getInputFunction();}
    private void getInputFunction(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите функцию");
        System.out.println("1) x = 0.3 - 0.1x² - 0.2y²");
        System.out.println("   y = 0.7 - 0.2x² - 0.1xy");
        System.out.println();
        System.out.println("Выберите функцию");
        System.out.println("2) sin(y + 0.5) - x = 1");
        System.out.println("   y + cos(x - 2) = 0");
        System.out.print("Выберите функцию из списка: ");
        f = new SystemNonlinearEquation(sc.nextInt());
    }
    public void Solve() {
        Scanner scanner = new Scanner(System.in);
        double epsilon, x, y;
        while (true) {
            System.out.print("Введите приближение x: ");
            x = Double.parseDouble(scanner.nextLine());
            System.out.print("Введите приближение y: ");
            y = Double.parseDouble(scanner.nextLine());
            while (true) {
                System.out.print("Введите точность: ");
                epsilon = Double.parseDouble(scanner.nextLine());
                if (epsilon > 0 && epsilon < 1)
                    break;
                else
                    System.out.print("Точность должна быть больше 0 и меньше 1.");
            }
            break;
        }
        Result res = simpleIterationMethod(x, y, epsilon);
        if(choice() == '+') res.file();
        else res.print();
        drawGraph(-10, 10, res);
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
    private Result simpleIterationMethod(double x, double y, double epsilon){
        Result res = new Result();
        double x0 = x, y0 = y;
        if(Math.max((Math.abs(f.dx(x,y,1)) + Math.abs(f.dy(x,y,1))),(Math.abs(f.dx(x,y,2)) + Math.abs(f.dy(x,y,2)))) >= 1) {
            System.out.println(f.dx(x,y,1));
            System.out.println("условие сходимости итерационного процесса не выполнено");
        }
        while(true){
            res.num++;
            x = f.g_x(x0,y0);
            y = f.g_y(x0,y0);
            if(Math.max(Math.abs(x - x0), Math.abs(y-y0)) <= epsilon){
                res.solutionX = x;
                res.solutionY = y;
                res.value1 = f.f1(x,y);
                res.value2 = f.f2(x,y);
                res.itr1 = x - x0;
                res.itr2 = y - y0;
                break;
            }
            x0 = x;
            y0 = y;
        }
        return res;
    }
    private void drawGraph(double a, double b, Result ans) {

        //Function 1
        double[] yValuesFunction1_Positive = linspace(a,b,2000);
        double[] xValuesFunction1_Positive = new double[yValuesFunction1_Positive.length];
        for(int i = 0; i < xValuesFunction1_Positive.length; i++)
            xValuesFunction1_Positive[i] = f.g_x_positive(yValuesFunction1_Positive[i]);

        double[] yValuesFunction1_Negative = linspace(a,b,2000);
        double[] xValuesFunction1_Negative = new double[yValuesFunction1_Negative.length];
        for(int i = 0; i < xValuesFunction1_Negative.length; i++)
            xValuesFunction1_Negative[i] = f.g_x_negative(yValuesFunction1_Negative[i]);

        //Function 2
        double[] xValuesFunction2 = linspace(a,b,2000);
        double[] yValuesFunction2 = new double[xValuesFunction2.length];
        for(int i = 0; i < xValuesFunction2.length; i++)
            yValuesFunction2[i] = f.g_y(xValuesFunction2[i], yValuesFunction2[i]);

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

                int xOffset = width / 2;
                int yOffset = height / 2;

                g2.setColor(Color.BLUE);
                for (int i = 1; i < yValuesFunction1_Positive.length; i++) {
                    double x1 = xValuesFunction1_Positive[i - 1] * 50 + xOffset;
                    double y1 = yOffset - yValuesFunction1_Positive[i - 1] * 50;
                    double x2 = xValuesFunction1_Positive[i] * 50 + xOffset;
                    double y2 = yOffset - yValuesFunction1_Positive[i] * 50;
                    g2.draw(new Line2D.Double(x1, y1, x2, y2));
                }
                for (int i = 1; i < yValuesFunction1_Negative.length; i++) {
                    double x1 = xValuesFunction1_Negative[i - 1] * 50 + xOffset;
                    double y1 = yOffset - yValuesFunction1_Negative[i - 1] * 50;
                    double x2 = xValuesFunction1_Negative[i] * 50 + xOffset;
                    double y2 = yOffset - yValuesFunction1_Negative[i] * 50;
                    g2.draw(new Line2D.Double(x1, y1, x2, y2));
                }

                g2.setColor(Color.RED);
                for (int i = 1; i < xValuesFunction2.length; i++) {
                    double x1 = xValuesFunction2[i - 1] * 50 + xOffset;
                    double y1 = yOffset - yValuesFunction2[i - 1] * 50;
                    double x2 = xValuesFunction2[i] * 50 + xOffset;
                    double y2 = yOffset - yValuesFunction2[i] * 50;
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

                g2.draw(new Rectangle((int) (ans.solutionX * 50 + xOffset - 5), (int) (yOffset - ans.solutionY * 50 - 5), 10,10));
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
