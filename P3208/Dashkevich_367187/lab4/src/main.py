import sys
import numpy as np
import matplotlib.pyplot as plt
from utils import *

from methods import linear
from methods import polynominal
from methods import power
from methods import exponent
from methods import logarithm

points = []
a, b, h = -4, 0, 0.4
base_f = lambda x: 15 * x / (x ** 4 + 4)
f = None
koofs = []


def gen_points_for_calc():
    x = a
    out = []
    while x <= b:
        out.append([x, base_f(x)])
        x += h
    return out


def show_plot():
    x_arr = [a + (b - a)/1000*i for i in range(1000)]
    x_arr[-1] = b
    if f != None:
        y_arr = [f.calc(x) for x in x_arr]
        plt.plot(x_arr, y_arr, '--r', label=f.tostr())

    for p in points:
        plt.scatter(p[0], p[1])

    plt.grid(True)
    plt.axhline(0, color='black', linewidth=1)
    plt.axvline(0, color='black', linewidth=1)

    plt.title(f.get_str_type())
    plt.legend()
    plt.show()


def solve_by_id(method_id):
    global f
    if method_id == 0:
        f = linear.aproximate(points)

    elif method_id == 1:
        f = power.aproximate(points)

    elif method_id == 2:
        f = exponent.aproximate(points)

    elif method_id == 3:
        f = logarithm.aproximate(points)

    elif method_id >= 4 and method_id <= 10:
        f = polynominal.aproximate(points, method_id - 2)

    else:
        print("ERROR: method id is not recognized", file=sys.stderr)
        sys.exit(-1)

    return f


filename = input("Enter file name or press Enter for manual input: ")

if filename == '':
    a, b = map(float, input("Range end points: ").split())

    if input("Generate points by default function? Y/N : ") == "Y":
        points = gen_points_for_calc()
    else:
        while True:
            n = int(input("Amount of points: "))
            if 8 <= n <= 12:
                for _ in range():
                    points.append(list(map(float, input().split())))
                break
            else:
                continue

else:
    with open(f"./resources/{filename}", 'r') as file:
        a, b = map(float, file.readline().strip().split())

        for line in file:
            points.append(list(map(float, line.strip().split())))

        print("File input successful")
        print("End points: ", a, b)
        print("Function points:", points)

approxes = []
for i in range(6):
    try:
        f = solve_by_id(i)
        S, eps, R2 = check_accuracy(f, points)
        approxes.append([f, S, eps, R2])
        show_plot()
    except ValueError:
        pass

approxes = sorted(approxes, key=lambda x: x[-2])
print_table_header(["method", "S", "𝜹", "R2", "f"])
for f, S, eps, R2 in approxes:
    print_table_row([f.get_str_type(), S, eps, R2, f.tostr()])

print()
print("Best approximation: ")
for f, S, eps, R2 in approxes:
    if eps != approxes[0][-2]:
        continue
    print()
    print("method:\t", f.get_str_type())
    print("function:\t", f.tostr())
    print("Deviation (S):\t", approxes[0][1])
    print("SKO:\t", approxes[0][2])
    print("Approximation reliability (R2):\t", approxes[0][3])
    print_table_header(["x", "y", "y'", "eps"])
    for i in points:
        print_table_row([i[0], i[1], f.calc(i[0]), f.calc(i[0]) - i[1]])
