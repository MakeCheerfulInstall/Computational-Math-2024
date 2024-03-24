import math

from enum import Enum
from typing import Callable


class EquationSystem(Enum):
    FIRST = (lambda x, y: 4 - (x * x + y * y), lambda x, y: 3 * x * x - y)
    SECOND = (lambda x, y: 7 - x * y, lambda x, y: 2 - (4 * x ** 3 - y ** 3))

    @staticmethod
    def get_system(system_type: int) -> tuple:
        match system_type:
            case 1:
                return EquationSystem.FIRST.value
            case 2:
                return EquationSystem.SECOND.value

