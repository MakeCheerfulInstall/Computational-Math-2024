import math
from typing import Callable


class Function:
    def __init__(self, func: Callable[[float], float], text: str):
        self.func = func
        self.text = text

    def __call__(self, x: float):
        return self.func(x)

    def __str__(self):
        return self.text


functions = [
    Function(lambda x: -2 * x ** 3 - 5 * x ** 2 + 7 * x - 13, "-2x^3 - 5x^2 + 7x - 13"),
    Function(lambda x: x ** 2, "x^2"),
    Function(lambda x: math.sin(x), "sin(x)"),
]