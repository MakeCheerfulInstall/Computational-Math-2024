import math

from dto import *
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


def calc_linear_kfs(points: PointTable) -> tuple[float, float]:
    sx, sy, sxx, sxy, n = points.sx(), points.sy(), points.sxx(), points.sxy(), points.n

    eq: Equation = Equation.create([
        [sxx, sx],
        [sx, n],
        [sxy, sy]
    ])
    eq.solve()
    a, b = eq.answers.elems
    return a, b


def approx_linear(points: PointTable) -> ApproxRes:
    a, b = calc_linear_kfs(points)

    callback: callable = lambda x: a * x + b
    func_view: str = f'{a:.3g}x'
    if b > 0:
        func_view += f' + {b:.3g}'
    elif b < 0:
        func_view += f' - {-b:.3g}x'

    def_data: DataTable = get_def_data(points, callback)

    return ApproxRes(
        type='Линейная аппроксимация',
        data=LinearApproxData(
            func_view=func_view,
            callback=callback,
            sko=calc_sko(def_data.eps),
            x_list=def_data.x_list,
            y_list=def_data.y_list,
            phi_x=def_data.phi_x,
            eps=def_data.eps,
            pirson_kf=calc_pirson_kf(def_data),
            det_kf=calc_det_kf(def_data)
        ),
        error_message=None
    )


def approx_quad(points: PointTable) -> ApproxRes:
    sx, sxx, s3x, s4x, sy, sxy, sxxy, n = (points.sx(), points.sxx(), points.s3x(), points.s4x(),
                                           points.sy(), points.sxy(), points.sxxy(), points.n)

    eq: Equation = Equation.create([
        [s4x, s3x, sxx],
        [s3x, sxx, sx],
        [sxx, sx, n],
        [sxxy, sxy, sy]
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
        data=ApproxData(
            func_view=func_view,
            callback=callback,
            sko=calc_sko(def_data.eps),
            x_list=def_data.x_list,
            y_list=def_data.y_list,
            phi_x=def_data.phi_x,
            eps=def_data.eps,
            det_kf=calc_det_kf(def_data)
        ),
        error_message=None
    )


def approx_cube(points: PointTable) -> ApproxRes:
    sx, sxx, s3x, s4x, s5x, s6x, sy, sxy, sxxy, s3xy, n = (points.sx(), points.sxx(), points.s3x(), points.s4x(),
                                                           points.s5x(), points.s6x(), points.sy(), points.sxy(),
                                                           points.sxxy(), points.s3xy(), points.n)
    eq: Equation = Equation.create([
        [s6x, s5x, s4x, s3x],
        [s5x, s4x, s3x, sxx],
        [s4x, s3x, sxx, sx],
        [s3x, sxx, sx, n],
        [s3xy, sxxy, sxy, sy]
    ])
    eq.solve()
    a, b, c, d = eq.answers.elems

    callback: callable = lambda x: a * (x ** 3) + b * (x ** 2) + c * x + d
    func_view: str = f'{a:.3g}x^3'
    if b > 0:
        func_view += f' + {b:.3g}x^2'
    elif b < 0:
        func_view += f' - {-b:.3g}x^2'
    if c > 0:
        func_view += f' + {c:.3g}x'
    elif c < 0:
        func_view += f' - {-c:.3g}x'
    if d > 0:
        func_view += f' + {d:.3g}'
    elif d < 0:
        func_view += f' - {-d:.3g}'

    def_data: DataTable = get_def_data(points, callback)

    return ApproxRes(
        type='Кубическая аппроксимация',
        data=ApproxData(
            func_view=func_view,
            callback=callback,
            sko=calc_sko(def_data.eps),
            x_list=def_data.x_list,
            y_list=def_data.y_list,
            phi_x=def_data.phi_x,
            eps=def_data.eps,
            det_kf=calc_det_kf(def_data)
        ),
        error_message=None
    )


def approx_exp(points: PointTable) -> ApproxRes:
    if not points.log_y_is_safe():
        return ApproxRes(
            type='Экспоненциальная аппроксимация',
            data=None,
            error_message="Can't approximate with negative ordinates"
        )
    points_copy: PointTable = points.copy()
    for i in range(points.n):
        points_copy[i].y = math.log(points_copy[i].y)

    b, A = calc_linear_kfs(points_copy)
    a = math.exp(A)

    callback: callable = lambda x: a * math.exp(b * x)
    func_view: str = f'{a:.3g}e^({b:.3g}x)'
    def_data: DataTable = get_def_data(points, callback)

    return ApproxRes(
        type='Экспоненциальная аппроксимация',
        data=ApproxData(
            func_view=func_view,
            callback=callback,
            sko=calc_sko(def_data.eps),
            x_list=def_data.x_list,
            y_list=def_data.y_list,
            phi_x=def_data.phi_x,
            eps=def_data.eps,
            det_kf=calc_det_kf(def_data)
        ),
        error_message=None
    )


def approx_log(points: PointTable) -> ApproxRes:
    if not points.log_x_is_safe():
        return ApproxRes(
            type='Логарифмическая аппроксимация',
            data=None,
            error_message="Can't approximate with negative abscisses"
        )

    points_copy: PointTable = points.copy()
    for i in range(points.n):
        points_copy[i].x = math.log(points_copy[i].x)

    a, b = calc_linear_kfs(points_copy)

    callback: callable = lambda x: a * math.log(x) + b
    func_view: str = f'{a:.3g}ln(x)'
    if b > 0:
        func_view += f' + {b:.3g}'
    elif b < 0:
        func_view += f' - {-b:.3g}'
    def_data: DataTable = get_def_data(points, callback)

    return ApproxRes(
        type='Логарифмическая аппроксимация',
        data=ApproxData(
            func_view=func_view,
            callback=callback,
            sko=calc_sko(def_data.eps),
            x_list=def_data.x_list,
            y_list=def_data.y_list,
            phi_x=def_data.phi_x,
            eps=def_data.eps,
            det_kf=calc_det_kf(def_data)
        ),
        error_message=None
    )


def approx_step(points: PointTable) -> ApproxRes:
    if not points.log_x_is_safe() or not points.log_y_is_safe():
        return ApproxRes(
            type='Степпеная аппроксимация',
            data=None,
            error_message="Can't approximate with negative abscisses or negative ordinates"
        )

    points_copy: PointTable = points.copy()
    for i in range(points.n):
        points_copy[i].x = math.log(points_copy[i].x)
        points_copy[i].y = math.log(points_copy[i].y)

    b, A = calc_linear_kfs(points_copy)
    a = math.exp(A)

    callback: callable = lambda x: a * (x ** b)
    func_view: str = f'{a:.3g}x^({b:.3g})'
    def_data: DataTable = get_def_data(points, callback)

    return ApproxRes(
        type='Степпеная аппроксимация',
        data=ApproxData(
            func_view=func_view,
            callback=callback,
            sko=calc_sko(def_data.eps),
            x_list=def_data.x_list,
            y_list=def_data.y_list,
            phi_x=def_data.phi_x,
            eps=def_data.eps,
            det_kf=calc_det_kf(def_data)
        ),
        error_message=None
    )


APPROXIMATORS: list[callable] = [
    approx_linear,
    approx_quad,
    approx_cube,
    approx_exp,
    approx_log,
    approx_step
]
