from enum import Enum
from typing import Callable


class Method:
    def __init__(self, func: Callable, descr: str, for_sys: bool) -> None:
        self.func: Callable = func
        self.descr: str = descr
        self.for_sys: bool = for_sys

    def solve(self, eq, data) -> None:
        self.func(eq, data)

    def __str__(self) -> str:
        return self.descr


class MethodData:
    def __init__(self, a: float, b: float, e: float) -> None:
        self.a = a
        self.b = b
        self.e = e


def mid_div_method(eq, data: MethodData) -> None:
    print("mid_div_method executing...")


def secant_method(eq, data: MethodData) -> None:
    print("secant_method executing...")


def simple_it_method(eq, data: MethodData) -> None:
    print("simple_it_method executing...")


def nuton_method(eq, data: MethodData) -> None:
    print("nuton_method executing...")


class MethodType(Enum):
    FIRST: Method = Method(mid_div_method, 'Метод половинного деления', False)
    SECOND: Method = Method(secant_method, 'Метод секущих', False)
    THIRD: Method = Method(simple_it_method, 'Метод простой итерации', False)
    FOURTH: Method = Method(nuton_method, 'Метод Ньютона', True)
