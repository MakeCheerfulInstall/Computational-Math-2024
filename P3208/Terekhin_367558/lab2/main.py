from typing import Callable, Final, Any, Sequence

import matplotlib.pyplot as plt
from matplotlib.axes import Axes

from P3208.Terekhin_367558.lab2.methods import METHODS, Method
from P3208.Terekhin_367558.lab2.functions import Function, FUNCTIONS, Describable, SYSTEMS, FunctionSystem
from P3208.Terekhin_367558.lab2.readers import AbstractReader, READERS

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


def calculate_single_equations() -> None:
    func: Function = request_from_list(FUNCTIONS)
    results: list[float] = draw_and_show(func.func)
    reader: AbstractReader = request_from_list(READERS)
    st, end, precision = reader.read_data(results)
    method: Method = request_from_list(METHODS)
    method.set_arguments(func, st, end, precision)
    ans_x, steps, ans_y = method.execute()
    if ans_x != 0 and ans_y != 0:
        draw_and_show(func.func, (ans_x, ans_y))
    print(f"Final answer: {round(ans_x, 4)},\n Steps: {steps},\n Function value: {round(ans_y, 4)}")


def get_cramer_answer(mat: list[list[float]]) -> tuple[float, float]:
    det_a: float = mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0]
    det_x: float = mat[0][2] * mat[1][1] - mat[1][2] * mat[0][1]
    det_y: float = mat[0][0] * mat[1][2] - mat[1][0] * mat[0][2]
    return det_x / det_a, det_y / det_a


def calculate_multiple_equations() -> None:
    sys: FunctionSystem = request_from_list(SYSTEMS)
    draw_and_show_system(sys.first_y_from_x, sys.second_y_from_x)
    reader: AbstractReader = request_from_list(READERS)
    x, y, precision = reader.read_tuple('Input approximation point using x and y coordinates: ')

    while True:
        mat = [[sys.first_x_derivation(x, y), sys.first_y_derivation(x, y), -sys.first(x, y)],
               [sys.second_x_derivation(x, y), sys.second_y_derivation(x, y), -sys.second(x, y)]]
        delta_x, delta_y = get_cramer_answer(mat)
        x += delta_x
        y += delta_y
        if abs(delta_y) < precision and abs(delta_x) < precision:
            break

    print("Newton's method calculations: ", round(x, 3), round(y, 3))
    draw_and_show_system(sys.first_y_from_x, sys.second_y_from_x, (x, y))


if __name__ == '__main__':
    calculate_multiple_equations()
   # calculate_single_equations()
