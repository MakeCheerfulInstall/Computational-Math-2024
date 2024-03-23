import math
from enum import Enum
from equation import Equation


class ExpressionType(Enum):
    FIRST = lambda x: 3 * (x ** 3) - 8 * x + 3
    SECOND = lambda x: math.e ** x - 7 * x
    THIRD = lambda x: 10 * math.log2(x ** x) + 1


class ExpressionViewType(Enum):
    FIRST = '3x^3 - 8x = -3'
    SECOND = 'e^x = 7x'
    THIRD = '10log(x^x) = -1'


class DerivativeType(Enum):
    FIRST = lambda x: 9 * (x ** 2) - 8
    SECOND = lambda x: math.e ** x - 7
    THIRD = lambda x: (10 + 10 * math.log(x, math.e)) / (math.log(2, math.e))


class EquationType(Enum):
    FIRST = Equation(ExpressionType.FIRST, DerivativeType.FIRST, [-1.7954, 0.3988, 1.3967], ExpressionViewType.FIRST)
    SECOND = Equation(ExpressionType.SECOND, DerivativeType.SECOND, [0.1692, 3.0664], ExpressionViewType.SECOND)
    THIRD = Equation(ExpressionType.THIRD, DerivativeType.THIRD, [0.1, 0.7929], ExpressionViewType.THIRD)
