# 2𝑥^3− 1,89𝑥^2 −5𝑥 + 2,34
# 𝑥^3 + 4,81𝑥^2 − 17,37𝑥 + 5,38
from typing import Callable, Final, TextIO

import matplotlib.pyplot as plt
from matplotlib.axes import Axes
import math

from P3208.Terekhin_367558.lab1.exceptions import ParsingError

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


def draw_and_show(function: Callable[[float], float]) -> None:
    x: list[float] = [i / SCALE - GRID for i in range(2 * GRID * SCALE)]
    y: list[float] = [function(num) for num in x]
    bounds: list[float] = [x[i] for i in range(len(y)) if abs(y[i]) < 0.5]

    ax: Axes = plt.axes()
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    if bounds:
        l_limit: float = min(-4, bounds[0], bounds[-1]) - 1
        r_limit: float = max(bounds[0], bounds[-1], 4) + 1
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


class AbstractReader:
    def read_data(self) -> tuple[float, float, float]:
        pass


class ConsoleReader(AbstractReader):
    def read_first_approx(self) -> tuple[float, float]:
        print('Input first approximation interval using two numbers:')
        while True:
            try:
                a, b = map(float, input().split(' '))
                return min(a, b), max(a, b)
            except ValueError as e:
                print(e)
                print('Try again: ')

    def read_precision(self) -> float:
        print('Input precision:')
        while True:
            try:
                eps: float = float(input())
                if eps <= 0 or eps > 1:
                    raise ValueError('Precision is a positive float less then 1')
                return eps
            except ValueError as e:
                print(e)
                print('Try again: ')

    def read_data(self) -> tuple[float, float, float]:
        a, b = self.read_first_approx()
        return a, b, self.read_precision()


class FileReader(AbstractReader):
    def __init__(self) -> None:
        self.file: TextIO or None = None

    def read_file_name(self) -> None:
        print('Enter file name with extension:')
        while True:
            try:
                filename: str = input()
                self.file = open(filename, "r")
                break
            except FileNotFoundError:
                print('No such file. Try again:')

    def read_first_approx(self) -> tuple[float, float]:
        try:
            a, b = map(float, self.file.readline().split(' '))
            return min(a, b), max(a, b)
        except ValueError as e:
            raise ParsingError(str(e))

    def read_precision(self) -> float:
        try:
            eps: float = float(self.file.readline())
            if eps <= 0 or eps > 1:
                raise ValueError('Precision should be a positive float less then 1')
            return eps
        except ValueError as e:
            raise ParsingError(str(e))

    def read_data(self) -> tuple[float, float, float]:
        while True:
            try:
                self.read_file_name()
                a, b = self.read_first_approx()
                return a, b, self.read_precision()
            except ParsingError as e:
                print(e)
                print('Try another file')


READERS: Final[list[tuple[AbstractReader, str]]] =\
    [(ConsoleReader(), 'From console'), (FileReader(), 'From file')]
READER_REQUEST: str = ''
for ind in range(len(READERS)):
    READER_REQUEST += f"{ind + 1}. {READERS[ind][1]}\n"
READER_REQUEST += 'Choose how to read the data: '


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


if __name__ == '__main__':
    func: Callable[[float], float] = request_function()
    draw_and_show(func)
    reader: AbstractReader = request_reader()
    reader.read_data()
