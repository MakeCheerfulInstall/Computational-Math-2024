import numpy
import scipy.misc
from prettytable import PrettyTable

from methods.Method import Method


class Secant(Method):
    name = "секущих"

    def solve(self) -> None:
        table = PrettyTable(["N", "x(i-1)", "x(i)", "x(i+1)", "f(x(i+1))", "|x(i+1)-x(i)|"])
        f = self.equation.function
        if f(self.left) * scipy.misc.derivative(f, self.left, dx=1e-6, n=2) > 0:
            x_prev = self.left
            x = x_prev + self.accuracy
        else:
            x_prev = self.right
            x = x_prev - self.accuracy
        x_next = x - (x - x_prev) / (f(x) - f(x_prev)) * f(x)
        count = 1
        while numpy.abs(x_next - x) > self.accuracy:
            table.add_row(list(map(lambda i: round(i, self.symbols_after_dot),
                              [count, x_prev, x, x_next, f(x_next), numpy.abs(x_next - x)])))
            x_prev = x
            x = x_next
            x_next = x - (x - x_prev) / (f(x) - f(x_prev)) * f(x)
            count += 1
        table.add_row(list(map(lambda i: round(i, self.symbols_after_dot),
                               [count, x_prev, x, x_next, f(x_next), numpy.abs(x_next - x)])))
        print(table)
        print(f"Найденный корень: {round(x, self.symbols_after_dot)}")
        print(f"Значение функции: {f(x)}")

#начальное приближение: x0=left, если f(left)*f''(left)>0, иначе x0=right
#𝑥1- выбирается рядом с начальным самостоятельно (+-точность)
