from enum import Enum


class EquationRoots(Enum):
    FIRST = [0.0]
    SECOND = [0.0]
    THIRD = [0.3662]

    @staticmethod
    def get_roots(equation_type: int) -> list:
        match equation_type:
            case 1:
                return EquationRoots.FIRST.value
            case 2:
                return EquationRoots.SECOND.value
            case 3:
                return EquationRoots.THIRD.value
