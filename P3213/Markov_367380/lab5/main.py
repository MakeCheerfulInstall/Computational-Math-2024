import math
from typing import Callable

import numpy as np
from matplotlib import pyplot as plt

from P3213.Markov_367380.lab5.dto.point import Point
from P3213.Markov_367380.lab5.dto.request import Request
from P3213.Markov_367380.lab5.dto.response import Response
from P3213.Markov_367380.lab5.interpolation.utils.all_interpolation import *


def main():
    ans_inp: str = input("How would you like to input?\n1.via func\n2.via file\n3.via console\n")
    if ans_inp == "2":
        request: Request = file_input()
    elif ans_inp == "3":
        request: Request = cli_input()
    else:
        request: Request = func_input()
    x0: float = 0
    while True:
        try:
            x0 = float(input('Введите значение аргумента: '))
            if x0 < request.points[0].x or x0 > request.points[-1].x:
                print("Не в интервале")
                continue
            break
        except ValueError:
            continue
    ys: list[float] = request.get_ys()
    unique_xs: set[float] = set()
    xs: list[float] = request.get_xs()

    for i in range(request.n):
        if xs[i] in unique_xs:
            new_x: float = xs[i] + 0.01
            while new_x in unique_xs:
                new_x += 0.01
            unique_xs.add(new_x)
            request.points[i].x = new_x
        unique_xs.add(xs[i])
    print('Конечные разности:\n', *[ys[i + 1] - ys[i] for i in range(request.n - 1)])
    print(request.get_xs())
    for interpolation in interpolations:
        response: Response = interpolation.interpolate(request, x0)
        if not response.status_code:
            print(f'{response.type.value}: {response.ans}')

    xs: list[float] = request.get_xs()
    plt.plot(xs, ys)
    plt.grid(True)
    plt.scatter(xs, ys)
    plt.scatter(x0, response.ans, color='r')
    plot_x = np.linspace(xs[0], xs[-1], 100)
    plot_y = [interpolations[1].interpolate(request, x_c).ans for x_c in plot_x]
    plt.plot(plot_x, plot_y, color='g', label=response.type.value)
    plt.show()


def cli_input() -> Request:
    points: list[Point] = []
    print("Введите координаты через пробел: ")

    while True:
        point_str: str = input("")
        coordinates: list[str] = point_str.split()

        if len(coordinates) != 2:
            break

        try:
            x: float = float(coordinates[0])
            y: float = float(coordinates[1])
            points.append(Point(x, y))
        except ValueError:
            print("Пожалуйста, введите ЧИСЛОВЫЕ значения для координат x и y.")
            continue

    points = sorted(points)
    if len(points) > 1:
        return Request(points)
    else:
        print("Недостаточно точек")
        cli_input()


def file_input() -> Request:
    file_path: str = input("Введите путь к файлу с координатами точек: ")
    points: list[Point] = []
    while True:
        try:
            with open(file_path, 'r') as file:
                for line in file:
                    coordinates = line.strip().split()

                    # проверяем, что в строке есть две координаты
                    if len(coordinates) != 2:
                        print(f"Ошибка в строке: {line}. Пожалуйста, убедитесь, что каждая строка содержит координаты.")
                        continue

                    try:
                        x = float(coordinates[0])
                        y = float(coordinates[1])
                        points.append(Point(x, y))
                    except ValueError:
                        print(
                            f"Ошибка в строке: {line}. Пожалуйста, убедитесь, что значения координат являются числами.")
                        continue
            points = sorted(points)
            if len(points) > 1:
                return Request(points)
            else:
                print("Недостаточно точек")
                file_input()

        except FileNotFoundError:
            print("Файл не найден. Пожалуйста, убедитесь, что указанный файл существует.")


def func_input() -> Request:
    print('1. sin(x)\n2. x^2')
    a: float = 0
    b: float = 0
    n: int = 0
    if input('Выберите уравнение: ') == "1":
        func: Callable = lambda k: math.sin(k)
    else:
        func: Callable = lambda k: k * k
    while True:
        try:
            a, b = map(float, input('Введите границы интервала через пробел: ').split(" "))
            break
        except ValueError:
            continue

    while True:
        try:
            n = int(input('Введите количество точек на интервале: '))
            if n < 1:
                print("Недостаточно точек")
                continue
            break
        except ValueError:
            continue
    points: list[Point] = [Point(a + ((b - a) / (n - 1)) * i, func(a + ((b - a) / (n - 1)) * i)) for i in range(n)]
    points = sorted(points)
    if len(points) > 1:
        return Request(points)
    else:
        print("Недостаточно точек")
        func_input()


main()
