import math

from Equations import calculate_fk, derivative1, derivative2


def __fixed_hordes(first, second, equ, eps):
    prev_x = second
    fx = calculate_fk(equ, prev_x)
    fab = calculate_fk(equ, first)
    counter = 0
    while True:
        x = prev_x - (first - prev_x) / (fab - fx) * fx
        counter += 1
        fx = calculate_fk(equ, x)
        if abs(x - prev_x) < eps:
            break
        prev_x = x
    return [counter, x]


def __unfixed_hordes(a, b, equ, eps):
    prev_x = math.inf
    counter = 0
    while True:
        fa = calculate_fk(equ, a)
        fb = calculate_fk(equ, b)
        x = a - (b - a) / (fb - fa) * fa
        counter += 1

        if abs(x - prev_x) < eps:
            break
        if a * x < 0:
            b = x
        elif b * x < 0:
            a = b
            b = x

        prev_x = x
    return [counter, x]


def hordes(a, b, equ, eps):
    fa = calculate_fk(equ, a)
    fb = calculate_fk(equ, b)
    x = a - (b - a) / (fb - fa) * fa
    der1 = derivative1(equ, x)
    der2 = derivative2(equ, x)
    if der1 * der2 < 0:
        return __fixed_hordes(a, b, equ, eps)
    elif der1 * der2 > 0:
        return __fixed_hordes(b, a, equ, eps)
    else:
        return __unfixed_hordes(a, b, equ, eps)
