from __future__ import annotations
from dataclasses import dataclass


@dataclass
class Point:
    x: float
    y: float

    def __str__(self) -> str:
        return f'({self.x}, {self.y})'


class PointTable:
    def __init__(self, points: list[Point]) -> None:
        self.points = sorted(points, key=lambda point: point.x)
        self.n = len(self.points)

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
class ApproxRes:
    type: str
    func_view: str
    callback: callable
    sko: float
    x_list: list[float]
    y_list: list[float]
    phi_x: list[float]
    eps: list[float]
    pirson_kf: float | None
    det_kf: float

    def __str__(self) -> str:
        data: str = (f"-- {self.type}\n" +
                f"phi: {self.func_view}\n" +
                f"sko: {self.sko:.3g}\n" +
                f"det_kf: {self.det_kf:.3g}\n" +
                f"phi: {self.phi_x}\n" +
                f"eps: {self.eps}")

        if self.pirson_kf is not None:
            data += f"\npirson_kf: {self.pirson_kf:.3g}"

        return f'{data}\n\n'
