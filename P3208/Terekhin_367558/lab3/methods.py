import math
from abc import abstractmethod
from typing import Final, Callable

from P3208.Terekhin_367558.lab2.functions import Describable


class Method(Describable):
    option_name = 'method'

    def __init__(self, description: str):
        super().__init__(description)
        self.function: Callable[[float], float] | None = None
        self.partition: int = 4
        self.prev_value: float = math.inf
        self.accuracy_order: int = 2

    @abstractmethod
    def calculate_integral(self, a: float, b: float, eps: float) -> float:
        pass

    def set_function(self, function: Callable[[float], float]) -> None:
        self.function = function


class RectangleMethod(Method):
    def __init__(self, description: str):
        super().__init__(description)

    @abstractmethod
    def get_interval_partition(self, a: float, b: float) -> list[float]:
        pass

    def calculate_integral(self, a: float, b: float, eps: float) -> float:
        h: float = (b - a) / self.partition
        x: list[float] = self.get_interval_partition(a, b)
        if self.function is not None:
            y: list[float] = [self.function(num) for num in x]
        else:
            raise TypeError('Function is not defined')
        ans: float = sum(y) * h
        if abs((ans - self.prev_value) / (2 ** self.accuracy_order - 1)) < eps:
            return ans
        self.prev_value = ans
        self.partition *= 2
        return self.calculate_integral(a, b, eps)


class LeftRectangleMethod(RectangleMethod):
    def __init__(self):
        super().__init__('Left Rectangle Method')

    def get_interval_partition(self, a: float, b: float) -> list[float]:
        h: float = (b - a) / self.partition
        return [a + h * i for i in range(0, self.partition)]


class RightRectangleMethod(RectangleMethod):
    def __init__(self):
        super().__init__('Right Rectangle Method')

    def get_interval_partition(self, a: float, b: float) -> list[float]:
        h: float = (b - a) / self.partition
        return [a + h * i for i in range(1, self.partition + 1)]


class MiddleRectangleMethod(RectangleMethod):
    def __init__(self):
        super().__init__('Middle Rectangle Method')

    def get_interval_partition(self, a: float, b: float) -> list[float]:
        h: float = (b - a) / self.partition
        return [a + h * i + h / 2 for i in range(0, self.partition)]


METHODS: Final[list[Method]] = [
    LeftRectangleMethod(),
    RightRectangleMethod(),
    MiddleRectangleMethod()
]
