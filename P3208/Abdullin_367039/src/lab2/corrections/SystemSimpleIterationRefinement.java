package lab2.corrections;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class SystemSimpleIterationRefinement {

  public Answer solve(List<BinaryOperator<Double>> list, double[] line, double eps) {
    double x1 = line[0], x2 = line[1], next_x1, next_x2;
    int i = 0;
    List<List<String>> history = new ArrayList<>();
    do {
      next_x1 = x1;
      x1 = list.get(0).apply(x1, x2);
      next_x2 = x2;
      x2 = list.get(1).apply(x1, x2);
      var cur = new ArrayList<String>();
      cur.add(String.valueOf(++i));
      cur.add(String.valueOf(x1));
      cur.add(String.valueOf(x2));
      cur.add(String.valueOf(Math.abs(next_x1 - x1)));
      cur.add(String.valueOf(Math.abs(next_x2 - x2)));
      history.add(cur);
    } while (Math.abs(next_x1 - x1) > eps && Math.abs(next_x2 - x2) > eps);

    getStory(history);
    return new Answer(getStory(history), new double[] {x1, x2});
  }

  public static class Answer {
    private final String history;
    private final double[] answer;

    public Answer(String history, double[] answer) {
      this.history = history;
      this.answer = answer;
    }

    public String getHistory() {
      return history;
    }

    public double[] getAnswer() {
      return answer;
    }
  }

  private String getStory(List<List<String>> history) {
    var buffer = new StringBuilder();

    buffer.append(
        String.format(
            "%20s | %20s | %20s | %20s | %20s\n",
            "Номер итерации, i",
            "x^(i)",
            "y^(i)",
            "(x^(i) - x^(i-1)) ^ e",
            "(y^(i) - y^(i-1)) ^ e"));

    for (List<String> part : history) {
      buffer.append(
          String.format(
              "%20d | %20.16f | %20.16f | %20.16f | %20.16f\n",
              Integer.parseInt(part.get(0)),
              Double.parseDouble(part.get(1)),
              Double.parseDouble(part.get(2)),
              Double.parseDouble(part.get(3)),
              Double.parseDouble(part.get(4))));
    }
    return buffer.toString();
  }
}
