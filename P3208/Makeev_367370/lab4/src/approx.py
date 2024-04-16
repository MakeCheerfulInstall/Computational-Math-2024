import math

from dto import Point, PointTable, ApproxRes
from utils import avg
from enum import Enum


def calc_pirson_kf(x_list: list[float], y_list: list[float]) -> float:
    n: int = len(x_list)
    avg_x = avg(x_list)
    avg_y = avg(y_list)

    return (sum([(x_list[i] - avg_x) * (y_list[i] - avg_y) for i in range(n)]) /
                        math.sqrt(sum([(x_list[i] - avg_x) ** 2 for i in range(n)]) *
                                  sum([(y_list[i] - avg_y) ** 2 for i in range(n)])
                                  ))


def calc_det_kf(phi_list: list[float], y_list: list[float]) -> float:
    n: int = len(phi_list)
    avg_phi = avg(phi_list)

    return (1 - sum([(y_list[i] - phi_list[i]) ** 2 for i in range(n)]) /
            sum([(y_list[i] - avg_phi) ** 2 for i in range(n)]))


def calc_sko(eps: list[float]) -> float:
    n: int = len(eps)
    return math.sqrt(sum([eps[i] ** 2 for i in range(n)]) / n)


def approx_linear(points: PointTable) -> ApproxRes:
    sx, sy, sxx, sxy, n = points.sx(), points.sy(), points.sxx(), points.sxy(), points.n

    a: float = (sxy * n - sx * sy) / (sxx * n - sx * sx)
    b: float = (sxx * sy - sx * sxy) / (sxx * n - sx * sx)

    callback: callable = lambda x: a * x + b
    func_view: str = f'{a:.3g}x + {b:.3g}'
    if b < 0:
        func_view = f'{a:.3g}x - {abs(b):.3g}'
    elif b == 0:
        func_view = f'{a:.3g}x'
    x_list: list[float] = points.get_all_x()
    y_list: list[float] = points.get_all_y()
    phi_x: list[float] = [callback(x) for x in x_list]
    eps: list[float] = [phi_x[i] - y_list[i] for i in range(points.n)]

    return ApproxRes(
        type='Линейная аппроксимация',
        func_view=func_view,
        callback=callback,
        sko=calc_sko(eps),
        x_list=x_list,
        y_list=y_list,
        phi_x=phi_x,
        eps=eps,
        pirson_kf=calc_pirson_kf(x_list, y_list),
        det_kf=calc_det_kf(phi_x, y_list)
    )


class Approximators(Enum):
    LINEAR = approx_linear
