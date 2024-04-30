from enum import Enum


class EquationRoots(Enum):
    FIRST = [-4.0, 1.0]
    SECOND = [-2.7785, 0.2892, 2.4893]
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
