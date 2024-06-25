package lab2.algebra;

public interface EquationSolver {

    public Object[][] solveByIterations(double[] G, Function... functions);
    /**
     * Решить уравнение методом хорд
     */
    Object[] solveByChord(Function function, double a, double b);

    /**
     * решить уравнение методом простых итераций
     */
    Object[] solveByIteration(Function function, double a, double b);

    /**
     * решить уравнение методом Ньютона
     */

    Object[] solveByNewton(Function function, double a, double b);

    /**
     * Set an accuracy.
     */
    void setAccuracy(double accuracy);
}
