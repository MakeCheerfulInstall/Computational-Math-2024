import math

import matplotlib.pyplot as plt
import numpy as np
from prettytable import PrettyTable


# Вспомогательные функции
def calculate_ordinate(equation, x):
    return equation(x)


def differentiation(equation, var_idx):
    h = 1e-5

    def df(x):
        x1 = x[:]
        x2 = x[:]
        x1[var_idx] += h
        x2[var_idx] -= h
        return (equation(x1) - equation(x2)) / (2 * h)

    return df


def calculate_jacobian(equations, x):
    jacobian = []
    for eq in equations:
        row = []
        for i in range(len(x)):
            df = differentiation(eq, i)
            row.append(df(x))
        jacobian.append(row)
    return jacobian


def calculate_iteration_count(epsilon, diapason):
    return 20  # Устанавливаем фиксированное число итераций для упрощения


ACCURACY = 4
EPSILON = 1e-4


# Основные функции метода Ньютона для системы уравнений
def calculate_system(equations, diapason):
    n = calculate_iteration_count(EPSILON, diapason)

    iter_number = 0
    x_cur = select_first_value(equations, diapason)
    values = []
    approximations = [x_cur.copy()]

    while True:
        f_x_cur = [round(calculate_ordinate(eq, x_cur), ACCURACY) for eq in equations]

        if max(abs(f) for f in f_x_cur) < EPSILON:
            break

        jacobian = calculate_jacobian(equations, x_cur)
        delta_x = gauss_elimination(jacobian, [-f for f in f_x_cur])

        x_next = [round(x + delta, ACCURACY) for x, delta in zip(x_cur, delta_x)]
        norm_delta_x = round(max(abs(delta) for delta in delta_x), ACCURACY)

        values.append([iter_number, x_cur, f_x_cur, jacobian, x_next, norm_delta_x])
        approximations.append(x_next.copy())

        if norm_delta_x <= EPSILON:
            break

        x_cur = x_next
        iter_number += 1
        if iter_number > n:
            break

    print_table(values)
    plot_graph(equations, approximations)
    return x_next


def gauss_elimination(a, b):
    n = len(b)
    for i in range(n):
        # Partial pivoting
        max_row = max(range(i, n), key=lambda r: abs(a[r][i]))
        a[i], a[max_row] = a[max_row], a[i]
        b[i], b[max_row] = b[max_row], b[i]

        # Eliminate column below i
        for j in range(i + 1, n):
            factor = a[j][i] / a[i][i]
            a[j] = [a_ij - factor * a_ik for a_ij, a_ik in zip(a[j], a[i])]
            b[j] -= factor * b[i]

    # Back substitution
    x = [0 for _ in range(n)]
    for i in range(n - 1, -1, -1):
        x[i] = (b[i] - sum(a[i][j] * x[j] for j in range(i + 1, n))) / a[i][i]
    return x


def select_first_value(equations, diapason):
    a, b = diapason
    initial_values = (a + b) / 2
    return [initial_values for _ in equations]


def print_table(values):
    pt = PrettyTable()
    pt.title = "Метод Ньютона для системы уравнений"
    pt.field_names = ["№", "x_i", "f(x_i)", "J(x_i)", "x_i+1", "|x_i+1 - x_i|"]
    for res in values:
        pt.add_row([res[0],
                    [round(x, ACCURACY) for x in res[1]],
                    [round(f, ACCURACY) for f in res[2]],
                    [[round(j, ACCURACY) for j in row] for row in res[3]],
                    [round(x, ACCURACY) for x in res[4]],
                    round(res[5], ACCURACY)])
    print(pt)


def plot_graph(equations, approximations):
    fig, ax = plt.subplots()

    x_vals = [approx[0] for approx in approximations]
    y_vals = [approx[1] for approx in approximations]

    x_range = [min(x_vals) - 1, max(x_vals) + 1]
    y_range = [min(y_vals) - 1, max(y_vals) + 1]

    x = np.linspace(x_range[0], x_range[1], 400)
    y = np.linspace(y_range[0], y_range[1], 400)
    X, Y = np.meshgrid(x, y)

    Z1 = calculate_ordinate(equations[0], [X, Y])
    Z2 = calculate_ordinate(equations[1], [X, Y])

    ax.contour(X, Y, Z1, levels=[0], colors='r')
    ax.contour(X, Y, Z2, levels=[0], colors='b')

    ax.plot(x_vals, y_vals, 'ko')

    ax.set_xlabel('x')
    ax.set_ylabel('y')
    ax.set_title('Графики функций и траектория метода Ньютона')
    plt.show()


# Пример использования:
equations = [
    lambda x: x[0] ** 2 - x[1] - 1,
    lambda x: x[1] ** 2 - 2 + x[0]
]
diapason = (1, 2)

solution = calculate_system(equations, diapason)
print(f"Solution: {solution}")
