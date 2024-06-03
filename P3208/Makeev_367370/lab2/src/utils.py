from typing import Callable
import matplotlib.pyplot as plt
from dto import Point, MethodData, PhiData

ACCURACY = 1000
EDGE = 0.5


def find_func_max(func: Callable, a: float, b: float) -> float:
    delta: float = (b - a) / ACCURACY
    func_max: float = func(a)
    for i in range(ACCURACY):
        f: float = func(a + delta * i)
        if f > func_max:
            func_max = f
    return func_max


def find_func_min(func: Callable, a: float, b: float) -> float:
    delta: float = (b - a) / ACCURACY
    func_min: float = func(a)
    for i in range(ACCURACY):
        f: float = func(a + delta * i)
        if f < func_min:
            func_min = f
    return func_min


def find_func_max_abs(func: Callable, a: float, b: float) -> float:
    f_max: float = find_func_max(func, a, b)
    f_min: float = find_func_min(func, a, b)
    if abs(f_max) > abs(f_min):
        return f_max
    else:
        return -f_min


def check_func_abs_smaller_one(func: Callable, a: float, b: float) -> bool:
    return find_func_max_abs(func, a, b) < 1


def to_float(val: str) -> float:
    return float(val.replace(',', '.'))


def draw_graph(func: Callable, a: float, b: float, point: Point) -> None:
    left: float = a - EDGE
    right: float = b + EDGE
    delta: float = (right - left) / ACCURACY
    x = []
    y = []
    for i in range(ACCURACY):
        x.append(left + delta * i)
        try:
            ordinate = func(x[-1])
            y.append(ordinate)
        except (ValueError, TypeError, ZeroDivisionError):
            x.pop()

        plt.grid(True)
    plt.plot([point.x], [point.y], 'ro', linewidth=3)
    plt.xlabel('x')
    plt.ylabel('f(x)')
    plt.plot(x, y, linewidth=2)


def check_sys_conv(phi1_data: PhiData, phi2_data: PhiData, data: MethodData) -> bool:
    check1 = lambda x, y: abs(phi1_data.der_x(x, y)) + abs(phi1_data.der_y(x, y))
    check2 = lambda x, y: abs(phi2_data.der_x(x, y)) + abs(phi2_data.der_y(x, y))
    delta_hor: float = (data.b - data.a) / ACCURACY
    delta_ver: float = (data.b_y - data.a_y) / ACCURACY
    check1_max: float = check1(data.a, data.a_y)
    check2_max: float = check2(data.a, data.a_y)
    for i in range(ACCURACY):
        for j in range(ACCURACY):
            curr_x: float = data.a + delta_hor * i
            curr_y: float = data.a_y + delta_ver * j
            ch1: float = check1(curr_x, curr_y)
            ch2: float = check2(curr_x, curr_y)
            if ch1 > check1_max:
                check1_max = ch1
            if ch2 > check2_max:
                check2_max = ch2

    return max(check1_max, check2_max) < 1
