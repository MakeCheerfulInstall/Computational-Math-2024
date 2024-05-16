from functools import reduce
from math import factorial

from matplotlib import pyplot as plt


def calc_lagrange_polynomial(xs, ys):
    n = len(xs) - 1
    f = lambda x: sum([ys[i] *
                       reduce(lambda a, b: a * b,
                              [(x - xs[j]) / (xs[i] - xs[j])
                               for j in range(n + 1) if i != j])
                       for i in range(n + 1)])
    return f


def calc_newton_divided_difference_polynomial(xs, ys):
    div_difs = []
    div_difs.append(ys[:])
    n = len(xs) - 1
    for k in range(1, n + 1):
        new = []
        last = div_difs[-1][:]
        for i in range(n - k + 1):
            new.append((last[i + 1] - last[i]) / (xs[i + k] - xs[i]))
        div_difs.append(new[:])
    print("divided differences:")
    for row in div_difs:
        print(*map(lambda a: round(a, 5), row), sep='\t')
    print('-' * 30)
    f = lambda x: ys[0] + sum([
        div_difs[k][0] * reduce(lambda a, b: a * b,
                                [x - xs[j] for j in range(k)])
        for k in range(1, n + 1)])
    return f


def calc_newton_finite_difference_polynomial(xs, ys):
    fin_difs = []
    fin_difs.append(ys[:])
    n = len(xs) - 1
    for k in range(1, n + 1):
        last = fin_difs[-1][:]
        fin_difs.append(
            [last[i + 1] - last[i] for i in range(n - k + 1)])
    print("finite differences:")
    for row in fin_difs:
        print(*map(lambda a: round(a, 5), row), sep='\t')
    print('-' * 30)
    h = xs[1] - xs[0]
    f = lambda x: ys[0] + sum([
        reduce(lambda a, b: a * b,
               [(x - xs[0]) / h - j for j in range(k)])
        * fin_difs[k][0] / factorial(k)
        for k in range(1, n + 1)])
    return f


