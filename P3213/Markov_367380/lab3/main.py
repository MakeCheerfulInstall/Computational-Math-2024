from function import Function, Function2
from solver import Solver, Solver2
from validator import Validator


def program_f_input() -> tuple:
    if input("Do you want to input with file? y/n: ") == "y":
        print("Not implemented")
        exit(0)
    else:
        given_function_type: str = ""
        while not Validator.validate_function_type(given_function_type):
            given_function_type = input("Choose equation:"
                                        "\n1. x^2 + 3x - 4 = 0"
                                        "\n2. x^3 - 7x + 2 = 0"
                                        "\n3. e^(3x) - 3 = 0\n")

        function_type: int = int(given_function_type)

        given_method_type: str = ""
        while not Validator.validate_method_type(given_method_type):
            given_method_type = input("Choose method:"
                                      "\n1. square"
                                      "\n2. trapezoidal"
                                      "\n3. simpson\n")

        method_type: int = int(given_method_type)

        given_modification: str = "0"
        if method_type == 1:
            while not Validator.validate_modification(given_modification, function_type):
                given_modification = input("Choose modification:"
                                           "\n1. right"
                                           "\n2. left"
                                           "\n3. middle\n")

        modification: int = int(given_modification)

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

        return function_type, method_type, modification, precision, left, right

def program_f2_input() -> tuple:
    if input("Do you want to input with file? y/n: ") == "y":
        print("Not implemented")
        exit(0)
    else:
        given_function2_type: str = ""
        while not Validator.validate_function2_type(given_function2_type):
            given_function2_type = input("Choose equation:"
                                        "\n1. 1 / x"
                                        "\n2. 1 / (2 * math.sqrt(x))\n")

        function2_type: int = int(given_function2_type)

        given_method_type: str = ""
        while not Validator.validate_method_type(given_method_type):
            given_method_type = input("Choose method:"
                                      "\n1. square"
                                      "\n2. trapezoidal"
                                      "\n3. simpson\n")

        method_type: int = int(given_method_type)

        given_modification: str = "0"
        if method_type == 1:
            while not Validator.validate_modification(given_modification, function_type):
                given_modification = input("Choose modification:"
                                           "\n1. right"
                                           "\n2. left"
                                           "\n3. middle\n")

        modification: int = int(given_modification)

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

        left, right = Validator.renew_sqrt_bounds(Function2(function2_type), left, right)

        is_ok, dot =  Validator.validate_function2(Function2(function2_type), left, right, precision)

        return function2_type, method_type, modification, precision, left, right, dot


function_type, method_type, modification, precision, left, right = program_f_input()

solver: Solver = Solver(Function(function_type), method_type, modification, precision, left, right)
print(solver.solve())

function2_type, method_type, modification, precision, left, right, dot = program_f2_input()
if dot is not None:
    print(dot)
    if dot == left:
        solver2: Solver2 = Solver2(Function2(function2_type), method_type, modification, precision, left + precision * 10, right)
        print(solver2.solve())
    elif dot == right:
        solve2: Solver2 = Solver2(Function2(function2_type), method_type, modification, precision, left,
                                  right - precision * 10)
        print(solve2.solve())
    else:
        solver1: Solver2 = Solver2(Function2(function2_type), method_type, modification, precision, left,
                                  dot - precision * 10)
        solver2: Solver2 = Solver2(Function2(function2_type), method_type, modification, precision, dot + precision * 10,
                                  right)
        print(solver1.solve()[0] + solver2.solve()[0], solver1.solve()[1] + solver2.solve()[1])