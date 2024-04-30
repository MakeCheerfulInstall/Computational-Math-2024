from enum import Enum
from typing import Callable


class EquationFunctionDerivative(Enum):
    FIRST = lambda x: -2 / 3 * x
    SECOND = lambda x: 3 / 7 * x ** 2
    THIRD = lambda x: 0

    @staticmethod
    def get_function(equation_type: int) -> Callable:
        match equation_type:
            case 1:
                return EquationFunctionDerivative.FIRST
            case 2:
                return EquationFunctionDerivative.SECOND
            case 3:
                return EquationFunctionDerivative.THIRD