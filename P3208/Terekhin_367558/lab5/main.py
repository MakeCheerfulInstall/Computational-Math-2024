import math

from tabulate import tabulate
import matplotlib.pyplot as plt

from P3208.Terekhin_367558.lab1.exceptions import InterpolationError
from P3208.Terekhin_367558.lab2.main import request_from_list
from P3208.Terekhin_367558.lab2.readers import AbstractReader, READERS
from P3208.Terekhin_367558.lab5.interpolation import INTERPOLATIONS

if __name__ == '__main__':
    reader: AbstractReader = request_from_list(READERS)
    points: list[tuple[float, float]] = sorted(reader.read_interpolation_data())
    argument: float = reader.read_interpolation_argument()

    n: int = len(points)
    sub_table: list[list[float]] = [[points[i][1] for i in range(n)]]
    for i in range(n - 1):
        sub_table.append([0] * n)
    x: list[float] = [points[i][0] for i in range(n)]
    headers = ['y']
    x_range: list[float] = [i / 100 for i in range(math.floor(points[0][0]) * 100, math.ceil(points[-1][0]) * 100)]

    colors = ['red', 'green', 'blue', 'orange', 'purple', 'cyan', 'magenta', 'pink', 'yellow', 'brown']
    color_index: int = 0

    for interpolation in INTERPOLATIONS:
        try:
            interpolation.set_points(points)
            y_range: list[float] = [interpolation.interpolate(i) for i in x_range]
            plt.plot(x_range, y_range, color=colors[color_index % len(colors)])
            color_index += 1
        except InterpolationError as e:
            print(e)

    for i in range(1, n):
        headers.append(f'Δ^{i}y' if i != 1 else 'Δy')
        for j in range(n - i):
            sub_table[i][j] = (sub_table[i - 1][j + 1] - sub_table[i - 1][j]) / (x[j + 1] - x[j])
    print(tabulate(sub_table, headers, tablefmt='pretty'))

    plt.scatter(x, sub_table[0])
    plt.show()



