import math
from abc import abstractmethod, ABC
from typing import Final

from prettytable import PrettyTable


class Method(ABC):
    EPSILON: Final = 4

    def __init__(self, name, view):
        self.name = name
        self.view = view

    @abstractmethod
    def calculate(self, dots: tuple) -> list:
        pass

    @abstractmethod
    def get_deviation_for_each(self, dots: tuple, coefficients: list) -> list:
        pass

    @abstractmethod
    def calculate_empire_func_ordinate(self, x: list, coefficients: list) -> list:
        pass

    def get_standard_deviation(self, dots: tuple, coefficients: list) -> float:
        x, y = dots
        return self.round_value(math.sqrt(self.get_deviation(dots, coefficients) / len(x)))

    def get_deviation(self, dots: tuple, coefficients: list) -> float:
        return self.round_value(sum([e ** 2 for e in self.get_deviation_for_each(dots, coefficients)]))

    def round_value(self, value: float):
        return round(value, self.EPSILON)

    def round_all(self, values: list):
        return [self.round_value(i) for i in values]

    def get_coefficient_of_determination(self, y, phi: list):
        n = len(phi)
        _phi = sum(phi) / n

        up = sum([(y[i] - phi[i]) ** 2 for i in range(n)])
        down = sum([(y[i] - _phi) ** 2 for i in range(n)])

        return self.round_value(1 - up / down)

    def draw_table(self, dots: tuple, empire_ordinates: list, e: list):
        x, y = dots
        t = PrettyTable()
        t.title = self.name
        t.field_names = ["i"] + [str(i) for i in range(len(x))]
        t.add_row(["x_i"] + x)
        t.add_row(["y_i"] + y)
        t.add_row(["P(x)"] + empire_ordinates)
        t.add_row(["e_i"] + e)
        print(t)
