package lab2.solvers;

import lab2.corrections.HalfDivisionRefinement;
import lab2.corrections.NewtonRefinement;
import lab2.corrections.Refinement;
import lab2.corrections.SimpleIterationRefinement;
import lab2.equations.Equation;
import lab2.prepareres.AlgebraicEquationPreparer;
import lab2.prepareres.EquationPreparer;
import lab2.prepareres.TransendentalEquationPreparer;
import lab2.utils.ConsoleWorker;

import java.util.*;

import static lab2.corrections.Refinement.*;
import static lab2.equations.Equation.*;

public class NonlinearEquationSolver {
  private static final String CHOOSE_TYPE = "> Выберите тип функции: ";
  private static final String CHOOSE_METHOD = "> Выберите метод решения: ";

  private final Map<String, Refinement> refinementMap =
      new HashMap<>() {
        {
          put(NEWTON, new NewtonRefinement());
          put(SIMPLE_ITERATION, new SimpleIterationRefinement());
          put(HALF_DIVISION, new HalfDivisionRefinement());
        }
      };
  private final Map<Equation.Type, EquationPreparer> preparerMap =
      new HashMap<>() {
        {
          put(Type.ALGEBRAIC, new AlgebraicEquationPreparer());
          put(Type.TRANSENDENTAL, new TransendentalEquationPreparer());
        }
      };

  public String getName() {
    return "Нелинейное уравнение";
  }

  public List<Equation> getEquation() {
    Equation.Type type = chooseType();
    EquationPreparer preparer = preparerMap.get(type);
    return List.of(preparer.prepare());
  }

  public Refinement chooseRefinement() {
    System.out.println(CHOOSE_METHOD);
    printMethods();

    return ConsoleWorker.getObjectsFromConsole(1, (refinementMap::get), (Objects::nonNull))
        .iterator()
        .next();
  }

  private void printMethods() {
    var iterator = refinementMap.entrySet().iterator();
    while (iterator.hasNext()) {
      var current = iterator.next();
      System.out.printf("%s (%s)", current.getValue(), current.getKey());
      if (iterator.hasNext()) {
        System.out.print(", ");
      }
      System.out.print("\n");
    }
  }

  private Equation.Type chooseType() {
    System.out.println(CHOOSE_TYPE);
    printTypes();
    return ConsoleWorker.getObjectsFromConsole(1, (Type::getByNumber), (Optional::isPresent))
        .iterator()
        .next()
        .get();
  }

  private void printTypes() {
    Equation.Type[] values = Equation.Type.values();
    for (int i = 0; i < values.length; i++) {
      var current = values[i];
      System.out.printf("%s (%s)", current.getName(), current.getNumber());
      if (i != values.length - 1) {
        System.out.print(", ");
      }
      System.out.print("\n");
    }
  }
}
