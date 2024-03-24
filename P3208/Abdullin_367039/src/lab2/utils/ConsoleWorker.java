package lab2.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ConsoleWorker {
  private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  public static final String INCORRECT_INPUT = "> Неверный ввод:";
  public static final String START_OUTPUT = "< ";

  public static <T> List<T> getObjectsFromConsole(
      int count, Function<String, T> parser, Predicate<T> isCorrect) {
    List<T> objects = new ArrayList<>(count);
    for (int i = 0; i < count; i++) {
      T t;
      while (true) {
        System.out.print(START_OUTPUT);
        try {
          String stringValue = reader.readLine();
          t = parser.apply(stringValue);

          if (!isCorrect.test(t)) {
            System.out.println(INCORRECT_INPUT);
          } else {
            break;
          }

        } catch (Exception e) {
          System.out.println(INCORRECT_INPUT);
        }
      }
      objects.add(t);
    }

    return objects;
  }
}
