package lab5;

import Abdullin_367039.lab2.utils.ConsoleWorker;
import Abdullin_367039.lab4.PointsFileReader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import Abdullin_367039.lab5.InterpolationPlot;
import org.apache.commons.math3.util.Pair;

import javax.swing.*;

import static java.lang.Double.*;

public class Interpolation {
  private static final Map<String, Method> METHODS_MAP;
  public static final List<Function<Double, Double>> FUNS =
      new ArrayList<>() {
        {
          add(x -> 2 * Math.pow(x, 2) - 8 * x + 1);
          add(x -> Math.pow(x, 0.5) / 2.0);
          add(Math::sin);
        }
      };

  static {
    METHODS_MAP =
        Arrays.stream(Interpolation.class.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(InterpolationMethod.class))
            .collect(
                Collectors.toMap(
                    method -> String.valueOf(method.getAnnotation(InterpolationMethod.class).id()),
                    method -> method));
  }

  public static void finiteDifferenceTable(double[] x, double[] y) {
    int n = y.length;
    String[][] table = new String[n][n + 1];

    for (int i = 0; i < n; i++) {
      table[i][0] = String.format("%.2f", x[i]).replaceAll(",", ".");
      table[i][1] = String.format("%.4f", y[i]).replaceAll(",", ".");
    }

    for (int j = 2; j <= n; j++) {
      for (int i = 0; i < n - j + 1; i++) {
        double difference = parseDouble(table[i + 1][j - 1]) - parseDouble(table[i][j - 1]);
        table[i][j] = String.format("%.5f", difference).replaceAll(",", ".");
      }
    }

    String[] headers;
    if (n < 3) {
      headers = new String[] {"x", "y", "∆y"};
    } else if (n < 5) {
      headers = new String[] {"x", "y", "∆y", "∆∆y"};
    } else {
      headers = new String[] {"x", "y", "∆y", "∆∆y", "∆³y", "∆⁴y", "∆⁵y"};
    }

    PrettyTable pt = new PrettyTable(headers);
    for (String[] row : table) {
      pt.addRow(row);
    }
    pt.print();
  }

  @InterpolationMethod(id = 1, name = "Лагранжа")
  public static double lagrange(double[] x, double[] y, double value) {
    double result = 0;
    for (int i = 0; i < x.length; i++) {
      double c1 = 1, c2 = 1;
      for (int j = 0; j < x.length; j++) {
        if (i != j) {
          c1 *= value - x[j];
          c2 *= x[i] - x[j];
        }
      }
      result += y[i] * c1 / c2;
    }
    return result;
  }

  @InterpolationMethod(id = 2, name = "Ньютона с разделенными разностями")
  public static double newtonSeparatedDifferences(double[] x, double[] y, double value) {
    double[][] f = subNewtonSeparatedDifferencesCreateTable(x, y);
    double result = y[0];
    for (int j = 1; j < f[0].length; j++) {
      double temp = f[0][j];
      for (int i = 0; i < j; i++) {
        temp *= (value - x[i]);
      }
      result += temp;
    }
    return Math.round(result * 100000) / 100000.0;
  }

  private static double[][] subNewtonSeparatedDifferencesCreateTable(double[] x, double[] y) {
    int n = y.length;
    double[][] f = new double[n][n];
    for (int i = 0; i < n; i++) {
      f[i][0] = y[i];
    }
    for (int j = 1; j < n; j++) {
      for (int i = 0; i < n - j; i++) {
        f[i][j] = (f[i + 1][j - 1] - f[i][j - 1]) / (x[i + j] - x[i]);
      }
    }
    return f;
  }

  private static double[][] subNewtonCreateTable(double[] y) {
    int n = y.length;
    double[][] table = new double[n][n];
    for (int i = 0; i < n; i++) {
      table[i][0] = y[i];
    }
    for (int j = 1; j < n; j++) {
      for (int i = 0; i < n - j; i++) {
        table[i][j] = table[i + 1][j - 1] - table[i][j - 1];
      }
    }
    return table;
  }

  @InterpolationMethod(id = 3, name = "Ньютона с конечными разностями")
  public static double newtonFiniteDifferences(double[] x, double[] y, double value) {
    double[][] table = subNewtonCreateTable(y);
    double t, result;
    if (value <= x[x.length - 1]) {
      int x0 = 0;
      for (int i = x.length - 1; i >= 0; i--) {
        if (value >= x[i]) {
          x0 = i;
          break;
        }
      }
      t = (value - x[x0]) / (x[1] - x[0]);
      result = table[x0][0];
      for (int i = 1; i < table[x0].length; i++) {
        double temp = t;
        for (int yi = 1; yi < i; yi++) temp *= (t - yi);
        result += (temp * table[x0][i]) / factorial(i);
      }
    } else {
      t = (value - x[x.length - 1]) / (x[1] - x[0]);
      result = table[x.length - 1][0];
      for (int i = 1; i < x.length; i++) {
        double temp = t;
        for (int yi = 1; yi < i; yi++) temp *= (temp + yi);
        result += (temp * table[x.length - i - 1][i]) / factorial(i);
      }
    }
    return Math.round(result * 100000) / 100000.0;
  }

