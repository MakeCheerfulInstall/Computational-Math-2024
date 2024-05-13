from prettytable import PrettyTable

import tools
from tools import ACCURACY
from tools import EPSILON


def calculate(equation, diapason):
    n = tools.calculate_iteration_count(EPSILON, diapason)

    iter_number = 0
    x_next = 0
    x_cur = select_first_value(equation, diapason)

    f_x_next = tools.calculate_ordinate(equation, x_cur)
    df_x_next = tools.calculate_ordinate(tools.differentiation(equation), x_cur)

    values = []
    while abs(x_cur - x_next) > EPSILON \
            or abs(f_x_next / df_x_next) > EPSILON \
            or abs(f_x_next) > EPSILON:
        f_x_cur = tools.calculate_ordinate(equation, x_cur)
        df_x_cur = tools.calculate_ordinate(tools.differentiation(equation), x_cur)
        x_next = round(x_cur - f_x_cur / df_x_cur, ACCURACY)

        f_x_next = tools.calculate_ordinate(equation, x_next)
        df_x_next = tools.calculate_ordinate(tools.differentiation(equation), x_next)

        values.append([iter_number, x_cur, f_x_cur, df_x_cur, x_next, round(abs(x_cur - x_next), ACCURACY)])
        x_cur = x_next

        iter_number += 1
        if iter_number > n:
            break

    print_table(values)
    return True


def select_first_value(equation, diapason):
    a, b = diapason
    if tools.calculate_ordinate(equation, a) * \
            tools.calculate_ordinate(tools.differentiation(tools.differentiation(equation)), a) > 0:
        return a
    return b


def print_table(values):
    pt = PrettyTable()
    pt.title = "Метод Ньютона"
    pt.field_names = ["№", "x_i", "f(x_i)", "f`(x_i)", "x_i+1", "|x_i+1 - x_i|"]
    for res in values:
        pt.add_row(res)
    print(pt)
