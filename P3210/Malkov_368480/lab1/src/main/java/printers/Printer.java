package printers;

public abstract class Printer {
    public abstract void print(Object line);

    public abstract void printf(String line, Object... args);

    public abstract void println(Object line);
}
