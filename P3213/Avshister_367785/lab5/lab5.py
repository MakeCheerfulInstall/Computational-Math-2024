import math

import numpy as np
from matplotlib import pyplot as plt


def g():
    while True:
        try:
            num = int(input("Введите 1, если ввод данных будет происходить из файла. Введите 2, если с клавиатуры. "
                            "Введите 3 для выбора уравнения"))
            if num != 1 and num != 2 and num != 3:
                print('Пожалуйста, введите 1, 2 или 3')
                continue
            else:
                return num
        except ValueError:
            print("Пожалуйста, введите число")
            continue


def get_data():
    num = g()
    while True:
        try:
            x = []
            y = []
            if num == 1:
                with open('input2.txt', 'r') as f:
                    while (line := f.readline()) != '\n':
                        x.append(float(line.split(' ')[0]))
                        y.append(float(line.split(' ')[1]))
                    return [x, y]
            elif num == 2:
                print('Введите координаты')
                while (line := input()) != '':
                    x.append(float(line.split(' ')[0]))
                    y.append(float(line.split(' ')[1]))
                return [x, y]
            else:
                print('1. sin(x)')
                print('2. x ** 2')
                print('Выберите уравнение (1 или 2)')
                n = int(input())
                print('Введите исследуемый интервал')
                inp = input().split(' ')
                a, b = int(inp[0]), int(inp[1])
                print('Введите количество точек на интервале')
                amount = int(input())
                for i in range(amount):
                    x_i = a + (b - a) * i / amount
                    x.append(x_i)
                    if n == 1:
                        y.append(math.sin(x_i))
                    elif n == 2:
                        y.append(x_i ** 2)
                    else:
                        print("Вы ввели неверную цифру")
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


def lagrange_polynomial(x, y, x_cur):
    res = 0
    for i in range(len(x)):
        p = 1
        for j in range(len(y)):
            if i != j:
                p *= (x_cur - x[j]) / (x[i] - x[j])
        res += p * y[i]
    return res


def newton_coefficient(x, y):
    m = len(x)
    x = np.copy(x)
    y = np.copy(y)
    for k in range(1, m):
        y[k:m] = (y[k:m] - y[k - 1]) / (x[k:m] - x[k - 1])
    return y


def newton_polynomial(x, y, x_cur):
    a = newton_coefficient(x, y)
    n = len(x) - 1
    p = a[n]
    for k in range(1, n + 1):
        p = a[n - k] + (x_cur - x[n - k]) * p
    return p


def t_calc(t, n, forward=True):
    result = t
    for i in range(1, n):
        if forward:
            result *= t - i
        else:
            result *= t + i
    return result


