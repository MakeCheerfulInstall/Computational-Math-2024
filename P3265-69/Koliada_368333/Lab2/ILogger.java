package Lab2;

public interface ILogger {
    void log(String message);
    default void clear(){};
}