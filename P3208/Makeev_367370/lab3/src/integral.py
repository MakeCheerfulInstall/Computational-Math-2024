import math
from typing import Tuple, Callable
from enum import Enum


class Integral:
    def __init__(self, func: Callable[[float], float], func_view: str) -> None:
        self.func = func
        self.func_view = func_view

    def __str__(self):
        return self.func_view

    def f_x(self, x: float) -> float:
        return self.func(x)


class IntegralType(Enum):
    ONE: Integral = Integral(lambda x: 2 * (x ** 3) - 9 * (x ** 2) - 7 * x + 11, '2x^3 - 9x^2 - 7x + 11')
    TWO: Integral = Integral(lambda x: 3 * (x ** 2) - math.exp(x), '3x^2 - e^x')
    FREE: Integral = Integral(lambda x: 1 / math.sin(abs(x) ** 0.5), '1 / sin(sqrt(|x|))')
