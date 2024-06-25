import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConsoleSLAUReader implements SLAUReader {
  private final Abdullin_367039.lab1.MatrixParser parser = new MatrixParser();

  @Override
  public BigDecimal[][] read() {
    var reader = new BufferedReader(new InputStreamReader(System.in));
    try {
      System.out.println("> Print size of matrix. Size <= 20");
      System.out.print("< ");
      int size = 0;
      do {
        try {
          size = Integer.parseInt(reader.readLine());
          if (size > 20 || size < 2) {
            System.out.println("> Size should be [1; 20]: " + size);
            System.out.print("< ");
            size = 0;
          }
        } catch (NumberFormatException ignored) {
          System.out.println("> Incorrect input ");
          System.out.print("< ");
        }
      } while (size == 0);

      List<String> lines = new ArrayList<>(size);
      System.out.println("> Print next lines, for example, if size == 4: `1 1 1 1 = 1`");
      for (int i = 0; i < size; i++) {
        System.out.print("< ");
        lines.add(reader.readLine());
      }
      return parser.parseMatrix(lines, size);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
