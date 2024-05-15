import math

from lab4.methods.method import Method


class Linal(Method):

    def __init__(self, name, view):
        super().__init__(name, view)

    def calculate(self, dots: tuple) -> list:
        x, y = dots

        n = len(x)
        sx = sum(x)
        sxx = sum([i ** 2 for i in x])
        sy = sum(y)
        sxy = sum([x[i] * y[i] for i in range(len(x))])

        delta = sxx * n - sx ** 2
        delta_1 = sxy * n - sx * sy
        delta_2 = sxx * sy - sx * sxy

        a, b = delta_1 / delta, delta_2 / delta
        coefficients = [a, b]
        phi = self.calculate_empire_func_ordinate(x, coefficients)

        # self.draw_table(
        #     dots,
        #     phi,
        #     self.get_deviation_for_each(dots, coefficients))

        r = self.find_r(dots)
        print(f"Коэффициент корреляции: {r}")

        dev = self.round_value(self.get_deviation(dots, coefficients))
        k = self.get_standard_deviation(dots, coefficients)
        cod = self.get_coefficient_of_determination(y, phi)

        return self.round_all(coefficients) + ["-", "-", dev, k, cod]

    def get_deviation_for_each(self, dots: tuple, coefficients: list) -> list:
        a, b = coefficients
        x, y = dots
        return [self.round_value(a * x[i] + b - y[i]) for i in range(len(x))]

    def calculate_empire_func_ordinate(self, x: list, coefficients: list) -> list:
        a, b = coefficients
        return [self.round_value(a * i + b) for i in x]

    def find_r(self, dots: tuple):
        x, y = dots
        n = len(x)

        _x = sum(x) / n
        _y = sum(y) / n

        up = sum([(x[i] - _x) * (y[i] - _y) for i in range(n)])
        down = sum([(x[i] - _x) ** 2 for i in range(n)]) * sum([(y[i] - _y) ** 2 for i in range(n)])
        return self.round_value(up / math.sqrt(down))
