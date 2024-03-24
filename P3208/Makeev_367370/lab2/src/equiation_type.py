import math
from enum import Enum
from equation import Equation


class ExpressionType(Enum):
    FIRST = lambda x: 3 * (x ** 3) - 8 * x + 3
    SECOND = lambda x: math.e ** x - 7 * x
    THIRD = lambda x: 10 * math.log2(x ** x) + 1 - x


class ExpressionTypeView(Enum):
    FIRST = '3x^3 - 8x + 3'
    SECOND = 'e^x - 7x'
    THIRD = '10log(x^x) - x + 1'


class DerivativeType(Enum):
    FIRST = lambda x: 9 * (x ** 2) - 8
    SECOND = lambda x: math.e ** x - 7
    THIRD = lambda x: (10 + 10 * math.log(x, math.e)) / (math.log(2, math.e)) - 1


class EquationType(Enum):
    FIRST: Equation = Equation(ExpressionType.FIRST, DerivativeType.FIRST,
                               [-1.7954, 0.3988, 1.3967], ExpressionTypeView.FIRST.value)
    SECOND: Equation = Equation(ExpressionType.SECOND, DerivativeType.SECOND,
                                [0.1692, 3.0664], ExpressionTypeView.SECOND.value)
    THIRD: Equation = Equation(ExpressionType.THIRD, DerivativeType.THIRD,
                               [0.086, 1], ExpressionTypeView.THIRD.value)
