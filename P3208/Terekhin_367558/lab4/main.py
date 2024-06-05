import math

from matplotlib import pyplot as plt
from matplotlib.axes import Axes
from tabulate import tabulate

from P3208.Terekhin_367558.lab2.main import request_from_list
from P3208.Terekhin_367558.lab2.readers import AbstractReader, READERS
from P3208.Terekhin_367558.lab4.approx import APPROXIMATIONS, Approximation, LinearApproximation


if __name__ == '__main__':
    reader: AbstractReader = request_from_list(READERS)
    points: list[tuple[float, float]] = reader.read_points()
    x_values: list[float] = [x for x, y in points]
    y_values: list[float] = [y for x, y in points]
    x_range = [i / 100 for i in range(math.floor(min(x_values)), 100 * math.ceil(max(x_values)))]

    ax: Axes = plt.axes()
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')

    best: float = math.inf
    ind: int = 0
    for i in range(len(APPROXIMATIONS)):
        method: Approximation = APPROXIMATIONS[i]
        method.build_approximation(points)
        print(tabulate([method.description]))
        results: list[list[float]] = []
        deviation: float = 0
        y_approx: list[float] = []
        if method.func is not None:
            y_range = [method.func(x) for x in x_range]
            plt.plot(x_range, y_range)
            for x, y in points:
                approx = round(method.func(x), 3)
                y_approx.append(approx)
                deviation += (y - approx) ** 2
                results.append([x, y, approx, y - approx])
        headers = ['Average deviation', 'R^2', 'Formula view']
        average_approx = sum(y_approx) / len(y_approx)
        average_deviation = round((deviation / len(points)) ** 0.5, 3)
        if average_deviation < best:
            best = average_deviation
            ind = i
        R = round(1 - sum([(y_values[i] - y_approx[i]) ** 2 for i in range(len(y_values))]) / sum([(y - average_approx) ** 2 for y in y_values]), 3)
        stat = [[average_deviation, R,  method.view]]

        if isinstance(method, LinearApproximation):
            headers.append('Pearson coefficient')
            stat[0].append(method.r)

        print(tabulate(stat, headers, numalign="center"), '\n')

        plt.plot(x_values, y_values, 'ro')

        print(tabulate(results, headers=['X', 'Y', 'Approximation', 'Epsilon'], numalign="center"))

    print(tabulate(["Best approximation"]))
    print(APPROXIMATIONS[ind].description)
    plt.show()
