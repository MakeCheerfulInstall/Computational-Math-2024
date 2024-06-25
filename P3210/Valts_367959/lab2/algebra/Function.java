package lab2.algebra;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
public class Function {


    private final Expression expression;

    /**
     * Создание функции
     */
    public Function(String function) {
        this.expression = new ExpressionBuilder(function).variables("x", "y").build();
    }
    public Function(Expression expression) {
        this.expression = expression;
    }

    /**
     * Находит значение функции в точке х
     */
    public double apply(double x) {
        return expression.setVariable("x", x).evaluate();
    }

    /**
     * Находит значение функции в точке (x, y)
     */
    public double apply(double x, double y) {
        return expression.setVariable("x", x).setVariable("y", y).evaluate();
    }

    /**
     * считает производную в точке x по определению у функции одной переменной
     */
    public double derivative(double x, double delta) {
        return (this.apply(x + delta) - this.apply(x - delta)) / (2 * delta);
    }

    public double derivative2(double x, double delta) {
        return (derivative(x + delta, delta) - derivative(x - delta, delta)) / (2 * delta);
    }

    /**
     * считает производную по x в точке (x, y)  по определению у функции от нескольких переменных
     */
    public double derivativeByX(double x, double y, double delta) {
        return (this.apply(x + delta, y) - this.apply(x - delta, y)) / (2 * delta);
    }

    /**
     * считает производную по y в точке (x, y)  по определению у функции от нескольких переменных
     */
    public double derivativeByY(double x, double y, double delta) {
        return (this.apply(x, y + delta) - this.apply(x, y - delta)) / (2 * delta);
    }
}
