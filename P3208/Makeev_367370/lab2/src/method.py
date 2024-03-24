from enum import Enum
from typing import Callable, List

from dto import MethodResult, MethodData, Point
from utils import draw_graph


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

        result = self.func(eq, data)
        draw_graph(eq, data.a, data.b, result.point)
        return result

    def __str__(self) -> str:
        return self.descr


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
    phi = eq.create_phi_func(data.a, data.b)
    if phi is None:
        print("Can't solve it by this method")
        return

    x_last: float = data.a
    x: float = data.b
    counter: int = 0
    while abs(x_last - x) > data.e:
        x_next: float = phi(x)
        x_last = x
        x = x_next
        counter += 1

        if counter > 1000:
            print('Too many iterations!')
            return

    return MethodResult(Point(x, eq.get_res(x)), counter)


def sys_simple_it_method(eq, data: MethodData) -> MethodResult | None:
    print("sys_simple_it_method executing...")
    print(eq)


class MethodType(Enum):
    FIRST: Method = Method(mid_div_method, 'Метод половинного деления', False)
    SECOND: Method = Method(secant_method, 'Метод секущих', False)
    THIRD: Method = Method(simple_it_method, 'Метод простых итераций', False)
    FOURTH: Method = Method(sys_simple_it_method, 'Метод простых итераций', True)
