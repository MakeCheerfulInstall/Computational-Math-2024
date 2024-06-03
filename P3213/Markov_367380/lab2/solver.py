from system import System
from matrix import SquareMatrix, Matrix
from equation import Equation
from typing import Callable


class Solver:
    def __init__(self, equation: Equation, method_type: int, precision: float, left: float, right: float,
                 start: float = None):
        self.__equation = equation
        self.__method_type = method_type
        self.__precision = precision
        self.__left = left
        self.__right = right
        self.__start = start

    def get_equation(self) -> Equation:
        return self.__equation

    def solve(self) -> tuple:
        if self.__method_type == 1:
            return self.__solve_chord()
        elif self.__method_type == 2:
            return self.__solve_sectional()
        elif self.__method_type == 3:
            return self.__solve_iter()

    def __solve_chord(self) -> tuple:
        a: float = self.__left
        b: float = self.__right
        f: Callable = self.__equation.get_function()
        current_precision: float = abs(b - a)
        xi: float = a - ((b - a) / (f(b) - f(a))) * f(a)
        iter_count: int = 0

        while current_precision > self.__precision:
            fa = f(a)
            fb = f(b)
            fxi = f(xi)
            iter_count += 1

            if fa * fxi < 0:
                b = xi
            elif fb * fxi < 0:
                a = xi
            else:
                return None, None, iter_count

            new_xi = (a * f(b) - b * f(a)) / (f(b) - f(a))
            current_precision = abs(new_xi - xi)
            xi = new_xi
        return xi, f(xi), iter_count

    def __solve_sectional(self) -> tuple:
        a: float = self.__left
        b: float = self.__right
        f: Callable = self.__equation.get_function()
        iter_count: int = 0
        xi_f: float = self.__start
        xi_s = xi_f

        if xi_f < (b + a) / 2:
            xi_s += (b + a) / 4
        else:
            xi_s -= (b + a) / 4

        current_precision: float = abs(xi_s - xi_f)

        while current_precision > self.__precision:
            iter_count += 1

            xi = xi_s - ((xi_s - xi_f) / (f(xi_s) - f(xi_f))) * f(xi_s)
            xi_f = xi_s
            xi_s = xi

            current_precision: float = abs(xi_s - xi_f)
        return xi_s, f(xi_s), iter_count

    def __solve_iter(self) -> tuple:
        q: float = self.__get_max_derivative()
        if q > 1:
            return None, None, 0

        xf: Callable = self.__equation.get_xfunction()
        f: Callable = self.__equation.get_function()
        iter_count: int = 0
        xi: float = self.__start

        current_precision: float = abs(xi)
        precision = self.__precision

        if q > 0.5:
            precision = (1 - q) / q * precision

        while current_precision > precision:
            iter_count += 1

            new_xi: float = xf(xi)
            current_precision: float = abs(new_xi - xi)
            xi = new_xi
        return xi, f(xi), iter_count

    def __get_max_derivative(self) -> float:
        f: Callable = self.__equation.get_dxfunction()
        return max([abs(f(i / 100)) for i in range(int(self.__left * 100), int(self.__right * 100))])


class SystemSolver:
    def __init__(self, system: System, precision: float, x0: float, y0: float):
        self.__system = system
        self.__precision = precision
        self.__x0 = x0
        self.__y0 = y0

    def get_system(self) -> System:
        return self.__system

    def solve(self) -> tuple:
        new_x = self.__x0
        new_y = self.__y0
        x: float = 0
        y: float = 0
        while abs(new_x - x) > self.__precision or abs(new_y - y) > self.__precision:
            x = new_x
            y = new_y
            matrix: SquareMatrix = SquareMatrix(2,
                                                [[self.__system.get_dxfunctions()[i][j](x, y) for j in range(2)] for i
                                                 in range(2)])
            results: Matrix = Matrix(2, 1, [[self.__system.get_functions()[i](x, y)] for i in range(0, 2)])
            dif_matrix = self.__solve_linear_system(matrix, results)
            new_x = x + dif_matrix.get_data()[0][0]
            new_y = y + dif_matrix.get_data()[1][0]
        return new_x, new_y

    def __solve_linear_system(self, matrix: SquareMatrix, results: Matrix) -> Matrix:
        return matrix.solve_cramer(results)
