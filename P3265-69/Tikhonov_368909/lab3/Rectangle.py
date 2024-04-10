import math

from Equations import calculate_fx


def rectangle(a, b, e, equ, mode, n):
    x = a
    h = (b - a) / n  # шаг
    if mode == 1:
        return [__rectangle_left(x, h, n, equ), n]
    elif mode == 2:
        return [__rectangle_right(x, h, n, equ, b), n]
    elif mode == 3:
        return __rectangle_avg(a, b, n, equ, e)


def __rectangle_left(x, h, n, equ):
    y = calculate_fx(equ, x) * h
    for i in range(1, n):
        x += h
        y += calculate_fx(equ, x) * h
    return round(y, 4)


def __rectangle_right(x, h, n, equ, b):
    y = 0
    for i in range(1, n):
        x += h
        y += calculate_fx(equ, x) * h
    y += calculate_fx(equ, b) * h
    return round(y, 4)


def __rectangle_avg(a, b, n, equ, e):
    i_h, i_half_h = 0, math.inf
    n -= 1

    while abs(i_half_h - i_h) / 3 > e:  # правило Рунге
        n += 1
        h = (b - a) / n  # шаг
        x = a
        i_h = 0  # интеграл
        i_half_h = 0  # интеграл с половинным шагом
        x += h / 2

        for i in range(1, n + 1):
            i_h += round(calculate_fx(equ, x) * h, 4)
            i_half_h += round((calculate_fx(equ, x + h / 4) + calculate_fx(equ, x - h / 4)) * (h / 2), 4)
            x += h
        print(abs(i_half_h - i_h) / 3)
    return [round(i_h, 4), n]
