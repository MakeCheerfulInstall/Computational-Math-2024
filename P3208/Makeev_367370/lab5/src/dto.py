from __future__ import annotations

import math
from dataclasses import dataclass
from enum import StrEnum, Enum


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
class Result:
    title: str
    func_points: PointTable
    answer: Point


@dataclass
class MethodDto:
    name: str
    solve: callable[[PointTable, float], float]


@dataclass
class FunctionDto:
    view: str
    func: callable[[float], float]

    def __str__(self) -> str:
        return self.view

    def create_func_points(self, start: float, end: float,  accuracy: int) -> PointTable | None:
        if start >= end:
            print("Start can't be more than end")
            return None

        h: float = (end - start) / accuracy
        x_list: list[float] = [i * h + start for i in range(accuracy+1)]
        y_list: list[float] = [self.func(x) for x in x_list]
        return PointTable.init(x_list, y_list)


class InputType(StrEnum):
    CMD = 'Console'
    FILE = 'File'
    FUNC = 'Function'


class DefFunctions(Enum):
    FIRST = FunctionDto('y = sin x', lambda x: math.sin(x))
    SECOND = FunctionDto('y = e^-x', lambda x: math.exp(-x))