  private static double[][] createTableGauss(double[] y) {
    ArrayList<double[]> result = new ArrayList<>();
    result.add(y);
    for (int i = 0; i < y.length - 1; i++) {
      double[] divDif = new double[result.get(i).length - 1];
      for (int j = 0; j < divDif.length; j++) {
        divDif[j] = result.get(i)[j + 1] - result.get(i)[j];
      }
      result.add(divDif);
    }
    double[][] resultArray = new double[result.size()][];
    for (int i = 0; i < result.size(); i++) {
      resultArray[i] = result.get(i);
    }
    return resultArray;
  }

  @InterpolationMethod(id = 4, name = "Стирлинга")
  public static double stirling(double[] x, double[] y, double value) {
    if (y.length % 2 == 0) {
      System.out.println("Четное число узлов. Формула Стирлинга не применяется");
      return NaN;
    }
    double[][] table = createTableGauss(y);
    int mid = y.length / 2;
    double h = x[1] - x[0];
    double t = (value - x[mid]) / h;
    if (Math.abs(t) > 0.25) {
      System.out.println("Результат по формуле Стирлинга содержит большую погрешность");
    }
    double result = y[mid];
    for (int i = 1; i <= mid; i++) {
      double mul = 1;
      for (int j = 1; j < i; j++) {
        mul *= (t * t - j * j);
      }
      result +=
          t
              * mul
              * (table[2 * i - 1][mid - i] + table[2 * i - 1][mid - i + 1])
              / (2 * factorial(2 * i - 1));
      result += t * t * mul * (table[2 * i][mid - i]) / factorial(2 * i);
    }
    return result;
  }

  @InterpolationMethod(id = 5, name = "Бесселя")
  public static double bessel(double[] x, double[] y, double value) {
    if (y.length % 2 != 0) {
      System.out.println("Нечетное число узлов. Формула Бесселя не применяется");
      return NaN;
    }
    double[][] table = createTableGauss(y);
    int mid = y.length / 2;
    double h = x[1] - x[0];
    double t = (value - x[mid]) / h;
    if (Math.abs(t) < 0.25 || Math.abs(t) > 0.75) {
      System.out.println("Результат по формуле Бесселя содержит большую погрешность");
    }
    double result = (y[mid] + y[mid + 1]) / 2 + (t - 0.5) * table[1][mid];
    for (int i = 2; i < mid; i++) {
      double mul = 1;
      for (int j = 0; j < i; j++) {
        mul *= (t + Math.pow(-1, j) * j);
      }
      int n = i - 1;
      result +=
          mul * (table[2 * n][mid - n] + table[2 * i - 2][mid - n + 1]) / (2 * factorial(2 * n));
      result += (t - 0.5) * mul * (table[2 * n + 1][mid - n]) / factorial(2 * n + 1);
    }
    return result;
  }

  private static int factorial(int n) {
    if (n == 0) return 1;
    int fact = 1;
    for (int i = 1; i <= n; i++) {
      fact *= i;
    }
    return fact;
  }

  public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
    System.out.println("> Выберите input: 1. Файл; 2. Консоль; 3. Выбрать функцию");

    int res =
        ConsoleWorker.getObjectsFromConsole(1, Integer::parseInt, Objects::nonNull)
            .iterator()
            .next();

    Pair<double[], double[]> points = null;
    if (res == 1) {
      points = getPointsFromFile();
    } else if (res == 2) {
      points = getPointsFromConsole();
    } else if (res == 3) {
      points = getPointsFromDeclaredFunction();
    }

    double[] x;
    double[] y;
    x = points.getFirst();
    y = points.getSecond();
    System.out.println("> Введите значение:");
    double val =
        ConsoleWorker.getObjectsFromConsole(1, Double::parseDouble, Objects::nonNull)
            .iterator()
            .next();

    finiteDifferenceTable(x, y);
    System.out.println("> Хотите использовать все методы? (да/нет == все кроме 'да')");

    String agreement =
        ConsoleWorker.getObjectsFromConsole(1, line -> line, Objects::nonNull).iterator().next();
    if (agreement.equals("да")) {
      invokeAll(x, y, val);
      return;
      // todo: draw graphs
    }
    printMethods();

