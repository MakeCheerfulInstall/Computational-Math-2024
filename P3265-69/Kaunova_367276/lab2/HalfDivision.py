import numpy
from prettytable import PrettyTable

from methods.Method import Method


class HalfDivision(Method):
    name = "половинного деления"

    def solve(self) -> None:
        table = PrettyTable(["N", "a", "b", "x", "f(a)", "f(b)", "f(x)", "|a - b|"])
        f = self.equation.function
        a = self.left
        b = self.right
        x = (a + b) / 2
        count = 1
        while numpy.abs(a - b) > self.accuracy:
            table.add_row(list(map(lambda i: round(i, self.symbols_after_dot),
                              [count, a, b, x, f(a), f(b), f(x), numpy.abs(a - b)])))
            if f(a) * f(x) < 0:
                b = x
            else:
                a = x
            x = (a + b) / 2
            count += 1
        table.add_row(list(map(lambda i: round(i, self.symbols_after_dot),
                               [count, a, b, x, f(a), f(b), f(x), numpy.abs(a - b)])))
        print(table)
        print(f"Найденный корень: {round(x, self.symbols_after_dot)}")
        print(f"Значение функции: {f(x)}")

