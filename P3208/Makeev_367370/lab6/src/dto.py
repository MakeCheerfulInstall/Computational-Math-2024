from __future__ import annotations

import math
from enum import Enum
from dataclasses import dataclass
from prettytable import PrettyTable


@dataclass
class Point:
    x: float
    y: float

    def __str__(self) -> str:
        return f'({self.x}, {self.y})'


@dataclass
class PointTable:
    points: list[Point]
    n: int

    def __init__(self, points: list[Point]) -> None:
        self.points = sorted(points, key=lambda point: point.x)
        self.n = len(self.points)

    @staticmethod
    def init(x_list: list[float], y_list: list[float]) -> PointTable | None:
        if len(x_list) != len(y_list):
            print("Can't create PointTable object with different numbers of X and Y elements")
            return None

        points: list[Point] = []
        for i in range(len(x_list)):
            points.append(Point(x_list[i], y_list[i]))
        return PointTable(points)

    def x_list(self) -> list[float]:
        return [p.x for p in self.points]

    def y_list(self) -> list[float]:
        return [p.y for p in self.points]

    def __str__(self) -> str:
        return str(self.points)

    def __len__(self) -> int:
        return len(self.points)

    def __getitem__(self, i) -> Point:
        return self.points[i]

    def __setitem__(self, key, value) -> None:
        self.points[key] = value


@dataclass
class AnswerDto:
    method_name: str
    x_list: list[float]
    y_list: list[float]
    acc_y_list: list[float]
    e: float

    def __str__(self) -> str:
        table: PrettyTable = PrettyTable()
        table.field_names = ["i", "x", "y", "Точный y"]
        for i in range(len(self.x_list)):
            table.add_row([i, round(self.x_list[i], 5), round(self.y_list[i], 5), round(self.acc_y_list[i], 5)])
        return (f'\nРешение методом {self.method_name}' +
                f'\nПогрешность: {self.e:.3E}' +
                f'\nКоличество точек: {len(self.x_list)}' +
                f'\nШаг: {(self.x_list[-1] - self.x_list[0]) / len(self.x_list):.3E}\n' +
                table.get_string())


@dataclass
class ParamsDto:
    y_0: float
    interval: tuple[float, float]
    h: float
    e: float


@dataclass
class DiffUrDto:
    view: str
    func: callable[[float, float], float]
    answer_func_to_C: callable[[float, float], float]
    answer_func_to_Y: callable[[float, float], float]

    def __str__(self) -> str:
        return self.view

    def find_c(self, point: Point) -> float:
        return self.answer_func_to_C(point.x, point.y)

    def find_y(self, x: float, point_0: Point) -> float:
        return self.answer_func_to_Y(x, self.find_c(point_0))

    def f_x_y(self, x: float, y: float) -> float:
        return self.func(x, y)


class DffUrType(Enum):
    FIRST = DiffUrDto("y' = y + (1 + x)y^2", lambda x, y: y + (x + 1) * (y ** 2),
                      lambda x, y: math.exp(x) / y + math.exp(x) * x,
                      lambda x, c: - (math.exp(x)) / (x * math.exp(x) + c))

    SECOND = DiffUrDto("y' = y * sin(x)", lambda x, y: y * math.sin(x),
                      lambda x, y: y * math.exp(math.cos(x)),
                      lambda x, c: c / math.exp(math.cos(x)))

    THIRD = DiffUrDto("y' = 2y + x^2", lambda x, y: 2*y + x**2,
                      lambda x, y: (y + x/2 + (x**2)/2 + 1/4) / math.exp(2*x),
                      lambda x, c: c*math.exp(2*x) - x/2 - (x**2)/2 - 1/4)
