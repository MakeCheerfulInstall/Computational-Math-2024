package lab4;

import Abdullin_367039.lab1.MatrixParser;
import org.apache.commons.math3.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class PointsFileReader {
  private final String RESOURCE_PACKAGE;

  public PointsFileReader(String path) {
    RESOURCE_PACKAGE = path;
  }

  public Pair<double[], double[]> read() {
    var list = getAllResources();

    printFiles(list);
    var reader = new BufferedReader(new InputStreamReader(System.in));

    Path file = null;
    do {
      System.out.print("< ");
      try {
        int number = Integer.parseInt(reader.readLine());
        if (number < 0 || number >= list.size()) {
          System.out.println("> Incorrect input");
          continue;
        }
        file = list.get(number);
      } catch (Exception e) {
        System.out.println("> Incorrect input");
      }
    } while (file == null);

    try {
      var lines =
          Files.lines(file)
              .filter(line -> !line.isBlank())
              .map(line -> line.replaceAll(",", "."))
              .toList();
      double[] x = new double[lines.size()];
      double[] y = new double[lines.size()];
      for (int i = 0; i < lines.size(); i++) {
        String[] data = lines.get(i).split(" ");
        x[i] = Double.parseDouble(data[0]);
        y[i] = Double.parseDouble(data[1]);
      }
      return Pair.create(x, y);
    } catch (IOException | RuntimeException e) {
      System.err.println("Problem with reading resource file");
      System.out.println(e.getMessage());
      System.exit(-1);
    }
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
