package lab1.src;

import java.math.BigDecimal;

public interface SLAUReader {
    String CONSOLE = "1";
    String FILE = "2";
    String RANDOM = "3";

    BigDecimal[][] read();
}
