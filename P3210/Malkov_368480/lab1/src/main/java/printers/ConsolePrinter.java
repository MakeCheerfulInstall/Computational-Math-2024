package printers;

public class ConsolePrinter extends Printer {
    @Override
    public void print(Object line) {
        System.out.print(line);
        System.out.println();
        System.out.print("> ");
    }

    @Override
    public void printf(String line, Object... args) {
        System.out.printf(line, args);
        System.out.println();
        System.out.print("> ");
    }

    @Override
    public void println(Object line) {
        System.out.println(line);
        System.out.print("> ");
    }
}
