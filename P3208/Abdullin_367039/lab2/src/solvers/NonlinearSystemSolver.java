package lab2.solvers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

public class NonlinearSystemSolver {
  private static final Map<String, String> graphs =
      new HashMap<>() {
        {
          put("1", "P3208/Abdullin_367039/src/lab2/first.png");
          put("2", "P3208/Abdullin_367039/src/lab2/second.png");
        }
      };
  private static final Map<String, List<BinaryOperator<Double>>> recurrentEq =
      new HashMap<>() {
        {
          put(
              "1",
              new ArrayList<>() {
                {
                  add((x, y) -> (2.0 - Math.cos(y)) / 2.0);
                  add((x, y) -> Math.sin(x + 1.0) - 1.2);
                }
              });
          put(
              "2",
              new ArrayList<>() {
                {
                  add((x, y) -> 0.3 - 0.1 * Math.pow(x, 2) - 0.2 * Math.pow(y, 2));
                  add((x, y) -> 0.7 - 0.2 * Math.pow(x, 2) - 0.1 * x * y);
                }
              });
        }
      };

  public List<BinaryOperator<Double>> getRecurrentSystem(String key) {
    return recurrentEq.get(key);
  }

  public String getGraph(String key) {
    return graphs.get(key);
  }
}
