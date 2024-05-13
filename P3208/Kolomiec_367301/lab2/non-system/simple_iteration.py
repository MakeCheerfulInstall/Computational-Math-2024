from prettytable import PrettyTable

import tools
from tools import ACCURACY
from tools import EPSILON


def calculate(equation, diapason):
    n = tools.calculate_iteration_count(EPSILON, diapason)
    iter_count = 0

    adbmal = round(-1 * abs(get_max_on_df(tools.differentiation(equation), diapason) ** (-1)), ACCURACY)

    t_x = [adbmal * k for k in equation]
    t_x[-2] += 1

    q = abs(get_max_on_df(tools.differentiation(t_x), diapason))

    if q > 1:
        print("Условие сходимости не выполняется")
        return False

    x_prev = get_min_ordinate_on_diapason(t_x, diapason)
    x_cur = tools.calculate_ordinate(t_x, x_prev)

    k = (1 if q <= 0.5 else (1 - q) / q) * EPSILON
    values = []

    while abs(x_cur - x_prev) > k:

        t_x_cur = tools.calculate_ordinate(t_x, x_cur)
        f_x_cur = tools.calculate_ordinate(equation, x_cur)

        values.append([iter_count, x_prev, x_cur, t_x_cur, f_x_cur, abs(x_cur - x_prev)])

        x_prev = x_cur
        x_cur = tools.calculate_ordinate(t_x, x_prev)

        iter_count += 1
        if iter_count > n:
            break

    print_table(values)
    return True


def get_max_on_df(df, diapason):
    a, b = diapason
    result = max(tools.calculate_ordinate(df, a), tools.calculate_ordinate(df, b))
    x_v = -1 * df[1] / (2 * df[0])
    if a >= x_v >= b:
        result = max(result, tools.calculate_ordinate(df, x_v))
    return result


def get_max_ordinate_on_diapason(equation, diapason):
    a,  b = diapason
    extremum = max(tools.calculate_ordinate(equation, b), tools.calculate_ordinate(equation, a))

    df = tools.differentiation(equation)
    discriminant = df[1] ** 2 - 4 * df[2] * df[0]

    if discriminant > 0:
        x_1 = (-1 * df[1] + discriminant ** 0.5) / (2 * df[0])
        x_2 = (-1 * df[1] - discriminant ** 0.5) / (2 * df[0])

        if a <= x_1 <= b:
            extremum = max(extremum, tools.calculate_ordinate(equation, x_1))
        if a <= x_2 <= b:
            extremum = max(extremum, tools.calculate_ordinate(equation, x_2))

    return extremum


def get_min_ordinate_on_diapason(equation, diapason):
    a, b = diapason
    extremum = min(tools.calculate_ordinate(equation, b), tools.calculate_ordinate(equation, a))

    df = tools.differentiation(equation)
    discriminant = df[1] ** 2 - 4 * df[2] * df[0]

    if discriminant > 0:
        x_1 = (-1 * df[1] + discriminant ** 0.5) / (2 * df[0])
        x_2 = (-1 * df[1] - discriminant ** 0.5) / (2 * df[0])

        if a <= x_1 <= b:
            extremum = min(extremum, tools.calculate_ordinate(equation, x_1))
        if a <= x_2 <= b:
            extremum = min(extremum, tools.calculate_ordinate(equation, x_2))

    return extremum


def print_table(values):
    pt = PrettyTable()
    pt.title = "Метод простой итерации"
    pt.field_names = ["№", "x_i", "x_i+1", "t(x_i+1)", "f(x_i+1)", "|x_i+1 - x_i|"]
    for res in values:
        pt.add_row(res)
    print(pt)
