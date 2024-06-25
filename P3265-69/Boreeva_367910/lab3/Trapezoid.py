import math
from Equations import calculate_fx


def trapezoid(a, b, e, equ, n):
    i_h, i_half_h = 0, math.inf
    n -= 1
    while abs(i_half_h - i_h) / 3 > e:  # правило Рунге
        n += 1
        h = (b - a) / n  # шаг
        x = a
        i_h = 0  # интеграл
        i_half_h = 0  # интеграл с половинным шагом
        x += h
        for i in range(1, n):
            i_h += round(calculate_fx(equ, x), 4)
            i_half_h += round((calculate_fx(equ, x + h / 2) + calculate_fx(equ, x - h / 2)), 4)
            x += h
        i_h = ((calculate_fx(equ, a) + calculate_fx(equ, b)) / 2 + i_h) * h
        i_half_h = ((calculate_fx(equ, a) + calculate_fx(equ, b)) + i_half_h) * h / 2
    return [round(i_h, 4), n]
