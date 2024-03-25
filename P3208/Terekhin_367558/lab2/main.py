# 2𝑥^3− 1,89𝑥^2 −5𝑥 + 2,34
# 𝑥^3 + 4,81𝑥^2 − 17,37𝑥 + 5,38
# 1 - Метод половинного деления
# 4 - Метод секущих
# 5 - Метод простой итерации
# 6 - Метод Ньютона

from typing import Callable, Final

import matplotlib.pyplot as plt
from matplotlib.axes import Axes
import math

from P3208.Terekhin_367558.lab2.methods import METHODS, METHOD_REQUEST
from P3208.Terekhin_367558.lab2.readers import AbstractReader, READER_REQUEST, READERS

GRID: Final[int] = 10
SCALE: Final[int] = 100
FUNCTIONS: Final[list[tuple[Callable[[float], float], str]]] = \
    [(lambda x: x * x * x + 4.81 * x * x - 17.37 * x + 5.38, 'x^3 + 4,81x^2 - 17,37x + 5,38'),
     (lambda x: 2 * x * x * x - 1.89 * x * x - 5 * x + 2.34, '2x^3 - 1,89x^2 - 5x + 2,34'),
     (lambda x: math.exp(x / 3) - 2 * math.cos(x + 4), 'e^(x / 3) - 2cos(x + 4)')]
CHOOSE_REQUEST: str = ''
for ind in range(len(FUNCTIONS)):
    CHOOSE_REQUEST += f"{ind + 1}. {FUNCTIONS[ind][1]}\n"
CHOOSE_REQUEST += 'Choose a function:'


def draw_and_show(function: Callable[[float], float]) -> list[float]:
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
    plt.show()
    return bounds


def request_function() -> Callable[[float], float]:
    print(CHOOSE_REQUEST)
    while True:
        try:
            num: int = int(input())
            if num <= 0:
                raise IndexError()
            return FUNCTIONS[num - 1][0]
        except (ValueError, IndexError):
            print('No such function. Try again')


def request_reader() -> AbstractReader:
    print(READER_REQUEST)
    while True:
        try:
            num: int = int(input())
            if num <= 0:
                raise IndexError()
            return READERS[num - 1][0]
        except (IndexError, ValueError):
            print('No such option. Try again')


def request_calculating_method() -> Callable[[float, float, float], float]:
    print(METHOD_REQUEST)
    while True:
        try:
            num: int = int(input())
            if num <= 0:
                raise IndexError()
            return METHODS[num - 1][0]
        except (IndexError, ValueError):
            print('No such method. Try again')


if __name__ == '__main__':
    func: Callable[[float], float] = request_function()
    results: list[float] = draw_and_show(func)
    reader: AbstractReader = request_reader()
    st, end, precision = reader.read_data(results)
    method: Callable[[float, float, float], float] = request_calculating_method()
    method(st, end, precision)
