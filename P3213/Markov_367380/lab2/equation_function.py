import math

from enum import Enum
from typing import Callable


class EquationFunction(Enum):
    FIRST = lambda x: x * x + 3 * x - 4
    SECOND = lambda x: x ** 3 - 7 * x + 2
    THIRD = lambda x: math.e ** (3 * x) - 3

    @staticmethod
    def get_function(equation_type: int) -> Callable:
        match equation_type:
            case 1:
                return EquationFunction.FIRST
            case 2:
                return EquationFunction.SECOND
            case 3:
                return EquationFunction.THIRD

