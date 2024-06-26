import pandas as pd
from typing import Callable


def read_points(filename: str):
    points = []
    with open(filename) as file:
        for line in file:
            x, y = line.split()
            points.append((float(x), float(y)))
    points.sort(key=lambda point: point[0])
    return points


def make_finite_difference_table(table):
    columns = list()
    indexes = list()
    for i in range(len(table)):
        columns.append(f'Δ{i}y')
        indexes.append(f'x{i}')
    frame = pd.DataFrame(table, columns=columns, index=indexes)
    frame = frame.dropna(axis=1, how='all')
    frame = frame.dropna(axis=0, how='all')
    frame = frame.fillna('—')
    return frame


def print_points(points):
    print(pd.DataFrame(list(zip(*points)), index=["X", "Y"]).to_string())


def step(points):
    return (points[-1][0] - points[0][0]) / (len(points) - 1)


def is_equidistant(points, eps=1e-6):
    stp = step(points)
    for i in range(1, len(points)):
        if abs(points[i][0] - points[i - 1][0] - stp) > eps:
            return False
    return True


def read_number(prompt: str, condition: Callable[[any], bool] = lambda x: True, integer: bool = False):
    while True:
        try:
            if (integer):
                value = int(input(prompt))
            else:
                value = float(input(prompt))
            if condition(value):
                return value
            else:
                raise ValueError("Condition not met")
        except ValueError:
            print("Неверный ввод")


def choose(what: str, objects: list):
    choice = read_number(f"Выберите {what}:\n" +
                         "\n".join([f"{i + 1}. {obj}" for i, obj in enumerate(objects)]) + "\n",
                         condition=lambda x: 1 <= x <= len(objects),
                         integer=True)
    print("Выбрано:", objects[choice - 1])
    return objects[choice - 1]