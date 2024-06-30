import sys
from sympy import symbols, limit, ln, Abs


def input_variables():
    quation = quation_input()
    method = method_input()
    left_border = choose_left_interval()
    right_border = choose_right_interval(left_border)
    validate_integral(quation, left_border, right_border)
    inaccuracy = choose_inaccuracy()
    parts = parts_input()

    return quation, method, left_border, right_border, inaccuracy, parts


def try_to_convert_to_int(number):
    try:
        number_float = float(number)
        if number_float.is_integer():
            return int(number_float)
        else:
            return number_float
    except ValueError:
        return float(number)


def validate_parts(parts):
    try:
        parts = int(parts)
        if not parts.is_integer():
            print("The number of parts must be an integer!")
            return False
    except ValueError:
        print("The number of parts must be an integer!")
        return False
    if parts < 0:
        print("The number of parts must be greater than 0!")
        return False
    return True


def quation_input():
    while True:
        try:
            print("Choose the quation:")
            input_quation = int(input("1) 1/x \n2) x^2 \n3) x\n"))
            if input_quation in range(1, 4):
                break
            print("Invalid command")
            continue
        except ValueError:
            print("Incorrect value entered")
            continue
    return input_quation


def method_input():
    while True:
        try:
            print("Choose the method:")
            input_variant = int(
                input(
                    "1) Rectangle method (left) \n2) Rectangle method (centre) \n3) Rectangle method (right) \n4) Trapezoidal method \n5) Simpson’s Method \n"
                )
            )
            if input_variant in range(1, 6):
                break
            print("Invalid command")
            continue
        except ValueError:
            print("Incorrect value entered")
            continue
    return input_variant


def choose_left_interval():
    while True:
        try:
            interval = input("Enter the left boundary of the interval: ").replace(
                ",", "."
            )
            interval = int(interval)
            return try_to_convert_to_int(interval)

        except ValueError:
            try:
                interval = try_to_convert_to_int(interval)
                return interval
            except ValueError:
                print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue


def choose_right_interval(a):
    while True:
        try:
            interval = input("Enter the right boundary of the interval: ").replace(
                ",", "."
            )
            if float(interval) - float(a) - 0.00001 < 0:
                print("The right boundary must be greater than the left boundary!")
                continue
            interval = int(interval)
            return try_to_convert_to_int(interval)

        except ValueError:
            try:
                interval = try_to_convert_to_int(interval)
                return interval
            except ValueError:
                print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue


def choose_inaccuracy():
    while True:
        try:
            inaccuracy = input("Enter the inaccuracy: ").replace(",", ".")
            inaccuracy = float(inaccuracy)
            if inaccuracy < 0:
                print("The inaccuracy must be greater than 0!")
                continue
            return try_to_convert_to_int(inaccuracy)
        except ValueError:
            print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue


def parts_input():
    while True:
        try:
            parts = input("Enter the number of parts: ").replace(",", ".")
            parts = int(parts)
            if validate_parts(parts):
                return parts
        except ValueError:
            print("Number must be Integer")
            continue

        except UnboundLocalError:
            print("Number must be Integer")
            continue


def validate_integral(quation, left_border, right_border):
    if quation == 1:
        x = symbols("x")
        f = ln(Abs(x))

    if quation == 2:
        x = symbols("x")
        f = (x**3) / 3
    if quation == 3:
        x = symbols("x")
        f = (x**2) / 2
    limit_value_left = limit(f, x, left_border, dir="+")
    limit_value_right = limit(f, x, right_border, dir="-")
    if limit_value_right.is_infinite or limit_value_left.is_infinite:
        print("The integral doesn't converge.")
        exit()
