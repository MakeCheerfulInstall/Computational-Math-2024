import numpy as np
import math
import matplotlib.pyplot as plt
from tabulate import tabulate
from scipy.integrate import odeint



def adams(f, x0, y0, interval, n):
    h = (interval[1] - interval[0])/n
    x_values = np.arange(interval[0], interval[1] + 3*h, h)
    y_values = [y0]

    for i in range(1, 4):  # первые шаги выполним методом Рунге-Кутта 4-го порядка
        k1 = h * f(x_values[i-1], y_values[i-1])
        k2 = h * f(x_values[i-1] + h/2, y_values[i-1] + k1/2)
        k3 = h * f(x_values[i-1] + h/2, y_values[i-1] + k2/2)
        k4 = h * f(x_values[i-1] + h, y_values[i-1] + k3)
        y_next = y_values[i-1] + (k1 + 2*k2 + 2*k3 + k4) / 6
        y_values.append(y_next)

    for i in range(3, len(x_values)-1):
        y_next = y_values[i] + h * (55*f(x_values[i], y_values[i]) - 59*f(x_values[i-1], y_values[i-1]) + 37*f(x_values[i-2], y_values[i-2]) - 9*f(x_values[i-3], y_values[i-3])) / 24
        y_values.append(y_next)

    return x_values, y_values

def euler(f, x0, y0, interval, n):
    h = (interval[1] - interval[0])/n
    x_values = np.arange(interval[0], interval[1] + h, h)
    y_values = [y0]

    for i in range(1, len(x_values)):
        y_next = y_values[-1] + h * f(x_values[i-1], y_values[-1])
        y_values.append(y_next)

    return x_values, y_values


def runge_kutta(f, x0, y0, interval, n):
    h = (interval[1] - interval[0])/n
    x_values = np.arange(interval[0], interval[1] + h, h)
    y_values = [y0]

    for i in range(1, len(x_values)):
        k1 = h * f(x_values[i-1], y_values[-1])
        k2 = h * f(x_values[i-1] + h/2, y_values[-1] + k1/2)
        k3 = h * f(x_values[i-1] + h/2, y_values[-1] + k2/2)
        k4 = h * f(x_values[i-1] + h, y_values[-1] + k3)
        y_next = y_values[-1] + (k1 + 2*k2 + 2*k3 + k4) / 6
        y_values.append(y_next)

    return x_values, y_values

def f1(x, y):
    return x**2 + y

def f2(x, y):
    return y + (1+x)*y**2

def f3(x, y):
    return x + y

def exact_solution1(x):
    return (np.exp(x) - x**2 - 2*x - 2)

def exact_solution2(x):
    return (-np.exp(x))/(x*(np.exp(x)))

def exact_solution3(x):
    return (np.exp(x) - x - 1)

# Правило рунге для проверки точности
def runge_rule(method, f, x0, y0, accuracy, k, interval, h):
    n = abs((interval[-1] - interval[0]))/h
    prev_x, prev_y = method(f, x0, y0, interval, n)
    n *= 2
    x, y = method(f, x0, y0, interval, n)

    while(abs(y[-1] - prev_y[-1])/(2^k-1)) >= accuracy:
        prev_y = y
        n *= 2
        x, y = method(f, x0, y0, interval, n)

    return x, y

# Проверка точности решения
def check_accuracy(method, f, df, x0, y0, accuracy, interval, h):
    n = abs((interval[-1] - interval[0]))/h
    x, y = method(f, x0, y0, interval, n)

    y_exact = df(x[-1])

    while abs(y[-1] - y_exact) < accuracy:
        n *= 2
        x, y = method(f, x0, y0, interval, n)

    return x, y

#Функции и методы
functions = {1: f1, 2: f2, 3: f3}
methods = {
    1: euler,
    2: runge_kutta,
    3: adams
}
dfunctions = {1: exact_solution1, 2: exact_solution2, 3: exact_solution3}

def main():

    print("Выберите дифференциальное уравнение для решения задачи Коши: ")
    print("1. x**2 + y")
    print("2. y + (1+x)*y**2")
    print("3. x + y")

    choice = int(input())
    if choice not in functions:
        raise ValueError("Введён неверный номер функции")
    f = functions[choice]
    df = dfunctions[choice]
    print("")

    print("Введите начальные условия ")
    x0 = float(input("x0:"))
    y0 = float(input("y0:"))
    print("")

    a = float(input("Введите левую границу интервала: "))
    b = float(input("Введите правую границу интервала: "))
    interval = [a, b]
    print("")

    h = float(input("Задайте шаг: "))
    print("")

    accuracy = float(input("Задай точность вычисления: "))
    print("")

    print("Выбери метод решения:")
    print("1. Метод Эйлера")
    print("2. Метод Рунге-Кутта 4-го порядка")
    print("3. Метод Адамса")
    method_choice = int(input())
    if method_choice == 1:
      k = 1
      x, y = runge_rule(methods[method_choice], f, x0, y0, accuracy, k, interval, h)
    elif (method_choice == 2):
      k = 4
      x, y = runge_rule(methods[method_choice], f, x0, y0, accuracy, k, interval, h)
    elif (method_choice == 3):
      x, y = check_accuracy(methods[method_choice], f, df, x0, y0, accuracy, interval, h)
    else:
        raise ValueError("Введён неверный номер метода")


    col_headers = ["x", "y"]
    merged_array = np.array([x, y]).T
    table = tabulate(merged_array , col_headers, tablefmt="fancy_grid", floatfmt = ".2f")
    print(table)

    a = []
    plt.plot(x, y, label='Numerical Method', color='blue',
         marker='o', markerfacecolor='blue', markersize=5)
    plt.legend()
    plt.xlabel('x')
    plt.ylabel('y')
    x_values = np.linspace(0, 10, 100)
    plt.plot(x, df(x), label='Exact', color='r', linestyle='-')
    plt.show()


try:
    main()
# except ValueError as e:
#     print("Ошибка: ", e)
except KeyboardInterrupt as e:
    print(e)
