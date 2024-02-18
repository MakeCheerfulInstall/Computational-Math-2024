package lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class MatrixGenerator implements SLAUReader {
  private final Random random = new Random();

  public double[][] generate() {
    int size = readSize();

    double[][] matrix = new double[size][size + 1];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j <= size; j++) {
        matrix[i][j] = random.nextDouble() * 200000 - 100000;
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
  public double[][] read() {
    return generate();
  }
}