def newton_interpolation(x, y, x_cur):
    n = len(x)
    is_equally_spaced = True
    h = x[1] - x[0]
    for i in range(1, n - 1):
        if round(x[i + 1] - x[i], 3) != h:
            is_equally_spaced = False
            break
    if not is_equally_spaced:
        return 'Узлы не являются равноотстоящими'
    a = [[0] * n for _ in range(n)]
    for i in range(n):
        a[i][0] = y[i]
    for i in range(1, n):
        for j in range(n - i):
            a[j][i] = a[j + 1][i - 1] - a[j][i - 1]
    if x_cur <= x[n // 2]:
        x0 = n - 1
        for i in range(n):
            if x_cur <= x[i]:
                x0 = i - 1
                break
        if x0 < 0:
            x0 = 0
        t = (x_cur - x[x0]) / h
        result = a[x0][0]
        for i in range(1, n):
            result += (t_calc(t, i) * a[x0][i]) / math.factorial(i)
    else:
        t = (x_cur - x[n - 1]) / h
        result = a[n - 1][0]
        for i in range(1, n):
            result += (t_calc(t, i, False) * a[n - i - 1][i]) / math.factorial(i)
    return result


def stirling_interpolation(x, y, x_cur):
    is_equally_spaced = True
    h = round(x[1] - x[0], 3)
    n = len(x)
    for i in range(1, n - 1):
        if round(x[i + 1] - x[i], 3) != h:
            is_equally_spaced = False
            break
    if not is_equally_spaced:
        return 'Узлы не являются равноотстоящими'
    a = [[0] * n for _ in range(n)]
    for i in range(n):
        a[i][0] = y[i]
    for i in range(1, n):
        for j in range(n - i):
            a[j][i] = a[j + 1][i - 1] - a[j][i - 1]
    if n % 2 == 0:
        x_0 = int(n / 2 - 1)
    else:
        x_0 = int(n / 2)
    t = (x_cur - x[x_0]) / h
    if abs(t) > 0.25:
        print('t > 0.25 => результат Стирлинга может быть неточным')
    n = a[x_0][0]
    comp_t1 = t
    comp_t2 = t**2
    pr_number = 0
    for i in range(1, len(x)):
        if i % 2 == 0:
            n += (comp_t2 / math.factorial(i)) * a[x_0 - (i // 2)][i]
            comp_t2 *= (t**2 - pr_number**2)
        else:
            n += (comp_t1 / math.factorial(i)) * \
                 ((a[x_0 - ((i + 1) // 2)][i] + a[x_0 - (((i + 1) // 2) - 1)][i]) / 2)
            pr_number += 1
            comp_t1 *= (t**2 - pr_number**2)
    return n


def bessel_interpolation(x, y, x_cur):
    is_equally_spaced = True
    h = round(x[1] - x[0], 3)
    n = len(x)
    a = [[0] * n for _ in range(n)]
    for i in range(n):
        a[i][0] = y[i]
    for i in range(1, n):
        for j in range(n - i):
            a[j][i] = a[j + 1][i - 1] - a[j][i - 1]
    for i in range(1, n - 1):
        if round(x[i + 1] - x[i], 3) != h:
            is_equally_spaced = False
            break
    if not is_equally_spaced:
        return 'Узлы не являются равноотстоящими'
    if n % 2 == 0:
        x_0 = int(n / 2 - 1)
    else:
        x_0 = int(n / 2)
    t = (x_cur - x[x_0]) / h
    if abs(t) < 0.25 or abs(t) > 0.75:
        print('t < 0.25 или t > 0.75 => результат Бесселя может быть неточным')
    n = (a[x_0][0] + a[x_0 + 1][0]) / 2
    n += (t - 0.5) * a[x_0][1]
    comp_t = t
    last_number = 0
    for i in range(2, len(x)):
        if i % 2 == 0:
            last_number += 1
            comp_t *= (t - last_number)
            n += (comp_t / math.factorial(i)) * ((a[x_0 - i // 2][i] + a[x_0 - ((i//2) - 1)][i]) / 2)
        else:
            n += (comp_t * (t - 0.5) / math.factorial(i)) * a[x_0 - ((i - 1)//2)][i]
            comp_t *= (t + last_number)
    return n


def finite_diff(data, y):
    temp = []
    if len(y) <= 1:
        return data
    for i in range(len(y) - 1):
        temp.append(y[i + 1] - y[i])
    data.append(temp)
    return finite_diff(data, temp)


def main():
    data = get_data()
    print('Конечные разности:', finite_diff([], data[1]))
    x = data[0]
    y = data[1]
    if len(x) != len(set(x)):
        temp = []
        for i in range(len(x)):
            if x[i] not in temp:
                temp.append(x[i])
            else:
                temp.append(x[i]+0.01)
        x = temp
    print('Введите значение аргумента')
    x_cur = float(input())
    answer1 = lagrange_polynomial(x, y, x_cur)
    answer2 = newton_polynomial(x, y, x_cur)
    answer3 = newton_interpolation(x, y, x_cur)
    answer4 = stirling_interpolation(x, y, x_cur)
    answer5 = bessel_interpolation(x, y, x_cur)
    print('Полином Лагранжа дал ответ: ', answer1)
    print('Полином Ньютона с разделенными разностями дал ответ: ', answer2)
    print('Полином Ньютона с конечными разностями дал ответ: ', answer3)
    print('Многочлен Стирлинга дал ответ: ', answer4)
    print('Многочлен Бесселя дал ответ: ', answer5)
    plt.plot(x, y)
    plt.scatter(x, y, label="Вводные точки")
    plt.grid(True)
    plot_x = np.linspace(np.min(x), np.max(x), 100)
    plot_y = [newton_polynomial(x, y, x_c) for x_c in plot_x]
    plt.plot(plot_x, plot_y, color='g', label='Newton_first')
    if answer3 != 'Узлы не являются равноотстоящими':
        plot_y = [newton_interpolation(x, y, x_c) for x_c in plot_x]
        plt.plot(plot_x, plot_y, color='r', label='Newton_second')
    plt.show()


main()
