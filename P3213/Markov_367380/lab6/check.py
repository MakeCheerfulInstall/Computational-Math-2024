import numpy as np
from matplotlib import pyplot as plt
from tabulate import tabulate
from sympy import solve, symbols

while True:
    try:
        print("Выберите дифференциальное уравнение: ")
        print("1. 3x + 7y")
        print("2. y + cos(2x)")
        print("3. x^3 + y")
        num = int(input())
    except ValueError:
        print("Пожалуйста, введите число")
        continue
    if num not in [1, 2, 3]:
        print('Пожалуйста, введите цифру от 1 до 3')
        continue
    else:
        break


def f():
    if num == 1:
        return lambda x, y: 3 * x + 7 * y
    elif num == 2:
        return lambda x, y: y + np.cos(2*x)
    else:
        return lambda x, y: x ** 3 + y


def euler(f, y0, x0, xn, h):
    n = int((xn - x0) / h)
    x = np.linspace(x0, xn, n + 1)
    y = np.zeros(n + 1)
    y[0] = y0
    for i in range(n):
        y[i + 1] = y[i] + h * f(x[i], y[i])
    return x, y


def runge_kutt(f, y0, x0, xn, h):
    n = int((xn - x0) / h)
    x = np.linspace(x0, xn, n + 1)
    y = np.zeros(n + 1)
    y[0] = y0
    for i in range(n):
        k1 = h * f(x[i], y[i])
        k2 = h * f(x[i] + h / 2, y[i] + k1 / 2)
        k3 = h * f(x[i] + h / 2, y[i] + k2 / 2)
        k4 = h * f(x[i] + h, y[i] + k3)
        y[i + 1] = y[i] + (k1 + 2 * k2 + 2 * k3 + k4) / 6
    return x, y


def adams(f, y0, x0, xn, h, m):
    n = int((xn - x0) / h)
    x = np.linspace(x0, xn, n + 1)
    y = np.zeros(n + 1)
    y[0] = y0
    for i in range(m):
        y[i + 1] = y[i] + h * f(x[i], y[i])
    for i in range(m, n):
        y[i + 1] = y[i] + h * (
                    55 * f(x[i], y[i]) - 59 * f(x[i - 1], y[i - 1]) + 37 * f(x[i - 2], y[i - 2]) - 9 * f(x[i - 3],
                                                                                                         y[i - 3])) / 24
    return x, y


def check_euler(f, y0, x0, xn, h1, h2, p):
    _, y_h1 = euler(f, y0, x0, xn, h1)
    _, y_h2 = euler(f, y0, x0, xn, h2)
    return np.abs(y_h1[-1] - y_h2[-1]) / (2 ** p - 1)


def check_runge(f, y0, x0, xn, h1, h2, p):
    _, y_h1 = runge_kutt(f, y0, x0, xn, h1)
    _, y_h2 = runge_kutt(f, y0, x0, xn, h2)
    return np.abs(y_h1[-1] - y_h2[-1]) / (2 ** p - 1)


def main():
    print('Введите y0, x0, xn, h, e')
    data = input().split(' ')
    if len(data) != 5:
        print('Вы ввели неверные данные')
        exit(0)
    try:
        y0, x0, xn, h, e = float(data[0].replace(',', '.')), float(data[1].replace(',', '.')), float(data[2].replace(',', '.')),\
                       float(data[3].replace(',', '.')), float(data[4].replace(',', '.'))
    except ValueError:
        print('Вы ввели неверные данные')
        exit(0)

    func = f()
    epsilon = check_euler(func, y0, x0, xn, h, h / 2, 1)
    while epsilon > e:
        h = h/2
        epsilon = check_euler(func, y0, x0, xn, h, h / 2, 2)
    print(f"Точность метода Эйлера по правилу Рунге: {epsilon}")
    epsilon = check_runge(func, y0, x0, xn, h, h / 2, 4)
    while epsilon > e:
        h = h/2
        epsilon = check_runge(func, y0, x0, xn, h, h / 2, 4)
    print(f"Точность метода Рунге-Кутта по правилу Рунге: {epsilon}")
    x_exact = np.arange(x0, xn + h, h)
    x = symbols('x')
    if num == 1:
        c = solve(x*np.exp(7 * x0) - 3 * x0 / 7 - 3 / 49 - y0, x)
        y_exact = c * np.exp(7 * x_exact) - 3 * x_exact / 7 - 3 / 49
    elif num == 2:
        c = solve(x*np.exp(x0) + 0.4 * np.sin(2 * x0) - 0.2 * np.cos(2 * x0) - y0, x)
        y_exact = c * np.exp(x_exact) + 0.4 * np.sin(2 * x_exact) - 0.2 * np.cos(2 * x_exact)
    else:
        c = solve(x*np.exp(x0) - x0 ** 3 - 3 * x0 ** 2 - 6 * x0 - 6 - y0, x)
        y_exact = c * np.exp(x_exact) - x_exact ** 3 - 3 * x_exact ** 2 - 6 * x_exact - 6

    plt.plot(x_exact, y_exact, label='Exact solution')

    x_euler, y_euler = euler(func, y0, x0, xn, h)
    plt.plot(x_euler, y_euler, label='Euler')

    x_runge, y_runge = runge_kutt(func, y0, x0, xn, h)
    plt.plot(x_runge, y_runge, label='Runge-Kutt')

    dif = 10**8
    h *= 2
    while dif > e:
        h /= 2
        x_adams, y_adams = adams(func, y0, x0, xn, h, 4)
        for i in range(min(len(y_adams), len(y_exact))):
            dif = 0
            if abs(y_adams[i] - y_exact[i]) > dif:
                dif = abs(y_adams[i] - y_exact[i])

    plt.plot(x_adams, y_adams, label='Adams')
    print('Точность метода Адамса: ', e)
    table_data = list(zip(x_euler, y_euler, y_runge, y_adams))
    print(tabulate(table_data, headers=["x", "Euler", "Runge-Kutt", "Adams"]))
    plt.legend()
    plt.show()

main()