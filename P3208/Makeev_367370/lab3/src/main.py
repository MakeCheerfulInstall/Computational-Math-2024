from integral import Integral
from method import Method
from parser import Parser
from dto import Interval, IntegralAnswer

from typing import Tuple


def main():
    intg: Integral = Parser.choose_integral()
    intv: Inerval = Parser.set_interval()
    eps: float = Parser.set_epsilon()
    method: Method = Parser.choose_method()
    answer: IntegralAnswer = method.solve(intg, intv, eps)
    print(answer)


if __name__ == '__main__':
    main()
