package lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SolvingSLAU {
  public static void main(String[] args) throws IOException {
    System.out.println("> Choose input: from console(1); from file(2); random(3)");
    var reader = new BufferedReader(new InputStreamReader(System.in));
    SLAUReaderHandler handler = new SLAUReaderHandler();
    SLAUReader slauReader;
    do {
      System.out.print("< ");
      String a = reader.readLine();
      slauReader = handler.getReader(a);
      if (slauReader == null) System.out.println("Incorrect input");
    } while (slauReader == null);

    double[][] matrix = slauReader.read();
    print(matrix);
    GaussExecutor ex = new GaussExecutor();
    ex.solve(matrix);
  }

  static void print(double[][] a) {
    System.out.println("> Исходная СЛАУ:");
    for (double[] doubles : a) {
      for (int j = 0; j < doubles.length; j++) {
        if (j == a.length) {
          System.out.print("= " + doubles[j]);
        } else {
          System.out.print(doubles[j] + " ");
        }
      }
      System.out.println();
    }
    System.out.println();
  }
}
