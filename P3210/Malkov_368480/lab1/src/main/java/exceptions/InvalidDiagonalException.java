package exceptions;

public class InvalidDiagonalException extends Exception {
    public InvalidDiagonalException() {
        super("Can not transform matrix diagonal!");
    }
}
