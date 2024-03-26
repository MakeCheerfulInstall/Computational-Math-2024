# 2𝑥^3− 1,89𝑥^2 −5𝑥 + 2,34
# 𝑥^3 + 4,81𝑥^2 − 17,37𝑥 + 5,38
# 1 - Метод половинного деления
# 4 - Метод секущих
# 5 - Метод простой итерации
# 6 - Метод Ньютона

from typing import Callable, Final, Any, Sequence

import matplotlib.pyplot as plt
from matplotlib.axes import Axes

from functions import Function, FUNCTIONS, Describable
from methods import METHODS, Method
from readers import AbstractReader, READERS

GRID: Final[int] = 10
SCALE: Final[int] = 100


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


def request_from_list(options: Sequence[Describable]) -> Any:
    print(create_input_request_from_list(options))
    while True:
        try:
            num: int = int(input())
            if num <= 0:
                raise IndexError()
            return options[num - 1]
        except (ValueError, IndexError):
            print(f'No such {options[0].option_name}. Try again')


def create_input_request_from_list(options: Sequence[Describable]) -> str:
    req: str = ''
    for i in range(len(options)):
        req += f"{i + 1}. {options[i].description}\n"
    return req + f'Choose {options[0].option_name}:'


if __name__ == '__main__':
    func: Function = request_from_list(FUNCTIONS)
    results: list[float] = draw_and_show(func.func)
    reader: AbstractReader = request_from_list(READERS)
    st, end, precision = reader.read_data(results)
    method: Method = request_from_list(METHODS)
    method.set_arguments(func, st, end, precision)
    ans_x, steps, ans_y = method.execute()
    print(ans_x, steps, ans_y)
