from abc import abstractmethod
from typing import Final

from tabulate import tabulate

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
        super().__init__('Newton separate interpolation')
        self.diverse_table = []

    def interpolate(self, x: float) -> float:
        res: float = self.points[0][1]
        for i in range(len(self.diverse_table)):
            subres: float = 1
            for j in range(i):
                subres *= x - self.points[j][0]
            res += self.diverse_table[i][0] * subres
        return res

    def set_points(self, points: list[tuple[float, float]]) -> None:
        self.points = points
        n = len(self.points)
        self.diverse_table = [[p[1] for p in points]]
        for i in range(1, n):
            self.diverse_table.append([0] * n)
        headers = ['y']
        for i in range(1, n):
            headers.append(f'Δ^{i}y' if i != 1 else 'Δy')
            for j in range(n - i):
                self.diverse_table[i][j] = (self.diverse_table[i - 1][j + 1]
                                            - self.diverse_table[i - 1][j]) / (self.points[j + i][0] - self.points[j][0])
        print(tabulate(self.diverse_table, headers, tablefmt='pretty'))


INTERPOLATIONS: Final[list[Interpolation]] = [
    LagrangeInterpolation(),
    NewtonSeparateInterpolation()
]
