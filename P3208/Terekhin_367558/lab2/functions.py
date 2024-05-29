import math
from typing import Callable, Final

from matplotlib import pyplot as plt


class Describable:
    option_name: str = 'option'

    def __init__(self, description: str) -> None:
        self.description = description


class Function(Describable):
    option_name = 'function'

    def __init__(self, func: Callable[[float], float], first_derivation: Callable[[float], float],
                 second_derivation: Callable[[float], float], description: str) -> None:
        super().__init__(description)
        self.func = func
        self.first_derivation = first_derivation
        self.second_derivation = second_derivation


class FunctionSystem(Describable):
    option_name = 'system'

    def __init__(self, first: Callable[[float, float], float], second: Callable[[float, float], float],
                 first_x_derivation: Callable[[float, float], float],
                 first_y_derivation: Callable[[float, float], float],
                 second_x_derivation: Callable[[float, float], float],
                 second_y_derivation: Callable[[float, float], float],
                 first_y_from_x: Callable[[float], float | list[float]],
                 second_y_from_x: Callable[[float], float | list[float]], description: str):
        super().__init__(description)
        self.first = first
        self.second = second
        self.first_x_derivation = first_x_derivation
        self.first_y_derivation = first_y_derivation
        self.second_x_derivation = second_x_derivation
        self.second_y_derivation = second_y_derivation
        self.first_y_from_x = first_y_from_x
        self.second_y_from_x = second_y_from_x


class DifferentialEquation(Describable):
    option_name = 'equation'

    def __init__(self, function: Callable[[float, float], float],
                 solution: Callable[[float, float], float],
                 const: Callable[[float, float], float], description: str):
        super().__init__(description)
        self.function = function
        self.solution = solution
        self.const = const
        self.c: float | None = None


EQUATIONS: Final[list[DifferentialEquation]] = [
    DifferentialEquation(lambda x, y: 0.7 + 2 * y + 1.1 * x * x,
                         lambda x, c=0: c * math.exp(2 * x) - 0.55 * x * x - 0.55 * x - 5 / 8,
                         lambda x, y: (y + 0.55 * x * x + 0.55 * x + 5 / 8) / math.exp(2 * x),
                         'y\' = 0,7 + 2y + 1,1x^2'),
    DifferentialEquation(lambda x, y: 4 * x + y / 3,
                         lambda x, c=0: c * math.exp(x / 3) - 12 * x - 36,
                         lambda x, y: (y + 12 * x + 36) / math.exp(x / 3),
                         'y\'= 4x + y/3'),
    DifferentialEquation(lambda x, y: y + (1 + x) * y * y,
                         lambda x, c=0: -math.exp(x) / (x * math.exp(x) + c),
                         lambda x, y: -math.exp(x) / y - x * math.exp(x),
                         'y\' = y + (1 + x)y^2'),
    DifferentialEquation(lambda x, y: 2 * x * y / (x * x + 1),
                         lambda x, c=0: c * (x * x + 1),
                         lambda x, y: y / (x * x + 1),
                         'y\' = 2xy / (x^2 + 1)')
]

FUNCTIONS: Final[list[Function]] = [
    Function(lambda x: x * x * x + 4.81 * x * x - 17.37 * x + 5.38,
             lambda x: 3 * x * x + 2 * 4.81 * x - 17.37,
             lambda x: 6 * x + 2 * 4.81,
             'x^3 + 4,81x^2 - 17,37x + 5,38'),
    Function(lambda x: 2 * x * x * x - 1.89 * x * x - 5 * x + 2.34,
             lambda x: 6 * x * x - 2 * 1.89 * x - 5,
             lambda x: 12 * x - 2 * 1.89,
             '2x^3 - 1,89x^2 - 5x + 2,34'),
    Function(lambda x: math.exp(x / 3) - 2 * math.cos(x + 4),
             lambda x: math.exp(x / 3) / 3 + 2 * math.sin(x + 4),
             lambda x: math.exp(x / 3) / 9 + 2 * math.cos(x + 4),
             'e^(x / 3) - 2cos(x + 4)'),
    Function(lambda x: x ** 2 - math.exp(x),
             lambda x: 2 * x - math.exp(x),
             lambda x: 2 - math.exp(x),
             'x^2 - e^x')
]

SYSTEMS: Final[list[FunctionSystem]] = [
    FunctionSystem(lambda x, y: math.sin(y) + 2 * x,
                   lambda x, y: math.cos(x - 1) + y - 0.7,
                   lambda x, y: 2,
                   lambda x, y: math.cos(y),
                   lambda x, y: -math.sin(x - 1),
                   lambda x, y: 1,
                   lambda x: [math.asin(-2 * x) + 2 * math.pi * i if 2 * abs(x) <= 1 else math.nan
                              for i in range(-3, 3)] + [math.asin(2 * x) + 2 * math.pi * i - math.pi if 2 * abs(x) <= 1
                                                        else math.nan for i in range(-3, 3)],
                   lambda x: 0.7 - math.cos(x - 1),
                   '| sin(y) + 2x = 0\n   | cos(x - 1) + y = 0,7'),
    FunctionSystem(lambda x, y: math.tan(x * y + 0.3) - x ** 2,
                   lambda x, y: 0.5 * x ** 2 + 2 * y ** 2 - 1,
                   lambda x, y: y / (math.cos(x * y + 0.3)) ** 2 - 2 * x,
                   lambda x, y: x / (math.cos(x * y + 0.3)) ** 2,
                   lambda x, y: x,
                   lambda x, y: 4 * y,
                   lambda x: [(math.atan(x ** 2) + 2 * math.pi * i - 0.3) / x if x != 0
                              else math.inf for i in range(-5, 5)],
                   lambda x: [math.sqrt((1 - 0.5 * x ** 2) / 2) if abs(x) ** 2 * 0.5 <= 1 else math.nan,
                              -math.atan(math.sqrt((1 - 0.5 * x ** 2)) / 2) if abs(x) ** 2 * 0.5 <= 1 else math.nan],
                   '| tg(xy + 0,3) = x^2\n   | 0.5x^2 + 2y^2 = 1')
]
