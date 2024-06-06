package lab1.src;

import Abdullin_367039.lab1.ConsoleSLAUReader;
import Abdullin_367039.lab1.FileSLAUReader;
import Abdullin_367039.lab1.MatrixGenerator;
import Abdullin_367039.lab1.SLAUReader;

import java.util.HashMap;
import java.util.Map;

public class SLAUReaderHandler {
  private final Map<String, Abdullin_367039.lab1.SLAUReader> map =
      new HashMap<>() {
        {
          put(Abdullin_367039.lab1.SLAUReader.CONSOLE, new ConsoleSLAUReader());
          put(Abdullin_367039.lab1.SLAUReader.FILE, new FileSLAUReader());
          put(Abdullin_367039.lab1.SLAUReader.RANDOM, new MatrixGenerator());
        }
      };

  public SLAUReader getReader(String value) {
    return map.get(value);
  }
}
