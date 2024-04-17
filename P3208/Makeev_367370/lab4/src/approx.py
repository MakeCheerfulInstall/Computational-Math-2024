import math

from dto import Point, PointTable, ApproxRes, DataTable
from utils import avg
from enum import Enum
from slau_solver.matrix import Equation


def calc_pirson_kf(def_data: DataTable) -> float:
    x_list: list[float] = def_data.x_list
    y_list: list[float] = def_data.y_list

    n: int = len(x_list)
    avg_x = avg(x_list)
    avg_y = avg(y_list)

    return (sum([(x_list[i] - avg_x) * (y_list[i] - avg_y) for i in range(n)]) /
                        math.sqrt(sum([(x_list[i] - avg_x) ** 2 for i in range(n)]) *
                                  sum([(y_list[i] - avg_y) ** 2 for i in range(n)])
                                  ))


def calc_det_kf(def_data: DataTable) -> float:
    phi_list: list[float] = def_data.phi_x
    y_list: list[float] = def_data.y_list

    n: int = len(phi_list)
    avg_phi = avg(phi_list)

    return (1 - sum([(y_list[i] - phi_list[i]) ** 2 for i in range(n)]) /
            sum([(y_list[i] - avg_phi) ** 2 for i in range(n)]))


def calc_sko(eps: list[float]) -> float:
    n: int = len(eps)
    return math.sqrt(sum([eps[i] ** 2 for i in range(n)]) / n)


def get_def_data(points: PointTable, func: callable) -> DataTable:
    x_list = points.get_all_x()
    y_list = points.get_all_y()
    phi_x = [func(x) for x in x_list]
    eps = [phi_x[i] - y_list[i] for i in range(points.n)]

    return DataTable(x_list, y_list, phi_x, eps)


def approx_linear(points: PointTable) -> ApproxRes:
    sx, sy, sxx, sxy, n = points.sx(), points.sy(), points.sxx(), points.sxy(), points.n

    eq: Equation = Equation.create([
        [sxx, sx],
        [sx, n],
        [sxy, sy]
    ])
    eq.solve()
    a, b = eq.answers.elems

    callback: callable = lambda x: a * x + b
    func_view: str = f'{a:.3g}x'
    if b > 0:
        func_view += f' + {b:.3g}'
    elif b < 0:
        func_view += f' - {-b:.3g}x'

    def_data: DataTable = get_def_data(points, callback)

    return ApproxRes(
        type='Линейная аппроксимация',
        func_view=func_view,
        callback=callback,
        sko=calc_sko(def_data.eps),
        x_list=def_data.x_list,
        y_list=def_data.y_list,
        phi_x=def_data.phi_x,
        eps=def_data.eps,
        pirson_kf=calc_pirson_kf(def_data),
        det_kf=calc_det_kf(def_data)
    )


def approx_quad(points: PointTable) -> ApproxRes:
    sx, sxx, s3x, s4x, sy, sxy, sxxy, n = points.sx(), points.sxx(), points.s3x(), points.s4x(), points.sy(), points.sxy(), points.sxxy(), points.n

    eq: Equation = Equation.create([
        [n, sx, sxx],
        [sx, sxx, s3x],
        [sxx, s3x, s4x],
        [sy, sxy, sxxy]
    ])
    eq.solve()
    a, b, c = eq.answers.elems

    callback: callable = lambda x: a * x * x + b * x + c
    func_view: str = f'{a:.3g}x^2'
    if b > 0:
        func_view += f' + {b:.3g}x'
    elif b < 0:
        func_view += f' - {-b:.3g}x'
    if c > 0:
        func_view += f' + {c:.3g}'
    elif c < 0:
        func_view += f' - {-c:.3g}'

    def_data: DataTable = get_def_data(points, callback)

    return ApproxRes(
        type='Квадратичная аппроксимация',
        func_view=func_view,
        callback=callback,
        sko=calc_sko(def_data.eps),
        x_list=def_data.x_list,
        y_list=def_data.y_list,
        phi_x=def_data.phi_x,
        eps=def_data.eps,
        pirson_kf=None,
        det_kf=calc_det_kf(def_data)
    )


APPROXIMATORS: list[callable] = [
    approx_linear,
    approx_quad
]
