# Лабораторная работа #4

import numpy as np
import matplotlib.pyplot as plt
from math import log, exp, sqrt

FILE_IN = "iofiles/input.txt"
FILE_OUT = "iofiles/output.txt"


def solve_minor(matrix, i, j):
    """ Найти минор элемента матрицы """
    n = len(matrix)
    return [[matrix[row][col] for col in range(n) if col != j] for row in range(n) if row != i]


def solve_det(matrix):
    """ Найти определитель матрицы """
    n = len(matrix)
    if n == 1:
        return matrix[0][0]
    det = 0
    sgn = 1
    for j in range(n):
        det += sgn * matrix[0][j] * solve_det(solve_minor(matrix, 0, j))
        sgn *= -1
    return det


def calc_s(dots, f):
    """ Найти меру отклонения """
    n = len(dots)
    x = [dot[0] for dot in dots]
    y = [dot[1] for dot in dots]

    return sum([(f(x[i]) - y[i]) ** 2 for i in range(n)])


def calc_stdev(dots, f):
    """ Найти среднеквадратичное отклонение """
    n = len(dots)

    return sqrt(calc_s(dots, f) / n)


def lin_func(dots):
    """ Линейная аппроксимация """
    data = {}

    n = len(dots)
    x = [dot[0] for dot in dots]
    y = [dot[1] for dot in dots]

    sx = sum(x)
    sx2 = sum([xi ** 2 for xi in x])
    sy = sum(y)
    sxy = sum([x[i] * y[i] for i in range(n)])

    d = solve_det([[sx2, sx],
                   [sx, n]])
    d1 = solve_det([[sxy, sx],
                    [sy, n]])
    d2 = solve_det([[sx2, sxy],
                    [sx, sy]])

    try:
        a = d1 / d
        b = d2 / d
    except ZeroDivisionError:
        return None
    data['a'] = a
    data['b'] = b

    f = lambda z: a * z + b
    data['f'] = f

    data['str_f'] = "fi = a*x + b"

    data['s'] = calc_s(dots, f)

    data['stdev'] = calc_stdev(dots, f)

    return data


def sqrt_func(dots):
    """ Квадратичная аппроксимация """
    data = {}

    n = len(dots)
    x = [dot[0] for dot in dots]
    y = [dot[1] for dot in dots]

    sx = sum(x)
    sx2 = sum([xi ** 2 for xi in x])
    sx3 = sum([xi ** 3 for xi in x])
    sx4 = sum([xi ** 4 for xi in x])
    sy = sum(y)
    sxy = sum([x[i] * y[i] for i in range(n)])
    sx2y = sum([(x[i] ** 2) * y[i] for i in range(n)])

    d = solve_det([[n, sx, sx2],
                   [sx, sx2, sx3],
                   [sx2, sx3, sx4]])
    d1 = solve_det([[sy, sx, sx2],
                    [sxy, sx2, sx3],
                    [sx2y, sx3, sx4]])
    d2 = solve_det([[n, sy, sx2],
                    [sx, sxy, sx3],
                    [sx2, sx2y, sx4]])
    d3 = solve_det([[n, sx, sy],
                    [sx, sx2, sxy],
                    [sx2, sx3, sx2y]])

    try:
        c = d1 / d
        b = d2 / d
        a = d3 / d
    except ZeroDivisionError:
        return None
    data['c'] = c
    data['b'] = b
    data['a'] = a

    f = lambda z: a * (z ** 2) + b * z + c
    data['f'] = f

    data['str_f'] = "fi = a*x^2 + b*x + c"

    data['s'] = calc_s(dots, f)

    data['stdev'] = calc_stdev(dots, f)

    return data


def exp_func(dots):
    """ Экспоненциальная аппроксимация """
    data = {}

    n = len(dots)
    x = [dot[0] for dot in dots]
    y = []
    for dot in dots:
        if dot[1] <= 0:
            return None
        y.append(dot[1])

    lin_y = [log(y[i]) for i in range(n)]
    lin_result = lin_func([(x[i], lin_y[i]) for i in range(n)])

    a = exp(lin_result['b'])
    b = lin_result['a']
    data['a'] = a
    data['b'] = b

    f = lambda z: a * exp(b * z)
    data['f'] = f

    data['str_f'] = "fi = a*e^(b*x)"

    data['s'] = calc_s(dots, f)

    data['stdev'] = calc_stdev(dots, f)

    return data


def log_func(dots):
    """ Логарифмическая аппроксимация """
    data = {}

    n = len(dots)
    x = []
    for dot in dots:
        if dot[0] <= 0:
            return None
        x.append(dot[0])
    y = [dot[1] for dot in dots]

    lin_x = [log(x[i]) for i in range(n)]
    lin_result = lin_func([(lin_x[i], y[i]) for i in range(n)])

    a = lin_result['a']
    b = lin_result['b']
    data['a'] = a
    data['b'] = b

    f = lambda z: a * log(z) + b
    data['f'] = f

    data['str_f'] = "fi = a*ln(x) + b"

    data['s'] = calc_s(dots, f)

    data['stdev'] = calc_stdev(dots, f)

    return data


