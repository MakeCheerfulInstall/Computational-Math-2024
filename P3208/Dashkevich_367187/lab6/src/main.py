from decimal import Decimal, getcontext
from matplotlib import pyplot as plt

from methods import euler as euler
from methods import advanced_euler as euler_extend
from methods import adams as adams

from utils import *

getcontext().prec = 50


def f1(x, y):
    return y + (1 + x) * float(Decimal(y) ** 2)


def f2(x, y):
    return 2 * x - y + x ** 2


def f3(x, y):
    return (x - y) ** 2 + 1


x0, y0, h, n, accuracy = 0, 0, 0, 0, 0

output = []
output_half = []

function_idx = 0
functions = [
    ["y\' = y + (1+x) * y^2", f1, lambda x: -1 / x],
    ["y\' = x^2 + 2x - y", f2, lambda x: x ** 2],
    ["y\' = (x - y)^2 + 1", f3, lambda x: x]
]

method_idx = 0
methods = [
    ["Euler", euler.solve, 1],
    ["Advanced Euler", euler_extend.solve, 2],
    ["Adams", adams.solve, 4],
]


def accuracy_check(values, values_half):
    flag = True
    out = []
    for i in range(len(values)):
        err = (values[i] - values_half[2 * i]) / (2 ** methods[method_idx][2] - 1)

        if abs(err) > accuracy:
            flag = False
        out.append(err)
    return out, flag


def show_plot():
    x_arr = [x0 + (h*n/1000)*i for i in range(1000)]
    y_arr = [functions[function_idx][2](x) for x in x_arr]
    plt.plot(x_arr, y_arr, '--r')

    for i in range(n):
        plt.scatter(x0 + i * h, output[i])

    plt.grid(True)

    plt.title(functions[function_idx][0])
    plt.show()


filename = input("Enter filename or press Enter: ")
if filename == '':
    print("Choose function:")
    for i in range(len(functions)):
        print("\t" + str(i) + ". " + functions[i][0])
    function_idx = int(input("Chosen function: "))

    print("Choose solving method:")
    for i in range(len(methods)):
        print("\t" + str(i) + ". " + methods[i][0])
    function_idx = int(input("Chosen method: "))

    x0 = float(input("Enter x0: "))
    y0 = float(input("Enter y0: "))
    h = float(input("Enter step h: "))
    n = int(input("Enter amount of intervals n: "))
    accuracy = float(input("Enter precision: "))

else:
    with open(f"./resources/{filename}", 'r') as file:
        function_idx = int(file.readline().strip())
        method_idx = int(file.readline().strip())
        x0 = float(file.readline().strip())
        y0 = float(file.readline().strip())
        h = float(file.readline().strip())
        n = int(file.readline().strip())
        accuracy = float(file.readline().strip())

        print("File read successfully")
        print("\tFunction:", functions[function_idx][0])
        print("\tMethod:", methods[method_idx][0])
        print("\tx_0 =", x0)
        print("\ty_0 =", y0)
        print("\tStep h =", h)
        print("\tIntervals amount n =", n)
        print("\tPrecision =", accuracy)

endflag = False
while True:
    output = output_half
    output_half = []

    print("\n")
    print_table_row(["i"] + list(range(n)))

    x_arr = [round(x0 + i * h, 6) for i in range(n)]
    print_table_header(["x"] + x_arr)

    if method_idx != 2:
        if len(output) == 0:
            output = methods[method_idx][1](functions[function_idx][1], x0, y0, h, n)
        output_half = methods[method_idx][1](functions[function_idx][1], x0, y0, h / 2, n * 2)
        errors, endflag = accuracy_check(output, output_half)
    else:
        if len(output) == 0:
            output = methods[method_idx][1](functions[function_idx][1], x0, y0, h, n)
        y_true = [functions[function_idx][2](x) for x in x_arr]
        errors = [abs(y_true[i] - output[i]) for i in range(len(output))]
        endflag = max(errors) < accuracy
    print_table_row(["y"] + output)
    if endflag:
        break
    else:
        n *= 2
        h /= 2

        print("Desired precision haven't been achieved yet. Changing parameters")
        print("\t h =", h)
        print("\t n =", n)

show_plot()
