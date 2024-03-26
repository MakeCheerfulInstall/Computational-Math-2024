import math
from typing import Callable, Final


class Describable:
    option_name: str = 'option'

    def __init__(self, description: str) -> None:
        self.description = description


class Function(Describable):
    option_name = 'function'

    def __init__(self, func: Callable[[float], float], first_derivation: Callable[[float], float],
                 second_derivation: Callable[[float], float], description: str) -> None:
        super().__init__(description)
        self.func = func
        self.first_derivation = first_derivation
        self.second_derivation = second_derivation


FUNCTIONS: Final[list[Function]] = \
    [Function(lambda x: x * x * x + 4.81 * x * x - 17.37 * x + 5.38,
              lambda x: 3 * x * x + 2 * 4.81 * x - 17.37,
              lambda x: 6 * x + 2 * 4.81,
              'x^3 + 4,81x^2 - 17,37x + 5,38'),
     Function(lambda x: 2 * x * x * x - 1.89 * x * x - 5 * x + 2.34,
              lambda x: 6 * x * x - 2 * 1.89 * x - 5,
              lambda x: 12 * x - 2 * 1.89,
              '2x^3 - 1,89x^2 - 5x + 2,34'),
     Function(lambda x: math.exp(x / 3) - 2 * math.cos(x + 4),
              lambda x: math.exp(x / 3) / 3 + 2 * math.sin(x + 4),
              lambda x: math.exp(x / 3) / 9 + 2 * math.cos(x + 4),
              'e^(x / 3) - 2cos(x + 4)')]
