import math
from abc import abstractmethod
from typing import Final, Callable

from P3208.Terekhin_367558.lab2.functions import Describable


class Approximation(Describable):
    def __init__(self, description: str):
        super().__init__(description)
        self.func: Callable[[float], float] | None = None
        self.view: str = ''

    @abstractmethod
    def build_approximation(self, points: list[tuple[float, float]]) -> None:
        pass

    def gauss_matrix_solve(self, matrix: list[list[float]]) -> list[float]:
        n = len(matrix)
        for i in range(n):
            max_index = i
            max_value = abs(matrix[i][i])
            for k in range(i + 1, n):
                if abs(matrix[k][i]) > max_value:
                    max_index = k
                    max_value = abs(matrix[k][i])
            if max_index != i:
                matrix[i], matrix[max_index] = matrix[max_index], matrix[i]

            pivot = matrix[i][i]
            if pivot == 0:
                raise ValueError("Matrix is singular")
            for j in range(i + 1, n):
                factor = matrix[j][i] / pivot
                for k in range(i, n + 1):
                    matrix[j][k] -= factor * matrix[i][k]

        solution: list[float] = [0] * n
        for i in range(n - 1, -1, -1):
            solution[i] = matrix[i][n] / matrix[i][i]
            for j in range(i - 1, -1, -1):
                matrix[j][n] -= matrix[j][i] * solution[i]
        return solution


class LinearApproximation(Approximation):
    def __init__(self):
        super().__init__("Linear Approximation")
        self.r = 0
        self.coefficients = []

    def build_approximation(self, points: list[tuple[float, float]]) -> None:
        sum_x = sum(x for x, _ in points)
        sum_y = sum(y for _, y in points)
        sum_x_squared = sum(x ** 2 for x, _ in points)
        sum_xy = sum(x * y for x, y in points)

        n = len(points)

        average_x = sum_x / n
        average_y = sum_y / n

        a, b = map(lambda x: round(x, 3), self.gauss_matrix_solve([[sum_x_squared, sum_x, sum_xy],
                                        [sum_x, n, sum_y]]))
        self.coefficients = [a, b]
        self.view = f'{a}x + {b}'

        self.r = round(
            sum((x - average_x) * (y - average_y) for x, y in points) / (sum((x - average_x) ** 2 for x, y in points) * sum((y - average_y) ** 2 for x, y in points)) ** 0.5,
            3)
        self.func = lambda x: a * x + b


class SquaredApproximation(Approximation):
    def __init__(self):
        super().__init__("Squared Approximation")

    def build_approximation(self, points: list[tuple[float, float]]) -> None:
        sum_x = sum(x for x, _ in points)
        sum_y = sum(y for _, y in points)
        sum_x_squared = sum(x ** 2 for x, _ in points)
        sum_x_cubed = sum(x ** 3 for x, _ in points)
        sum_x_quad = sum(x ** 4 for x, _ in points)
        sum_xy = sum(x * y for x, y in points)
        sum_x_squared_y = sum(x ** 2 * y for x, y in points)

        n = len(points)
        c, b, a = map(lambda x: round(x, 3), self.gauss_matrix_solve([[n, sum_x, sum_x_squared, sum_y],
                                           [sum_x, sum_x_squared, sum_x_cubed, sum_xy],
                                           [sum_x_squared, sum_x_cubed, sum_x_quad, sum_x_squared_y]]))
        self.view = f'{a}x^2 + {b}x + {c}'

        self.func = lambda x: a * x ** 2 + b * x + c


class CubedApproximation(Approximation):
    def __init__(self):
        super().__init__("Cubed Approximation")

    def build_approximation(self, points: list[tuple[float, float]]) -> None:
        sum_x = sum(x for x, _ in points)
        sum_y = sum(y for _, y in points)
        sum_x_squared = sum(x ** 2 for x, _ in points)
        sum_x_cubed = sum(x ** 3 for x, _ in points)
        sum_x_quad = sum(x ** 4 for x, _ in points)
        sum_x_fifth = sum(x ** 5 for x, _ in points)
        sum_x_sixth = sum(x ** 6 for x, _ in points)
        sum_xy = sum(x * y for x, y in points)
        sum_x_squared_y = sum(y * x ** 2 for x, y in points)
        sum_x_cubed_y = sum(y * x ** 3 for x, y in points)

        n = len(points)

        d, c, b, a = map(lambda x: round(x, 3), self.gauss_matrix_solve([[n, sum_x, sum_x_squared, sum_x_cubed, sum_y],
                                              [sum_x, sum_x_squared, sum_x_cubed, sum_x_quad, sum_xy],
                                              [sum_x_squared, sum_x_cubed, sum_x_quad, sum_x_fifth, sum_x_squared_y],
                                              [sum_x_cubed, sum_x_quad, sum_x_fifth, sum_x_sixth, sum_x_cubed_y]]))

        self.view = f'{a}x^3 + {b}x^2 + {c}x + {d}'

        self.func = lambda x: a * x ** 3 + b * x ** 2 + c * x + d


class DegreeApproximation(Approximation):
    def __init__(self):
        super().__init__("Degree Approximation")
        self.linear = LinearApproximation()

    def build_approximation(self, points: list[tuple[float, float]]) -> None:
        ln_points = list(map(lambda x: (math.log(x[0]), math.log(x[1])), points))
        self.linear.build_approximation(ln_points)
        a, b = self.linear.coefficients
        self.view = f'{round(math.exp(b), 3)}x^{round(a, 3)}'
        self.func = lambda x: math.exp(b) * x ** a


class ExponentialApproximation(Approximation):
    def __init__(self):
        super().__init__("Exponential Approximation")
        self.linear = LinearApproximation()

    def build_approximation(self, points: list[tuple[float, float]]) -> None:
        ln_points = list(map(lambda x: (x[0], math.log(x[1])), points))
        self.linear.build_approximation(ln_points)
        a, b = self.linear.coefficients
        self.view = f'{round(math.exp(b), 3)}e^{a}x'
        self.func = lambda x: math.exp(b) * math.exp(a * x)


class LogarithmicApproximation(Approximation):
    def __init__(self):
        super().__init__("Logarithmic Approximation")
        self.linear = LinearApproximation()

    def build_approximation(self, points: list[tuple[float, float]]) -> None:
        ln_points = list(map(lambda x: (math.log(x[0]), x[1]), points))
        self.linear.build_approximation(ln_points)
        a, b = self.linear.coefficients
        self.view = f'{a} ln(x) + {b}'
        self.func = lambda x: a * (math.nan if x <= 0 else math.log(x)) + b


APPROXIMATIONS: Final[list[Approximation]] = [
    LinearApproximation(),
    SquaredApproximation(),
    CubedApproximation(),
    DegreeApproximation(),
    ExponentialApproximation(),
    LogarithmicApproximation()
]
