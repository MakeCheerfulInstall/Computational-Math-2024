import math

import sys
import time
import methods.half_div as half_div
import methods.secant as secant
import methods.base_iter as base_iter
import methods.newton_sys as newton

from typing import Callable, Final, Any

import matplotlib.pyplot as plt
from matplotlib.axes import Axes

GRID: Final[int] = 10
SCALE: Final[int] = 100


def draw_and_show(function: Callable[[float], float], point: tuple[float, float] | None = None) -> list[float]:
    x: list[float] = [i / SCALE - GRID for i in range(2 * GRID * SCALE)]
    y: list[float] = [function(num) for num in x]
    bounds: list[float] = [x[i] for i in range(1, len(y)) if (y[i - 1] * y[i] < 0)]

    ax: Axes = plt.axes()
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    if bounds:
        l_limit: float = min(-4.0, bounds[0], bounds[-1]) - 1
        r_limit: float = max(bounds[0], bounds[-1], 4.0) + 1
    else:
        l_limit = -GRID
        r_limit = GRID

    ax.grid(which='major', alpha=0.5)
    ax.grid(which='minor', alpha=0.2)
    ax.set_xticks([i * 0.5 - GRID for i in range(GRID * 4)], minor=True)
    ax.set_yticks([i * 0.5 - GRID for i in range(GRID * 4)], minor=True)
    ax.set_xticks([i * 2 - GRID for i in range(GRID)])
    ax.set_yticks([i * 2 - GRID for i in range(GRID)])
    ax.set_xlim(l_limit, r_limit)
    ax.set_ylim(-GRID, GRID)

    plt.plot(x, y, linewidth=2)
    if point is not None:
        plt.plot(point[0], point[1], 'bo')
        plt.annotate(f'[{round(point[0], 2)}, {round(point[1], 2)}]',
                     xy=(point[0], point[1]), textcoords='offset points',
                     xytext=(10, 10), ha='right', va='bottom', fontsize=10,
                     weight='bold', color='darkblue')
    plt.show()
    return bounds


def compare_list_values(x: list[float], y: list[float]) -> bool:
    for x_val in x:
        for y_val in y:
            if abs(x_val - y_val) < 0.01:
                return True
    return False


def draw_and_show_system(first: Callable[[float], float | list[float]],
                         second: Callable[[float], float | list[float]],
                         point: tuple[float, float] | None = None) -> list[float]:
    x: list[float] = [i / SCALE - GRID for i in range(2 * GRID * SCALE)]
    y_first: Any = [first(num) for num in x]
    y_second: Any = [second(num) for num in x]

    if type(y_first[0]) is not list:
        y_first = [[y_val] for y_val in y_first]
    if type(y_second[0]) is not list:
        y_second = [[y_val] for y_val in y_second]

    bounds: list[float] = [x[i] for i in range(len(x)) if compare_list_values(y_first[i], y_second[i])]
    ax: Axes = plt.axes()
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    if bounds:
        l_limit: float = min(-4.0, bounds[0], bounds[-1]) - 1
        r_limit: float = max(bounds[0], bounds[-1], 4.0) + 1
    else:
        l_limit = -GRID
        r_limit = GRID

    ax.grid(which='major', alpha=0.5)
    ax.grid(which='minor', alpha=0.2)
    ax.set_xticks([i * 0.5 - GRID for i in range(GRID * 4)], minor=True)
    ax.set_yticks([i * 0.5 - GRID for i in range(GRID * 4)], minor=True)
    ax.set_xticks([i * 2 - GRID for i in range(GRID)])
    ax.set_yticks([i * 2 - GRID for i in range(GRID)])
    ax.set_xlim(l_limit, r_limit)
    ax.set_ylim(-GRID, GRID)

    plt.plot(x, y_first, linewidth=2, color='blue')
    plt.plot(x, y_second, linewidth=2, color='red')
    if point is not None:
        plt.plot(point[0], point[1], 'bo')
        plt.annotate(f'[{round(point[0], 2)}, {round(point[1], 2)}]',
                     xy=(point[0], point[1]), textcoords='offset points',
                     xytext=(10, 10), ha='right', va='bottom', fontsize=10,
                     weight='bold', color='darkblue')
    plt.show()
    return bounds


def get_input_type() -> int:
    print("Select function:\n"
          "1. x^3 + 2,84x^2 - 5,606x - 14,766\n"
          "2. (x^2)/2 - sin(x)\n"
          "3. sin(x) + 2cos(x)")
    while True:
        inp = input("Function: ")
        if inp == "1":
            return 0
        elif inp == "2":
            return 1
        elif inp == "3":
            return 2
        print("Invalid input, please try again", file=sys.stderr)
        time.sleep(0.5)


def get_solve_type() -> int:
    print("Select method:\n"
          "1. basic iterations\n"
          "2. halfing division\n"
          "3. secants method")
    while True:
        inp = input("Solve type: ")
        if inp == "1":
            return 0
        elif inp == "2":
            return 1
        elif inp == "3":
            return 2
        print("Invalid input, please try again", file=sys.stderr)
        time.sleep(0.5)


