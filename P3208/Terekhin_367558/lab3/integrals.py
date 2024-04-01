from typing import Final, Callable

from P3208.Terekhin_367558.lab2.functions import Describable


class Integral(Describable):
    option_name = 'function to calculate integral'

    def __init__(self, description: str, function: Callable[[float], float]):
        super().__init__(description)
        self.function: Callable[[float], float] = function


INTEGRALS: Final[list[Integral]] = [
    Integral('x^2', lambda x: x**2),
    Integral('x - 2', lambda x: x - 2)
]