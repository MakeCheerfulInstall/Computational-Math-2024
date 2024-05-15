from math import log, exp

from lab4.methods.method import Method


class Logarithmic(Method):
    def __init__(self, name, view):
        super().__init__(name, view)

    def calculate(self, dots: tuple) -> list:
        x, y = dots

        n = len(x)
        sx = sum([log(i) for i in x])
        sxx = sum([log(i) ** 2 for i in x])
        sy = sum(y)
        sxy = sum([y[i] * log(x[i]) for i in range(n)])

        delta = sxx * n - sx ** 2
        delta_1 = sxy * n - sx * sy
        delta_2 = sxx * sy - sx * sxy

        a, b = delta_1 / delta, delta_2 / delta

        coefficients = [a, b]
        phi = self.calculate_empire_func_ordinate(x, coefficients)

        dev = self.get_deviation(dots, coefficients)
        k = self.get_standard_deviation(dots, coefficients)
        cod = self.get_coefficient_of_determination(y, phi)

        return self.round_all(coefficients) + ["-", "-", dev, k, cod]

    def get_deviation_for_each(self, dots: tuple, coefficients: list) -> list:
        x, y = dots
        a, b = coefficients
        return [a * log(x[i]) + b - y[i] for i in range(len(x))]

    def calculate_empire_func_ordinate(self, x: list, coefficients: list) -> list:
        a, b = coefficients
        return [a * log(i) + b for i in x]
