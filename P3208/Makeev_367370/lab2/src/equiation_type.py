import math
from enum import Enum
from equation import Equation, EquationSystem


class ExpressionType(Enum):
    FIRST = lambda x: 3 * (x ** 3) - 8 * x + 3
    SECOND = lambda x: math.e ** x - 7 * x
    THIRD = lambda x: 10 * math.log2(x ** x) + 1 - x
    FOURTH = lambda x, y: math.tan(x*y + 0.2) - x ** 2
    FITH = lambda x, y: (x ** 2) + 2 * (y ** 2) - 1
    SIXTH = lambda x, y: math.sin(x + y) - 1.2 * x - 0.2


class DerivativeType(Enum):
    FIRST = lambda x: 9 * (x ** 2) - 8
    SECOND = lambda x: math.e ** x - 7
    THIRD = lambda x: (10 + 10 * math.log(x, math.e)) / (math.log(2, math.e)) - 1
    FOURTH_X = lambda x, y: y * math.tan(x * y + 0.2) ** 2 + y - 2 * x
    FOURTH_Y = lambda x, y: x * math.tan(x * y + 0.2) ** 2 + x
    FITH_X = lambda x, y: 2 * x
    FITH_Y = lambda x, y: 4 * y
    SIXTH_X = lambda x, y: math.cos(x + y) - 1.2
    SIXTH_Y = lambda x, y:  math.cos(x + y)


class ExpressionTypeView(Enum):
    FIRST = '3x^3 + 3 = 8x'
    SECOND = 'e^x = 7x'
    THIRD = '10log(x^x) = x - 1'
    FOURTH = 'tg(xy + 0.2) = x^2'
    FIFTH = 'x^2 + 2y^2 = 1'
    SIXTH = 'sin(x + y) - 1.2x = 0.2'


class EquationType(Enum):
    FIRST: Equation = Equation(ExpressionType.FIRST, DerivativeType.FIRST,
                               [-1.7954, 0.3988, 1.3967], ExpressionTypeView.FIRST.value)

    SECOND: Equation = Equation(ExpressionType.SECOND, DerivativeType.SECOND,
                                [0.1692, 3.0664], ExpressionTypeView.SECOND.value)

    THIRD: Equation = Equation(ExpressionType.THIRD, DerivativeType.THIRD,
                               [0.086, 1], ExpressionTypeView.THIRD.value)


class EquationSystemType(Enum):
    FIRST: EquationSystem = EquationSystem(ExpressionType.FOURTH, ExpressionType.FITH, DerivativeType.FOURTH_X,
                                           DerivativeType.FOURTH_Y, DerivativeType.FITH_X, DerivativeType.FITH_Y,
                                           [(-0.779, -0.4434), (-0.2199, 0.6898), (0.2199, -0.6898), (0.779, 0.4434)],
                                           ExpressionTypeView.FOURTH.value, ExpressionTypeView.FIFTH.value)

    SECOND: EquationSystem = EquationSystem(ExpressionType.SIXTH, ExpressionType.FITH, DerivativeType.SIXTH_X,
                                           DerivativeType.SIXTH_Y, DerivativeType.FITH_X, DerivativeType.FITH_Y,
                                           [(-0.9381, -0.24492), (0.5991, 0.5662)],
                                           ExpressionTypeView.SIXTH.value, ExpressionTypeView.FIFTH.value)
