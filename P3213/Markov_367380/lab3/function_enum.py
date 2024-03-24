import math

from enum import Enum
from typing import Callable


class FunctionEnum(Enum):
    FIRST = lambda x: x * x + 3 * x - 4
    SECOND = lambda x: x ** 3 - 7 * x + 2
    THIRD = lambda x: math.e ** (math.sin(x)) - 3

    @staticmethod
    def get_function(function_type: int) -> Callable:
        match function_type:
            case 1:
                return FunctionEnum.FIRST
            case 2:
                return FunctionEnum.SECOND
            case 3:
                return FunctionEnum.THIRD

