from abc import abstractmethod
from typing import Final

from tabulate import tabulate

from P3208.Terekhin_367558.lab1.exceptions import InterpolationError
from P3208.Terekhin_367558.lab2.functions import Describable


class Interpolation(Describable):
    option_name = 'interpolation'

    def __init__(self, description: str):
        super().__init__(description)
        self.points: list[tuple[float, float]] = []

    def set_points(self, points: list[tuple[float, float]]) -> None:
        self.points = points

    @abstractmethod
    def interpolate(self, x: float) -> float:
        pass


class LagrangeInterpolation(Interpolation):
    def __init__(self):
        super().__init__('Lagrange interpolation')

    def interpolate(self, x: float) -> float:
        res: float = 0
        for i in range(len(self.points)):
            subres: float = 1
            for j in range(len(self.points)):
                if i != j:
                    subres *= x - self.points[j][0]
                    subres /= self.points[i][0] - self.points[j][0]
            res += self.points[i][1] * subres
        return res


class NewtonSeparateInterpolation(Interpolation):
    def __init__(self):
        super().__init__('Newton\'s separate interpolation')
        self.diverse_table = []

    def interpolate(self, x: float) -> float:
        res: float = self.points[0][1]
        subres: float = 1
        for i in range(len(self.diverse_table) - 1):
            subres *= x - self.points[i][0]
            res += self.diverse_table[i + 1][0] * subres
        return res

    def set_points(self, points: list[tuple[float, float]]) -> None:
        self.points = points
        n = len(self.points)
        self.diverse_table = [[p[1] for p in points]]
        for i in range(1, n):
            self.diverse_table.append([0] * n)
        headers = ['y']
        for i in range(1, n):
            headers.append(f'f{i}')
            for j in range(n - i):
                self.diverse_table[i][j] = (self.diverse_table[i - 1][j + 1]
                                            - self.diverse_table[i - 1][j]) / (self.points[j + i][0] - self.points[j][0])
        print(tabulate([[headers[k]] + list(map(lambda x: round(x, 4), self.diverse_table[k])) for k in range(n)], tablefmt='pretty'))


class NewtonFiniteInterpolation(Interpolation):
    def __init__(self):
        super().__init__('Newton\'s finite interpolation')
        self.diverse_table = []
        self.h = 0

    def set_points(self, points: list[tuple[float, float]]):
        h = points[1][0] - points[0][0]
        for i in range(len(points) - 1):
            if (points[i + 1][0] - points[i][0]) - h >= 0.001:
                raise InterpolationError('Can\'t solve using finite sums. Step isn\'t constant')
        self.points = points
        self.diverse_table = [[p[1] for p in points]]
        self.h = h
        n = len(self.points)
        for i in range(1, n):
            self.diverse_table.append([0] * n)
        headers = ['y']
        for i in range(1, n):
            headers.append(f'Δ^{i}y' if i != 1 else 'Δy')
            for j in range(n - i):
                self.diverse_table[i][j] = (self.diverse_table[i - 1][j + 1]
                                            - self.diverse_table[i - 1][j])
        print(tabulate([[headers[k]] + list(map(lambda x: round(x, 4), self.diverse_table[k])) for k in range(n)], tablefmt='pretty', numalign='decimal'))

    def interpolate(self, x: float) -> float:
        ind: int = 0
        res: float = 0
        subres = 1
        mid = (self.points[-1][0] + self.points[0][0]) / 2
        if x <= mid:
            while x >= self.points[ind + 1][0]:
                ind += 1
            res += self.points[ind][1]
            for i in range(len(self.diverse_table) - 1 - ind):
                subres *= (x - self.points[ind + i][0]) / self.h
                res += self.diverse_table[i + 1][ind] * subres
        else:
            while x <= self.points[-(ind + 2)][0]:
                ind += 1
            res += self.points[-(ind + 1)][1]
            for i in range(len(self.diverse_table) - 1 - ind):
                subres *= (x - self.points[-(i + ind + 1)][0]) / self.h
                res += self.diverse_table[i + 1][-(ind + i + 2)] * subres
        return res


INTERPOLATIONS: Final[list[Interpolation]] = [
    LagrangeInterpolation(),
    NewtonSeparateInterpolation(),
    NewtonFiniteInterpolation()
]
