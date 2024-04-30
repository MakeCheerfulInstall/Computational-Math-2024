import math
import numpy as np
import matplotlib.pyplot as plt


def g():
    while True:
        try:
            num = int(input("Введите 1, если ввод данных будет происходить из файла. Введите 2, если с клавиатуры"))
        except ValueError:
            print("Пожалуйста, введите число")
            continue
        if num != 1 and num != 2:
            print('Пожалуйста, введите 1 или 2')
            continue
        else:
            return num


def get_data():
    num = g()
    while True:
        try:
            if num == 1:
                with open('input.txt', 'r') as f:
                    x = []
                    y = []
                    am = int(f.readline())
                    for i in range(am):
                        coords = f.readline().split(' ')
                        x.append(float(coords[0].replace(',', '.')))
                        y.append(float(coords[1].replace(',', '.')))
                    return [x, y]
            else:
                x = []
                y = []
                print("Введите количество точек (от 8 до 12)")
                am = int(input())
                if am > 12 or am < 8:
                    print("Пожалуйста, введите целое положительное число от 8 до 12")
                    continue
                print("Введите координаты точек (x и y через пробел, каждая пара на новой строке)")
                for i in range(am):
                    coords = input().split(' ')
                    x.append(float(coords[0].replace(',', '.')))
                    y.append(float(coords[1].replace(',', '.')))
                return [x, y]
        except TypeError:
            print("Вы ввели неверные данные")
            exit(0)
        except ValueError:
            print("Вы ввели неверные данные")
            exit(0)
        except IndexError:
            print("Вы ввели неверные данные")
            exit(0)


gl_data = get_data()
x = gl_data[0]
y = gl_data[1]


def minor(matrix, i, j):
    n = len(matrix)
    return [[matrix[row][col] for col in range(n) if col != j] for row in range(n) if row != i]


def det(matrix):
    n = len(matrix)
    if n == 1:
        return matrix[0][0]
    d = 0
    sgn = 1
    for j in range(n):
        d += sgn * matrix[0][j] * det(minor(matrix, 0, j))
        sgn *= -1
    return d


def calc_s(y, f):
    n = len(y)
    return sum((f[i] - y[i]) ** 2 for i in range(n))


def calc_delta(y, f):
    n = len(y)
    return math.sqrt(calc_s(y, f) / n)


def lin_func(x, y):
    data = {}
    n = len(x)
    sx = sum(x)
    sx2 = sum([xi ** 2 for xi in x])
    sy = sum(y)
    sxy = sum([x[i] * y[i] for i in range(n)])
    x_m = sx / n
    y_m = sy / n
    numerator = sum((x[i] - x_m) * (y[i] - y_m) for i in range(n))
    denominator1 = sum((x[i] - x_m) ** 2 for i in range(n))
    denominator2 = sum((y[i] - y_m) ** 2 for i in range(n))
    r = numerator / math.sqrt(denominator1 * denominator2)       #коэффициент корреляции
    d = det([[sx2, sx],
             [sx, n]])
    d1 = det([[sxy, sx],
              [sy, n]])
    d2 = det([[sx2, sxy],
              [sx, sy]])
    try:
        a = d1 / d
        b = d2 / d
    except ZeroDivisionError:
        raise ZeroDivisionError
    data['a'] = a
    data['b'] = b
    data['x'] = x
    data['y'] = y
    f = [a * x_i + b for x_i in x]
    data['f'] = f
    numerator = [(y[i] - f[i]) ** 2 for i in range(n)]
    denominator = [(y[i] - (sum(f) / n)) ** 2 for i in range(n)]
    R2 = 1 - (sum(numerator) / sum(denominator))                  #коэффициент детерминации
    if R2 < 0.5:
        data['R2'] = 'Недостаточная точность аппроксимации'
    elif R2 < 0.75:
        data['R2'] = 'Слабая точность аппроксимации'
    elif R2 < 0.95:
        data['R2'] = 'Удовлетворительная точность аппроксимации'
    else:
        data['R2'] = 'Высокая точность аппроксимации'
    data['s'] = calc_s(y, f)
    data['delta'] = calc_delta(y, f)
    data['r'] = r
    return data


