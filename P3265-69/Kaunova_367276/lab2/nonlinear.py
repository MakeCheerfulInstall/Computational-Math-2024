import math
import numpy

from methods.HalfDivision import HalfDivision
from methods.Secant import Secant
from methods.Iteration import Iteration
from models.Equation import Equation
from Console import *

elist: list[Equation] = [
    Equation(lambda x: x ** 3 + 2.28 * x ** 2 - 1.934 * x - 3.907, "x^3 + 2.28x^2  - 1.934x - 3.907"),
    Equation(lambda x: x ** 3 - 3.125 * x ** 2 - 3.5 * x + 2.458, "x^3 - 3.125x^2  - 3.5x + 2.458"),
    Equation(lambda x: numpy.sin(x) + 0.5 * x ** 2 - 1, "sin{x} + 0.5x^2 - 1"),
]

mlist: list[type[Method]] = [
    HalfDivision,
    Secant,
    Iteration
]

if __name__ == "__main__":
    eindex, equation = askequation(elist)
    if eindex == 0:
        equation.draw(-4, 4)
    if eindex == 1:
        equation.draw(-4, 4)  
    if eindex == 2:
        equation.draw(-10, 10)

    left, right, accuracy = ask_for_initial_data()
    mindex, mtype = askmethod(mlist)
    method = mtype(equation, left, right, accuracy)
    if not method.check():
        print("Неверный промежуток (нет корней или корней больше 1", file=sys.stderr)
        sys.exit(0)
    method.solve()
