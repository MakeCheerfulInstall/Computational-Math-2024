package lab2.prepareres;

import Abdullin_367039.lab2.equations.Algebraic;
import Abdullin_367039.lab2.equations.Equation;
import Abdullin_367039.lab2.prepareres.EquationPreparer;
import Abdullin_367039.lab2.utils.ConsoleWorker;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class AlgebraicEquationPreparer implements EquationPreparer {
  private static final String MAX_ROW = "> Введите максимальную степень уравнения, от 2:";
  private static final String PRINT_KOEF = "> Введите коэффициенты:";

  @Override
  public Equation prepare() {
    var ks = getKoef(getRow());
    return new Algebraic(ks);
  }

  private int getRow() {
    System.out.println(MAX_ROW);

    return ConsoleWorker.getObjectsFromConsole(1, (Integer::parseInt), (value) -> value > 1)
        .iterator()
        .next();
  }

  private List<BigDecimal> getKoef(int row) {
    System.out.println(PRINT_KOEF);

    return ConsoleWorker.getObjectsFromConsole(
        row+1, ((value) -> BigDecimal.valueOf(Double.parseDouble(value))), (Objects::nonNull));
  }
}
