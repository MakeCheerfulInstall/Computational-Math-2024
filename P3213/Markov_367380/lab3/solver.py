from typing import Callable

from function import Function, Function2


class Solver:
    def __init__(self, function: Function, method_type: int, modification: int, precision: float, left: float,
                 right: float):
        self.__function = function
        self.__method_type = method_type
        self.__precision = precision
        self.__left = left
        self.__right = right
        self.__modification = modification

    def solve(self) -> tuple:
        if self.__method_type == 1:
            return self.__solve_rect()
        elif self.__method_type == 2:
            return self.__solve_trapezoid()
        elif self.__method_type == 3:
            return self.__solve_simpson()

    def __solve_rect(self) -> tuple:
        n: int = 5
        ans1: float = self.__rect_for_n(n)
        ans2: float = self.__rect_for_n(n * 2)
        cur_precision: float = abs(ans2 - ans1)

        while cur_precision > self.__precision:
            n *= 2
            ans1: float = ans2
            ans2: float = self.__rect_for_n(n * 2)
            cur_precision = abs(ans2 - ans1) * 3
        return ans1, n

    def __rect_get_start(self, h: float) -> float:
        match self.__modification:
            case 1:
                return self.__left
            case 2:
                return self.__left + h
            case 3:
                return self.__left + h / 2

    def __rect_for_n(self, n: int) -> float:
        a: float = self.__left
        b: float = self.__right
        f: Callable = self.__function.get_function()
        h: float = (b - a) / n
        cur: float = self.__rect_get_start(h)
        ans: float = 0

        for i in range(n):
            ans += f(cur) * h
            cur += h

        return ans

    def __solve_trapezoid(self) -> tuple:
        n: int = 5
        ans1: float = self.__trapezoid_for_n(n)
        ans2: float = self.__trapezoid_for_n(n * 2)
        cur_precision: float = abs(ans2 - ans1) * 3

        while cur_precision > self.__precision:
            n *= 2
            ans1: float = ans2
            ans2: float = self.__trapezoid_for_n(n * 2)
            cur_precision = abs(ans2 - ans1)
        return ans1, n

    def __trapezoid_for_n(self, n: int) -> float:
        a: float = self.__left
        b: float = self.__right
        f: Callable = self.__function.get_function()
        h: float = (b - a) / n
        ans: float = 0
        cur: float = a

        for i in range(n):
            ans += ((f(cur) + f(cur + h)) / 2) * h
            cur += h

        return ans

    def __solve_simpson(self) -> tuple:
        n: int = 4
        ans1: float = self.__simpson_for_n(n)
        ans2: float = self.__simpson_for_n(n * 2)
        cur_precision: float = abs(ans2 - ans1)

        while cur_precision > self.__precision:
            n *= 2
            ans1: float = ans2
            ans2: float = self.__simpson_for_n(n * 2)
            cur_precision = abs(ans2 - ans1)
        return ans1, n

    def __simpson_for_n(self, n: int) -> float:
        a: float = self.__left
        b: float = self.__right
        f: Callable = self.__function.get_function()
        h: float = (b - a) / n
        ans: float = f(a) + f(b)
        cur: float = a
        for i in range(1, n):
            cur += h
            if i % 2:
                ans += 4 * f(cur)
            else:
                ans += 2 * f(cur)
        return ans * h / 3


class Solver2:
    def __init__(self, function: Function2, method_type: int, modification: int, precision: float, left: float,
                 right: float):
        self.__function = function
        self.__method_type = method_type
        self.__precision = precision
        self.__left = left
        self.__right = right
        self.__modification = modification

    def get_function(self) -> Function2:
        return self.__function

    def solve(self) -> tuple:
        if self.__method_type == 1:
            return self.__solve_rect()
        elif self.__method_type == 2:
            return self.__solve_trapezoid()
        elif self.__method_type == 3:
            return self.__solve_simpson()

    def __solve_rect(self) -> tuple:
        n: int = 5
        ans1: float = self.__rect_for_n(n)
        ans2: float = self.__rect_for_n(n * 2)
        cur_precision: float = abs(ans2 - ans1)

        while cur_precision > self.__precision:
            n *= 2
            ans1: float = ans2
            ans2: float = self.__rect_for_n(n * 2)
            cur_precision = abs(ans2 - ans1) * 3
        return ans1, n

    def __rect_get_start(self, h: float) -> float:
        match self.__modification:
            case 1:
                return self.__left
            case 2:
                return self.__left + h
            case 3:
                return self.__left + h / 2

    def __rect_for_n(self, n: int) -> float:
        a: float = self.__left
        b: float = self.__right
        f: Callable = self.__function.get_function()
        h: float = (b - a) / n
        cur: float = self.__rect_get_start(h)
        ans: float = 0

        for i in range(n):
            ans += f(cur) * h
            cur += h

        return ans

    def __solve_trapezoid(self) -> tuple:
        n: int = 5
        ans1: float = self.__trapezoid_for_n(n)
        ans2: float = self.__trapezoid_for_n(n * 2)
        cur_precision: float = abs(ans2 - ans1) * 3

        while cur_precision > self.__precision:
            n *= 2
            ans1: float = ans2
            ans2: float = self.__trapezoid_for_n(n * 2)
            cur_precision = abs(ans2 - ans1)
        return ans1, n

    def __trapezoid_for_n(self, n: int) -> float:
        a: float = self.__left
        b: float = self.__right
        f: Callable = self.__function.get_function()
        h: float = (b - a) / n
        ans: float = 0
        cur: float = a

        for i in range(n):
            ans += ((f(cur) + f(cur + h)) / 2) * h
            cur += h

        return ans

    def __solve_simpson(self) -> tuple:
        n: int = 4
        ans1: float = self.__simpson_for_n(n)
        ans2: float = self.__simpson_for_n(n * 2)
        cur_precision: float = abs(ans2 - ans1)

        while cur_precision > self.__precision:
            n *= 2
            ans1: float = ans2
            ans2: float = self.__simpson_for_n(n * 2)
            cur_precision = abs(ans2 - ans1)
        return ans1, n

    def __simpson_for_n(self, n: int) -> float:
        a: float = self.__left
        b: float = self.__right
        f: Callable = self.__function.get_function()
        h: float = (b - a) / n
        ans: float = f(a) + f(b)
        cur: float = a
        for i in range(1, n):
            cur += h
            if i % 2:
                ans += 4 * f(cur)
            else:
                ans += 2 * f(cur)
        return ans * h / 3