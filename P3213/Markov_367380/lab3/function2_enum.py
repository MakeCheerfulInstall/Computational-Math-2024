import math

from enum import Enum
from typing import Callable


class Function2Enum(Enum):
    FIRST = lambda x: 1 / x
    SECOND = lambda x: 1 / (2 * math.sqrt(x))

    @staticmethod
    def get_function(function_type: int) -> Callable:
        match function_type:
            case 1:
                return Function2Enum.FIRST
            case 2:
                return Function2Enum.SECOND
