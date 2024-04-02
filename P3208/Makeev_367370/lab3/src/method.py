import math
from typing import Callable, Tuple
from enum import Enum
from integral import Integral
from dto import Interval, IntegralAnswer

DEF_N: int = 4


class Method:
    def __init__(self, func: Callable[[Integral, Interval, float], IntegralAnswer], name: str) -> None:
        self.func = func
        self.name = name

    def solve(self, intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
        return self.func(intg, intv, eps)

    def __str__(self) -> str:
        return self.name


def left_squares(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    r: float = math.inf
    prev_ans: float = math.inf
    n: int = DEF_N
    while r > eps:
        h: float = (intv.b - intv.a) / n
        ans: float = 0
        for i in range(n):
            x: float = intv.a + h * i
            y: float = intg.f_x(x)
            ans += y

        ans *= h
        r = abs(ans - prev_ans)
        prev_ans = ans
        n *= 2

    return IntegralAnswer(prev_ans, n // 2)


def right_squares(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    pass


def center_squares(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    pass


def trap(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    pass


def simpson(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    pass


class MethodType(Enum):
    ONE: Method = Method(left_squares, 'Метод левых прямоугольников')
    TWO: Method = Method(right_squares, 'Метод правых прямоугольников')
    FREE: Method = Method(center_squares, 'Метод средних прямоугольников')
    FOUR: Method = Method(trap, 'Метод трапеций')
    FIVE: Method = Method(simpson, 'Метод симпсона')
