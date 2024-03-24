import math
from enum import Enum
from equation import Equation, EquationSystem
from dto import PhiData


class ExpressionType(Enum):
    FIRST = lambda x: 3 * (x ** 3) - 8 * x + 3
    SECOND = lambda x: math.e ** x - 7 * x
    THIRD = lambda x: 10 * math.log2(x ** x) + 1 - x


class DerivativeType(Enum):
    FIRST = lambda x: 9 * (x ** 2) - 8
    SECOND = lambda x: math.e ** x - 7
    THIRD = lambda x: (10 + 10 * math.log(x)) / (math.log(2)) - 1


class ExpressionTypeView(Enum):
    FIRST = '3x^3 + 3 = 8x'
    SECOND = 'e^x = 7x'
    THIRD = '10log(x^x) = x - 1'


class EquationType(Enum):
    FIRST: Equation = Equation(ExpressionType.FIRST, DerivativeType.FIRST,
                               [-1.7954, 0.3988, 1.3967], ExpressionTypeView.FIRST.value)

    SECOND: Equation = Equation(ExpressionType.SECOND, DerivativeType.SECOND,
                                [0.1692, 3.0664], ExpressionTypeView.SECOND.value)

    THIRD: Equation = Equation(ExpressionType.THIRD, DerivativeType.THIRD,
                               [0.086, 1], ExpressionTypeView.THIRD.value)


class SystemExpressionType(Enum):
    FIRST = (lambda x: (7 * x - (x ** 2)) / 2,
             lambda x: (x + 1) ** (1/3))
    SECOND = (lambda x: math.sin(x) - x - 4,
              lambda x: (x ** 3 + x + 1) / 5)


class SystemPhiType(Enum):
    FIRST1 = PhiData(lambda x, y: (x ** 2 + 2 * y) / 7,
              lambda x, y: 2/7 * x, lambda x, y: 2/7)
    FIRST2 = PhiData(lambda x, y: y ** 3 - 1,
              lambda x, y: 0, lambda x, y: 3 * (y ** 2))

    SECOND1 = PhiData(lambda x, y: math.sin(x) - y - 4,
               lambda x, y: math.cos(x), lambda x, y: -1)
    SECOND2 = PhiData(lambda x, y: 5 * y - x ** 3 - 1,
               lambda x, y: -3 * (x ** 2), lambda x, y: 5)


class SystemTypeView(Enum):
    FIRST = ('x^2 + 2y = 7x',
             'y^3 - x = 1')
    SECOND = ('sin(x) = 4 + x + y',
             '5y = x^3 + x + 1')


class EquationSystemType(Enum):
    FIRST: EquationSystem = EquationSystem(SystemExpressionType.FIRST.value, SystemPhiType.FIRST1, SystemPhiType.FIRST2,
                                           [(0.3297, 1.0996), (6.39, 1.9479)], SystemTypeView.FIRST.value)

    SECOND: EquationSystem = EquationSystem(SystemExpressionType.SECOND.value, SystemPhiType.SECOND1, SystemPhiType.SECOND2,
                                           [(-2.25, -2.5281)], SystemTypeView.SECOND.value)
