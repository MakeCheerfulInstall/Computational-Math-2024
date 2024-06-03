import math

from enum import Enum


class EquationSystemDerivative(Enum):
    FIRST = ((lambda x, y: 2 * x, lambda x, y: 2 * y), (lambda x, y: -6 * x, lambda x, y: 1))
    SECOND = ((lambda x, y: y, lambda x, y: x), (lambda x, y: 12 * x ** 2, lambda x, y: -3 * y ** 2))

    @staticmethod
    def get_system(system_type: int) -> tuple:
        match system_type:
            case 1:
                return EquationSystemDerivative.FIRST.value
            case 2:
                return EquationSystemDerivative.SECOND.value

