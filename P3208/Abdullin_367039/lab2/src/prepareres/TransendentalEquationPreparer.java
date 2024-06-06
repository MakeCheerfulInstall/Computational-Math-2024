package lab2.prepareres;

import Abdullin_367039.lab2.equations.Equation;
import Abdullin_367039.lab2.equations.Transendental;
import Abdullin_367039.lab2.prepareres.EquationPreparer;
import Abdullin_367039.lab2.utils.ConsoleWorker;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class TransendentalEquationPreparer implements EquationPreparer {
  private final Transendental e1 =
      new Transendental(
          (value) -> BigDecimal.valueOf(Math.pow(Math.E, Math.sin(value.doubleValue())) - 2.0),
          "y = e^(sin(x)) - 2");
  private final Transendental e2 =
      new Transendental(
          (value) ->
              BigDecimal.valueOf(Math.tan(value.doubleValue()) - Math.cos(value.doubleValue())),
          "y = tg(x) - cos(x)");

  private final Map<String, Transendental> map =
      new HashMap<>() {
        {
          put("1", e1);
          put("2", e2);
        }
      };

  @Override
  public Equation prepare() {
    String solversNames = prepareNames();
    System.out.printf("> Выберите уравнение %s\n", solversNames);
    return getEquation();
  }

  private Transendental getEquation() {
    return ConsoleWorker.getObjectsFromConsole(1, (map::get), ((object) -> !Objects.isNull(object)))
        .iterator()
        .next();
  }

  private String prepareNames() {
    var builder = new StringBuilder();

    Iterator<Map.Entry<String, Transendental>> iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
      var entry = iterator.next();
      builder.append("\n");

      String prepare = String.format("%s (%s)", entry.getValue().getName(), entry.getKey());
      builder.append(prepare);

      if (iterator.hasNext()) {
        builder.append(", ");
      }
    }
    return builder.toString();
  }
}
