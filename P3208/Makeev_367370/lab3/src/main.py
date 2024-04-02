from integral import IntegralType, Integral
from parser import Parser

from typing import Tuple


def main():
    intg: Integral = Parser.choose_integral()
    intv: Tuple[float, float] = Parser.set_interval()


if __name__ == '__main__':
    main()