def poly_2(x, y):
    data = {}
    n = len(x)
    sx = sum(x)
    sx2 = sum([xi ** 2 for xi in x])
    sx3 = sum([xi ** 3 for xi in x])
    sx4 = sum([xi ** 4 for xi in x])
    sy = sum(y)
    sxy = sum([x[i] * y[i] for i in range(n)])
    sx2y = sum([(x[i] ** 2) * y[i] for i in range(n)])
    d = det([[n, sx, sx2],
             [sx, sx2, sx3],
             [sx2, sx3, sx4]])
    d1 = det([[sy, sx, sx2],
              [sxy, sx2, sx3],
              [sx2y, sx3, sx4]])
    d2 = det([[n, sy, sx2],
              [sx, sxy, sx3],
              [sx2, sx2y, sx4]])
    d3 = det([[n, sx, sy],
              [sx, sx2, sxy],
              [sx2, sx3, sx2y]])
    try:
        c = d1 / d
        b = d2 / d
        a = d3 / d
    except ZeroDivisionError:
        raise ZeroDivisionError
    data['a'] = a
    data['b'] = b
    data['c'] = c
    data['x'] = x
    data['y'] = y
    f = [a * x_i ** 2 + b * x_i + c for x_i in x]
    data['f'] = f
    numerator = [(y[i] - f[i]) ** 2 for i in range(n)]
    denominator = [(y[i] - (sum(f) / n)) ** 2 for i in range(n)]
    R2 = 1 - (sum(numerator) / sum(denominator))
    if R2 < 0.5:
        data['R2'] = 'Недостаточная точность аппроксимации'
    elif R2 < 0.75:
        data['R2'] = 'Слабая точность аппроксимации'
    elif R2 < 0.95:
        data['R2'] = 'Удовлетворительная точность аппроксимации'
    else:
        data['R2'] = 'Высокая точность аппроксимации'
    data['s'] = calc_s(y, f)
    data['delta'] = calc_delta(y, f)
    return data


def poly_3(x, y):
    data = {}
    n = len(x)
    sx = sum(x)
    sx2 = sum([xi ** 2 for xi in x])
    sx3 = sum([xi ** 3 for xi in x])
    sx4 = sum([xi ** 4 for xi in x])
    sx5 = sum([xi ** 5 for xi in x])
    sx6 = sum([xi ** 6 for xi in x])
    sy = sum(y)
    sxy = sum([x[i] * y[i] for i in range(n)])
    sx2y = sum([(x[i] ** 2) * y[i] for i in range(n)])
    sx3y = sum([(x[i] ** 3) * y[i] for i in range(n)])

    d0 = det([[n, sx, sx2, sx3],
              [sx, sx2, sx3, sx4],
              [sx2, sx3, sx4, sx5],
              [sx3, sx4, sx5, sx6]])
    d1 = det([[sy, sx, sx2, sx3],
              [sxy, sx2, sx3, sx4],
              [sx2y, sx3, sx4, sx5],
              [sx3y, sx4, sx5, sx6]])
    d2 = det([[n, sy, sx2, sx3],
              [sx, sxy, sx3, sx4],
              [sx2, sx2y, sx4, sx5],
              [sx3, sx3y, sx5, sx6]])
    d3 = det([[n, sx, sy, sx3],
              [sx, sx2, sxy, sx4],
              [sx2, sx3, sx2y, sx5],
              [sx3, sx4, sx3y, sx6]])
    d4 = det([[n, sx, sx2, sy],
              [sx, sx2, sx3, sxy],
              [sx2, sx3, sx4, sx2y],
              [sx3, sx4, sx5, sx3y]])

    try:
        d = d1 / d0
        c = d2 / d0
        b = d3 / d0
        a = d4 / d0
    except ZeroDivisionError:
        raise ZeroDivisionError
    data['a'] = a
    data['b'] = b
    data['c'] = c
    data['d'] = d
    data['x'] = x
    data['y'] = y
    f = [a * x_i ** 3 + b * x_i ** 2 + c * x_i + d for x_i in x]
    data['f'] = f
    numerator = [(y[i] - f[i]) ** 2 for i in range(n)]
    denominator = [(y[i] - (sum(f) / n)) ** 2 for i in range(n)]
    R2 = 1 - (sum(numerator) / sum(denominator))
    if R2 < 0.5:
        data['R2'] = 'Недостаточная точность аппроксимации'
    elif R2 < 0.75:
        data['R2'] = 'Слабая точность аппроксимации'
    elif R2 < 0.95:
        data['R2'] = 'Удовлетворительная точность аппроксимации'
    else:
        data['R2'] = 'Высокая точность аппроксимации'
    data['s'] = calc_s(y, f)
    data['delta'] = calc_delta(y, f)
    return data


