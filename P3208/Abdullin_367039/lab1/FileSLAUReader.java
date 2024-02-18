package lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileSLAUReader implements SLAUReader {
  private final MatrixParser parser = new MatrixParser();
  private static final String RESOURCE_PACKAGE =
      "/home/yestai/IdeaProjects/CountingMath/src/resources";

  @Override
  public double[][] read() {
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
      var lines = Files.readAllLines(file);
      return parser.parseMatrix(lines, lines.size());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
