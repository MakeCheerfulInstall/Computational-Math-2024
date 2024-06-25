package lab2.solvers;

import Abdullin_367039.lab2.corrections.HalfDivisionRefinement;
import Abdullin_367039.lab2.corrections.NewtonRefinement;
import Abdullin_367039.lab2.corrections.Refinement;
import Abdullin_367039.lab2.corrections.SimpleIterationRefinement;
import Abdullin_367039.lab2.equations.Equation;
import Abdullin_367039.lab2.prepareres.AlgebraicEquationPreparer;
import Abdullin_367039.lab2.prepareres.EquationPreparer;
import Abdullin_367039.lab2.prepareres.TransendentalEquationPreparer;
import Abdullin_367039.lab2.utils.ConsoleWorker;

import java.util.*;

public class NonlinearEquationSolver {
  private static final String CHOOSE_TYPE = "> Выберите тип функции: ";
  private static final String CHOOSE_METHOD = "> Выберите метод решения: ";

  private final Map<String, Refinement> refinementMap =
      new HashMap<>() {
        {
          put(Refinement.NEWTON, new NewtonRefinement());
          put(Refinement.SIMPLE_ITERATION, new SimpleIterationRefinement());
          put(Refinement.HALF_DIVISION, new HalfDivisionRefinement());
        }
      };
  private final Map<Equation.Type, EquationPreparer> preparerMap =
      new HashMap<>() {
        {
          put(Equation.Type.ALGEBRAIC, new AlgebraicEquationPreparer());
          put(Equation.Type.TRANSENDENTAL, new TransendentalEquationPreparer());
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
    return ConsoleWorker.getObjectsFromConsole(1, (Equation.Type::getByNumber), (Optional::isPresent))
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