def exp_func(x, y):
    data = {}
    i = 0
    while i < len(y):
        if y[i] <= 0:
            del y[i]
            del x[i]
        i += 1
    n = len(y)
    lin_y = [math.log(y[i]) for i in range(n)]
    lin_result = lin_func(x, lin_y)
    a = math.exp(lin_result['b'])
    b = lin_result['a']
    data['a'] = a
    data['b'] = b
    data['x'] = x
    data['y'] = y
    f = [a * np.exp(b * x_i) for x_i in x]
    data['f'] = f
    numerator = [(y[i] - f[i]) ** 2 for i in range(n)]
    denominator = [(y[i] - (sum(f) / n)) ** 2 for i in range(n)]
    R2 = 1 - (sum(numerator) / sum(denominator))
    if R2 < 0.5:
        data['R2'] = 'Недостаточная точность аппроксимации'
    elif R2 < 0.75:
        data['R2'] = 'Слабая точность аппроксимации'
    elif R2 < 0.95:
        data['R2'] = 'Удовлетворительная точность аппроксимации'
    else:
        data['R2'] = 'Высокая точность аппроксимации'
    data['s'] = calc_s(y, f)
    data['delta'] = calc_delta(y, f)
    return data


def log_func(x, y):
    data = {}
    i = 0
    while i < len(x):
        if x[i] <= 0:
            del y[i]
            del x[i]
        i += 1
    n = len(x)
    lin_x = [math.log(x[i]) for i in range(n)]
    lin_result = lin_func(lin_x, y)
    a = lin_result['a']
    b = lin_result['b']
    data['a'] = a
    data['b'] = b
    data['x'] = x
    data['y'] = y
    f = [a * np.log(x_i) + b for x_i in x]
    data['f'] = f
    numerator = [(y[i] - f[i]) ** 2 for i in range(n)]
    denominator = [(y[i] - (sum(f) / n)) ** 2 for i in range(n)]
    R2 = 1 - (sum(numerator) / sum(denominator))
    if R2 < 0.5:
        data['R2'] = 'Недостаточная точность аппроксимации'
    elif R2 < 0.75:
        data['R2'] = 'Слабая точность аппроксимации'
    elif R2 < 0.95:
        data['R2'] = 'Удовлетворительная точность аппроксимации'
    else:
        data['R2'] = 'Высокая точность аппроксимации'
    data['s'] = calc_s(y, f)
    data['delta'] = calc_delta(y, f)
    return data


