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


class MethodType(Enum):
    LEFT_SQ = 0,
    RIGHT_SQ = 1,
    MID_SQ = 2,
    TRAP = 3,
    SIMPSON = 4


def proxy_method(intg: Integral, intv: Interval, eps: float, m_type: MethodType) -> IntegralAnswer:
    r: float = math.inf
    prev_ans: float = math.inf
    n: int = DEF_N
    while r > eps:
        h: float = (intv.b - intv.a) / n
        ans: float
        if m_type == MethodType.TRAP or m_type == MethodType.SIMPSON:
            ans = trap_or_simpson(intg, intv.a, n, h, m_type)
        else:
            ans = abstract_sqares(intg, intv.a, n, h, m_type)

        r = abs(ans - prev_ans)
        prev_ans = ans
        n *= 2

    return IntegralAnswer(prev_ans, n // 2)


def abstract_sqares(intg: Integral, left: float, n: int, h: float, m_type: MethodType) -> float:
    ans: float = 0
    for i in range(n):
        x: float = left
        match m_type:
            case MethodType.LEFT_SQ:
                x += h * i
            case MethodType.RIGHT_SQ:
                x += h * (i + 1)
            case MethodType.MID_SQ:
                x += h * (i + 0.5)

        y: float = intg.f_x(x)
        ans += y

    ans *= h

    return ans


def trap_or_simpson(intg: Integral, left: float, n: int, h: float, m_type: MethodType) -> float:
    x_0: float = left
    x_n: float = left + n * h
    y_0: float = intg.f_x(x_0)
    y_n: float = intg.f_x(x_n)

    ans: float = y_0 + y_n

    for i in range(1, n):
        x: float = left + h * i
        y: float = intg.f_x(x)

        if m_type == MethodType.TRAP or i % 2 == 0:
            ans += 2 * y
        else:
            ans += 4 * y

    if m_type == MethodType.TRAP:
        return ans * h / 2
    else:
        return ans * h / 3


def left_squares(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    return proxy_method(intg, intv, eps, MethodType.LEFT_SQ)


def right_squares(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    return proxy_method(intg, intv, eps, MethodType.RIGHT_SQ)


def mid_squares(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    return proxy_method(intg, intv, eps, MethodType.MID_SQ)


def trap(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    return proxy_method(intg, intv, eps, MethodType.TRAP)


def simpson(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    return proxy_method(intg, intv, eps, MethodType.SIMPSON)


class MethodList(Enum):
    ONE: Method = Method(left_squares, 'Метод левых прямоугольников')
    TWO: Method = Method(right_squares, 'Метод правых прямоугольников')
    FREE: Method = Method(mid_squares, 'Метод средних прямоугольников')
    FOUR: Method = Method(trap, 'Метод трапеций')
    FIVE: Method = Method(simpson, 'Метод симпсона')
