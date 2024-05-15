import math

import numpy as np

ACCURACY = 3
EPSILON = 0.01


def differentiation(equation):
    result = []
    degree = len(equation) - 1
    for i in range(len(equation)):
        result.append(equation[i] * (degree - i))
    return result[:-1]


def get_sections(equation):
    result = []
    prev = calculate_ordinate(equation, -1001)
    for i in np.arange(-10, 10, 0.5):
        cur = calculate_ordinate(equation, i)
        if cur * prev < 0:
            result.append((i - 0.5, i))
        prev = cur
    return result


def calculate_ordinate(equation, x):
    result = 0
    for i in range(len(equation)):
        result += equation[i] * x ** (len(equation) - i - 1)
    return round(result, ACCURACY)


def calculate_iteration_count(epsilon, diapason):
    a, b = diapason
    return round(math.log2(abs(a - b) / epsilon)) + 1


def print_equation(equation):
    result = []
    for i in range(len(equation) - 1, -1, -1):
        if equation[i] == 0:
            continue
        if i == 0:
            result.append(str(equation[i]))
            break
        result.append(f"{equation[i]} * x^{i}")
    print(" + ".join(result))


def print_all(eqs):
    for eq in range(len(eqs)):
        print(eq + 1, end=". ")
        print_equation(eqs[eq])
