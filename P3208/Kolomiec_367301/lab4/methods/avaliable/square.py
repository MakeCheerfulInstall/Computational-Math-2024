from lab4.methods.method import Method
from lab4.utils.calculator import calculate_degree_sum
from lab4.utils.determinant import *


class Square(Method):

    def __init__(self, name, view):
        super().__init__(name, view)

    def calculate(self, dots: tuple) -> list:
        x, y = dots
        n = len(x)

        sx = sum(x)
        sxx = calculate_degree_sum(2, x)
        sxxx = calculate_degree_sum(3, x)
        sxxxx = calculate_degree_sum(4, x)

        sy = sum(y)
        sxy = sum([x[i] * y[i] for i in range(n)])
        sxxy = sum([x[i] ** 2 * y[i] for i in range(n)])

        matrix = [
            [n, sx, sxx],
            [sx, sxx, sxxx],
            [sxx, sxxx, sxxxx]
        ]

        values = [sy, sxy, sxxy]

        det = calculate(matrix, 3)
        det1 = calculate(replace_column(matrix, values, 0), 3)
        det2 = calculate(replace_column(matrix, values, 1), 3)
        det3 = calculate(replace_column(matrix, values, 2), 3)

        a, b, c = det1 / det, det2 / det, det3 / det
        coefficients = [c, b, a]
        phi = self.calculate_empire_func_ordinate(x, coefficients)

        # self.draw_table(
        #     dots,
        #     phi,
        #     self.get_deviation_for_each(dots, coefficients))

        dev = self.get_deviation(dots, coefficients)
        k = self.get_standard_deviation(dots, coefficients)
        cod = self.get_coefficient_of_determination(y, phi)

        return self.round_all(coefficients) + ["-", dev, k, cod]

    def get_deviation_for_each(self, dots: tuple, coefficients: list) -> list:
        a, b, c = coefficients
        x, y = dots
        return [self.round_value(a * x[i] ** 2 + b * x[i] + c - y[i]) for i in range(len(x))]

    def calculate_empire_func_ordinate(self, x: list, coefficients: list) -> list:
        a, b, c = coefficients
        return [self.round_value(a * i ** 2 + b * i + c) for i in x]
