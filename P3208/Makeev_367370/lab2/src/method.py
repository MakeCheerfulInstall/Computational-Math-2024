from enum import Enum
from typing import Callable, List


class Point:
    def __init__(self, x: float, y: float) -> None:
        self.x = x
        self.y = y

    def __str__(self) -> str:
        return f'Point({self.x}, {self.y})'


class MethodResult:
    def __init__(self, point: Point, iterations: int) -> None:
        self.point = point
        self.iterations = iterations

    def __str__(self) -> str:
        return f'{self.point} It={self.iterations}'


class Method:
    def __init__(self, func: Callable, descr: str, for_sys: bool) -> None:
        self.func: Callable = func
        self.descr: str = descr
        self.for_sys: bool = for_sys

    def solve(self, eq, data) -> MethodResult | None:
        segment: List = [data.a, data.b]
        if not eq.has_one_root(segment):
            print('Not one root on this interval!')
            return

        return self.func(eq, data)

    def __str__(self) -> str:
        return self.descr


class MethodData:
    def __init__(self, a: float, b: float, e: float) -> None:
        self.a = a
        self.b = b
        self.e = e


def mid_div_method(eq, data: MethodData) -> MethodResult | None:
    counter = 0
    a: float = data.a
    b: float = data.b
    x: float = (a + b) / 2
    rising: bool = eq.get_res(a) < 0
    while abs(a - b) > data.e:
        f_x = eq.get_res(x)
        if (f_x < 0 and rising) or (f_x > 0 and not rising):
            a = x
        else:
            b = x

        x = (a + b) / 2
        counter += 1

        if counter > 1000:
            print('Too many iterations!')
            return

    return MethodResult(Point(x, eq.get_res(x)), counter)


def secant_method(eq, data: MethodData) -> MethodResult | None:
    counter = 0
    x_last: float = data.a
    x: float = data.b
    f_xlast = eq.get_res(x_last)
    f_x = eq.get_res(x)
    while abs(x_last - x) > data.e:
        x_next = x - ((x - x_last) * f_x / (f_x - f_xlast))
        x_last = x
        x = x_next
        f_xlast = eq.get_res(x_last)
        f_x = eq.get_res(x)
        counter += 1

        if counter > 1000:
            print('Too many iterations!')
            return

    return MethodResult(Point(x, eq.get_res(x)), counter)


def simple_it_method(eq, data: MethodData) -> MethodResult | None:
    print("simple_it_method executing...")


def nuton_method(eq, data: MethodData) -> MethodResult | None:
    print("nuton_method executing...")


class MethodType(Enum):
    FIRST: Method = Method(mid_div_method, 'Метод половинного деления', False)
    SECOND: Method = Method(secant_method, 'Метод секущих', False)
    THIRD: Method = Method(simple_it_method, 'Метод простой итерации', False)
    FOURTH: Method = Method(nuton_method, 'Метод Ньютона', True)
