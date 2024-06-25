package lab2.util;

public interface FuncX {
    Double solve(double x);

    default Double derivative(double x, double delta) {
        return (this.solve(x + delta) - this.solve(x - delta)) / (2 / delta);
    }
}
