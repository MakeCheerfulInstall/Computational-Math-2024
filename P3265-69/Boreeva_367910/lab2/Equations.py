import math
import matplotlib.pyplot as plt
import numpy as np


def calculate_fk(num, k):
    if num == 1:
        return -1.38 * (k ** 3) - 1.93 * (k ** 2) - 15.28 * k - 3.72
    elif num == 2:
        return k - 10 * math.sin(k)
    elif num == 3:
        return 4 * (k ** 3) - 7 * k + 5
    elif num == 4:
        return k ** 3 - k + 4
    elif num == 5:
        return -1.38 * (k ** 3) - 5.42 * (k ** 2) + 2.57 * k + 10.95


def derivative1(num, k):
    if num == 1:
        return -4.14 * (k ** 2) - 3.86 * k - 15.28
    elif num == 2:
        return 1 - 10 * math.cos(k)
    elif num == 3:
        return 12 * (k ** 2) - 7
    elif num == 4:
        return 3 * (k ** 2) - 1
    elif num == 5:
        return -4.14 * (k ** 2) - 10.84 * k + 2.57


def derivative2(num, k):
    if num == 1:
        return -8.28 * k - 3.86
    elif num == 2:
        return 10 * math.sin(k)
    elif num == 3:
        return 24 * k
    elif num == 4:
        return 6 * k
    elif num == 5:
        return -8.28 * k - 10.84


def graph(xmin, xmax, count, num, sys=False):
    xlist = np.linspace(xmin, xmax, count)
    if sys:
        x = np.linspace(-10, 10, 400)
        x1, x2 = np.meshgrid(x, x)
        z1 = calculate_graph_sys(num, x1, x2)[0]
        z2 = calculate_graph_sys(num, x1, x2)[1]
        # Построение графика с обеими функциями
        plt.figure(figsize=(10, 7))
        plt.contour(x1, x2, z1, levels=[0], colors='r')
        plt.contour(x1, x2, z2, levels=[0], colors='b')
        plt.xlabel('x1')
        plt.ylabel('x2')
        plt.title('Графики обеих функций на одном графике')
    else:
        ylist = [calculate_fk(num, x) for x in xlist]
        plt.plot(xlist, ylist)
    ax = plt.gca()
    ax.spines['left'].set_position('center')
    ax.spines['bottom'].set_position('center')
    ax.spines['top'].set_visible(False)
    ax.spines['right'].set_visible(False)
    plt.show()


def calculate_fx_sys(num, x1, x2):
    if num == 1:
        return [0.3 - 0.1 * (x1 ** 2) - 0.2 * (x2 ** 2), 0.7 - 0.2 * (x1 ** 2) + 0.1 * x1 * x2]
    elif num == 2:
        return [1.5 - math.cos(x2), (1 + math.sin(x1 - 0.5)) / 2]


def calculate_graph_sys(num, x1, x2):
    if num == 1:
        return [0.1 * (x1 ** 2) + x1 + 0.2 * (x2 ** 2) - 0.3, 0.2 * (x1 ** 2) + x2 + +0.1 * x1 * x2 - 0.7]
    elif num == 2:
        return [math.cos(x2) + x1 - 1.5, 2 * x2 - math.sin(x1 - 0.5) - 1]


def derivative_sys(num, x1, x2):
    if num == 1:
        return [abs(-0.2 * x1) + abs(-0.4 * x2), abs(-0.4 * x1 - 0.1 * x2) + abs(-0.1 * x1)]
    elif num == 2:
        return [abs(math.sin(x2)), abs(math.cos((2 * x1 - 1) / 2) / 2)]
