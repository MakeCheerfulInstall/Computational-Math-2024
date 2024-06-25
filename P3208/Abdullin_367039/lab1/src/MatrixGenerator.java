package lab1.src;

import Abdullin_367039.lab1.SLAUReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Random;

public class MatrixGenerator implements SLAUReader {
  private final Random random = new Random();

  public BigDecimal[][] generate() {
    int size = readSize();

    BigDecimal[][] matrix = new BigDecimal[size][size + 1];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j <= size; j++) {
        matrix[i][j] =
            new BigDecimal(
                String.format("%.3f", random.nextDouble() * 200000 - 100000).replace(',', '.'));
      }
    }

    return matrix;
  }

  private int readSize() {
    int size = 0;
    System.out.println("> Print size of matrix. Size <= 20");
    System.out.print("< ");
    var reader = new BufferedReader(new InputStreamReader(System.in));
    do {
      try {
        size = Integer.parseInt(reader.readLine());
        if (size > 20 || size < 2) {
          System.out.println("> Size should be [1; 20]: " + size);
          System.out.print("< ");
          size = 0;
        }
      } catch (NumberFormatException | IOException ignored) {
      }
    } while (size == 0);
    return size;
  }

  @Override
  public BigDecimal[][] read() {
    return generate();
  }
}
