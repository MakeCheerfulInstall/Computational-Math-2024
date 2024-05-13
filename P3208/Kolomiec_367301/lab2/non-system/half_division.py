from prettytable import PrettyTable

import tools
from tools import EPSILON


def calculate(equation, diapason):
    n = tools.calculate_iteration_count(EPSILON, diapason)
    iter_count = 0

    a, b = diapason
    x_cur = (a + b) / 2
    x_prev = 0
    f_x_cur = tools.calculate_ordinate(equation, x_cur)

    values = []
    while abs(x_cur - x_prev) > EPSILON \
            or abs(f_x_cur) > EPSILON \
            or abs(a - b) > EPSILON:

        f_x_cur = tools.calculate_ordinate(equation, x_cur)
        f_a = tools.calculate_ordinate(equation, a)
        f_b = tools.calculate_ordinate(equation, b)

        values.append([iter_count, a, b, x_cur, f_a, f_b, f_x_cur, abs(a - b)])

        if select_next_limit(equation, a, x_cur):
            a = x_cur
        else:
            b = x_cur

        x_prev = x_cur
        x_cur = (a + b) / 2

        iter_count += 1
        if iter_count > n:
            break

    print_table(values)
    return True


def select_next_limit(equation, a, x):
    if tools.calculate_ordinate(equation, a) * tools.calculate_ordinate(equation, x) > 0:
        return True
    return False


def print_table(values):
    pt = PrettyTable()
    pt.title = "Метод половинного деления"
    pt.field_names = ["№", "a", "b", "x", "f(a)", "f(b)", "f(x)", "|a - b|"]
    for res in values:
        pt.add_row(res)
    print(pt)
