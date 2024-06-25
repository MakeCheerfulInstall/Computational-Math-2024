package lab3.integral_suppliers;

import Abdullin_367039.lab3.domains.Integral;
import Abdullin_367039.lab2.utils.ConsoleWorker;
import Abdullin_367039.lab3.integral_suppliers.FunctionSupplier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Deprecated
public class FileFunctionSupplier implements FunctionSupplier {
  private static final String RESOURCE_PACKAGE = "P3208/Abdullin_367039/lab3/resources";

  @Override
  public Integral get() {
    var list = getAllResources();
    printFiles(list);

    int id =
        ConsoleWorker.getObjectsFromConsole(1, Integer::parseInt, i -> (i >= 0 && i <= list.size()))
            .iterator()
            .next();

    Path ch = list.get(id);

    return readIntegral(ch);
  }

  private Integral readIntegral(Path ch) {
    try (Stream<String> stream = Files.lines(ch)) {
      var lines = stream.filter(line -> !line.isBlank()).toList();
      return parseIntegral(lines);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  private Integral parseIntegral(List<String> list) {
    return null;
  }

  private void printFiles(List<Path> list) {
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

  private List<Path> getAllResources() {
    try (Stream<Path> files =
        Files.walk(Paths.get(RESOURCE_PACKAGE)).filter(Files::isRegularFile)) {
      return files.toList();
    } catch (IOException e) {
      System.err.println("System error");
      System.exit(-1);
    }
    return null;
  }
}