def pow_func(x, y):
    data = {}
    n = len(x)
    for i in range(n):
        if x[i] <= 0 or y[i] <= 0:
            del y[i]
            del x[i]
    n = len(x)
    lin_x = [math.log(x[i]) for i in range(n)]
    lin_y = [math.log(y[i]) for i in range(n)]
    lin_result = lin_func(lin_x, lin_y)
    a = math.exp(lin_result['a'])
    b = lin_result['b']
    data['a'] = a
    data['b'] = b
    data['x'] = x
    data['y'] = y
    f = [a * x_i ** b for x_i in x]
    data['f'] = f
    numerator = [(y[i] - f[i]) ** 2 for i in range(n)]
    denominator = [(y[i] - (sum(f) / n)) ** 2 for i in range(n)]
    R2 = 1 - (sum(numerator) / sum(denominator))
    if R2 < 0.5:
        data['R2'] = 'Недостаточная точность аппроксимации'
    elif R2 < 0.75:
        data['R2'] = 'Слабая точность аппроксимации'
    elif R2 < 0.95:
        data['R2'] = 'Удовлетворительная точность аппроксимации'
    else:
        data['R2'] = 'Высокая точность аппроксимации'
    data['s'] = calc_s(y, f)
    data['delta'] = calc_delta(y, f)
    return data


def main(x, y):
    while True:
        try:
            ex = int(input("Введите 1, если вывод данных будет происходить в файл. Введите 2, если в консоль"))
            if ex != 1 and ex != 2:
                print('Пожалуйста, введите 1 или 2')
                continue
            break
        except ValueError:
            print("Пожалуйста, введите число")
            continue

    lin = lin_func(x, y)
    p_2 = poly_2(x, y)
    p_3 = poly_3(x, y)
    exp = exp_func(x, y)
    log = log_func(x, y)
    pow_f = pow_func(x, y)
    if ex == 1:
        with open('output.txt', 'w') as f:
            f.write("Линейная\n")
            f.write(str(lin)+'\n')
            f.write("Полином 2 степени\n")
            f.write(str(p_2)+'\n')
            f.write("Полином 3 степени\n")
            f.write(str(p_3)+'\n')
            f.write("Экспоненциальная\n")
            f.write(str(exp)+'\n')
            f.write("Логарифмическая\n")
            f.write(str(log)+'\n')
            f.write("Степенная\n")
            f.write(str(pow_f)+'\n')
    else:
        print("Линейная")
        print(lin)
        print("Полином 2 степени")
        print(p_2)
        print("Полином 3 степени")
        print(p_3)
        print("Экспоненциальная")
        print(exp)
        print("Логарифмическая")
        print(log)
        print("Степенная")
        print(pow_f)

    min_delta = min(lin['delta'], p_2['delta'], p_3['delta'], exp['delta'], log['delta'], pow_f['delta'])
    best = []
    if lin['delta'] == min_delta:
        best.append('Линейная')
    if p_2['delta'] == min_delta:
        best.append('Полином 2 степени')
    if p_3['delta'] == min_delta:
        best.append('Полином 3 степени')
    if exp['delta'] == min_delta:
        best.append('Экспоненциальная')
    if log['delta'] == min_delta:
        best.append('Логарифмическая')
    if pow_f['delta'] == min_delta:
        best.append('Степенная')

    print('Наилучшее приближение дают функции:', best)
    plt.scatter(x, y, label="Вводные точки")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.grid(True)
    x = np.arange(min(x), max(x), 0.01)
    plt.xlim(min(x) - 0.5, max(x) + 0.5)
    plt.ylim(min(y) - 0.5, max(y) + 0.5)
    plt.title("Приближение функции различными методами")
    plt.plot(x, lin['a'] * x + lin['b'], color='r', label='lin')
    plt.plot(x, p_2['a'] * x ** 2 + p_2['b'] * x + p_2['c'], color='g', label='p_2')
    plt.plot(x, p_3['a'] * x ** 3 + p_3['b'] * x ** 2 + p_3['c'] * x + p_3['d'], color='b', label='p_3')
    plt.plot(x, exp['a'] * np.exp(exp['b'] * x), color='y', label='exp')
    plt.plot(x, log['a'] * np.log(x) + log['b'], color='m', label='log')
    plt.plot(x, pow_f['a'] ** x + pow_f['b'], color='c', label='pow')
    plt.legend()
    plt.show()


main(x, y)
