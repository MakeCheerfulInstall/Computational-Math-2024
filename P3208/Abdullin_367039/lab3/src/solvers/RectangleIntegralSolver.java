package lab3.solvers;

import Abdullin_367039.lab2.utils.ConsoleWorker;
import Abdullin_367039.lab3.domains.Integral;
import Abdullin_367039.lab3.solvers.IntegralSolver;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class RectangleIntegralSolver implements IntegralSolver {
  private final Map<String, Function<Integral, BigDecimal>> solverMap = new HashMap<>();

  {
    solverMap.put("1", this::applyAlgorithmLeft);
    solverMap.put("2", this::applyAlgorithmRight);
    solverMap.put("3", this::applyAlgorithmMiddle);
  }

  @Override
  public Result solve(Integral integral) {
    throwIfNotConverge(integral);
    var solver = chooseSolver();
    BigDecimal common, duble;
    do {
      common = solver.apply(integral);
      integral.setN(integral.getN() * 2);
      duble = solver.apply(integral);
    } while (integral.getEps().compareTo(common.subtract(duble).abs()) < 0);

    return prepareResult(duble, integral.getN());
  }

  private Function<Integral, BigDecimal> chooseSolver() {
    ConsoleWorker.printMap(
        solverMap,
        "> Выберите модификацию метода прямоугольников",
        List.of(
            "Метод прямоугольников (левых)",
            "Метод прямоугольников (правых)",
            "Метод прямоугольников (средних)"));
    return ConsoleWorker.getObjectsFromConsole(1, solverMap::get, Objects::nonNull)
        .iterator()
        .next();
  }

  private BigDecimal applyAlgorithmLeft(Integral integral) {
    BigDecimal x = integral.getLeft(), h = getH(integral), sum = BigDecimal.ZERO;

    for (int i = 0; i <= integral.getN(); i++) {
      sum = sum.add(integral.getFunction().apply(x));
      x = x.add(h);
    }

    return sum.multiply(h);
  }

  private BigDecimal applyAlgorithmMiddle(Integral integral) {
    BigDecimal startX = integral.getLeft(),
        nextX,
        middle,
        h = getH(integral),
        sum = BigDecimal.ZERO;

    for (int i = 0; i < integral.getN(); i++) {
      nextX = startX.add(h);
      middle = startX.add(nextX).divide(BigDecimal.valueOf(2), 20, RoundingMode.HALF_UP);
      sum = sum.add(integral.getFunction().apply(middle));
      startX = nextX;
    }

    return sum.multiply(h);
  }

  private BigDecimal applyAlgorithmRight(Integral integral) {
    BigDecimal x = integral.getLeft(), h = getH(integral), sum = BigDecimal.ZERO;
    x = x.add(h);

    for (int i = 1; i <= integral.getN(); i++) {
      sum = sum.add(integral.getFunction().apply(x));
      x = x.add(h);
    }

    return sum.multiply(h);
  }

  @Override
  public String toString() {
    return "Метод прямоугольников";
  }
}
