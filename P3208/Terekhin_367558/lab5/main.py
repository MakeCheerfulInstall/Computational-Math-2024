import math

import matplotlib.pyplot as plt
from tabulate import tabulate

from P3208.Terekhin_367558.lab1.exceptions import InterpolationError
from P3208.Terekhin_367558.lab2.main import request_from_list
from P3208.Terekhin_367558.lab2.readers import AbstractReader, READERS
from P3208.Terekhin_367558.lab5.interpolation import INTERPOLATIONS

if __name__ == '__main__':
    reader: AbstractReader = request_from_list(READERS)
    points: list[tuple[float, float]] = sorted(reader.read_interpolation_data())
    argument: float = reader.read_interpolation_argument()

    n: int = len(points)
    x: list[float] = [points[i][0] for i in range(n)]
    y: list[float] = [p[1] for p in points]
    x_range: list[float] = [i / 100 for i in range(math.floor(x[0] * 100), math.ceil(x[-1] * 100))]

    colors = ['red', 'green', 'blue', 'orange', 'purple', 'cyan', 'magenta', 'pink', 'yellow', 'brown']
    color_index: int = 0

    for interpolation in INTERPOLATIONS:
        try:
            interpolation.set_points(points)
            y_range: list[float] = [interpolation.interpolate(i) for i in x_range]
            plt.plot(x_range, y_range, color=colors[color_index % len(colors)])
            color_index += 1
            print(tabulate([interpolation.description]))
            print(f'For x = {argument} y = {round(interpolation.interpolate(argument), 3)}')
        except InterpolationError as e:
            print(e)

    plt.scatter(x, y)
    plt.show()



