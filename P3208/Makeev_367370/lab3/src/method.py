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


class SquaresType(Enum):
    LEFT = 0,
    RIGHT = 1,
    CENTER = 2


def abstract_sqares(intg: Integral, intv: Interval, eps: float, sq_type: SquaresType) -> IntegralAnswer:
    r: float = math.inf
    prev_ans: float = math.inf
    n: int = DEF_N
    while r > eps:
        h: float = (intv.b - intv.a) / n
        ans: float = 0
        for i in range(n):
            x: float = intv.a
            match sq_type:
                case SquaresType.LEFT:
                    x += h * i
                case SquaresType.RIGHT:
                    x += h * (i + 1)
                case SquaresType.CENTER:
                    x += h * (i + 0.5)

            y: float = intg.f_x(x)
            ans += y

        ans *= h
        r = abs(ans - prev_ans)
        prev_ans = ans
        n *= 2

    return IntegralAnswer(prev_ans, n // 2)


def left_squares(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    return abstract_sqares(intg, intv, eps, SquaresType.LEFT)


def right_squares(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    return abstract_sqares(intg, intv, eps, SquaresType.RIGHT)


def center_squares(intg: Integral, intv: Interval, eps: float) -> IntegralAnswer:
    return abstract_sqares(intg, intv, eps, SquaresType.CENTER)


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
