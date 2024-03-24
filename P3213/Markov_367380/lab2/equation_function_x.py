import math
from enum import Enum
from typing import Callable


class EquationFunctionX(Enum):
    FIRST = lambda x: (4 - x * x) / 3
    SECOND = lambda x: (x ** 3 + 2) / 7
    THIRD = lambda x: 1 / 3 * math.log(3)

    @staticmethod
    def get_function(equation_type: int) -> Callable:
        match equation_type:
            case 1:
                return EquationFunctionX.FIRST
            case 2:
                return EquationFunctionX.SECOND
            case 3:
                return EquationFunctionX.THIRD
