package lab1.src;

import Abdullin_367039.lab1.GaussExecutor;
import Abdullin_367039.lab1.SLAUReader;
import Abdullin_367039.lab1.SLAUReaderHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

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

    BigDecimal[][] matrix = slauReader.read();
    print(matrix);
    GaussExecutor ex = new GaussExecutor();
    ex.solve(matrix);
  }

  static void print(BigDecimal[][] a) {
    System.out.println("> Исходная СЛАУ:");
    for (BigDecimal[] doubles : a) {
      for (int j = 0; j < doubles.length; j++) {
        if (j == a.length) {
          System.out.print("= " + doubles[j].toString());
        } else {
          System.out.print(doubles[j].toString() + " ");
        }
      }
      System.out.println();
    }
    System.out.println();
  }
}
