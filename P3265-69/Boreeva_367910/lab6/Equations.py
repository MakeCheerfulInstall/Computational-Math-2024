import math

import numpy
from matplotlib import pyplot as plt

exact = [
    [-1, -0.909090, -0.833333, -0.769230, -0.714256, -0.666666],

]


def calculate(equation, x, y):
    if equation == 1:
        return round(y + (1 + x) * (y ** 2), 6)
    elif equation == 2:
        return round(3 * (x ** 2) * y + (x ** 2) * (math.e ** (x ** 3)), 6)
    elif equation == 3:
        return round(-y * math.cos(x) + math.sin(x) * math.cos(x), 6)


def runge(y, h, p, e):
    return round((numpy.real(y ** h - y ** (h / 2))) / (2 ** p - 1), 6) <= e


def graph(xarr, yarr, equation):
    plt.plot(xarr, yarr, color="blue")
    plt.plot(xarr, exact[equation - 1], color="green")
    plt.scatter(xarr, yarr, color="red")
    plt.show()
    plt.close()
