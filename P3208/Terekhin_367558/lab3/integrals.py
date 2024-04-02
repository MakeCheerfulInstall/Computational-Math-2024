import math
from typing import Final, Callable

from P3208.Terekhin_367558.lab2.functions import Describable


class Integral(Describable):
    option_name = 'function to calculate integral'

    def __init__(self, description: str, function: Callable[[float], float]):
        super().__init__(description)
        self.function: Callable[[float], float] = function


def condition_function(x: float) -> float:
    if x <= -2:
        return -2 / x
    if x <= 2:
        return abs(x) - 1
    return (x - 2)**0.5 + 1


INTEGRALS: Final[list[Integral]] = [
    Integral('-2x^3 - 5x^2 + 7x - 13', lambda x: -2 * x**3 - 5 * x**2 + 7 * x - 13),
    Integral('atan(sqrt(6x - 1))', lambda x: math.atan((6 * x - 1)**0.5) if 6 * x > 1 else math.nan),
    Integral('cos(x) / (e^x + 4)', lambda x: math.cos(x) / (math.e**x + 4)),
    Integral('tan(sqrt(x)) / sqrt(x)', lambda x: math.tan(x**0.5) / x**0.5 if x > 0 else math.nan),
    Integral('| -2 / x ,  x <= -2\n   | |x| - 1 ,  -2 < x <= 2\n   | sqrt(x - 2) + 1 ,  x > 2', condition_function)
]