package lab2.equations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public interface Equation {

  BigDecimal apply(BigDecimal value);

  UnaryOperator<BigDecimal> getFunction();

  List<BigDecimal> getKoef();

  enum Type {
    ALGEBRAIC("1", "Алгебраическая"),
    TRANSENDENTAL("2", "Трансцендентная");

    private final String number;

    private final String name;

    Type(String number, String name) {
      this.number = number;
      this.name = name;
    }

    public static Optional<Type> getByNumber(String number) {
      return Arrays.stream(values()).filter((type) -> type.number.equals(number)).findFirst();
    }

    public String getNumber() {
      return number;
    }

    public String getName() {
      return name;
    }
  }
}
