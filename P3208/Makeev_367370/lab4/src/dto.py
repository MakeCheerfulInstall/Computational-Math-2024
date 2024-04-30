from __future__ import annotations
from dataclasses import dataclass

import pylab as p


@dataclass
class Point:
    x: float
    y: float

    def __str__(self) -> str:
        return f'({self.x}, {self.y})'

    def copy(self) -> Point:
        return Point(self.x, self.y)


class PointTable:
    def __init__(self, points: list[Point]) -> None:
        self.points = sorted(points, key=lambda point: point.x)
        self.n = len(self.points)

    def copy(self) -> PointTable:
        return PointTable([point.copy() for point in self.points])

    def __getitem__(self, index: int) -> Point:
        return self.points[index]

    def __setitem__(self, index: int, point: Point) -> None:
        self.points[index] = point

    def log_y_is_safe(self) -> bool:
        for point in self.points:
            if point.y <= 0 or point.y == 1:
                return False

        return True

    def log_x_is_safe(self) -> bool:
        for point in self.points:
            if point.x <= 0 or point.x == 1:
                return False

        return True

    def get_all_x(self) -> list[float]:
        return [point.x for point in self.points]

    def get_all_y(self) -> list[float]:
        return [point.y for point in self.points]

    def sx(self) -> float:
        return sum([point.x for point in self.points])

    def sxx(self) -> float:
        return sum([point.x ** 2 for point in self.points])

    def s3x(self) -> float:
        return sum([point.x ** 3 for point in self.points])

    def s4x(self) -> float:
        return sum([point.x ** 4 for point in self.points])

    def s5x(self) -> float:
        return sum([point.x ** 5 for point in self.points])

    def s6x(self) -> float:
        return sum([point.x ** 6 for point in self.points])

    def sy(self) -> float:
        return sum([point.y for point in self.points])

    def sxy(self) -> float:
        return sum([point.x * point.y for point in self.points])

    def sxxy(self) -> float:
        return sum([point.x * point.x * point.y for point in self.points])

    def s3xy(self) -> float:
        return sum([(point.x ** 3) * point.y for point in self.points])


@dataclass
class DataTable:
    x_list: list[float]
    y_list: list[float]
    phi_x: list[float]
    eps: list[float]


@dataclass
class ApproxData:
    func_view: str
    callback: callable
    sko: float
    x_list: list[float]
    y_list: list[float]
    phi_x: list[float]
    eps: list[float]
    det_kf: float

    def __str__(self) -> str:
        return (f"phi: {self.func_view}\n" +
                f"sko: {self.sko:.3g}\n" +
                f"det_kf: {self.det_kf:.3g}\n\n")


@dataclass
class LinearApproxData(ApproxData):
    pirson_kf: float

    def __str__(self) -> str:
        return f'{super().__str__()[:-1]}pirson: {self.pirson_kf:.3g}\n\n'


@dataclass
class ApproxRes:
    type: str
    data: ApproxData | None
    error_message: str | None

    def __str__(self) -> str:
        prefix = f'-- {self.type}\n'
        if self.error_message is not None:
            return f'{prefix}\tERROR: {self.error_message}\n\n'
        else:
            return f'{prefix}{self.data}'
