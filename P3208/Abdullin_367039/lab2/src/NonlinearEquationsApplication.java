package lab2;

import Abdullin_367039.lab2.Graph;
import Abdullin_367039.lab2.SystemFrame;
import Abdullin_367039.lab2.corrections.Refinement;
import Abdullin_367039.lab2.corrections.SystemSimpleIterationRefinement;
import Abdullin_367039.lab2.corrections.Value;
import Abdullin_367039.lab2.equations.Equation;
import Abdullin_367039.lab2.solvers.NonlinearEquationSolver;
import Abdullin_367039.lab2.solvers.NonlinearSystemSolver;
import Abdullin_367039.lab2.utils.ConsoleWorker;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class NonlinearEquationsApplication {
  private static final String CHOOSE_SYSTEM = "> Выберите систему: ";

  private static final String GREETINGS =
      "> Здравствуйте, выберите, что хотите решить: НЛАУ(1), Нелинейное уравнение(2)";
  private static final String RESOURCE_PACKAGE = "P3208/Abdullin_367039/src/lab2/resources";
  private static final String CHOOSE_LINE = "> Выберите интервал:";
  private static final String CHOOSE_CLOSE = "> Выберите приближение:";

  private static final String CHOOSE_EPSILON = "> Выберите точность:";
  private static final String CHOOSE_INPUT =
      "> Выберите формат ввода интервала и точности: file(1), console(2)";
  private static final String CHOOSE_OUTPUT =
      "> Выберите формат вывода ответа: file(1), console(2)";

  public static void main(String[] args) {
    System.out.println(GREETINGS);
    String chooseObj = getAgree();

    if (chooseObj.equals("1")) {
      doSystem();
    } else if (chooseObj.equals("2")) {
      doNonlinearEquation();
    }
  }

  private static void doSystem() {
    var solver = new NonlinearSystemSolver();
    String key = chooseRecurrentKey();
    List<BinaryOperator<Double>> system = solver.getRecurrentSystem(key);
    var equation = new SystemSimpleIterationRefinement();

    System.out.println(CHOOSE_EPSILON);
    double eps =
        ConsoleWorker.getObjectsFromConsole(1, Double::parseDouble, Objects::nonNull)
            .iterator()
            .next();

    System.out.println(CHOOSE_CLOSE);
    double[] close =
        ConsoleWorker.getObjectsFromConsole(2, Double::parseDouble, Objects::nonNull).stream()
            .mapToDouble(Double::doubleValue)
            .toArray();

    SystemSimpleIterationRefinement.Answer ans = equation.solve(system, close, eps);
    System.out.println(CHOOSE_OUTPUT);

    String an = getAgree();
    System.out.println(Arrays.toString(ans.getAnswer()));
    optionalWrite(an, ans.getHistory());

    SwingUtilities.invokeLater(() -> new SystemFrame(solver.getGraph(key)).setVisible(true));
  }

  private static void doNonlinearEquation() {
    var solver = new NonlinearEquationSolver();
    List<Equation> eq = solver.getEquation();
    var equation = eq.iterator().next();
    Refinement refinement = solver.chooseRefinement();

    System.out.println(CHOOSE_INPUT);

    String answer = getAgree();

    double[] line = null;
    BigDecimal eps = null;
    if (answer.equals("1")) {
      List<String> lines = read();
      line = Arrays.stream(lines.get(0).split(" ")).mapToDouble(Double::parseDouble).toArray();
      eps = BigDecimal.valueOf(Double.parseDouble(lines.get(1)));
    } else if (answer.equals("2")) {
      System.out.println(CHOOSE_LINE);
      line =
          ConsoleWorker.getObjectsFromConsole(2, (Double::parseDouble), (Objects::nonNull)).stream()
              .mapToDouble(Double::doubleValue)
              .toArray();
      System.out.println(CHOOSE_EPSILON);
      eps =
          ConsoleWorker.getObjectsFromConsole(
                  1, ((l) -> BigDecimal.valueOf(Double.parseDouble(l))), (Objects::nonNull))
              .iterator()
              .next();
    }

    Value value = refinement.solve(equation, line, eps);
    System.out.println(CHOOSE_OUTPUT);

    String an = getAgree();

    String a =
        String.format(
            "> Корень (функции %s, на отрезке: %s) равен %s",
            equation, Arrays.toString(line), value.getValue().doubleValue());

    optionalWrite(an, a);
    draw(equation, line);
  }

  private static void optionalWrite(String an, String a) {
    if (an.equals("1")) {

      String name =
          "P3208/Abdullin_367039/src/lab2/results/"
              + "answer"
              + System.currentTimeMillis() % 1000
              + ".txt";

      Path p = Paths.get(name);
      try {
        Files.writeString(p, a);
      } catch (IOException e) {
        System.out.println(e.getMessage());
        System.err.print("Ошибка при записи в файл");
      }
    } else if (an.equals("2")) {
      System.out.println(a);
    }
  }

  private static String chooseRecurrentKey() {
    System.out.println(CHOOSE_SYSTEM);
    System.out.println("> Первая(1) :");
    System.out.println("{ sin(x+1) - y = 1.2 \n{ 2x + cos(y) = 2");
    System.out.println("> Вторая(2) :");
    System.out.println(
        "{ f_1(x_1, x_2) = 0.1(x_1)^2 + x_1 + 0.2(x_2)^2 - 0.3 = 0 \n{ f_2(x_1, x_2) = 0.2(x_1)^2 + x_2 + 0.1*x_1*x_2 - 0.7 = 0");
    return ConsoleWorker.getObjectsFromConsole(
            1, String::trim, line -> line.equals("1") || line.equals("2"))
        .iterator()
        .next();
  }

  public static List<String> read() {
    var list = getAllResources();
    printFiles(list);

    int ans =
        ConsoleWorker.getObjectsFromConsole(
                1, Integer::parseInt, val -> (val >= 0 && val < list.size()))
            .iterator()
            .next();

    Path cur = list.get(ans);

    try (Stream<String> stream = Files.lines(cur)) {
      return stream.filter(line -> !line.isBlank()).toList();
    } catch (IOException | RuntimeException e) {
      System.err.println("Problem with reading resource file");
      System.out.println(e.getMessage());
      System.exit(-1);
      return null;
    }
  }

  private static void printFiles(List<Path> list) {
    StringBuilder builder = new StringBuilder();
    builder.append("> Choose resource-file: ");
    for (int i = 0; i < list.size(); i++) {
      builder.append(String.format("%s(%s)", list.get(i).getFileName(), i));
      if (list.size() - 1 != i) {
        builder.append(", ");
      }
    }
    System.out.println(builder);
  }

  private static List<Path> getAllResources() {
    try (Stream<Path> files =
        Files.walk(Paths.get(RESOURCE_PACKAGE)).filter(Files::isRegularFile)) {
      return files.toList();
    } catch (IOException e) {
      System.err.println("File system error");
      System.exit(-1);
    }
    return null;
  }

  private static void draw(Equation e, double[] line) {
    EventQueue.invokeLater(
        () -> {
          Abdullin_367039.lab2.Graph demo = new Graph(e, line, "График функции");
          demo.pack();
          demo.setLocationRelativeTo(null);
          demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          demo.setVisible(true);
        });
  }

  private static String getAgree() {
    return ConsoleWorker.getObjectsFromConsole(
            1, String::trim, line -> line.equals("1") || line.equals("2"))
        .iterator()
        .next();
  }
}
