import math
from enum import Enum
from equation import Equation, EquationSystem


class ExpressionType(Enum):
    FIRST = lambda x: 3 * (x ** 3) - 8 * x + 3
    SECOND = lambda x: math.e ** x - 7 * x
    THIRD = lambda x: 10 * math.log2(x ** x) + 1 - x
    FOURTH = lambda x: (7 * x - (x ** 2)) / 2
    FITH = lambda x: (x + 1) ** (1/3)


class DerivativeType(Enum):
    FIRST = lambda x: 9 * (x ** 2) - 8
    SECOND = lambda x: math.e ** x - 7
    THIRD = lambda x: (10 + 10 * math.log(x, math.e)) / (math.log(2, math.e)) - 1
    FOURTH_X = lambda x, y: 2 * x - 7
    FOURTH_Y = lambda x, y: 2
    FITH_X = lambda x, y: -1
    FITH_Y = lambda x, y: 3 * (y ** 2)


class ExpressionTypeView(Enum):
    FIRST = '3x^3 + 3 = 8x'
    SECOND = 'e^x = 7x'
    THIRD = '10log(x^x) = x - 1'
    FOURTH = 'x^2 + 2y = 7x'
    FIFTH = 'y^3 - x = 1'


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
                                           [(0.3297, 1.0996), (6.39, 1.9479)],
                                           ExpressionTypeView.FOURTH.value, ExpressionTypeView.FIFTH.value)

    # SECOND: EquationSystem = EquationSystem(ExpressionType.SIXTH, ExpressionType.FITH, DerivativeType.SIXTH_X,
    #                                        DerivativeType.SIXTH_Y, DerivativeType.FITH_X, DerivativeType.FITH_Y,
    #                                        [(-0.9381, -0.24492), (0.5991, 0.5662)],
    #                                        ExpressionTypeView.SIXTH.value, ExpressionTypeView.FIFTH.value)
