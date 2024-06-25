import methods.rectangles as rect
import methods.simpson as simp
import methods.trapezoid as trap

import sys
import time


n = 4
functions = [lambda x: -2*x**3 - 4*x**2 + 8*x - 4,
             lambda x: -x**3 - x**2 + x + 3,
             lambda x: x**2]


def get_input_type() -> int:
    print("Select function:\n"
          "1. -2x^3 -4x^2 + 8x - 4\n"
          "2. -x^3 - x^2 + x + 3\n"
          "3. x^2")
    while True:
        inp = input("Function: ")
        if inp == "1":
            return 0
        elif inp == "2":
            return 1
        elif inp == "3":
            return 2
        print("Invalid input, please try again", file=sys.stderr)
        time.sleep(0.5)


def get_range():
    while True:
        try:
            b, a = [int(x) for x in input("Please enter range in format: from to\n").split(" ")]
            return b, a
        except ValueError:
            print("Invalid input, please try again", file=sys.stderr)
            time.sleep(0.5)


def get_precision():
    while True:
        try:
            ans = float(input("Enter desired precision: ").replace(",", "."))
            if 0 < ans < 1:
                return ans
            print("precision should be in format of x.xxx...x and in range of [0; 1]", file=sys.stderr)
        except ValueError:
            print("invalid number format, can't parse!", file=sys.stderr)
        time.sleep(0.5)


def get_solve_type():
    print("Select solve method:\n"
          "1. Left rectangles\n"
          "2. Middle rectangles\n"
          "3. Right rectangles\n"
          "4. Trapezoids\n"
          "5. Simpson")
    while True:
        inp = input("Function: ")
        if inp == "1":
            return 0
        elif inp == "2":
            return 1
        elif inp == "3":
            return 2
        elif inp == "4":
            return 3
        elif inp == "5":
            return 4
        print("Invalid input, please try again", file=sys.stderr)
        time.sleep(0.5)


if __name__ == "__main__":
    func = get_input_type()
    method = get_solve_type()
    eps = get_precision()
    b, a = get_range()
    if method == 0:
        res, n = rect.solve(n, functions[func], a, b, eps, "left")
    elif method == 1:
        res, n = rect.solve(n, functions[func], a, b, eps, "middle")
    elif method == 2:
        res, n = rect.solve(n, functions[func], a, b, eps, "right")
    elif method == 3:
        res, n = trap.solve(n, functions[func], a, b, eps)
    else:
        res, n = simp.solve(n, functions[func], a, b, eps)
    print(f"Calculated integral value: {res}\n"
          f"Total number of parts to achieve precision: {n}")