from lab4.methods.method import Method
from lab4.utils.calculator import calculate_degree_sum
from lab4.utils.determinant import calculate, replace_column


class Cubic(Method):
    def __init__(self, name, view):
        super().__init__(name, view)

    def calculate(self, dots: tuple) -> list:
        x, y = dots
        n = len(x)

        sx = sum(x)
        sxx = calculate_degree_sum(2, x)
        sx3 = calculate_degree_sum(3, x)
        sx4 = calculate_degree_sum(4, x)
        sx5 = calculate_degree_sum(5, x)
        sx6 = calculate_degree_sum(6, x)

        sy = sum(y)
        sxy = sum([x[i] * y[i] for i in range(n)])
        sxxy = sum([x[i] ** 2 * y[i] for i in range(n)])
        sx3y = sum([x[i] ** 3 * y[i] for i in range(n)])

        matrix = [
            [n, sx, sxx, sx3],
            [sx, sxx, sx3, sx4],
            [sxx, sx3, sx4, sx5],
            [sx3, sx4, sx5, sx6]
        ]

        values = [sy, sxy, sxxy, sx3y]

        det = calculate(matrix, 4)
        det1 = calculate(replace_column(matrix, values, 0), 4)
        det2 = calculate(replace_column(matrix, values, 1), 4)
        det3 = calculate(replace_column(matrix, values, 2), 4)
        det4 = calculate(replace_column(matrix, values, 3), 4)

        a, b, c, d = det1 / det, det2 / det, det3 / det, det4 / det
        coefficients = [d, c, b, a]
        phi = self.calculate_empire_func_ordinate(x, coefficients)

        # self.draw_table(
        #     dots,
        #     phi,
        #     self.get_deviation_for_each(dots, coefficients))

        dev = self.get_deviation(dots, coefficients)
        k = self.get_standard_deviation(dots, coefficients)
        cod = self.get_coefficient_of_determination(y, phi)

        return self.round_all(coefficients) + [dev, k, cod]

    def get_deviation_for_each(self, dots: tuple, coefficients: list) -> list:
        a, b, c, d = coefficients
        x, y = dots
        return [self.round_value(a * x[i] ** 3 + b * x[i] ** 2 + c * x[i] + d - y[i]) for i in range(len(x))]

    def calculate_empire_func_ordinate(self, x: list, coefficients: list) -> list:
        a, b, c, d = coefficients
        return [self.round_value(a * i ** 3 + b * i ** 2 + c * i + d) for i in x]
