package Abdullin_367039.lab1;

import java.util.HashMap;
import java.util.Map;

public class SLAUReaderHandler {
  private final Map<String, SLAUReader> map =
      new HashMap<>() {
        {
          put(SLAUReader.CONSOLE, new ConsoleSLAUReader());
          put(SLAUReader.FILE, new FileSLAUReader());
          put(SLAUReader.RANDOM, new MatrixGenerator());
        }
      };

  public SLAUReader getReader(String value) {
    return map.get(value);
  }
}
