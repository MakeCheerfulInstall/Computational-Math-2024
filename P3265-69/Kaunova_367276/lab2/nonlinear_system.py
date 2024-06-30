import numpy
import sympy

from Console import *
from methods.System_Newton import SystemNewtonSolver
from models.SystemEquation import SystemEquation

elist: list[SystemEquation] = [
    SystemEquation(
        lambda x, y: x ** 2 + y ** 2 - 4,
        lambda x, y: - 3 * x ** 2 + y,
        "x^2 + y^2 - 4",
        "y - 3x^2"
    ),
    SystemEquation(
        lambda x, y: x ** 2 + y ** 2 - 2,
        lambda x, y: - 3 * x ** 2 + 5*y,
        "x^2 + y^2 - 2",
        "5y - 3x^2"
    ),
]

if __name__ == "__main__":
    eindex, equation = asksystem(elist)
    equation.draw(-10, 10, -10, 10)
    x_start, y_start, accuracy = ask_for_initial_data_system()
    solver = SystemNewtonSolver(equation, x_start, y_start, accuracy)
    solver.solve()