def calc_gauss_polynomial(xs, ys):
    n = len(xs) - 1
    alpha_ind = n // 2
    fin_difs = []
    fin_difs.append(ys[:])

    for k in range(1, n + 1):
        last = fin_difs[-1][:]
        fin_difs.append(
            [last[i + 1] - last[i] for i in range(n - k + 1)])

    h = xs[1] - xs[0]
    dts1 = [0, -1, 1, -2, 2, -3, 3, -4, 4]
    f1 = lambda x: ys[alpha_ind] + sum([
        reduce(lambda a, b: a * b,
               [(x - xs[alpha_ind]) / h + dts1[j] for j in range(k)])
        * fin_difs[k][len(fin_difs[k]) // 2] / factorial(k)
        for k in range(1, n + 1)])
    f2 = lambda x: ys[alpha_ind] + sum([
        reduce(lambda a, b: a * b,
               [(x - xs[alpha_ind]) / h - dts1[j] for j in range(k)])
        * fin_difs[k][len(fin_difs[k]) // 2 - (1 - len(fin_difs[k]) % 2)] / factorial(k)
        for k in range(1, n + 1)])
    return lambda x: f1(x) if x > xs[alpha_ind] else f2(x)


def calc_stirling_polynomial(xs, ys):
    n = len(xs) - 1
    alpha_ind = n // 2
    fin_difs = []
    fin_difs.append(ys[:])

    for k in range(1, n + 1):
        last = fin_difs[-1][:]
        fin_difs.append(
            [last[i + 1] - last[i] for i in range(n - k + 1)])

    h = xs[1] - xs[0]
    dts1 = [0, -1, 1, -2, 2, -3, 3, -4, 4]
    f1 = lambda x: ys[alpha_ind] + sum([
        reduce(lambda a, b: a * b,
               [(x - xs[alpha_ind]) / h + dts1[j] for j in range(k)])
        * fin_difs[k][len(fin_difs[k]) // 2] / factorial(k)
        for k in range(1, n + 1)])
    f2 = lambda x: ys[alpha_ind] + sum([
        reduce(lambda a, b: a * b,
               [(x - xs[alpha_ind]) / h - dts1[j] for j in range(k)])
        * fin_difs[k][len(fin_difs[k]) // 2 - (1 - len(fin_difs[k]) % 2)] / factorial(k)
        for k in range(1, n + 1)])
    return lambda x: (f1(x) + f2(x)) / 2


def calc_bessel_polynomial(xs, ys):
    n = len(xs) // 2
    alpha_ind = 0
    fin_difs = []
    fin_difs.append(ys[:])

    for k in range(1, n + 1):
        last = fin_difs[-1][:]
        fin_difs.append(
            [last[i + 1] - last[i] for i in range(n - k + 1)])

    h = xs[1] - xs[0]
    dts1 = [0, -1, 1, -2, 2, -3, 3, -4, 4, -5, 5]

    f = lambda x: (ys[alpha_ind] + ys[alpha_ind + 1]) / 2 + sum([
        reduce(lambda a, b: a * b,
               [(x - xs[alpha_ind]) / h + dts1[j] + dts1[j + 1] for j in range(k)])
        * fin_difs[k][k + len(fin_difs[k]) // 2] / factorial(2 * k) +
        ((x - xs[alpha_ind]) / h - 1 / 2) *
        reduce(lambda a, b: a * b,
               [(x - xs[alpha_ind]) / h + dts1[j] + dts1[j + 1] for j in range(k)])
        * fin_difs[k][k + len(fin_difs[k]) // 2] / factorial(2 * k + 1)
        for k in range(1, n + 1)])
    return f


def draw_plot(a, b, func, dx=0.01):
    xs, ys = [], []
    a -= dx
    b += dx
    x = a
    while x <= b:
        xs.append(x)
        ys.append(func(x))
        x += dx
    plt.plot(xs, ys, 'g')


def main(xs, ys, x):
    methods = [calc_lagrange_polynomial,
               calc_newton_divided_difference_polynomial,
               calc_newton_finite_difference_polynomial,
               calc_gauss_polynomial,
               calc_stirling_polynomial]
    for method in methods:
        if (method is calc_gauss_polynomial or method is calc_stirling_polynomial) \
                and len(xs) % 2 == 0:
            continue
        if method is calc_bessel_polynomial and len(xs) % 2 == 1:
            continue
        print(method.__name__)

        P = method(xs, ys)

        plt.title(method.__name__)

        draw_plot(xs[0], xs[-1], P)
        for i in range(len(xs)):
            plt.scatter(xs[i], ys[i], c='r')
        plt.xlabel("X")
        plt.ylabel("Y")
        plt.show()

        print(f'P({x}) = {P(x)}')
        print('-' * 60)


def read_number(s: str):
    while True:
        try:
            return float(input(s))
        except Exception:
            continue


if __name__ == '__main__':
    mode = read_number("Введите режим: ")
    while mode not in (1, 2, 3):
        mode = read_number("Введите режим: ")
    if mode == 1:
        xs = list(map(float, input('Введите иксы: ').split()))
        ys = list(map(float, input('Введите игреки: ').split()))
        x = float(input('Введите икс: '))
    elif mode == 2:
        with open('input.txt') as f:
            xs = list(map(float, f.readline().strip().split()))
            ys = list(map(float, f.readline().strip().split()))
            x = float(f.readline().strip())
    elif mode == 3:
        print('Функции: ')
        print('1. x ^ 2 - 3 * x')
        print('2. x')
        func_number = read_number("Выберите функцию")
        f = lambda x: x ** 2 - 3 * x if func_number == 1 else x ** 5
        n = int(input('Введите n: '))
        x0 = float(input('Введите первый x: '))
        xn = float(input('Введите последний x: '))
        h = (xn - x0) / (n - 1)
        xs = [x0 + h * i for i in range(n)]
        ys = list(map(f, xs))
        x = float(input('Введите икс: '))
    # main(xs, ys, x)
    xs = sorted(xs)
    if len(set(xs)) != len(xs):
        print('Иксы должны быть разными')
    else:
        main(xs, ys, x)