def pow_func(dots):
    """ Степенная аппроксимация """
    data = {}

    n = len(dots)
    x = []
    for dot in dots:
        if dot[0] <= 0:
            return None
        x.append(dot[0])
    y = []
    for dot in dots:
        if dot[1] <= 0:
            return None
        y.append(dot[1])

    lin_x = [log(x[i]) for i in range(n)]
    lin_y = [log(y[i]) for i in range(n)]
    lin_result = lin_func([(lin_x[i], lin_y[i]) for i in range(n)])

    a = exp(lin_result['b'])
    b = lin_result['a']
    data['a'] = a
    data['b'] = b

    f = lambda z: a * (z ** b)
    data['f'] = f

    data['str_f'] = "fi = a*x^b"

    data['s'] = calc_s(dots, f)

    data['stdev'] = calc_stdev(dots, f)

    return data


def plot(x, y, plot_x, plot_ys, labels):
    """ Отрисовать графики полученных функций """
    plt.gcf().canvas.set_window_title("График")

    ax = plt.gca()
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    ax.plot(1, 0, marker=">", ms=5, color='k',
            transform=ax.get_yaxis_transform(), clip_on=False)
    ax.plot(0, 1, marker="^", ms=5, color='k',
            transform=ax.get_xaxis_transform(), clip_on=False)

    plt.plot(x, y, 'o')
    for i in range(len(plot_ys)):
        plt.plot(plot_x, plot_ys[i], label=labels[i])

    plt.legend()
    plt.show(block=False)


def getdata_file():
    """ Получить данные из файла """
    data = {'dots': []}

    with open(FILE_IN, 'rt', encoding='UTF-8') as fin:
        try:
            for line in fin:
                current_dot = tuple(map(float, line.strip().split()))
                if len(current_dot) != 2:
                    raise ValueError
                data['dots'].append(current_dot)
            if len(data['dots']) < 2:
                raise AttributeError
        except (ValueError, AttributeError):
            return None

    return data


def getdata_input():
    """ Получить данные с клавиатуры """
    data = {'dots': []}

    print("\nВводите координаты через пробел, каждая точка с новой строки.")
    print("Чтобы закончить, введите 'END'.")
    while True:
        try:
            current = input().strip()
            if current == 'END':
                if len(data['dots']) < 2:
                    raise AttributeError
                break
            current_dot = tuple(map(float, current.split()))
            if len(current_dot) != 2:
                raise ValueError
            data['dots'].append(current_dot)
        except ValueError:
            print("Введите точку повторно - координаты некорректны!")
        except AttributeError:
            print("Минимальное количество точек - 2!")

    return data


def main():
    print("\tЛабораторная работа #4 (19)")
    print("\t  Аппроксимация функций")

    print("\nВзять исходные данные из файла (+) или ввести с клавиатуры (-)?")
    inchoice = input("Режим ввода: ")
    while (inchoice != '+') and (inchoice != '-'):
        print("Введите '+' или '-' для выбора способа ввода.")
        inchoice = input("Режим ввода: ")

    if inchoice == '+':
        data = getdata_file()
        if data is None:
            print("\nДанные в файле некорректны!")
            print("Режим ввода переключен на ручной.")
            data = getdata_input()
    else:
        data = getdata_input()

    answers = []
    temp_answers = [lin_func(data['dots']),
                    sqrt_func(data['dots']),
                    exp_func(data['dots']),
                    log_func(data['dots']),
                    pow_func(data['dots'])]
    for answer in temp_answers:
        if answer is not None:
            answers.append(answer)

    print("\n\n%20s%20s" % ("Вид функции", "Ср. отклонение"))
    print("-" * 40)
    for answer in answers:
        print("%20s%20.4f" % (answer['str_f'], answer['stdev']))

    x = np.array([dot[0] for dot in data['dots']])
    y = np.array([dot[1] for dot in data['dots']])
    plot_x = np.linspace(np.min(x), np.max(x), 100)
    plot_y = []
    labels = []
    for answer in answers:
        plot_y.append([answer['f'](x) for x in plot_x])
        labels.append(answer['str_f'])
    plot(x, y, plot_x, plot_y, labels)

    best_answer = min(answers, key=lambda z: z['stdev'])
    print("\nНаилучшая аппроксимирующая функция.")
    print(f" {best_answer['str_f']}, где")
    print(f"  a = {round(best_answer['a'], 4)}")
    print(f"  b = {round(best_answer['b'], 4)}")
    print(f"  c = {round(best_answer['c'], 4) if 'c' in best_answer else '-'}")

    input("\n\nНажмите Enter, чтобы выйти.")


main()
