from typing import Tuple, Callable
from enum import Enum


class Integral:
    def __init__(self, segment: Tuple[float, float], func: Callable[[float], float], func_view: str) -> None:
        self.segment = segment
        self.func = func
        self.func_view = func_view

    def __str__(self):
        return f'{self.func_view}\t[{self.segment[0]}; {self.segment[1]}]'


class IntegralType(Enum):
    ONE: Integral = Integral((1, 3), lambda x: 2 * (x ** 3) - 9 * (x ** 2) - 7 * x + 11, '2x^3 - 9x^2 - 7x + 11')
