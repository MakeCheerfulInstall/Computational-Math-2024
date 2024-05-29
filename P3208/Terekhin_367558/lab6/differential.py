from abc import abstractmethod
from typing import Final

from P3208.Terekhin_367558.lab2.functions import Describable, DifferentialEquation


class Differential(Describable):
    def __init__(self, description: str):
        super().__init__(description)
        self.x: list[float] = []
        self.x_new: list[float] = []
        self.y: list[float] = []
        self.y_new: list[float] = []
        self.h: float = 0

    def set_data(self, a: float, b: float, h: float, init_y: float):
        self.x = []
        self.x_new = []
        c: float = a
        while c < b:
            self.x.append(c)
            self.x_new.append(c)
            c += h / 2
            if c >= b:
                break
            self.x_new.append(c)
            c += h / 2
        self.x.append(b)
        self.x_new.append(b)
        self.y = [init_y] + [0] * (len(self.x) - 1)
        self.y_new = [init_y] + [0] * (len(self.x) - 1)
        self.h = h

    @abstractmethod
    def solve(self, equation: DifferentialEquation, eps: float):
        pass


class EulerDifferential(Differential):
    def __init__(self):
        super().__init__('Euler Method')

    def solve(self, equation: DifferentialEquation, eps: float) -> list[float]:
        self.set_data(self.x[0], self.x[-1], self.h, self.y[0])
        n: int = len(self.x)
        for i in range(1, n):
            self.y[i] = self.y[i - 1] + self.h * equation.function(self.x[i - 1], self.y[i - 1])
            self.y_new[i] = self.y[i] + self.h * equation.function((self.x[i] + self.x[i - 1]) / 2, self.y[i])
            if i == (n - 1) and abs(self.y[i] - self.y_new[i]) >= eps:
                self.h /= 2
                return self.solve(equation, eps)
        return self.y

DIFFERENTIALS: Final[list[Differential]] = [
    EulerDifferential()
]
