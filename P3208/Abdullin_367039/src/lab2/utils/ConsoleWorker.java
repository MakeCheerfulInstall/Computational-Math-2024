package lab2.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

  public static <K, V> void printMap(Map<K, V> map, String choose) {
    System.out.println(choose);
    var iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
      var current = iterator.next();
      System.out.printf("%s (%s)", current.getValue(), current.getKey());
      if (iterator.hasNext()) {
        System.out.print(", ");
      }
      System.out.print("\n");
    }
  }

  public static <K, V> void printMap(Map<K, V> map, String choose, List<String> names) {
    if (map.size() != names.size()) {
      throw new RuntimeException("Invalid count of names!");
    }

    System.out.println(choose);

    var mapIterator = map.entrySet().iterator();
    var listIterator = names.iterator();
    while (mapIterator.hasNext() && listIterator.hasNext()) {
      var current = mapIterator.next();
      String message = listIterator.next();
      System.out.printf("%s (%s)", message, current.getKey());
      if (mapIterator.hasNext() && listIterator.hasNext()) {
        System.out.print(", ");
      }
      System.out.print("\n");
    }
  }
}
