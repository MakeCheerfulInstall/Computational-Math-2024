package lab3;

import Abdullin_367039.lab2.utils.ConsoleWorker;
import Abdullin_367039.lab3.domains.Integral;
import Abdullin_367039.lab3.integral_suppliers.ConsoleFunctionSupplier;
import Abdullin_367039.lab3.integral_suppliers.FunctionSupplier;
import Abdullin_367039.lab3.solvers.IntegralSolver;
import Abdullin_367039.lab3.solvers.RectangleIntegralSolver;
import Abdullin_367039.lab3.solvers.SimpsonIntegralSolver;
import Abdullin_367039.lab3.solvers.TrapezoidIntegralSolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NumericalIntegrationApplication {
  private final Map<String, IntegralSolver> solverMap =
      new HashMap<>() {
        {
          put("1", new SimpsonIntegralSolver());
          put("2", new TrapezoidIntegralSolver());
          put("3", new RectangleIntegralSolver());
        }
      };
  private final Map<String, FunctionSupplier> suppliers =
      new HashMap<>() {
        {
          put("1", new ConsoleFunctionSupplier());
        }
      };

  public static void main(String[] args) {
    NumericalIntegrationApplication application = new NumericalIntegrationApplication();

    FunctionSupplier supplier = getSupplier(application);
    IntegralSolver solver = getSolver(application);

    Integral integral = supplier.get();

    try {
      IntegralSolver.Result result = solver.solve(integral);
      System.out.printf(
          "> Значение интеграла: %.3f; Число разбиения интервала: %d",
          result.getResult().doubleValue(), result.getEndN());
    } catch (Exception e) {
      System.out.println("> Функция имеет разрыв, значение интеграла не может быть вычислено :(");
    }
  }

  private static IntegralSolver getSolver(NumericalIntegrationApplication application) {
    var solvers = application.getSolverMap();
    ConsoleWorker.printMap(solvers, "> Выберите метод:");

    return ConsoleWorker.getObjectsFromConsole(1, solvers::get, Objects::nonNull).iterator().next();
  }

  private static FunctionSupplier getSupplier(NumericalIntegrationApplication application) {
    var suppliers = application.getSuppliers();
    ConsoleWorker.printMap(suppliers, "> Выберите ввод: ");

    return ConsoleWorker.getObjectsFromConsole(1, suppliers::get, Objects::nonNull)
        .iterator()
        .next();
  }

  public Map<String, IntegralSolver> getSolverMap() {
    return solverMap;
  }

  public Map<String, FunctionSupplier> getSuppliers() {
    return suppliers;
  }
}