def get_prog_type() -> int:
    print("What are we doing:\n"
          "1. single equation\n"
          "2. equation system\n"
          "3. both")
    while True:
        inp = input("Your choice: ")
        if inp == "1":
            return 0
        elif inp == "2":
            return 1
        elif inp == "3":
            return 2
        print("Invalid input, please try again", file=sys.stderr)
        time.sleep(0.5)


def get_system() -> int:
    print("What are we doing:\n"
          "1:\n"
          "x^2 + y^2 = 4\n"
          "y = 3x^2\n"
          "2:\n"
          "cos(x-1) + y = 0.5\n"
          "x - cos(y) = 3")
    while True:
        inp = input("Your choice: ")
        if inp == "1":
            return 0
        elif inp == "2":
            return 1
        print("Invalid input, please try again", file=sys.stderr)
        time.sleep(0.5)


def check(f, a, b):
    flag = True
    count = 0
    prev = f(a)
    step = abs(b - a) / 10
    i = a + step
    while i < b:
        n = f(i)
        if (prev >= 0 >= n) or (n >= 0 >= prev):
            count += 1
        prev = n
        i += step
    if count == 0:
        print("No solution on this interval\n", file=sys.stderr)
        flag = False
        time.sleep(0.5)
    if count > 1:
        print("More than one solution on this interval\n", file=sys.stderr)
        flag = False
        time.sleep(0.5)
    return flag


def get_precision():
    while True:
        try:
            ans = float(input("Enter desired precision: ").replace(",", "."))
            if 0 < ans < 1:
                return ans
            print("precision should be in format of x.xxx...x and in range of [0; 1]", file=sys.stderr)
        except ValueError:
            print("invalid number format, can't parse!", file=sys.stderr)
        time.sleep(0.5)


def single():
    q = get_input_type()
    while (True):
        r = [float(x) for x in input("Enter interval: ").replace(",", ".").split(" ")]
        a = r[0]
        b = r[1]
        if check(functions[q][0], a, b):
            break
        print("Invalid interval, please try again", file=sys.stderr)
        time.sleep(0.5)
    eps = get_precision()
    var = get_solve_type()
    if var == 0:
        print("Метод простой итерации")
        x = base_iter.solve(functions[q][0], functions[q][1], a, b, eps)
        print("Найдено решение x =", x, "f(x) =", round(functions[q][0](x), 10), "\n")

    elif var == 1:
        print("Метод половинного деления")
        x = half_div.solve(functions[q][0], a, b, eps)
        print("Найдено решение x =", x, "f(x) =", round(functions[q][0](x), 10), "\n")
    else:
        print("Метод секущих")
        x = secant.solve(functions[q][0], functions[q][1], a, b, eps)
        print("Найдено решение x =", x, "f(x) =", round(functions[q][0](x), 10), "\n")
    draw_and_show(functions[q][0], (x, functions[q][0](x)))


def system():
    q = get_system()
    while True:
        try:
            r = [float(x) for x in input("Enter starting x and y: ").replace(",", ".").split(" ")]
            a = float(r[0])
            b = float(r[1])
            break
        except ValueError:
            print("Invalid input. Please try again", file=sys.stderr)
        time.sleep(0.5)
    eps = get_precision()
    point = tuple(newton.solve(functions_systems[q], a, b, eps))
    print("Найдено решение x =", point[0], "y = ", point[1], "\n")
    draw_and_show_system(yfunc[q][0], yfunc[q][1], point)


functions = [[lambda x: x ** 3 + 2.84 * x ** 2 - 5.606 * x - 14.766, lambda x: 3*x**2 + 5.68*x - 5.06, lambda x: 6*x + 5.68],
             [lambda x: 0.5*x**2 - math.sin(x), lambda x: x - math.cos(x), lambda x: 1 + math.sin(x)],
             [lambda x: math.sin(x)+2*math.cos(x), lambda x: math.cos(x)-2*math.sin(x), lambda x: 2*math.cos(x)-math.sin(x)]]

functions_systems = [[[lambda x, y: x**2 + y**2 - 4, lambda x: 2*x, lambda y: 2*y],
                      [lambda x, y: y - 3*x**2, lambda x: -6*x, lambda y: 1]],
                     [[lambda x, y: math.cos(x-1) + y - 0.5, lambda x: -math.sin(x), lambda y: 1],
                      [lambda x, y: x - math.cos(y) - 3, lambda x: 1, lambda y: math.sin(y)]]]

yfunc = [[lambda x: [math.sqrt(4 - x**2) if x**2 < 4 else math.nan, -math.sqrt(4 - x**2) if x**2 < 4 else math.nan], lambda x: 3*x**2],
         [lambda x: 0.5 - math.cos(x - 1), lambda x:  [math.acos(x - 3) + 2 * math.pi * i if abs(x - 3) <= 1 else math.nan
                              for i in range(-3, 3)] + [math.acos(3 - x) + 2 * math.pi * i - math.pi if abs(x - 3) <= 1
                                                        else math.nan for i in range(-3, 3)]]]


if __name__ == '__main__':
    ans = get_prog_type()
    if ans == 0:
        single()
    if ans == 1:
        system()
    if ans == 2:
        single()
        system()
