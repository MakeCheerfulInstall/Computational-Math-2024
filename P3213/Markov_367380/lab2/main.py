from validator import Validator
from equation import Equation
from solver import Solver, SystemSolver
from system import System
import matplotlib.pyplot as plt

input = Validator.safe_input


def program_eq_input() -> tuple:
    if input("Do you want to input with file? y/n: ") == "y":
        print("Not implemented")
        exit(0)
    else:
        given_equation_type: str = ""
        while not Validator.validate_equation_type(given_equation_type):
            given_equation_type = input("Choose equation:"
                                        "\n1. x^2 + 3x - 4 = 0"
                                        "\n2. x^3 - 7x + 2 = 0"
                                        "\n3. e^(3x) - 3 = 0\n")

        equation_type: int = int(given_equation_type)

        given_method_type: str = ""
        while not Validator.validate_method_type(given_method_type):
            given_method_type = input("Choose method:"
                                      "\n1. chord"
                                      "\n2. sectional"
                                      "\n3. iter\n")

        method_type: int = int(given_method_type)

        given_precision: str = ""
        while not Validator.validate_precision(given_precision):
            given_precision = input("Provide precision: ")

        precision: float = round(float(given_precision.replace(",", ".")), 9)

        given_left: str = ""
        while not Validator.validate_left(given_left):
            given_left = input("Provide left border: ")

        left: float = round(float(given_left.replace(",", ".")), 4)

        given_right: str = ""
        while not Validator.validate_right(given_right):
            given_right = input("Provide right border: ")

        right: float = round(float(given_right.replace(",", ".")), 4)

        given_start: str = ""
        while not Validator.validate_start(given_start, equation_type):
            given_start = input("Provide evaluate start: ")

        start: float | None
        try:
            start = round(float(given_start.replace(",", ".")), 4)
        except ValueError:
            start = None

        if not Validator.validate_area(equation_type, left, right, start):
            raise ValueError("More than one root between borders or start not in borders")

        if input("Do you want to output to file? y/n: ") == "y":
            filename = input("Filename: ")
        else:
            filename = None

        return equation_type, method_type, precision, left, right, start, filename


def program_system_input() -> tuple:
    if input("Do you want to input with file? y/n: ") == "y":
        print("Not implemented")
        exit(0)
    else:
        given_system_type: str = ""
        while not Validator.validate_system_type(given_system_type):
            given_system_type = input("Choose system:"
                                      "\n1. x^2 + y^2 - 4 = 0"
                                      "\n   y - 3x^2 = 0"
                                      "\n2. xy - 7 = 0"
                                      "\n   4x^3 - y^3 - 2 = 0\n")

        system_type: int = int(given_system_type)

        given_precision: str = ""
        while not Validator.validate_precision(given_precision):
            given_precision = input("Provide precision: ")

        precision: float = round(float(given_precision.replace(",", ".")), 9)

        given_x0: str = ""
        while not Validator.validate_left(given_x0):
            given_x0 = input("Provide x0: ")

        x0: float = round(float(given_x0.replace(",", ".")), 4)

        given_y0: str = ""
        while not Validator.validate_right(given_y0):
            given_y0 = input("Provide y0: ")

        y0: float = round(float(given_y0.replace(",", ".")), 4)

        return system_type, precision, x0, y0


if input("Are we solvng single equation(1) system(2): ") == "2":
    system_type, precision, x0, y0 = program_system_input()
    solver_matrix: SystemSolver = SystemSolver(System(system_type), precision, x0, y0)
    print(solver_matrix.solve())

    xs: list = [i / 10 for i in range(100, 1000)]
    ys: list = [i / 10 for i in range(100, 1000)]


    zs1 = [[solver_matrix.get_system().get_functions()[0](i, j) for i in xs] for j in ys]
    zs2 = [[solver_matrix.get_system().get_functions()[1](i, j) for i in xs] for j in ys]

    plt.plot(zs1)
    plt.show()
    plt.plot(zs2)
    plt.show()
else:
    equation_type, method_type, precision, left, right, start, filename = program_eq_input()
    solver: Solver = Solver(Equation(equation_type), method_type, precision, left, right, start)

    xs: list = [i/100 for i in range((int(left) - 1) * 100, (int(right) + 1) * 100)]
    ys: list = list(map(solver.get_equation().get_function(), xs))
    plt.plot(xs, ys)
    plt.show()
    if filename is not None:
        with open(filename, "w") as f:
            f.write(str(solver.solve()))
    else:
        print(solver.solve())
