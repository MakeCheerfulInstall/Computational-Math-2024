from enum import Enum
from typing import Callable


class Method:
    def __init__(self, func: Callable, descr: str, for_sys: bool) -> None:
        self.func: Callable = func
        self.descr: str = descr
        self.for_sys: bool = for_sys

    def solve(self, eq) -> None:
        self.func(eq)

    def __str__(self) -> str:
        return self.descr


def mid_div_method(eq) -> None:
    print("mid_div_method executing...")


def secant_method(eq) -> None:
    print("secant_method executing...")


def simple_it_method(eq) -> None:
    print("simple_it_method executing...")


def nuton_method(eq) -> None:
    print("nuton_method executing...")


class MethodType(Enum):
    FIRST: Method = Method(mid_div_method, 'Метод половинного деления', False)
    SECOND: Method = Method(secant_method, 'Метод секущих', False)
    THIRD: Method = Method(simple_it_method, 'Метод простой итерации', False)
    FOURTH: Method = Method(nuton_method, 'Метод Ньютона', True)