    Method chosen =
        ConsoleWorker.getObjectsFromConsole(1, METHODS_MAP::get, Objects::nonNull)
            .iterator()
            .next();

    System.out.printf("> x = %s, ", val);
    double result = (double) chosen.invoke(Interpolation.class, x, y, val);
    System.out.printf("y = %s", result);
  }

  private static void invokeAll(double[] x, double[] y, double val) {
    System.out.printf("> x = %s%n", val);
    METHODS_MAP.forEach(
        (id, method) -> {
          InterpolationMethod annotation = method.getAnnotation(InterpolationMethod.class);
          double result;
          try {
            result = (double) method.invoke(Interpolation.class, x, y, val);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
          System.out.printf("%s: %f%n", annotation.name(), result);
        });
    SwingUtilities.invokeLater(
        () -> {
          InterpolationPlot example = new InterpolationPlot("Interpolation Plot", x, y);
          example.setSize(800, 600);
          example.setLocationRelativeTo(null);
          example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          example.setVisible(true);
        });
  }

  private static void printMethods() {
    System.out.println("> Выберите метод: ");
    StringBuilder builder = new StringBuilder();
    METHODS_MAP.forEach(
        (id, method) -> {
          InterpolationMethod annotation = method.getAnnotation(InterpolationMethod.class);
          builder.append(String.format("%s) %s\n", id, annotation.name()));
        });
    System.out.print(builder);
  }

  private static Pair<double[], double[]> getPointsFromDeclaredFunction() {
    double[] y;
    double[] x;
    Pair<double[], double[]> pair;
    System.out.println("Выберите функцию");
    System.out.println("1) 2x² - 8x + 1");
    System.out.println("2) (\\|(x)) / 2");
    System.out.println("3) sin(x)");
    Function<Double, Double> f =
        ConsoleWorker.getObjectsFromConsole(
                1, line -> FUNS.get(Integer.parseInt(line)), Objects::nonNull)
            .iterator()
            .next();
    int count = getCount();
    System.out.println("> Введите отрезок: a b");
    Pair<Double, Double> part =
        ConsoleWorker.getObjectsFromConsole(1, Interpolation::getPart, Objects::nonNull)
            .iterator()
            .next();
    x = new double[count];
    y = new double[count];
    double h = (part.getFirst() - part.getSecond()) / (count - 1);
    for (int i = 0; i < count; i++) {
      x[i] = part.getFirst() + i * h;
      y[i] = f.apply(x[i]);
    }
    pair = Pair.create(x, y);
    return pair;
  }

  private static Pair<double[], double[]> getPointsFromConsole() {
    double[] y;
    double[] x;
    Pair<double[], double[]> pair;
    int count = getCount();
    System.out.println("> Вводите точки:");
    List<Pair<Double, Double>> points =
        ConsoleWorker.getObjectsFromConsole(
            count,
            line -> {
              String[] data = line.split(" ");
              return Pair.create(parseDouble(data[0]), parseDouble(data[1]));
            },
            Objects::nonNull);
    x = new double[count];
    y = new double[count];
    for (int i = 0; i < count; i++) {
      x[i] = points.get(i).getFirst();
      y[i] = points.get(i).getSecond();
    }
    pair = Pair.create(x, y);
    return pair;
  }

  private static Pair<double[], double[]> getPointsFromFile() {
    PointsFileReader fileReader =
        new PointsFileReader(
            "/home/yestai/IdeaProjects/Computational-Math-2024/P3208/src/Abdullin_367039/lab5/resources");
    Pair<double[], double[]> points = fileReader.read();
    return points;
  }

  private static Pair<Double, Double> getPart(String line) {
    String[] data = line.split(" ");
    double left = parseDouble(data[0]), right = parseDouble(data[1]);
    return Pair.create(min(left, right), max(left, right));
  }

  private static int getCount() {
    System.out.println("> Введите количество точек:");
    return ConsoleWorker.getObjectsFromConsole(1, Integer::parseInt, i -> i > 0).iterator().next();
  }
}

class PrettyTable {
  private final ArrayList<String[]> rows;
  private final String[] headers;

  public PrettyTable(String[] headers) {
    this.headers = headers;
    this.rows = new ArrayList<>();
  }

  public void addRow(String[] row) {
    rows.add(row);
  }

  public void print() {
    System.out.println(Arrays.toString(headers));
    for (String[] row : rows) {
      System.out.println(Arrays.toString(row));
    }
  }
}

@Retention(RetentionPolicy.RUNTIME)
@interface InterpolationMethod {
  int id();

  String name();
}
