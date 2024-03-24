from typing import Callable

ACCURACY = 1000


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
