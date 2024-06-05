import math

from matplotlib import pyplot as plt
from tabulate import tabulate

from P3208.Terekhin_367558.lab2.functions import DifferentialEquation, EQUATIONS
from P3208.Terekhin_367558.lab2.main import request_from_list
from P3208.Terekhin_367558.lab2.readers import ConsoleReader
from P3208.Terekhin_367558.lab6.differential import DIFFERENTIALS

if __name__ == '__main__':
    equation: DifferentialEquation = request_from_list(EQUATIONS)
    reader: ConsoleReader = ConsoleReader('From console')
    init_y = reader.read_argument('Enter initial y: ')
    h = reader.read_argument('Enter step size h: ')
    a, b, eps = reader.read_tuple('Enter differential interval using two numbers: ')

    a, b = min(a, b), max(a, b)

    if equation.c is None:
        equation.c = equation.const(a, init_y)

    x_range = [i / 100 for i in range(math.floor(a * 100), math.ceil(b * 100))]
    y_range = [equation.solution(k, equation.c) for k in x_range]

    colors = ['red', 'green', 'orange', 'purple', 'cyan', 'magenta', 'pink', 'yellow', 'brown']
    color_index: int = 0

    plt.plot(x_range, y_range)

    for diff in DIFFERENTIALS:
        diff.set_data(a, b, h, init_y)
        y = diff.solve(equation, eps)
        y_accurate = [equation.solution(k, equation.c) for k in diff.x]
        ans = []
        for i in range(len(y_accurate)):
            ans.append(list(map(lambda k: round(k, 4), [diff.x[i], y[i], y_accurate[i]])))

        print(tabulate([diff.description]))
        print(tabulate(ans, tablefmt='pretty', headers=['x', 'y', 'accurate_answer']))

        plt.plot(diff.x, diff.y, color=colors[color_index % len(colors)])
        color_index += 1
    plt.show()
