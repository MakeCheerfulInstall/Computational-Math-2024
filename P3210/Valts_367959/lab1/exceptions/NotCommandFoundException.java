package lab1.exceptions;

public class NotCommandFoundException extends RuntimeException {
    public NotCommandFoundException() {
        super();
    }
    public NotCommandFoundException(String s) {
        super(s);
    }
}
